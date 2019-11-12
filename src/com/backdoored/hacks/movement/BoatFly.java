package com.backdoored.hacks.movement;

import net.minecraft.util.math.MathHelper;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class BoatFly extends BaseHack
{
    public BoatFly() {
        super("BoatFly", CategoriesInit.MOVEMENT, "Experimental boatfly");
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        if (BoatFly.mc.field_71439_g.func_184218_aH()) {
            final boolean forward = BoatFly.mc.field_71474_y.field_74351_w.func_151470_d();
            final boolean left = BoatFly.mc.field_71474_y.field_74370_x.func_151470_d();
            final boolean right = BoatFly.mc.field_71474_y.field_74366_z.func_151470_d();
            final boolean back = BoatFly.mc.field_71474_y.field_74368_y.func_151470_d();
            int angle;
            if (left && right) {
                angle = (forward ? 0 : (back ? 180 : -1));
            }
            else if (forward && back) {
                angle = (left ? -90 : (right ? 90 : -1));
            }
            else {
                angle = (left ? -90 : (right ? 90 : 0));
                if (forward) {
                    angle /= 2;
                }
                else if (back) {
                    angle = 180 - angle / 2;
                }
            }
            if (angle != -1 && (forward || left || right || back)) {
                final float yaw = BoatFly.mc.field_71439_g.field_70177_z + angle;
                BoatFly.mc.field_71439_g.func_184187_bx().field_70159_w = this.getRelativeX(yaw) * 0.20000000298023224;
                BoatFly.mc.field_71439_g.func_184187_bx().field_70179_y = this.getRelativeZ(yaw) * 0.20000000298023224;
            }
            BoatFly.mc.field_71439_g.field_70181_x = 0.0;
            BoatFly.mc.field_71439_g.func_184187_bx().field_70181_x = 0.0;
            BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(BoatFly.mc.field_71439_g.func_184187_bx().field_70165_t + BoatFly.mc.field_71439_g.func_184187_bx().field_70159_w, BoatFly.mc.field_71439_g.func_184187_bx().field_70163_u, BoatFly.mc.field_71439_g.func_184187_bx().field_70161_v + BoatFly.mc.field_71439_g.func_184187_bx().field_70179_y, BoatFly.mc.field_71439_g.field_70177_z, BoatFly.mc.field_71439_g.field_70125_A, false));
            BoatFly.mc.field_71439_g.func_184187_bx().field_70181_x = 0.0;
            if (BoatFly.mc.field_71474_y.field_74314_A.func_151470_d()) {
                BoatFly.mc.field_71439_g.func_184187_bx().field_70181_x = 0.3;
            }
            if (BoatFly.mc.field_71474_y.field_151444_V.func_151470_d()) {
                BoatFly.mc.field_71439_g.func_184187_bx().field_70181_x = -0.3;
            }
            BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketVehicleMove());
            BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketSteerBoat(true, true));
            BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(BoatFly.mc.field_71439_g.func_184187_bx().field_70165_t + BoatFly.mc.field_71439_g.func_184187_bx().field_70159_w, BoatFly.mc.field_71439_g.func_184187_bx().field_70163_u - 42069.0, BoatFly.mc.field_71439_g.func_184187_bx().field_70161_v + BoatFly.mc.field_71439_g.func_184187_bx().field_70179_y, BoatFly.mc.field_71439_g.field_70177_z, BoatFly.mc.field_71439_g.field_70125_A, true));
            BoatFly.mc.field_71439_g.func_184187_bx().field_70163_u -= 42069.0;
            BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketVehicleMove());
            BoatFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketSteerBoat(true, true));
        }
    }
    
    private double[] moveLooking(final int ignored) {
        return new double[] { BoatFly.mc.field_71439_g.field_70177_z, ignored };
    }
    
    public double getRelativeX(final float yaw) {
        return MathHelper.func_76126_a(-yaw * 0.017453292f);
    }
    
    public double getRelativeZ(final float yaw) {
        return MathHelper.func_76134_b(yaw * 0.017453292f);
    }
}
