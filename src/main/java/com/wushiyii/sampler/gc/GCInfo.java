package com.wushiyii.sampler.gc;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GCInfo {

    private String gcName;
    private long gcCount;
    private long gcTime;
    private long sampleTime;
}