package com.backdoored.hacks.render;

import net.minecraft.util.math.AxisAlignedBB;
import java.util.Iterator;
import com.backdoored.utils.FriendUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import com.backdoored.utils.RenderUtils;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class HitboxEsp extends BaseHack
{
    private Setting boxOthers;
    private Setting boxFriends;
    
    public HitboxEsp() {
        super("Hitbox ESP", CategoriesInit.RENDER, "See outlines of players through walls", true, false);
        this.boxFriends = new Setting("Show friends hitbox", this, true);
        this.boxOthers = new Setting("Show others hitbox", this, false);
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        if (!this.getEnabled() || HitboxEsp.mc.field_71441_e.field_73010_i.size() <= 0) {
            return;
        }
        RenderUtils.glStart(0.0f, 0.0f, 0.0f, 1.0f);
        this.renderOthers();
        GL11.glColor4f(0.0f, 255.0f, 0.0f, 1.0f);
        this.renderFriends();
        RenderUtils.glEnd();
    }
    
    private void renderFriends() {
        if (this.boxFriends.getValBoolean()) {
            for (final EntityPlayer player : HitboxEsp.mc.field_71441_e.field_73010_i) {
                if (FriendUtils.isFriend(player) && !player.func_110124_au().equals(HitboxEsp.mc.field_71439_g.func_110124_au())) {
                    final AxisAlignedBB bb = player.func_174813_aQ();
                    RenderUtils.drawOutlinedBox(bb);
                }
            }
        }
    }
    
    private void renderOthers() {
        if (this.boxOthers.getValBoolean()) {
            for (final EntityPlayer player : HitboxEsp.mc.field_71441_e.field_73010_i) {
                if (!FriendUtils.isFriend(player) && !player.func_110124_au().equals(HitboxEsp.mc.field_71439_g.func_110124_au())) {
                    final AxisAlignedBB bb = player.func_174813_aQ();
                    RenderUtils.drawOutlinedBox(bb);
                }
            }
        }
    }
}
