package com.backdoored.hacks.ui;

import java.util.Iterator;
import java.awt.Color;
import net.minecraft.entity.player.EntityPlayer;
import com.backdoored.extensions.Wrapper;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class Radar extends BaseHack
{
    private Setting xS;
    private Setting yS;
    private Setting textHeight;
    private Setting r;
    private Setting g;
    private Setting b;
    private Setting a;
    
    public Radar() {
        super("Radar", CategoriesInit.UI, "See nearby players");
        this.xS = new Setting("x", this, 0, 0, Radar.mc.field_71443_c + 50);
        this.yS = new Setting("y", this, 0, 0, Radar.mc.field_71443_c + 50);
        this.textHeight = new Setting("Text Height", this, 20, 1, 50);
        this.r = new Setting("r", this, 0, 0, 255);
        this.g = new Setting("g", this, 0, 0, 255);
        this.b = new Setting("b", this, 0, 0, 255);
        this.a = new Setting("a", this, 0, 0, 255);
    }
    
    public void onRender() {
        if (!this.getEnabled()) {
            return;
        }
        int offset = 0;
        final int fontHeightOriginal = Wrapper.fontRenderer.field_78288_b;
        Wrapper.fontRenderer.field_78288_b = this.textHeight.getValInt();
        for (final EntityPlayer player : Radar.mc.field_71441_e.field_73010_i) {
            if (!player.equals((Object)Radar.mc.field_71439_g)) {
                Wrapper.fontRenderer.func_78276_b(player.getDisplayNameString(), this.xS.getValInt(), this.yS.getValInt() + offset, new Color(this.r.getValInt(), this.g.getValInt(), this.b.getValInt(), this.a.getValInt()).getRGB());
                offset += Wrapper.fontRenderer.field_78288_b + 2;
            }
        }
        Wrapper.fontRenderer.field_78288_b = fontHeightOriginal;
    }
}
