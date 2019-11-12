package com.backdoored.hacks.movement;

import net.minecraft.util.MovementInput;
import com.backdoored.Globals;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.MathHelper;
import com.backdoored.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.event.PlayerMove;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class Speed extends BaseHack
{
    private Setting forwardS;
    private Setting preset;
    private Setting customY;
    private Setting groundBoost;
    private Setting inAirBoost;
    
    public Speed() {
        super("Speed", CategoriesInit.MOVEMENT, "Speeeeeeeeeeeeeeed");
        this.forwardS = new Setting("Only Forward", this, false);
        this.preset = new Setting("Preset(Options below for Custom)", this, "Strafe", new String[] { "Custom", "Strafe", "strafe other thing idk" });
        this.customY = new Setting("Jump height", this, 0.405, 0.0, 1.0);
        this.groundBoost = new Setting("Ground Boost", this, 0.2, 0.0, 2.0);
        this.inAirBoost = new Setting("In Air Boost", this, 1.0064, 0.0, 2.0);
    }
    
    @SubscribeEvent
    public void motion(final PlayerMove.Post event) {
        if (!this.getEnabled()) {
            return;
        }
        final String valString = this.preset.getValString();
        switch (valString) {
            case "Strafe": {
                this.run(0.405, 0.2f, 1.0064);
                break;
            }
            case "Custom": {
                this.run(this.customY.getValDouble(), (float)this.groundBoost.getValDouble(), this.inAirBoost.getValDouble());
                break;
            }
        }
    }
    
    private void run(final double y, final float ground, final double air) {
        final boolean forward = (!this.forwardS.getValBoolean() && Speed.mc.field_71439_g.field_191988_bg != 0.0f) || Speed.mc.field_71439_g.field_191988_bg > 0.0f;
        if (forward || Speed.mc.field_71439_g.field_70702_br != 0.0f) {
            Speed.mc.field_71439_g.func_70031_b(true);
            if (Speed.mc.field_71439_g.field_70122_E) {
                Speed.mc.field_71439_g.field_70181_x = y;
                final float f = Utils.getPlayerDirection();
                final EntityPlayerSP field_71439_g = Speed.mc.field_71439_g;
                field_71439_g.field_70159_w -= MathHelper.func_76126_a(f) * ground;
                final EntityPlayerSP field_71439_g2 = Speed.mc.field_71439_g;
                field_71439_g2.field_70179_y += MathHelper.func_76134_b(f) * ground;
            }
            else {
                final double currentSpeed = Math.sqrt(Speed.mc.field_71439_g.field_70159_w * Speed.mc.field_71439_g.field_70159_w + Speed.mc.field_71439_g.field_70179_y * Speed.mc.field_71439_g.field_70179_y);
                final double direction = Utils.getPlayerDirection();
                Speed.mc.field_71439_g.field_70159_w = -Math.sin(direction) * air * currentSpeed;
                Speed.mc.field_71439_g.field_70179_y = Math.cos(direction) * air * currentSpeed;
            }
        }
    }
    
    public void onJump(final double oldMotionX, final double oldMotionZ, final EntityPlayerSP playerSP) {
        if (this.getEnabled() && this.getSetting("Preset(Options below for Custom)").getValString().equals("strafe other thing idk")) {
            final MovementInput movementInput = Globals.mc.field_71439_g.field_71158_b;
            float forward = movementInput.field_192832_b;
            float strafe = movementInput.field_78902_a;
            float yaw = Globals.mc.field_71439_g.field_70177_z;
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0f;
                if (forward > 0.0) {
                    forward = 1.0f;
                }
                else if (forward < 0.0) {
                    forward = -1.0f;
                }
            }
            if (strafe > 0.0) {
                strafe = 1.0f;
            }
            else if (strafe < 0.0) {
                strafe = -1.0f;
            }
            playerSP.field_70159_w = oldMotionX + (forward * 0.2 * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * 0.2 * Math.sin(Math.toRadians(yaw + 90.0f)));
            playerSP.field_70179_y = oldMotionZ + (forward * 0.2 * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * 0.2 * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }
}
