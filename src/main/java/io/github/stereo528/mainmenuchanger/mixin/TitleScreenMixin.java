package io.github.stereo528.mainmenuchanger.mixin;

import io.github.stereo528.mainmenuchanger.Config;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;


@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen{
    @Shadow private String splash;

    @Shadow @Final private boolean fading;
    @Shadow private long fadeInStart;
    @Shadow @Final @Mutable public static Component COPYRIGHT_TEXT;

    protected TitleScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At("HEAD"))
    protected void changeCopyright(CallbackInfo info) {
        if (Config.CHANGE_COPYRIGHT) {
            COPYRIGHT_TEXT = Component.literal("© Mojang AB");
        } else {
            COPYRIGHT_TEXT = Component.literal("Copyright Mojang AB. Do not distribute!");
        }
    }

    @Inject(method = "init", at = @At("HEAD"))
    protected void init(CallbackInfo info) {
    }

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V"))
    public void scale(Args args) {
        float o = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * 6.2831855F) * 0.1F);
        o = o * 100.0F / (float) (this.font.width(this.splash) + 32);
        if (Config.SMALLER_SPLASH) {
            args.set(0, o / 2);
            args.set(1, o / 2);
            args.set(2, o / 2);
        } else {
            args.set(0, o);
            args.set(1, o);
            args.set(2, o);
        }
    }
}
