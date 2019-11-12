package com.backdoored.event;

import com.backdoored.hacks.BaseHack;
import java.util.List;

public abstract class HackInitialisation extends BackdooredEvent
{
    public HackInitialisation() {
        super();
    }
    
    public static class Pre extends HackInitialisation
    {
        public Pre() {
            super();
        }
    }
    
    public static class Post extends HackInitialisation
    {
        public List<BaseHack> hacks;
        
        public Post(final List<BaseHack> hacks) {
            super();
            this.hacks = hacks;
        }
    }
}
