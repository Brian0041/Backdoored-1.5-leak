package com.backdoored.commands;

import java.util.Iterator;
import com.backdoored.utils.Utils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.inventory.GuiInventory;

public class ViewInventory extends CommandBase
{
    public ViewInventory() {
        super(new String[] { "viewinv", "inventory", "inventoryview" });
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length < 1) {
            this.mc.func_147108_a((GuiScreen)new GuiInventory((EntityPlayer)this.mc.field_71439_g));
            return true;
        }
        for (final EntityPlayer player : this.mc.field_71441_e.field_73010_i) {
            if (player.getDisplayNameString().equalsIgnoreCase(args[0])) {
                this.mc.func_147108_a((GuiScreen)new GuiInventory(player));
                return true;
            }
        }
        Utils.printMessage("Could not find player " + args[0]);
        return false;
    }
    
    @Override
    public String getSyntax() {
        return "-viewinv FitMC";
    }
}
