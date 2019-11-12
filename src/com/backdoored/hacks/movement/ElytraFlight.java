package com.backdoored.hacks.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.MathHelper;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class ElytraFlight extends BaseHack
{
    private final Setting mode;
    private final Setting speed;
    private final Setting boostSpeed;
    private final Setting controlSpeed;
    
    public ElytraFlight() {
        super("ElytraFlight", CategoriesInit.MOVEMENT, "Rockets aren't needed");
        this.mode = new Setting("Mode", this, "booost", new String[] { "booost", "control", "flight" });
        this.boostSpeed = new Setting("Boost-Speed", this, 0.05, 0.01, 0.2);
        this.speed = new Setting("Flight-Speed", this, 0.05, 0.01, 0.2);
        this.controlSpeed = new Setting("Control-Speed", this, 0.9, 0.01, 4.0);
    }
    
    public void onUpdate() {
        if (!ElytraFlight.mc.field_71439_g.func_184613_cA() || !this.getEnabled()) {
            return;
        }
        if (this.mode.getValString().equals("booost")) {
            if (ElytraFlight.mc.field_71439_g.field_71075_bZ.field_75100_b) {
                ElytraFlight.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
            }
            if (ElytraFlight.mc.field_71439_g.func_70090_H()) {
                ElytraFlight.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
            }
            final float yaw = (float)Math.toRadians(ElytraFlight.mc.field_71439_g.field_70177_z);
            if (ElytraFlight.mc.field_71474_y.field_74351_w.func_151470_d()) {
                final EntityPlayerSP field_71439_g = ElytraFlight.mc.field_71439_g;
                field_71439_g.field_70159_w -= MathHelper.func_76126_a(yaw) * this.boostSpeed.getValDouble();
                final EntityPlayerSP field_71439_g2 = ElytraFlight.mc.field_71439_g;
                field_71439_g2.field_70179_y += MathHelper.func_76134_b(yaw) * this.boostSpeed.getValDouble();
            }
            else if (ElytraFlight.mc.field_71474_y.field_74368_y.func_151470_d()) {
                final EntityPlayerSP field_71439_g3 = ElytraFlight.mc.field_71439_g;
                field_71439_g3.field_70159_w += MathHelper.func_76126_a(yaw) * this.boostSpeed.getValDouble();
                final EntityPlayerSP field_71439_g4 = ElytraFlight.mc.field_71439_g;
                field_71439_g4.field_70179_y -= MathHelper.func_76134_b(yaw) * this.boostSpeed.getValDouble();
            }
        }
        if (this.mode.getValString().equals("flight")) {
            ElytraFlight.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
            ElytraFlight.mc.field_71439_g.field_70747_aH = (float)this.speed.getValDouble();
            if (ElytraFlight.mc.field_71474_y.field_74314_A.func_151470_d()) {
                final EntityPlayerSP field_71439_g5 = ElytraFlight.mc.field_71439_g;
                field_71439_g5.field_70181_x += this.speed.getValDouble();
            }
            if (ElytraFlight.mc.field_71474_y.field_74311_E.func_151470_d()) {
                final EntityPlayerSP field_71439_g6 = ElytraFlight.mc.field_71439_g;
                field_71439_g6.field_70181_x -= this.speed.getValDouble();
            }
        }
        if (this.mode.getValString().equals("control")) {
            ElytraFlight.mc.field_71439_g.field_70181_x = 0.0;
            ElytraFlight.mc.field_71439_g.field_70159_w = 0.0;
            ElytraFlight.mc.field_71439_g.field_70179_y = 0.0;
            final float yaw = (float)Math.toRadians(ElytraFlight.mc.field_71439_g.field_70177_z);
            final float pitch = (float)Math.toRadians(ElytraFlight.mc.field_71439_g.field_70125_A);
            if (ElytraFlight.mc.field_71474_y.field_74351_w.func_151470_d()) {
                ElytraFlight.mc.field_71439_g.field_70159_w = -(MathHelper.func_76126_a(yaw) * MathHelper.func_76134_b(pitch) * this.controlSpeed.getValDouble());
                ElytraFlight.mc.field_71439_g.field_70179_y = MathHelper.func_76134_b(yaw) * MathHelper.func_76134_b(pitch) * this.controlSpeed.getValDouble();
                ElytraFlight.mc.field_71439_g.field_70181_x = -(MathHelper.func_76126_a(pitch) * this.controlSpeed.getValDouble());
            }
            else if (ElytraFlight.mc.field_71474_y.field_74368_y.func_151470_d()) {
                ElytraFlight.mc.field_71439_g.field_70159_w = MathHelper.func_76126_a(yaw) * MathHelper.func_76134_b(pitch) * this.controlSpeed.getValDouble();
                ElytraFlight.mc.field_71439_g.field_70179_y = -(MathHelper.func_76134_b(yaw) * MathHelper.func_76134_b(pitch) * this.controlSpeed.getValDouble());
                ElytraFlight.mc.field_71439_g.field_70181_x = MathHelper.func_76126_a(pitch) * this.controlSpeed.getValDouble();
            }
            if (ElytraFlight.mc.field_71474_y.field_74370_x.func_151470_d()) {
                ElytraFlight.mc.field_71439_g.field_70179_y = MathHelper.func_76126_a(yaw) * MathHelper.func_76134_b(pitch) * this.controlSpeed.getValDouble();
                ElytraFlight.mc.field_71439_g.field_70159_w = MathHelper.func_76134_b(yaw) * MathHelper.func_76134_b(pitch) * this.controlSpeed.getValDouble();
            }
            else if (ElytraFlight.mc.field_71474_y.field_74366_z.func_151470_d()) {
                ElytraFlight.mc.field_71439_g.field_70179_y = -(MathHelper.func_76126_a(yaw) * this.controlSpeed.getValDouble());
                ElytraFlight.mc.field_71439_g.field_70159_w = -(MathHelper.func_76134_b(yaw) * this.controlSpeed.getValDouble());
            }
            if (ElytraFlight.mc.field_71474_y.field_74314_A.func_151470_d()) {
                ElytraFlight.mc.field_71439_g.field_70181_x = this.controlSpeed.getValDouble();
            }
            else if (ElytraFlight.mc.field_71474_y.field_74311_E.func_151470_d()) {
                ElytraFlight.mc.field_71439_g.field_70181_x = -this.controlSpeed.getValDouble();
            }
        }
    }
    
    public void onEnabled() {
        if (!this.mode.getValString().equals("flight")) {
            return;
        }
        ElytraFlight.mc.field_71439_g.field_71075_bZ.func_75092_a((float)this.speed.getValDouble());
        ElytraFlight.mc.func_152344_a(() -> {
            if (ElytraFlight.mc.field_71439_g != null && ElytraFlight.mc.field_71439_g.func_184613_cA() && this.getEnabled() && this.mode.getValString().equals("flight")) {
                ElytraFlight.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
            }
        });
    }
    
    public void onDisabled() {
        if (this.mode.getValString().equals("flight")) {
            ElytraFlight.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
            ElytraFlight.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }
    
    private /* synthetic */ void lambda$onEnabled$0() {
        if (ElytraFlight.mc.field_71439_g != null && ElytraFlight.mc.field_71439_g.func_184613_cA() && this.getEnabled() && this.mode.getValString().equals("flight")) {
            ElytraFlight.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }
}
