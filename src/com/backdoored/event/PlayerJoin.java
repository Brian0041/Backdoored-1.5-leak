package com.backdoored.event;

import com.mojang.authlib.GameProfile;

public class PlayerJoin extends PlayerConnection
{
    public PlayerJoin(final GameProfile gameProfile) {
        super(gameProfile);
    }
}
