package io.github.stereo528.mainmenuchanger.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "mainmenuchanger")
public class ModConfig implements ConfigData {

    public boolean smallerSplash = false;

    public boolean changeCopyrightToC = false;

    @Comment("Changes the version text to <version> <modloader>.")
    @ConfigEntry.Gui.Tooltip
    public boolean shorterVersionText = false;

    public boolean modCount = false;

    public boolean disableRealmsButtonAndNotifs = false;

    @Comment("Removes Language and Accessibility buttons from the title screen.")
    @ConfigEntry.Gui.Tooltip
    public boolean disableSideButtons = false;
}