package com.backdoored.hacks.client;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import com.backdoored.event.GetShulkerContents;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class ShulkerFaker extends BaseHack
{
    public ShulkerFaker() {
        super("Shulker Faker", CategoriesInit.CLIENT, "Reeee");
    }
    
    @SubscribeEvent
    public void onGetShulkerContents(final GetShulkerContents event) {
        if (this.getEnabled()) {
            int j = 0;
            for (final ItemStack ignored : event.items) {
                event.items.set(j, (Object)new ItemStack(Blocks.field_150357_h));
                ++j;
            }
        }
    }
}
