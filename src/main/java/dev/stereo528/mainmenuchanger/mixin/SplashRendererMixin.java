package dev.stereo528.mainmenuchanger.mixin;

import dev.stereo528.mainmenuchanger.config.ModConfig;
import net.minecraft.client.gui.components.SplashRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SplashRenderer.class)
public class SplashRendererMixin{

    public SplashRendererMixin() {
    }

    @ModifyVariable(method = "render", at = @At(value = "STORE"))
    public float setSplashScale(float f) {
        if (ModConfig.hideSplash) return 0f;
        return ModConfig.splashScaleMult * f;
    }

}
