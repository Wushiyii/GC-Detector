package com.wushiyii;

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

        premain0(agentArgs, instrumentation);

    }

    private static void premain0(String agentArgs, Instrumentation instrumentation) {

        try {
            log.info("Staring GCDetectorAgent, args={}", agentArgs);

            timer.scheduleAtFixedRate(new GCSamplerTask(), 0, 5000);


            log.info("Start GCDetectorAgent success");
        } catch (Exception e) {
            log.error("Failed to start GCDetectorAgent, args={}", agentArgs, e);
        }
    }

}
