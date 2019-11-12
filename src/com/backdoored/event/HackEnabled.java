package com.backdoored.event;

import com.backdoored.hacks.BaseHack;

public class HackEnabled extends BackdooredEvent
{
    public BaseHack hack;
    
    public HackEnabled(final BaseHack hack) {
        super();
        this.hack = hack;
    }
}
