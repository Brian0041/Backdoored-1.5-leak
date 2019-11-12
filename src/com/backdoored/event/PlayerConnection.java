package com.backdoored.event;

import com.mojang.authlib.GameProfile;

public class PlayerConnection extends BackdooredEvent
{
    public GameProfile gameProfile;
    
    public PlayerConnection(final GameProfile gameProfile) {
        super();
        this.gameProfile = gameProfile;
    }
}
