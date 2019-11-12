package com.backdoored.event;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class GetShulkerContents extends BackdooredEvent
{
    public NonNullList<ItemStack> items;
    
    public GetShulkerContents(final NonNullList<ItemStack> items) {
        super();
        this.items = items;
    }
}
