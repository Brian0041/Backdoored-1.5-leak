package com.backdoored.event;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.network.NetworkPlayerInfo;

public abstract class PlayerSkin extends BackdooredEvent
{
    public NetworkPlayerInfo networkPlayerInfo;
    
    public PlayerSkin(final NetworkPlayerInfo networkPlayerInfo) {
        super();
        this.networkPlayerInfo = networkPlayerInfo;
    }
    
    public static class HasSkin extends PlayerSkin
    {
        public boolean result;
        
        public HasSkin(final NetworkPlayerInfo networkPlayerInfo, final boolean result) {
            super(networkPlayerInfo);
            this.result = result;
        }
    }
    
    public static class GetSkin extends PlayerSkin
    {
        public ResourceLocation skinLocation;
        
        public GetSkin(final NetworkPlayerInfo networkPlayerInfo, final ResourceLocation skinLocation) {
            super(networkPlayerInfo);
            this.skinLocation = skinLocation;
        }
    }
}
