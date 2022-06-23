package io.github.stereo528.mainmenuchanger.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import io.github.stereo528.mainmenuchanger.config.ModConfig;

public class MainMenuChangerClient implements ClientModInitializer {
    public static ModConfig config;
    public static final Logger LOGGER = LoggerFactory.getLogger("MainMenuChanger");

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        LOGGER.info("MainMenuChanger Loaded!");
    }
}
