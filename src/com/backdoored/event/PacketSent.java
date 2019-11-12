package com.backdoored.event;

import net.minecraft.network.Packet;

public class PacketSent extends PacketEvent
{
    public PacketSent(final Packet<?> packet) {
        super(packet);
    }
}
