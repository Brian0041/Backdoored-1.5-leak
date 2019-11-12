package com.backdoored.mixin;

import com.backdoored.hacks.BaseHack;
import com.backdoored.Globals;
import com.backdoored.hacks.movement.Speed;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import com.backdoored.event.PlayerMove;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityPlayerSP.class })
public class MixinEntityPlayerSP extends MixinEntityLivingBase
{
    private Minecraft mc;
    
    public MixinEntityPlayerSP() {
        super();
        this.mc = Minecraft.func_71410_x();
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("HEAD") })
    private void preMotion(final CallbackInfo callbackInfo) {
        final PlayerMove.Pre event = new PlayerMove.Pre(this.mc.field_71439_g.field_70177_z, this.mc.field_71439_g.field_70125_A, this.mc.field_71439_g.field_70122_E);
        MinecraftForge.EVENT_BUS.post((Event)event);
        this.mc.field_71439_g.field_70177_z = event.yaw;
        this.mc.field_71439_g.field_70125_A = event.pitch;
        this.mc.field_71439_g.field_70122_E = event.onGround;
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("RETURN") })
    private void postMotion(final CallbackInfo callbackInfo) {
        final PlayerMove.Post event = new PlayerMove.Post(this.mc.field_71439_g.field_70177_z, this.mc.field_71439_g.field_70125_A, this.mc.field_71439_g.field_70122_E);
        MinecraftForge.EVENT_BUS.post((Event)event);
        this.mc.field_71439_g.field_70177_z = event.yaw;
        this.mc.field_71439_g.field_70125_A = event.pitch;
        this.mc.field_71439_g.field_70122_E = event.onGround;
    }
    
    @Override
    public void func_70664_aZ() {
        try {
            final double oldMotionX = ((EntityPlayerSP)this).field_70159_w;
            final double oldMotionZ = ((EntityPlayerSP)this).field_70179_y;
            super.func_70664_aZ();
            ((Speed)Globals.getHack(Speed.class)).onJump(oldMotionX, oldMotionZ, (EntityPlayerSP)this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
