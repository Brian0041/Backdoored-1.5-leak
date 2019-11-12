package com.backdoored.mixin;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import com.backdoored.event.GetPortalCooldown;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.Shadow;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { EntityPlayer.class }, priority = 9900)
public abstract class MixinEntityPlayer
{
    public MixinEntityPlayer() {
        super();
    }
    
    @Shadow
    public abstract GameProfile func_146103_bH();
    
    @ModifyConstant(method = { "attackTargetEntityWithCurrentItem" }, constant = { @Constant(doubleValue = 0.6) })
    private double decelerate(final double original) {
        return 1.0;
    }
    
    @Redirect(method = { "attackTargetEntityWithCurrentItem" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;setSprinting(Z)V"))
    private void dontSprintPlsThx(final EntityPlayer player, final boolean sprinting) {
    }
    
    @ModifyConstant(method = { "getPortalCooldown" }, constant = { @Constant(intValue = 10) })
    private int getModifiedPortalCooldown(final int original) {
        final GetPortalCooldown event = new GetPortalCooldown(original, (EntityPlayer)this);
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event.cooldown;
    }
}
