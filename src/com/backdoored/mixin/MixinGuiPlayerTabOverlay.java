package com.backdoored.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { GuiPlayerTabOverlay.class }, priority = 999999999)
public class MixinGuiPlayerTabOverlay
{
    public MixinGuiPlayerTabOverlay() {
        super();
    }
    
    @Redirect(method = { "renderPlayerlist" }, at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 0))
    public int noMin(final int listSize, final int maxTabSize) {
        return listSize;
    }
    
    @ModifyConstant(method = { "renderPlayerlist" }, constant = { @Constant(intValue = 20, ordinal = 0) })
    public int getNumRows(final int rows) {
        return 30;
    }
    
    @Redirect(method = { "renderPlayerlist" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isIntegratedServerRunning()Z"))
    public boolean renderPlayerIcons(final Minecraft mc) {
        return true;
    }
}
