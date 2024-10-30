package dev.stereo528.mainmenuchanger.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {

    @Entry(category = "text") public static boolean smallerSplash = false;


    public enum TextTypeEnum {
        VANILLA, SHORT, CUSTOM
    }

}