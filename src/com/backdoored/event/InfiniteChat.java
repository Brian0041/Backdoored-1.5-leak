package com.backdoored.event;

public class InfiniteChat extends BackdooredEvent
{
    public InfiniteChat() {
        super();
    }
    
    public boolean isCancelable() {
        return true;
    }
}
