package io.github.stereo528.mainmenuchanger.mixin;

import io.github.stereo528.mainmenuchanger.config.ModConfig;
import net.minecraft.client.gui.components.SplashRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SplashRenderer.class)
public class SplashRendererMixin{

    @Shadow @Final private String splash;
    public SplashRendererMixin() {
    }

    @ModifyVariable(method = "render", at = @At(value = "STORE"))
    public float setSplashScale(float f) {
        if (ModConfig.smallerSplash) return (float) (f/1.5);
        return f;
    }
}
