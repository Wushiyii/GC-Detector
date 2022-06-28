package com.wushiyii.sampler.thread;

import java.lang.Thread.State;
import lombok.Data;

/**
 * copy from Arthas
 * Thread VO of 'dashboard' and 'thread' command
 *
 * @author gongdewei 2020/4/22
 */
@Data
public class ThreadVO {
    private long id;
    private String name;
    private String group;
    private int priority;
    private State state;
    private double cpu;
    private long deltaTime;
    private long time;
    private boolean interrupted;
    private boolean daemon;
}
