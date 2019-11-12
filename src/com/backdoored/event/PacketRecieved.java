package com.backdoored.event;

import net.minecraft.network.Packet;

public class PacketRecieved extends PacketEvent
{
    public PacketRecieved(final Packet<?> packet) {
        super(packet);
    }
}
