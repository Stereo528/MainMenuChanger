package dev.stereo528.mainmenuchanger.mixin;

import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.realmsclient.RealmsMainScreen;
import dev.stereo528.mainmenuchanger.config.ModConfig;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.options.LanguageSelectScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    @Shadow private SplashRenderer splash;

    protected TitleScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfo ci) {
        int heightPlacement = this.height / 4 + 48;
        int buttonSpacing = 24;

        if (!ModConfig.hideSplash) {
            if (this.splash == null) {
                this.splash = this.minecraft.getSplashManager().getSplash();
            }
        }

        SpriteIconButton langButton = this.addRenderableWidget(CommonButtons.language(20, (button) -> {
            this.minecraft.setScreen(new LanguageSelectScreen(this, this.minecraft.options, this.minecraft.getLanguageManager()));
        }, true));
        if (!ModConfig.langButton) langButton.visible = false;
        SpriteIconButton accessButton = this.addRenderableWidget(CommonButtons.accessibility(20, (button) -> {
            this.minecraft.setScreen(new AccessibilityOptionsScreen(this, this.minecraft.options));
        }, true));
        if (!ModConfig.accessButton) accessButton.visible = false;

        if(ModConfig.smallButtons) {
            createSmallButtons(heightPlacement, buttonSpacing);
            if (ModConfig.langButton) langButton.setPosition(this.width / 2 - 124, heightPlacement + 72 + 12);
            if (ModConfig.accessButton) accessButton.setPosition(this.width / 2 + 104, heightPlacement + 72 + 12);
        }

        else {
            createNormalMenuButtons(heightPlacement, buttonSpacing);

            int heightOffestRealms = 0;
            if (!ModConfig.realmsButton) {
                heightOffestRealms = 24;
            }

            if (ModConfig.langButton) langButton.setPosition(this.width / 2 - 124, heightPlacement + 108 - heightOffestRealms);
            if (ModConfig.accessButton) accessButton.setPosition(this.width / 2 + 104, heightPlacement + 108 - heightOffestRealms);

            this.addRenderableWidget(Button.builder(Component.translatable("menu.options"), (button) -> {
                this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
            }).bounds(this.width / 2 - 100, heightPlacement + 84 - heightOffestRealms, 98, 20).build());
            this.addRenderableWidget(Button.builder(Component.translatable("menu.quit"), (button) -> {
                this.minecraft.stop();
            }).bounds(this.width / 2 + 2, heightPlacement + 84 - heightOffestRealms, 98, 20).build());
        }

        ci.cancel();
    }

    private void createSmallButtons(int placement, int spacing) {

    }

    private void createNormalMenuButtons(int placement, int spacing) {
        Button singleplayer = this.addRenderableWidget(Button.builder(Component.translatable("menu.singleplayer"), (button) -> {
            this.minecraft.setScreen(new SelectWorldScreen(this));
        }).bounds(this.width / 2 - 100, placement, 200, 20).build());

        Component component = copyMultiplayerDisabledReasons();
        boolean bl = component == null;
        Tooltip tooltip = component != null ? Tooltip.create(component) : null;

        Button multiplayer = this.addRenderableWidget(Button.builder(Component.translatable("menu.multiplayer"), (button) -> {
            Screen screen = this.minecraft.options.skipMultiplayerWarning ? new JoinMultiplayerScreen(this) : new SafetyScreen(this);
            this.minecraft.setScreen((Screen)screen);
        }).bounds(this.width / 2 - 100, placement + spacing, 200, 20).tooltip(tooltip).build());
        multiplayer.active = bl;

        Button realms = this.addRenderableWidget(Button.builder(Component.translatable("menu.online"), (button) -> {
            this.minecraft.setScreen(new RealmsMainScreen(this));
        }).bounds(this.width / 2 - 100, placement + spacing * 2, 200, 20).tooltip(tooltip).build());
        realms.active = bl;

        if (!ModConfig.realmsButton) {
            realms.visible = false;
        }



    }


    @Nullable
    private Component copyMultiplayerDisabledReasons() {
        if (this.minecraft.allowsMultiplayer()) {
            return null;
        } else if (this.minecraft.isNameBanned()) {
            return Component.translatable("title.multiplayer.disabled.banned.name");
        } else {
            BanDetails banDetails = this.minecraft.multiplayerBan();
            if (banDetails != null) {
                return banDetails.expires() != null ? Component.translatable("title.multiplayer.disabled.banned.temporary") : Component.translatable("title.multiplayer.disabled.banned.permanent");
            } else {
                return Component.translatable("title.multiplayer.disabled");
            }
        }
    }

}
