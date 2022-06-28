package com.wushiyii.config;

import java.util.Map;
import lombok.Data;

@Data
public class GCSampleConfig {

    public static boolean ENABLE_GC_SAMPLE = true; //是否开启GC统计

    public static long COUNT_GC_MILLS_INTERVAL = 1000; //每次统计GC的间隔时间

    public static Integer COUNT_GC_MINUTES_RANGE = 1; //统计GC的时间范围

    public static Integer ALERT_SECOND_INTERVAL = 15; //报警通知的间隔时间

    public static long YGC_COUNT_THRESHOLD = 5; //ygc在时间范围内超过阈值，则报警

    public static int YGC_TIME_MILLS_THRESHOLD = 1000; //ygc在时间范围内GC时间超过阈值，则报警

    public static long OLD_GC_COUNT_THRESHOLD = 2; //fgc在时间范围内超过阈值，则报警

    public static long OLD_GC_TIME_MILLS_THRESHOLD = 1000; //fgc在时间范围内GC时间超过阈值，则报警



    public static void parseAgentArgs(Map<String, String> agentArgsMap) {
        if (agentArgsMap.containsKey("enableGCSample")) {
            ENABLE_GC_SAMPLE = Boolean.parseBoolean(agentArgsMap.get("enableGCSample"));
        }
        if (agentArgsMap.containsKey("countGcMillsInterval")) {
            COUNT_GC_MILLS_INTERVAL = Long.parseLong(agentArgsMap.get("countGcMillsInterval"));
        }
        if (agentArgsMap.containsKey("countGcMinutesRange")) {
            COUNT_GC_MINUTES_RANGE = Integer.valueOf(agentArgsMap.get("countGcMinutesRange"));
        }
        if (agentArgsMap.containsKey("alertSecondInterval")) {
            ALERT_SECOND_INTERVAL = Integer.valueOf(agentArgsMap.get("alertSecondInterval"));
        }
        if (agentArgsMap.containsKey("ygcCountThreshold")) {
            YGC_COUNT_THRESHOLD = Long.parseLong(agentArgsMap.get("ygcCountThreshold"));
        }
        if (agentArgsMap.containsKey("ygcTimeMillsThreshold")) {
            YGC_TIME_MILLS_THRESHOLD = Integer.parseInt(agentArgsMap.get("ygcTimeMillsThreshold"));
        }
        if (agentArgsMap.containsKey("oldGcCountThreshold")) {
            OLD_GC_COUNT_THRESHOLD = Long.parseLong(agentArgsMap.get("oldGcCountThreshold"));
        }
        if (agentArgsMap.containsKey("oldGcTimeMillsThreshold")) {
            OLD_GC_TIME_MILLS_THRESHOLD = Long.parseLong(agentArgsMap.get("oldGcTimeMillsThreshold"));
        }
    }


}
