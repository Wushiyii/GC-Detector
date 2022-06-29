package com.wushiyii.config;

import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GCSampleConfig extends BaseConfig{

    private static GCSampleConfig instance = new GCSampleConfig();

    private int countGcMinutesRange = 1; //统计GC的时间范围

    private long ygcCountThreshold = 5; //ygc数量在时间范围内超过阈值，则报警

    private long ygcTimeMillsThreshold; //ygc耗时在时间范围内GC时间超过阈值，则报警

    private long oldGcCountThreshold = 2; //fgc数量在时间范围内超过阈值，则报警

    private long oldGcTimeMillsThreshold; //fgc耗时在时间范围内GC时间超过阈值，则报警


    public static void parseAgentArgs(Map<String, String> agentArgsMap) {
        if (agentArgsMap.containsKey("enableGCSample")) {
            instance.setEnable(Boolean.parseBoolean(agentArgsMap.get("enableGCSample")));
        }
        if (agentArgsMap.containsKey("countGcMinutesRange")) {
            instance.setCountGcMinutesRange(Integer.parseInt(agentArgsMap.get("countGcMinutesRange")));
        }
        if (agentArgsMap.containsKey("ygcCountThreshold")) {
            instance.setYgcCountThreshold(Long.parseLong(agentArgsMap.get("ygcCountThreshold")));
        }
        if (agentArgsMap.containsKey("ygcTimeMillsThreshold")) {
            instance.setYgcTimeMillsThreshold(Long.parseLong(agentArgsMap.get("ygcTimeMillsThreshold")));
        }
        if (agentArgsMap.containsKey("oldGcCountThreshold")) {
            instance.setOldGcCountThreshold(Long.parseLong(agentArgsMap.get("oldGcCountThreshold")));
        }
        if (agentArgsMap.containsKey("oldGcTimeMillsThreshold")) {
            instance.setOldGcTimeMillsThreshold(Long.parseLong(agentArgsMap.get("oldGcTimeMillsThreshold")));
        }
    }

    public static GCSampleConfig getInstance() {
        return instance;
    }
}
