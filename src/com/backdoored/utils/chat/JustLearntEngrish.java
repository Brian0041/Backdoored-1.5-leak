package com.backdoored.utils.chat;

public class JustLearntEngrish extends Mutator
{
    public JustLearntEngrish() {
        super();
    }
    
    @Override
    public String getName() {
        return "JustLearntEngrish";
    }
    
    @Override
    public String mutate(final String msg) {
        final StringBuilder sb = new StringBuilder();
        final String[] split;
        final String[] words = split = msg.split(" ");
        for (final String word : split) {
            sb.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        return sb.toString();
    }
}
