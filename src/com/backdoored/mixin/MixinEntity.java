package com.backdoored.mixin;

import org.spongepowered.asm.mixin.injection.Redirect;
import com.backdoored.event.GetMaxInPortalTime;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import com.backdoored.event.SetRotation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Entity.class })
public abstract class MixinEntity
{
    public MixinEntity() {
        super();
    }
    
    @Shadow
    public abstract int func_82145_z();
    
    @Inject(method = { "turn" }, at = { @At("HEAD") }, cancellable = true)
    private void turn(float yaw, float pitch, final CallbackInfo ci) {
        final SetRotation event = new SetRotation((Entity)this, yaw, pitch);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            ci.cancel();
        }
        yaw = event.yaw;
        pitch = event.pitch;
    }
    
    @Redirect(method = { "onEntityUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getMaxInPortalTime()I"))
    private int getModifiedMaxInPortalTime(final Entity entity) {
        final GetMaxInPortalTime event = new GetMaxInPortalTime(entity, this.func_82145_z());
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event.maxInPortalTime;
    }
}
