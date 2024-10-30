package dev.stereo528.mainmenuchanger.client;

import eu.midnightdust.lib.config.MidnightConfig;
import dev.stereo528.mainmenuchanger.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainMenuChangerClient implements ClientModInitializer {

    public static final String MODID = "mainmenuchanger";
    public static final Logger LOGGER = LoggerFactory.getLogger("MainMenuChanger");

    @Override
    public void onInitializeClient() {
        MidnightConfig.init(MODID, ModConfig.class);
        LOGGER.info("MainMenuChanger Loaded!");
    }
}
