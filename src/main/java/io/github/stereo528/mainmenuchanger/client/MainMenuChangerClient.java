package io.github.stereo528.mainmenuchanger.client;

import io.github.stereo528.mainmenuchanger.Config;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MainMenuChangerClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("MainMenuChanger");
    @Override
    public void onInitializeClient() {
        LOGGER.info("MainMenuChanger Loaded!");
        try {
            Config.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
