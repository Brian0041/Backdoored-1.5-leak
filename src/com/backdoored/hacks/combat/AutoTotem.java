package com.backdoored.hacks.combat;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.init.Items;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class AutoTotem extends BaseHack
{
    private Setting alwaysHolding;
    private Setting maxDamageToNotHold;
    private Setting hotbar;
    private Setting hotbarSlot;
    private boolean shouldEquip;
    
    public AutoTotem() {
        super("Auto Totem", "Works in guis", CategoriesInit.COMBAT);
        this.shouldEquip = true;
        this.alwaysHolding = new Setting("Always Holding", this, true);
        this.maxDamageToNotHold = new Setting("Min Health to Equip", this, 6, 0, 20);
        this.hotbar = new Setting("Refill Hotbar Slot", this, false);
        this.hotbarSlot = new Setting("Hotbar Slot", this, 9, 0, 9);
    }
    
    public void onUpdate() {
        if (this.getEnabled()) {
            if (AutoTotem.mc.field_71439_g.func_110143_aJ() <= this.maxDamageToNotHold.getValInt() && !this.alwaysHolding.getValBoolean()) {
                this.shouldEquip = true;
            }
            if (this.shouldEquip && AutoTotem.mc.field_71439_g.func_184592_cb().func_190926_b()) {
                final int index = this.findItemInWholeInv(Items.field_190929_cY);
                if (index != -1) {
                    AutoTotem.mc.field_71442_b.func_187098_a(0, index, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.field_71439_g);
                    AutoTotem.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.field_71439_g);
                }
            }
            if (this.alwaysHolding.getValBoolean()) {
                this.shouldEquip = true;
            }
            if (!this.alwaysHolding.getValBoolean()) {
                this.shouldEquip = false;
            }
            if (this.hotbar.getValBoolean()) {
                final ItemStack refillSlot = AutoTotem.mc.field_71439_g.field_71071_by.func_70301_a(this.hotbarSlot.getValInt());
                if (refillSlot.func_190926_b()) {
                    final int index2 = this.findItemInWholeInv(Items.field_190929_cY);
                    AutoTotem.mc.field_71442_b.func_187098_a(0, index2, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.field_71439_g);
                    AutoTotem.mc.field_71442_b.func_187098_a(0, this.hotbarSlot.getValInt(), 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.field_71439_g);
                }
            }
        }
    }
    
    private int findItemInWholeInv(final Item item) {
        for (int i = 9; i <= 44; ++i) {
            if (AutoTotem.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c().func_77973_b() == item) {
                return i;
            }
        }
        return -1;
    }
}
