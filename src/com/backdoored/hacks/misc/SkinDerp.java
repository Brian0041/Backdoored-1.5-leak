package com.backdoored.hacks.misc;

import com.backdoored.setting.Setting;
import com.backdoored.gui.CategoriesInit;
import net.minecraft.entity.player.EnumPlayerModelParts;
import com.backdoored.hacks.BaseHack;

public class SkinDerp extends BaseHack
{
    private static final EnumPlayerModelParts[] PARTS;
    
    public SkinDerp() {
        super("Skin Derp", CategoriesInit.MISC, "Flickers your skin");
        new Setting("Speed", this, 2.0, 1.0, 5.0);
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        int i = (int)Math.round(SkinDerp.mc.field_71439_g.field_70173_aa / this.getSetting("Speed").getValDouble() % ((SkinDerp.PARTS.length - 1) * 2));
        boolean on = false;
        if (i >= SkinDerp.PARTS.length) {
            on = true;
            i -= SkinDerp.PARTS.length;
        }
        SkinDerp.mc.field_71474_y.func_178878_a(SkinDerp.PARTS[i], on);
    }
    
    public void onDisabled() {
        for (final EnumPlayerModelParts part : SkinDerp.PARTS) {
            SkinDerp.mc.field_71474_y.func_178878_a(part, true);
        }
    }
    
    static {
        PARTS = new EnumPlayerModelParts[] { EnumPlayerModelParts.LEFT_SLEEVE, EnumPlayerModelParts.JACKET, EnumPlayerModelParts.HAT, EnumPlayerModelParts.LEFT_PANTS_LEG, EnumPlayerModelParts.RIGHT_PANTS_LEG, EnumPlayerModelParts.RIGHT_SLEEVE };
    }
}
