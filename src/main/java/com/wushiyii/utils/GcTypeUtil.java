package com.wushiyii.utils;


public class GcTypeUtil {

    public static boolean isOld(String gcName) {
        return gcName.contains("MarkSweepCompact") || gcName.contains("PS MarkSweep") ||
            gcName.contains("ConcurrentMarkSweep") || gcName.contains("G1 Old Generation");
    }

}
