package com.backdoored.hacks.player;

import com.backdoored.utils.Utils;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import com.backdoored.gui.CategoriesInit;
import javax.swing.Timer;
import com.backdoored.hacks.BaseHack;

public class Twerk extends BaseHack
{
    private boolean isSneaking;
    private Timer timer;
    private int i;
    private boolean b;
    
    public Twerk() {
        super("Twerk", CategoriesInit.PLAYER, "Twerk that ass");
        this.isSneaking = false;
        this.i = 0;
        this.b = false;
    }
    
    public void onUpdate() {
        if (this.i <= 0 && this.getEnabled()) {
            if (this.b) {
                Twerk.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Twerk.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                Twerk.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Twerk.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.b = !this.b;
            Utils.printMessage("Sent");
            this.i = 6;
        }
        --this.i;
    }
    
    public void onDisabled() {
        Twerk.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Twerk.mc.field_71439_g, Twerk.mc.field_71439_g.func_70093_af() ? CPacketEntityAction.Action.START_SNEAKING : CPacketEntityAction.Action.STOP_SNEAKING));
    }
}
