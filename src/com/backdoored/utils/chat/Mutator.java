package com.backdoored.utils.chat;

public abstract class Mutator
{
    public Mutator() {
        super();
    }
    
    public abstract String getName();
    
    public abstract String mutate(final String p0);
}
