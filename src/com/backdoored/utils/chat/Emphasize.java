package com.backdoored.utils.chat;

public class Emphasize extends Mutator
{
    public Emphasize() {
        super();
    }
    
    @Override
    public String getName() {
        return "Emphasize";
    }
    
    @Override
    public String mutate(String msg) {
        msg = msg.replaceAll(" ", "");
        final StringBuilder sb = new StringBuilder();
        for (final char c : msg.toCharArray()) {
            sb.append(Character.toUpperCase(c)).append(" ");
        }
        return sb.toString();
    }
}
