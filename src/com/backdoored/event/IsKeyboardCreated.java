package com.backdoored.event;

import net.minecraft.client.gui.GuiScreen;

public class IsKeyboardCreated extends BackdooredEvent
{
    public final GuiScreen screen;
    
    public IsKeyboardCreated(final GuiScreen screen) {
        super();
        this.screen = screen;
    }
}
