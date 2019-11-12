package com.backdoored.hacks.combat;

import net.minecraft.entity.Entity;
import java.util.Iterator;
import com.backdoored.hacks.render.HoleEsp;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import com.backdoored.utils.WorldUtils;
import net.minecraft.init.Blocks;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class WebAura extends BaseHack
{
    private Setting range;
    private Setting onlyInHole;
    
    public WebAura() {
        super("Web Aura", CategoriesInit.COMBAT, "Trap people camping in holes");
        this.range = new Setting("Range", this, 4.0, 1.0, 10.0);
        this.onlyInHole = new Setting("Only in hole", this, true);
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        final int slot = WorldUtils.findBlock(Blocks.field_150321_G);
        if (slot == -1) {
            return;
        }
        final List<EntityPlayer> players = (List<EntityPlayer>)WebAura.mc.field_71441_e.field_73010_i.stream().filter(p -> WebAura.mc.field_71439_g.func_70032_d(p) <= this.range.getValDouble() && !WebAura.mc.field_71439_g.equals((Object)p)).collect(Collectors.toList());
        if (players.size() > 0) {
            WebAura.mc.field_71439_g.field_71071_by.field_70461_c = slot;
        }
        for (final EntityPlayer player : players) {
            final BlockPos playerPos = new BlockPos((int)player.field_70165_t, (int)player.field_70163_u, (int)player.field_70161_v);
            if (this.onlyInHole.getValBoolean() && !HoleEsp.isHole(playerPos)) {
                continue;
            }
            if (WebAura.mc.field_71441_e.func_180495_p(playerPos).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(playerPos);
            }
            if (this.onlyInHole.getValBoolean()) {
                continue;
            }
            if (WebAura.mc.field_71441_e.func_180495_p(playerPos.func_177982_a(1, 0, 0)).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(playerPos.func_177982_a(1, 0, 0));
            }
            if (WebAura.mc.field_71441_e.func_180495_p(playerPos.func_177982_a(0, 0, 1)).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(playerPos.func_177982_a(0, 0, 1));
            }
            if (WebAura.mc.field_71441_e.func_180495_p(playerPos.func_177982_a(0, 0, -1)).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(playerPos.func_177982_a(0, 0, -1));
            }
            if (!WebAura.mc.field_71441_e.func_180495_p(playerPos.func_177982_a(-1, 0, 0)).func_185904_a().func_76222_j()) {
                continue;
            }
            WorldUtils.placeBlockMainHand(playerPos.func_177982_a(-1, 0, 0));
        }
    }
    
    private /* synthetic */ boolean lambda$onUpdate$0(final EntityPlayer p) {
        return WebAura.mc.field_71439_g.func_70032_d((Entity)p) <= this.range.getValDouble() && !WebAura.mc.field_71439_g.equals((Object)p);
    }
}
