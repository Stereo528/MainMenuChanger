package io.github.stereo528.mainmenuchanger.mixin;

import io.github.stereo528.mainmenuchanger.config.ModConfig;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(SplashRenderer.class)
public class SplashRendererMixin{

    @Shadow @Final private String splash;
    protected Font font;
    public SplashRendererMixin() {
    }

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V"))
    public void scale(Args args) {
        float f = 1.8F - Mth.abs(Mth.sin((float)(Util.getMillis() % 1000L) / 1000.0F * 6.2831855F) * 0.1F);
        f = f * 100.0F / (float)(font.width(this.splash) + 32);
        if (ModConfig.smallerSplash) {
            args.set(0, f / 2);
            args.set(1, f / 2);
            args.set(2, f / 2);
        } else {
            args.set(0, f);
            args.set(1, f);
            args.set(2, f);
        }
    }
}
