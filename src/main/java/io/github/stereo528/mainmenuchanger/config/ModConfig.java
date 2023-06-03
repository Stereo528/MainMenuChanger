package io.github.stereo528.mainmenuchanger.config;

import eu.midnightdust.lib.config.MidnightConfig;



public class ModConfig extends MidnightConfig {

    @Entry(category = "text") public static boolean smallerSplash = false;

    @Entry(category = "text") public static boolean changeCopyrightToC = false;

    @Entry(category = "text") public static boolean includeYearInCopyright = true;

    @Entry(category = "text") public static boolean modCount = false;

    @Entry(category = "text") public static boolean disableRealmsButtonAndNotifs = false;

    @Entry(category = "text") public static boolean disableSideButtons = false;

    @Entry(category = "text") public static boolean mergeMultiAndSingle = false;

    @Entry(category = "text") public static VersionTextEnum versionTextEnum = VersionTextEnum.VANILLA;
    public enum VersionTextEnum {
        VANILLA, SHORT, CUSTOM
    }
    @Entry(category = "text") public static String customVersionString = "{minecraft} - {loader}";
}