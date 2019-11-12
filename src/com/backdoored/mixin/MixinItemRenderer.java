package com.backdoored.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.backdoored.hacks.render.NoFog;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { ItemRenderer.class }, priority = 999999999)
public class MixinItemRenderer
{
    public MixinItemRenderer() {
        super();
    }
    
    @Inject(method = { "renderWaterOverlayTexture" }, at = { @At("HEAD") }, cancellable = true)
    private void renderWaterOverlayTexture(final float partialTicks, final CallbackInfo callbackInfo) {
        if (NoFog.INSTANCE != null && NoFog.INSTANCE.getEnabled()) {
            callbackInfo.cancel();
        }
    }
}
