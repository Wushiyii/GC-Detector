package com.wushiyii.sampler.thread;

import com.wushiyii.utils.ThreadUtil;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class BusyThreadSampler {

    public static List<BusyThreadInfo> getTopNBusyThread(int n) {
        ThreadSampler threadSampler = new ThreadSampler();
        List<ThreadVO> threadStats = threadSampler.sample(ThreadUtil.getThreads());
        int limit = Math.min(threadStats.size(), n);

        List<ThreadVO> topNThreads;
        if (limit > 0) {
            topNThreads = threadStats.subList(0, limit);
        } else { // -1 for all threads
            topNThreads = threadStats;
        }

        List<Long> threadIdList = new ArrayList<>(topNThreads.size());
        for (ThreadVO thread : topNThreads) {
            if (thread.getId() > 0) {
                threadIdList.add(thread.getId());
            }
        }

        ThreadInfo[] threadInfoList = ManagementFactory.getThreadMXBean().getThreadInfo(toPrimitive(threadIdList.toArray(new Long[0])), false, false);
        if (threadIdList.size()> 0 && threadInfoList == null) {
            return Collections.emptyList();
        }

        Map<Long, ThreadInfo> threadInfoMap = Arrays.stream(threadInfoList).collect(Collectors.toMap(ThreadInfo::getThreadId, Function.identity(), (a, b) -> b));

        return topNThreads.stream()
            .filter(t -> threadInfoMap.containsKey(t.getId()))
            .map(t -> new BusyThreadInfo(t, threadInfoMap.get(t.getId())))
            .collect(Collectors.toList());
    }


    public static long[] toPrimitive(final Long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new long[0];
        }
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }
}
