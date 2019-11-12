package com.backdoored.commands;

import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import com.backdoored.utils.Utils;
import net.minecraft.entity.Entity;

public class Spectate extends CommandBase
{
    public Spectate() {
        super(new String[] { "spectate", "view", "watch", "possess" });
    }
    
    @Override
    public boolean execute(final String[] args) {
        try {
            if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("self")) {
                this.mc.func_175607_a((Entity)this.mc.field_71439_g);
                Utils.printMessage("Now viewing from own perspective", "green");
                return true;
            }
            for (final EntityPlayer player : this.mc.field_71441_e.field_73010_i) {
                if (player.getDisplayNameString().equalsIgnoreCase(args[0])) {
                    this.mc.func_175607_a((Entity)player);
                    Utils.printMessage("Now viewing from perspective of '" + player.getDisplayNameString() + "'", "green");
                    return true;
                }
            }
            Utils.printMessage("Couldnt find player '" + args[0] + "'");
        }
        catch (Exception e) {
            Utils.printMessage("Error: " + e.getMessage(), "red");
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public String getSyntax() {
        return "-spectate <playername/self>";
    }
}
