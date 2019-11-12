package com.backdoored.subguis;

import net.minecraft.client.gui.GuiScreen;
import javax.annotation.Nullable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.client.gui.GuiGameOver;

public class BackdooredGuiGameOver extends GuiGameOver
{
    public BackdooredGuiGameOver(@Nullable final ITextComponent causeOfDeathIn) {
        super(causeOfDeathIn);
    }
    
    public void func_73876_c() {
        if (this.field_146297_k.field_71439_g != null && !this.field_146297_k.field_71439_g.field_70128_L && this.field_146297_k.field_71439_g.func_110143_aJ() > 0.0f) {
            this.field_146297_k.func_147108_a((GuiScreen)null);
            this.field_146297_k.func_71381_h();
        }
        else {
            super.func_73876_c();
        }
    }
}
