package com.backdoored.event;

import net.minecraft.util.text.ITextComponent;

public class BackdooredChatEvent extends BackdooredEvent
{
    public ITextComponent txt;
    
    public BackdooredChatEvent(final ITextComponent txt) {
        super();
        this.txt = txt;
    }
}
