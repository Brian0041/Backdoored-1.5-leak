package com.backdoored.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.backdoored.event.PlayerSkin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import com.backdoored.event.FindCapeEvent;
import net.minecraft.util.ResourceLocation;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { AbstractClientPlayer.class }, priority = 999999999)
public abstract class MixinAbstractClientPlayer extends MixinEntityPlayer
{
    public MixinAbstractClientPlayer() {
        super();
    }
    
    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo func_175155_b();
    
    @Overwrite
    @Nullable
    public ResourceLocation func_110303_q() {
        final NetworkPlayerInfo networkplayerinfo = this.func_175155_b();
        final FindCapeEvent event = new FindCapeEvent(networkplayerinfo);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.capeLoc != null) {
            return event.capeLoc;
        }
        return (networkplayerinfo == null) ? null : networkplayerinfo.func_178861_h();
    }
    
    @Inject(method = { "hasSkin" }, at = { @At("RETURN") }, cancellable = true)
    public void hasSkin(final CallbackInfoReturnable<Boolean> cir) {
        final PlayerSkin.HasSkin event = new PlayerSkin.HasSkin(this.func_175155_b(), (boolean)cir.getReturnValue());
        MinecraftForge.EVENT_BUS.post((Event)event);
        cir.setReturnValue((Object)event.result);
    }
    
    @Inject(method = { "getLocationSkin()Lnet/minecraft/util/ResourceLocation;" }, at = { @At("RETURN") }, cancellable = true)
    public void getSkin(final CallbackInfoReturnable<ResourceLocation> cir) {
        final PlayerSkin.GetSkin event = new PlayerSkin.GetSkin(this.func_175155_b(), (ResourceLocation)cir.getReturnValue());
        MinecraftForge.EVENT_BUS.post((Event)event);
        cir.setReturnValue((Object)event.skinLocation);
    }
}
