package io.github.stereo528.mainmenuchanger.mixin;

import io.github.stereo528.mainmenuchanger.client.MainMenuChangerClient;
import io.github.stereo528.mainmenuchanger.config.ModConfig;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.time.Year;
import java.util.List;
import java.util.Objects;


@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Shadow private String splash;
    @Shadow @Final @Mutable public static Component COPYRIGHT_TEXT;

    @Shadow @Final private static Logger LOGGER;

    protected TitleScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At("HEAD"))
    protected void changeCopyright(CallbackInfo info) {
        if (ModConfig.changeCopyrightToC) {
            COPYRIGHT_TEXT = Component.literal("© Mojang AB");
            if (ModConfig.includeYearInCopyright) {
                COPYRIGHT_TEXT = Component.literal("© Mojang AB (2009-" + Year.now().getValue() + ")");
            }
        } else if (ModConfig.includeYearInCopyright && !ModConfig.changeCopyrightToC) {
            COPYRIGHT_TEXT = Component.literal("Copyright Mojang AB (2009-" + Year.now().getValue() + "). Do not distribute!");
        } else {
            COPYRIGHT_TEXT = Component.literal("Copyright Mojang AB. Do not distribute!");
        }
    }

    @Inject(method = "init", at = @At("HEAD"))
    protected void noRealmsNotifs(CallbackInfo info) {
        if (ModConfig.disableRealmsButtonAndNotifs) {
            assert this.minecraft != null;
            this.minecraft.options.realmsNotifications().set(false);
        }
    }

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V"))
    public void scale(Args args) {
        float o = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * 6.2831855F) * 0.1F);
        o = o * 100.0F / (float) (this.font.width(this.splash) + 32);
        if (ModConfig.smallerSplash) {
            args.set(0, o / 2);
            args.set(1, o / 2);
            args.set(2, o / 2);
        } else {
            args.set(0, o);
            args.set(1, o);
            args.set(2, o);
        }
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;drawString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"), index = 2)
    public String drawString(String par3) {
        String version = SharedConstants.getCurrentVersion().getName();
        if (ModConfig.versionTextEnum == ModConfig.VersionTextEnum.SHORT) {
            if (this.minecraft.isDemo()) {
                version = version + "Demo";
            } else {
                version = version + " " + this.minecraft.getVersionType();
            }
            if (ModConfig.modCount) {
                version = I18n.get("text.mainmenuchanger.modcount", version, FabricLoader.getInstance().getAllMods().size());
            }


        } else if (ModConfig.versionTextEnum == ModConfig.VersionTextEnum.CUSTOM) {
            String customVersion = ModConfig.customVersionString;
            assert this.minecraft != null;
            String versionTemp = customVersion.replace("{minecraft}", SharedConstants.getCurrentVersion().getName());
            if(FabricLoader.getInstance().isModLoaded("quilt_loader")) {
                version = versionTemp.replace("{loader}", this.minecraft.getVersionType());
            } else {
                version = versionTemp.replace("{loader}", this.minecraft.getVersionType() + " " + FabricLoader.getInstance().getModContainer("fabricloader").get().getMetadata().getVersion().getFriendlyString());
            }
        } else if(ModConfig.versionTextEnum == ModConfig.VersionTextEnum.VANILLA) {
            version = "Minecraft " + version;
            if (this.minecraft.isDemo()) {
                version = version + " Demo";
            } else {
                version = version + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType());
            }
            if (Minecraft.checkModStatus().shouldReportAsModified()) {
                if (ModConfig.modCount) {
                    version = I18n.get("text.mainmenuchanger.modcount", version, FabricLoader.getInstance().getAllMods().size());
                } else {
                    version = version + I18n.get("menu.modded", new Object[0]);
                }
            }
        }
        return version;
    }

    @Inject(method = "init", at = @At("TAIL"))
    protected void removeButtons(CallbackInfo info) {
        final int space = 24;
        List<AbstractWidget> widgetList = Screens.getButtons((Screen) (Object) this);
        for (AbstractWidget button : widgetList) {
            if (ModConfig.disableRealmsButtonAndNotifs) {
                if (Objects.equals(button.getMessage(), Component.translatable("menu.online"))) {
                    button.visible = false;
                }

                //bring buttons up so there isnt a weird gap, part 2
                if (Objects.equals(button.getMessage(), Component.translatable("menu.options"))) {
                    button.setY(button.getY() - space);
                }
                if (Objects.equals(button.getMessage(), Component.translatable("menu.quit"))) {
                    button.setY(button.getY() - space);
                }
                if (Objects.equals(button.getMessage(), Component.translatable("narrator.button.language"))) {
                    button.setY(button.getY() - space);
                }
                if (Objects.equals(button.getMessage(), Component.translatable("narrator.button.accessibility"))) {
                    button.setY(button.getY() - space);
                }
            }
            if (ModConfig.disableSideButtons) {
                if (Objects.equals(button.getMessage(), Component.translatable("narrator.button.language"))) {
                    button.visible = false;
                }
                if (Objects.equals(button.getMessage(), Component.translatable("narrator.button.accessibility"))) {
                    button.visible = false;
                }
            }

        }
    }
}
