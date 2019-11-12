package com.backdoored.hacks.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class PullDown extends BaseHack
{
    private Setting speed;
    private boolean isJumping;
    
    public PullDown() {
        super("Pull Down", CategoriesInit.MOVEMENT, "Fast fall");
        this.isJumping = false;
        this.speed = new Setting("Speed", this, 10.0, 0.0, 20.0);
    }
    
    public void onUpdate() {
        if (this.isJumping && PullDown.mc.field_71439_g.field_70122_E) {
            this.isJumping = false;
        }
        if (!this.getEnabled() || PullDown.mc.field_71439_g.func_184613_cA() || PullDown.mc.field_71439_g.field_71075_bZ.field_75100_b) {
            return;
        }
        final boolean isBlockBelow = !PullDown.mc.field_71441_e.func_175623_d(PullDown.mc.field_71439_g.func_180425_c().func_177982_a(0, -1, 0)) || !PullDown.mc.field_71441_e.func_175623_d(PullDown.mc.field_71439_g.func_180425_c().func_177982_a(0, -2, 0));
        if (!PullDown.mc.field_71439_g.field_70122_E && !isBlockBelow) {
            PullDown.mc.field_71439_g.field_70181_x = -this.speed.getValInt();
        }
    }
    
    @SubscribeEvent
    public void onJump(final LivingEvent.LivingJumpEvent event) {
        if (event.getEntityLiving().equals((Object)PullDown.mc.field_71439_g)) {
            this.isJumping = true;
        }
    }
}
