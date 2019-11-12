package com.backdoored.hacks.render;

import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class RainbowEnchant extends BaseHack
{
    public static RainbowEnchant INSTANCE;
    
    public RainbowEnchant() {
        super("Rainbow Enchant", CategoriesInit.RENDER, "Rainbow enchant");
        RainbowEnchant.INSTANCE = this;
    }
}
