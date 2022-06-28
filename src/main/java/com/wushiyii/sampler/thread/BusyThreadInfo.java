package com.wushiyii.sampler.thread;

import java.lang.management.LockInfo;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * copy from Arthas
 * Busy thread info, include ThreadInfo fields
 *
 * @author gongdewei 2020/4/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusyThreadInfo extends ThreadVO {

    private long         blockedTime;
    private long         blockedCount;
    private long         waitedTime;
    private long         waitedCount;
    private LockInfo lockInfo;
    private String       lockName;
    private long         lockOwnerId;
    private String       lockOwnerName;
    private boolean      inNative;
    private boolean      suspended;
    private StackTraceElement[] stackTrace;
    private MonitorInfo[]       lockedMonitors;
    private LockInfo[]          lockedSynchronizers;


    public BusyThreadInfo(ThreadVO thread, ThreadInfo threadInfo) {
        this.setId(thread.getId());
        this.setName(thread.getName());
        this.setDaemon(thread.isDaemon());
        this.setInterrupted(thread.isInterrupted());
        this.setPriority(thread.getPriority());
        this.setGroup(thread.getGroup());
        this.setState(thread.getState());
        this.setCpu(thread.getCpu());
        this.setDeltaTime(thread.getDeltaTime());
        this.setTime(thread.getTime());

        //thread info
        if (threadInfo != null) {
            this.setLockInfo(threadInfo.getLockInfo());
            this.setLockedMonitors(threadInfo.getLockedMonitors());
            this.setLockedSynchronizers(threadInfo.getLockedSynchronizers());
            this.setLockName(threadInfo.getLockName());
            this.setLockOwnerId(threadInfo.getLockOwnerId());
            this.setLockOwnerName(threadInfo.getLockOwnerName());
            this.setStackTrace(threadInfo.getStackTrace());
            this.setBlockedCount(threadInfo.getBlockedCount());
            this.setBlockedTime(threadInfo.getBlockedTime());
            this.setInNative(threadInfo.isInNative());
            this.setSuspended(threadInfo.isSuspended());
            this.setWaitedCount(threadInfo.getWaitedCount());
            this.setWaitedTime(threadInfo.getWaitedTime());
        }

    }

}
