package com.wushiyii.sampler.gc;

import com.wushiyii.config.GCSampleConfig;
import com.wushiyii.sampler.thread.BusyThreadInfo;
import com.wushiyii.sampler.thread.BusyThreadSampler;
import com.wushiyii.utils.GcTypeUtil;
import com.wushiyii.utils.ThreadUtil;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GCSampleTask extends TimerTask {

    private final Map<String, LinkedBlockingDeque<GCInfo>> gcWindowMap = new HashMap<>();
    private final Map<String, Long> lastNoticeTimeMap = new HashMap<>();

    @Override
    public void run() {
        List<GarbageCollectorMXBean> mxBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean bean : mxBeans) {

            String gcName = bean.getName();
            long currentTime = System.currentTimeMillis();
            GCInfo currentGcInfo = new GCInfo(gcName, bean.getCollectionCount(), bean.getCollectionTime(), currentTime);
            if (log.isDebugEnabled()) {
                log.debug("[GCSamplerTask] name={}, count={}, time={}", gcName, bean.getCollectionCount(), bean.getCollectionTime());
            }


            if (!gcWindowMap.containsKey(gcName)) {

                LinkedBlockingDeque<GCInfo> linkedBlockingDeque = new LinkedBlockingDeque<>();
                linkedBlockingDeque.add(currentGcInfo);
                gcWindowMap.put(gcName, linkedBlockingDeque);
                continue;
            }

            LinkedBlockingDeque<GCInfo> linkedBlockingDeque = gcWindowMap.get(gcName);
            linkedBlockingDeque.add(currentGcInfo);


            long gcCountRange = TimeUnit.MINUTES.toMillis(GCSampleConfig.getInstance().getCountGcMinutesRange());
            long alertIntervalTime = GCSampleConfig.getInstance().getAlertIntervalInMills();

            //先移除统计时间间隔外的数据
            linkedBlockingDeque.removeIf(info -> currentTime - info.getSampleTime() > gcCountRange);

            //时间范围内若无数据则不统计
            if (linkedBlockingDeque.isEmpty()) {
                continue;
            }

            //统计gc次数、gc耗时
            GCInfo firstGcInfo = linkedBlockingDeque.getFirst();
            GCInfo lastGcInfo = linkedBlockingDeque.getLast();
            if (log.isDebugEnabled()) {
                log.debug("last gc time={}, count={}, name={}", new Date(lastGcInfo.getSampleTime()), lastGcInfo.getGcCount(), lastGcInfo.getGcName());
                log.debug("first gc time={} count={}, name={}", new Date(firstGcInfo.getSampleTime()), firstGcInfo.getGcCount(), firstGcInfo.getGcName());
            }

            boolean gcCountAlert = doGCCountSample(gcName, firstGcInfo, lastGcInfo);
            boolean gcTimeAlert = doGCTimeSample(gcName, firstGcInfo, lastGcInfo);


            if (gcCountAlert || gcTimeAlert) {

                Long lastNoticeTime = lastNoticeTimeMap.getOrDefault(gcName, 0L);
                if (currentTime - lastNoticeTime >= alertIntervalTime) {

                    lastNoticeTimeMap.put(gcName, currentTime);
                    List<BusyThreadInfo> busyThreadInfoList = BusyThreadSampler.getTopNBusyThread(5);
                    for (BusyThreadInfo info : busyThreadInfoList) {
                        String stacktrace = ThreadUtil.getFullStacktrace(info, -1, -1);
                        log.info("{}", stacktrace);
                    }
                }
            }
        }

    }

    private boolean doGCCountSample(String gcName, GCInfo firstGcInfo, GCInfo lastGcInfo) {

        long increasedGcCount = lastGcInfo.getGcCount() - firstGcInfo.getGcCount();
        long threshold = GcTypeUtil.isOld(gcName) ? GCSampleConfig.getInstance().getOldGcCountThreshold() : GCSampleConfig.getInstance().getYgcCountThreshold();
        if (threshold == 0) {
            return false;
        }
        if (increasedGcCount >= threshold) {
            log.error("[GCSamplerTask] GC-count over limit, gcCountThreshold={} gcName={} increasedGcCount={}", threshold, gcName, increasedGcCount);
            return true;
        }
        return false;
    }

    private boolean doGCTimeSample(String gcName, GCInfo firstGcInfo, GCInfo lastGcInfo) {

        long increasedGcTime = firstGcInfo.getGcTime() - lastGcInfo.getGcTime();
        long threshold = GcTypeUtil.isOld(gcName) ? GCSampleConfig.getInstance().getOldGcTimeMillsThreshold() : GCSampleConfig.getInstance().getYgcTimeMillsThreshold();
        if (threshold == 0) {
            return false;
        }
        if (increasedGcTime >= threshold) {
            log.error("[GCSamplerTask] GC-time over limit, gcTimeThreshold={} gcName={} increasedGcTime={}ms", threshold, gcName, increasedGcTime);
            return true;
        }
        return false;
    }
}
