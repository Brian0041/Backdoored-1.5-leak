package com.backdoored.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import com.backdoored.utils.ColourUtils;
import com.backdoored.hacks.render.RainbowEnchant;
import net.minecraft.client.renderer.RenderItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { RenderItem.class }, priority = 999999999)
public class MixinRenderItem
{
    public MixinRenderItem() {
        super();
    }
    
    @ModifyArg(method = { "renderEffect" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/RenderItem.renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;I)V"), index = 1)
    private int renderModel(final int oldColour) {
        if (RainbowEnchant.INSTANCE != null && RainbowEnchant.INSTANCE.getEnabled()) {
            return ColourUtils.rainbow().getRGB();
        }
        return oldColour;
    }
}
