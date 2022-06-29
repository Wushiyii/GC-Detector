package com.wushiyii.config;

import lombok.Data;

@Data
public class BaseConfig {

    /**
     * 是否开启，默认开启
     */
    protected boolean enable = true;

    /**
     * 进行取样的时间间隔，默认1000ms一次
     */
    protected long sampleIntervalInMills = 1000;

    /**
     * 进行通知的时间间隔，默认60000ms一次
     */
    protected long alertIntervalInMills = 60000;


}
