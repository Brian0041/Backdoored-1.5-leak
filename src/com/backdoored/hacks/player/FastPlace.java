package com.backdoored.hacks.player;

import com.backdoored.utils.Utils;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class FastPlace extends BaseHack
{
    Setting expOnly;
    
    public FastPlace() {
        super("FastPlace", CategoriesInit.PLAYER, "Place blocks or use items faster");
        this.expOnly = new Setting("Whitelist", this, "All", new String[] { "All", "Exp Only", "Crystal Only", "Exp and Crystal only" });
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        final Item item = FastPlace.mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b();
        final boolean isExp = item instanceof ItemExpBottle;
        final boolean isCrystal = item instanceof ItemEndCrystal;
        final String valString = this.expOnly.getValString();
        switch (valString) {
            case "All": {
                this.set();
                break;
            }
            case "Exp Only": {
                if (isExp) {
                    this.set();
                    break;
                }
                break;
            }
            case "Crystal Only": {
                if (isCrystal) {
                    this.set();
                    break;
                }
                break;
            }
            case "Exp and Crystal only": {
                if (isCrystal || isExp) {
                    this.set();
                    break;
                }
                break;
            }
        }
    }
    
    private void set() {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)Minecraft.class, (Object)FastPlace.mc, (Object)0, new String[] { "rightClickDelayTimer", "field_71467_ac" });
        }
        catch (Exception e) {
            e.printStackTrace();
            this.setEnabled(false);
            Utils.printMessage("Disabled fastplace due to error: " + e.toString());
        }
    }
}
