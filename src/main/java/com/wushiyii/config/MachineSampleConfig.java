package com.wushiyii.config;

import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MachineSampleConfig extends BaseConfig {

    private int cpuUsePercentThreshold = 90; // CPU使用率百分比在一段时间内超过阈值，则报警

    private int memoryPercentThreshold = 90; // 内存使用率百分比在一段时间内超过阈值，则报警

    private static MachineSampleConfig instance = new MachineSampleConfig();

    public static MachineSampleConfig getInstance() {
        return instance;
    }

    public static void parseAgentArgs(Map<String, String> agentArgsMap) {
        if (agentArgsMap.containsKey("cpuUsePercentThreshold")) {
            instance.setCpuUsePercentThreshold(Integer.parseInt(agentArgsMap.get("cpuUsePercentThreshold")));
        }
        if (agentArgsMap.containsKey("memoryPercentThreshold")) {
            instance.setMemoryPercentThreshold(Integer.parseInt(agentArgsMap.get("memoryPercentThreshold")));
        }
    }
}
