package com.backdoored.event;

import com.mojang.authlib.GameProfile;

public class PlayerLeave extends PlayerConnection
{
    public PlayerLeave(final GameProfile gameProfile) {
        super(gameProfile);
    }
}
