package dev.stereo528.mainmenuchanger.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {

    @Entry(category = "main", min = 0.5f, max = 1.5f, isSlider = true, precision = 10) public static float splashScaleMult = 1f;
    @Entry(category = "main") public static boolean hideSplash = false;

    @Entry(category = "main") public static boolean langButton = true;
    @Entry(category = "main") public static boolean accessButton = true;
    @Entry(category = "main") public static boolean realmsButton = true;

    @Entry(category = "main") public static boolean smallButtons = false;

}