package com.backdoored.hacks.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.utils.Utils;
import net.minecraft.client.gui.GuiScreen;
import com.backdoored.subguis.BackdooredGuiGameOver;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraftforge.client.event.GuiOpenEvent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class AntiDeathScreen extends BaseHack
{
    public static AntiDeathScreen INSTANCE;
    
    public AntiDeathScreen() {
        super("Anti Death Screen", CategoriesInit.COMBAT, "Prevents the death screen from incorrectly coming up during combat");
        AntiDeathScreen.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onOpenGUI(final GuiOpenEvent event) {
        if (event.getGui() instanceof GuiGameOver) {
            try {
                final ITextComponent causeOfDeath = (ITextComponent)ObfuscationReflectionHelper.getPrivateValue((Class)GuiGameOver.class, (Object)event.getGui(), new String[] { "causeOfDeath", "field_184871_f" });
                event.setGui((GuiScreen)new BackdooredGuiGameOver(causeOfDeath));
            }
            catch (Exception e) {
                Utils.printMessage("Disabled Anti Death Screen due to an error: " + e.toString());
                e.printStackTrace();
                this.setEnabled(false);
            }
        }
    }
}
