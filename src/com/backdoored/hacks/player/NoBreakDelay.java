package com.backdoored.hacks.player;

import com.backdoored.utils.Utils;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class NoBreakDelay extends BaseHack
{
    public NoBreakDelay() {
        super("No Break Delay", "like fast place but for breaking", CategoriesInit.PLAYER);
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)PlayerControllerMP.class, (Object)NoBreakDelay.mc.field_71442_b, (Object)0, new String[] { "blockHitDelay", "field_78781_i" });
        }
        catch (Exception e) {
            this.setEnabled(false);
            Utils.printMessage("Disabled fastplace due to error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
