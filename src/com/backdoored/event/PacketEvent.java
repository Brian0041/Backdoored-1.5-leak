package com.backdoored.event;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class PacketEvent extends BackdooredEvent
{
    public Packet packet;
    
    public PacketEvent(final Packet<?> packet) {
        super();
        this.packet = packet;
    }
    
    public boolean isCancelable() {
        return true;
    }
}
