package com.backdoored.event;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.network.NetworkPlayerInfo;

public class FindCapeEvent extends BackdooredEvent
{
    public NetworkPlayerInfo networkPlayerInfo;
    public ResourceLocation capeLoc;
    
    public FindCapeEvent(final NetworkPlayerInfo networkPlayerInfo) {
        super();
        this.networkPlayerInfo = networkPlayerInfo;
        this.capeLoc = null;
    }
}
