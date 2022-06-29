package com.wushiyii;

import com.wushiyii.config.GCSampleConfig;
import com.wushiyii.config.GlobalConfig;
import com.wushiyii.sampler.gc.GCSamplerTask;
import java.lang.instrument.Instrumentation;
import java.util.Timer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StackDetectorAgent {


    private static final Timer timer = new Timer("GC-Detector-timer", true);

    /**
     * 入口
     * @param agentArgs agent使用的参数
     * @param instrumentation instru包
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {

        premain0(agentArgs);

    }

    private static void premain0(String agentArgs) {

        try {
            log.info("Staring GCDetectorAgent, args={}", agentArgs);

            GlobalConfig.parseArgs(agentArgs);

            if (GCSampleConfig.getInstance().isEnable()) {
                timer.scheduleAtFixedRate(new GCSamplerTask(), 5000, GCSampleConfig.getInstance().getSampleIntervalInMills());
            }


            log.info("Start GCDetectorAgent success");
        } catch (Exception e) {
            log.error("Failed to start GCDetectorAgent, args={}", agentArgs, e);
        }
    }

}
