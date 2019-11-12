package com.backdoored.hacks.movement;

import net.minecraft.util.math.MathHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class PacketFly extends BaseHack
{
    public PacketFly() {
        super("Packet Fly", CategoriesInit.MOVEMENT, "Experimental");
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        final boolean forward = PacketFly.mc.field_71474_y.field_74351_w.func_151470_d();
        final boolean left = PacketFly.mc.field_71474_y.field_74370_x.func_151470_d();
        final boolean right = PacketFly.mc.field_71474_y.field_74366_z.func_151470_d();
        final boolean back = PacketFly.mc.field_71474_y.field_74368_y.func_151470_d();
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
            final float yaw = PacketFly.mc.field_71439_g.field_70177_z + angle;
            PacketFly.mc.field_71439_g.field_70159_w = this.getRelativeX(yaw) * 0.20000000298023224;
            PacketFly.mc.field_71439_g.field_70179_y = this.getRelativeZ(yaw) * 0.20000000298023224;
        }
        PacketFly.mc.field_71439_g.field_70181_x = 0.0;
        PacketFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(PacketFly.mc.field_71439_g.field_70165_t + PacketFly.mc.field_71439_g.field_70159_w, PacketFly.mc.field_71439_g.field_70163_u + (PacketFly.mc.field_71474_y.field_74314_A.func_151470_d() ? 0.0622 : 0.0) - (PacketFly.mc.field_71474_y.field_74311_E.func_151470_d() ? 0.0622 : 0.0), PacketFly.mc.field_71439_g.field_70161_v + PacketFly.mc.field_71439_g.field_70179_y, PacketFly.mc.field_71439_g.field_70177_z, PacketFly.mc.field_71439_g.field_70125_A, false));
        PacketFly.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(PacketFly.mc.field_71439_g.field_70165_t + PacketFly.mc.field_71439_g.field_70159_w, PacketFly.mc.field_71439_g.field_70163_u - 42069.0, PacketFly.mc.field_71439_g.field_70161_v + PacketFly.mc.field_71439_g.field_70179_y, PacketFly.mc.field_71439_g.field_70177_z, PacketFly.mc.field_71439_g.field_70125_A, true));
    }
    
    private double[] moveLooking(final int ignored) {
        return new double[] { PacketFly.mc.field_71439_g.field_70177_z, ignored };
    }
    
    public double getRelativeX(final float yaw) {
        return MathHelper.func_76126_a(-yaw * 0.017453292f);
    }
    
    public double getRelativeZ(final float yaw) {
        return MathHelper.func_76134_b(yaw * 0.017453292f);
    }
}
