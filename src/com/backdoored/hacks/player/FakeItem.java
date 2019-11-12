package com.backdoored.hacks.player;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.utils.Utils;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import com.backdoored.event.PacketSent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class FakeItem extends BaseHack
{
    private int i;
    private int fakeItem;
    
    public FakeItem() {
        super("Fake Item", CategoriesInit.PLAYER, "Always be holding your first item");
        this.fakeItem = 0;
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSent event) {
        if (event.packet instanceof CPacketHeldItemChange && this.getEnabled()) {
            final CPacketHeldItemChange packet = (CPacketHeldItemChange)event.packet;
            this.fakeItem = this.findAirSlot();
            try {
                ObfuscationReflectionHelper.setPrivateValue((Class)CPacketHeldItemChange.class, (Object)packet, (Object)this.fakeItem, new String[] { "slotId", "field_149615_a" });
            }
            catch (Exception e) {
                Utils.printMessage("Disabled fake item due to error: " + e.getMessage());
                this.setEnabled(false);
                e.printStackTrace();
            }
        }
    }
    
    public void onEnabled() {
        FakeItem.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(0));
    }
    
    public void onDisabled() {
        FakeItem.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(FakeItem.mc.field_71439_g.field_71071_by.field_70461_c));
    }
    
    private int findAirSlot() {
        for (int slot = 0; slot < 9; ++slot) {
            final ItemStack currentStack = (ItemStack)FakeItem.mc.field_71439_g.field_71071_by.field_70462_a.get(slot);
            if (currentStack.func_77973_b() == Items.field_190931_a) {
                return slot;
            }
        }
        return 0;
    }
}
