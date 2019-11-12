package com.backdoored.hacks.combat;

import java.util.Iterator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class ArmorAlert extends BaseHack
{
    private Setting maxDamage;
    private Setting x;
    private Setting y;
    
    public ArmorAlert() {
        super("Armor Durability Alert", "Get alerted when your armor is on low durability", CategoriesInit.COMBAT);
        this.maxDamage = new Setting("Max Damage", this, 15, 0, 100);
        this.x = new Setting("x", this, 50.0, 0.0, ArmorAlert.mc.field_71443_c * 1.2);
        this.y = new Setting("y", this, 50.0, 0.0, ArmorAlert.mc.field_71440_d * 1.2);
    }
    
    public void onRender() {
        if (this.getEnabled() && this.isLowDurability()) {
            final ScaledResolution sr = new ScaledResolution(ArmorAlert.mc);
            final String txt = "Low Armor Durability!";
            ArmorAlert.mc.field_71466_p.func_175063_a(txt, (float)(this.x.getValInt() - ArmorAlert.mc.field_71466_p.func_78256_a(txt) / 2), (float)(this.y.getValInt() - ArmorAlert.mc.field_71466_p.field_78288_b / 2), Color.RED.getRGB());
        }
    }
    
    private boolean isLowDurability() {
        for (final ItemStack itemStack : ArmorAlert.mc.field_71439_g.field_71071_by.field_70460_b) {
            if (itemStack != null) {
                final int id = Item.func_150891_b(itemStack.func_77973_b());
                if (id >= 298 && id <= 317 && itemStack.func_77958_k() - itemStack.func_77952_i() < this.maxDamage.getValInt()) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
}
