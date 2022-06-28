package com.wushiyii.config;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GlobalConfig {

    /**
     * 解析agent配置, 如 enableGCSample:true,countGcMillsInterval:500
     * @param agentArgs agent参数
     */
    public static void parseArgs(String agentArgs) {
        if (Objects.isNull(agentArgs) || "".equals(agentArgs)) {
            return;
        }

        Map<String, String> agentArgsMap = Arrays
            .stream(agentArgs.split(","))
            .collect(Collectors.toMap(arg -> arg.split(":")[0], arg -> arg.split(":")[1], (a, b) -> b));

        GCSampleConfig.parseAgentArgs(agentArgsMap);

    }


}
