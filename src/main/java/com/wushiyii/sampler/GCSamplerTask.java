package com.wushiyii.sampler;

import com.wushiyii.constants.DefaultAlertConstant;
import com.wushiyii.utils.GcTypeUtil;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GCSamplerTask extends TimerTask {

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

            //todo configurable
            if (true) {

                //todo configurable
                long gcIntervalTime = TimeUnit.MINUTES.toMillis(DefaultAlertConstant.GC_MINUTES_INTERVAL);
                long noticeIntervalTime = TimeUnit.MINUTES.toMillis(DefaultAlertConstant.NOTICE_MINUTES_INTERVAL);

                //先移除统计时间间隔外的数据
                linkedBlockingDeque.removeIf(info -> currentTime - info.getSampleTime() > gcIntervalTime);

                //时间范围内若无数据则不统计
                if (linkedBlockingDeque.isEmpty()) {
                    continue;
                }
                //统计gc次数
                GCInfo firstGcInfo = linkedBlockingDeque.getFirst();
                GCInfo lastGcInfo = linkedBlockingDeque.getLast();
                if (log.isDebugEnabled()) {
                    log.debug("last gc time={}, count={}, name={}", new Date(lastGcInfo.getSampleTime()), lastGcInfo.getGcCount(), lastGcInfo.getGcName());
                    log.debug("first gc time={} count={}, name={}", new Date(firstGcInfo.getSampleTime()), firstGcInfo.getGcCount(), firstGcInfo.getGcName());
                }

                long increasedGcCount = lastGcInfo.getGcCount() - firstGcInfo.getGcCount();
                long threshold = GcTypeUtil.isOld(gcName) ? DefaultAlertConstant.OLD_GC_TIMES_THRESHOLD : DefaultAlertConstant.YGC_TIMES_THRESHOLD;
                Long lastNoticeTime = lastNoticeTimeMap.getOrDefault(gcName, 0L);
                if (increasedGcCount >= threshold && (currentTime - lastNoticeTime >= noticeIntervalTime)) {
                    long increasedGcTime = lastGcInfo.getGcTime() - firstGcInfo.getGcTime();
                    lastNoticeTimeMap.put(gcName, currentTime);
                    log.error("[GCSamplerTask] GC-over limit, gcCountThreshold={} gcName={} increasedGcCount={} increasedGcTime={}ms", threshold, gcName, increasedGcCount, increasedGcTime);
                }
            }


        }

    }


    @Data
    @AllArgsConstructor
    public static class GCInfo {

        private String gcName;
        private long gcCount;
        private long gcTime;
        private long sampleTime;
    }

    public static void main(String[] args) {
        LinkedBlockingDeque<Integer> queue = new LinkedBlockingDeque<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(5);
        System.out.println(queue.getFirst());
        System.out.println(queue.getLast());
    }
}
