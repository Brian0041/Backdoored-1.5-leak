package com.backdoored.hacks.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.inventory.ContainerHorseChest;
import net.minecraft.entity.passive.AbstractHorse;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class DonkeyDrop extends BaseHack
{
    public DonkeyDrop() {
        super("Donkey Drop", "Drop all items in donkeys inv", CategoriesInit.MISC);
    }
    
    public void onEnabled() {
        final Entity ridingEntity = DonkeyDrop.mc.field_71439_g.func_184187_bx();
        if (ridingEntity != null && ridingEntity instanceof AbstractHorse) {
            final AbstractHorse horse = (AbstractHorse)ridingEntity;
            final ContainerHorseChest horseChest = (ContainerHorseChest)ObfuscationReflectionHelper.getPrivateValue((Class)AbstractHorse.class, (Object)horse, new String[] { "horseChest", "field_110296_bG" });
            final NonNullList<ItemStack> horseItems = (NonNullList<ItemStack>)ObfuscationReflectionHelper.getPrivateValue((Class)InventoryBasic.class, (Object)horseChest, new String[] { "inventoryContents", "field_70482_c" });
            System.out.println(horseItems.toString());
            for (int i = 0; i < DonkeyDrop.mc.field_71439_g.field_71071_by.func_70302_i_(); ++i) {
                System.out.println("Dropping item in slot: " + i);
                DonkeyDrop.mc.field_71442_b.func_187098_a(DonkeyDrop.mc.field_71439_g.field_71070_bA.field_75152_c, i, 0, ClickType.THROW, (EntityPlayer)DonkeyDrop.mc.field_71439_g);
            }
        }
        this.setEnabled(false);
    }
}
