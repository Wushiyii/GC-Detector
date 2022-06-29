package com.wushiyii.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MachineSampleConfig extends BaseConfig {


    private static MachineSampleConfig instance = new MachineSampleConfig();

    public static MachineSampleConfig getInstance() {
        return instance;
    }
}
