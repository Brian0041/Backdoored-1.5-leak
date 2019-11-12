package com.backdoored.config;

import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SetConfigs
{
    private static long lastSaved;
    
    public SetConfigs() {
        super();
    }
    
    public void setConfigs(final TickEvent.ClientTickEvent event) {
        if (System.currentTimeMillis() >= SetConfigs.lastSaved + 30000L) {
            SetConfigs.lastSaved = System.currentTimeMillis();
            ConfigJsonManager.save();
        }
    }
    
    static {
        SetConfigs.lastSaved = 0L;
    }
}
