package com.backdoored.hacks.render;

import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class NoFog extends BaseHack
{
    public static NoFog INSTANCE;
    
    public NoFog() {
        super("No Fog", CategoriesInit.RENDER, "Remove fog");
        NoFog.INSTANCE = this;
    }
}
