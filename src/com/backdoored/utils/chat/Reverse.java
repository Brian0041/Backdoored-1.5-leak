package com.backdoored.utils.chat;

public class Reverse extends Mutator
{
    public Reverse() {
        super();
    }
    
    @Override
    public String getName() {
        return "Reverse";
    }
    
    @Override
    public String mutate(final String msg) {
        return new StringBuilder(msg).reverse().toString();
    }
}
