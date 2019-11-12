package com.backdoored.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import com.backdoored.utils.ColourUtils;
import com.backdoored.hacks.render.RainbowEnchant;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { LayerArmorBase.class }, priority = 999999999)
public class MixinLayerArmorBase
{
    public MixinLayerArmorBase() {
        super();
    }
    
    @Redirect(method = { "renderEnchantedGlint" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.color(FFFF)V"))
    private static void renderEnchantedGlint(float red, float green, float blue, float alpha) {
        if (RainbowEnchant.INSTANCE != null && RainbowEnchant.INSTANCE.getEnabled()) {
            final Color rainbow = ColourUtils.rainbow();
            red = (float)rainbow.getRed();
            blue = (float)rainbow.getBlue();
            green = (float)rainbow.getGreen();
            alpha = (float)rainbow.getAlpha();
        }
        GlStateManager.func_179131_c(red, green, blue, alpha);
    }
}
