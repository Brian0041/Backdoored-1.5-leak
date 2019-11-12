package com.backdoored.hacks.player;

import net.minecraft.entity.player.InventoryPlayer;
import java.util.Iterator;
import java.util.List;
import net.minecraft.item.ItemStack;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class HotbarReplenish extends BaseHack
{
    private Setting cooldownSetting;
    private Setting minStackSize;
    private int cooldown;
    
    public HotbarReplenish() {
        super("Hotbar Replenish", "Replenish items in your hotbar when they are used", CategoriesInit.PLAYER);
        this.cooldown = 0;
        this.cooldownSetting = new Setting("Cooldown in ticks", this, 100, 0, 200);
        this.minStackSize = new Setting("Min Stack Size (percent)", this, 20, 1, 99);
    }
    
    public void onUpdate() {
        --this.cooldown;
        if (this.cooldown <= 0) {
            final List<ItemStack> playerHotbar = getHotbar();
            for (final ItemStack stack : playerHotbar) {
                if (stack == null || getStackSizePercent(stack) < this.minStackSize.getValInt()) {}
            }
            this.cooldown = this.cooldownSetting.getValInt();
        }
    }
    
    private static int getStackSizePercent(final ItemStack stack) {
        return (int)Math.ceil(stack.func_190916_E() * 100.0f / stack.func_77976_d());
    }
    
    private static List<ItemStack> getHotbar() {
        return (List<ItemStack>)HotbarReplenish.mc.field_71439_g.field_71071_by.field_70462_a.subList(0, InventoryPlayer.func_70451_h());
    }
}
