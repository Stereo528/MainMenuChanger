package io.github.stereo528.mainmenuchanger.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import io.github.stereo528.mainmenuchanger.config.ModConfig;


import java.util.List;
import java.util.Objects;

public class MainMenuChangerClient implements ClientModInitializer {
    public static ModConfig config;
    public static final Logger LOGGER = LoggerFactory.getLogger("MainMenuChanger");

    ResourceLocation latePhase = new ResourceLocation("mainmenuchanger", "late");


    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        LOGGER.info("MainMenuChanger Loaded!");

        ScreenEvents.AFTER_INIT.addPhaseOrdering(Event.DEFAULT_PHASE, latePhase);
        ScreenEvents.AFTER_INIT.register(latePhase, MainMenuChangerClient::mergeMultiplayerAndSingleplayer);
    }

    private static void mergeMultiplayerAndSingleplayer(Minecraft minecraft, Screen screen, int scaledWidth, int scaledHeight) {
        final int space = 24;
        if (screen instanceof TitleScreen titleScreen) {
            List<AbstractWidget> widgetList = Screens.getButtons((Screen) (Object) screen);
            for (AbstractWidget button : widgetList) {

                if (MainMenuChangerClient.config.mergeMultiAndSingle) {
                    //bring buttons up so there isn't a weird gap, but only if realms be gone and sp & mp buttons are merged
                    if(MainMenuChangerClient.config.disableRealmsButtonAndNotifs) {
                        if (Objects.equals(button.getMessage(), Component.translatable("menu.singleplayer"))) {
                            button.setWidth(98);
                            button.setY(button.getY() + space);
                        }
                        if (Objects.equals(button.getMessage(), Component.translatable("menu.multiplayer"))) {
                            button.setWidth(98);
                            button.setX(button.getX() + 102);
                        }
                        //do a funny if modmenu is installed & realms button is off
                        // TODO Make this it's own config option
                        if (Objects.equals(button.getMessage(), Component.translatable("modmenu.title"))) {
                            button.setWidth(64);
                            button.setX(button.getX() + 68);
                        }

                        if (Objects.equals(button.getMessage(), Component.translatable("menu.options"))) {
                            button.setY(button.getY() + (-space-12));
                            button.setWidth(64);
                        }
                        if (Objects.equals(button.getMessage(), Component.translatable("menu.quit"))) {
                            button.setY(button.getY() + (-space-12));
                            button.setWidth(64);
                            button.setX(button.getX() + 34);
                        }
                        if (Objects.equals(button.getMessage(), Component.translatable("narrator.button.language"))) {
                            button.setY(button.getY() + (-space-12));
                        }
                        if (Objects.equals(button.getMessage(), Component.translatable("narrator.button.accessibility"))) {
                            button.setY(button.getY() + (-space-12));
                        }
                    } else { // bring everything up by 1 button so there isnt a gap from the title to sp/mp buttons
                        if (Objects.equals(button.getMessage(), Component.translatable("menu.singleplayer"))) {
                            button.setWidth(98);
                        }
                        if (Objects.equals(button.getMessage(), Component.translatable("menu.multiplayer"))) {
                            button.setWidth(98);
                            button.setX(button.getX() + 102);
                            button.setY(button.getY() - space);
                        }
                        if (Objects.equals(button.getMessage(), Component.translatable("menu.online"))) {
                            button.setY(button.getY() - space);
                        }
                        if (Objects.equals(button.getMessage(), Component.translatable("menu.options"))) {
                            button.setY(button.getY() - space/2);
                        }
                        if (Objects.equals(button.getMessage(), Component.translatable("menu.quit"))) {
                            button.setY(button.getY() - space/2);
                        }
                        if (Objects.equals(button.getMessage(), Component.translatable("narrator.button.language"))) {
                            button.setY(button.getY() - space);
                        }
                        if (Objects.equals(button.getMessage(), Component.translatable("narrator.button.accessibility"))) {
                            button.setY(button.getY() - space);
                        }
                        if (Objects.equals(button.getMessage(), Component.translatable("modmenu.title"))) {
                            button.setY(button.getY() - space);
                        }
                    }
                }
            }
        }
    }
}
