package com.backdoored.hacks.misc;

import java.util.Iterator;
import com.backdoored.hacks.client.Notifications;
import com.backdoored.utils.Utils;
import java.util.Collection;
import com.backdoored.setting.Setting;
import java.util.ArrayList;
import com.backdoored.gui.CategoriesInit;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import com.backdoored.hacks.BaseHack;

public class VisualRange extends BaseHack
{
    private List<EntityPlayer> playerEntities;
    
    public VisualRange() {
        super("Visual Range", CategoriesInit.MISC, "Get notified when someone enters your render distance");
        this.playerEntities = new ArrayList<EntityPlayer>();
        new Setting("Color", this, "red", new String[] { "red", "blue", "green", "white" });
        new Setting("Mode", this, "private", new String[] { "private", "public" });
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        final List<EntityPlayer> list = new ArrayList<EntityPlayer>(VisualRange.mc.field_71441_e.field_73010_i);
        list.removeAll(this.playerEntities);
        for (final EntityPlayer player : list) {
            if (VisualRange.mc.field_71441_e.field_73010_i.contains(player)) {
                this.sendMessage("Player '" + player.getDisplayNameString() + "' entered your render distance at " + Utils.vectorToString(player.func_174791_d(), new boolean[0]), "gold");
                Notifications.INSTANCE.visualRangeTrigger(player);
            }
            else {
                if (!this.playerEntities.contains(player)) {
                    continue;
                }
                this.sendMessage("Player '" + player.getDisplayNameString() + "' left your render distance at " + Utils.vectorToString(player.func_174791_d(), new boolean[0]), "gold");
            }
        }
        this.playerEntities = (List<EntityPlayer>)VisualRange.mc.field_71441_e.field_73010_i;
    }
    
    private void sendMessage(final String msg, final String colour) {
        if (this.getSetting("Mode").getValString().equalsIgnoreCase("private")) {
            Utils.printMessage(msg, colour);
        }
        else {
            VisualRange.mc.field_71439_g.func_71165_d(msg);
        }
    }
}
