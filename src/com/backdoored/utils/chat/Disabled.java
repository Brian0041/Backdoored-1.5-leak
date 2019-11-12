package com.backdoored.utils.chat;

public class Disabled extends Mutator
{
    public Disabled() {
        super();
    }
    
    @Override
    public String getName() {
        return "Disabled";
    }
    
    @Override
    public String mutate(final String msg) {
        return "♿" + msg + "♿";
    }
}
