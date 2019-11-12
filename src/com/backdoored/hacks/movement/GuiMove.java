package com.backdoored.hacks.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraft.client.gui.GuiChat;
import com.backdoored.event.IsKeyboardCreated;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class GuiMove extends BaseHack
{
    public GuiMove() {
        super("Gui Move", "Walk while in guis", CategoriesInit.MOVEMENT);
    }
    
    @SubscribeEvent
    public void isKeyboardCreated(final IsKeyboardCreated event) {
        if (this.getEnabled() && !(event.screen instanceof GuiChat)) {
            event.setResult(Event.Result.DENY);
            if (Keyboard.isKeyDown(200)) {
                final EntityPlayerSP field_71439_g = GuiMove.mc.field_71439_g;
                field_71439_g.field_70125_A -= 2.0f;
            }
            if (Keyboard.isKeyDown(208)) {
                final EntityPlayerSP field_71439_g2 = GuiMove.mc.field_71439_g;
                field_71439_g2.field_70125_A += 2.0f;
            }
            if (Keyboard.isKeyDown(203)) {
                final EntityPlayerSP field_71439_g3 = GuiMove.mc.field_71439_g;
                field_71439_g3.field_70177_z -= 2.0f;
            }
            if (Keyboard.isKeyDown(205)) {
                final EntityPlayerSP field_71439_g4 = GuiMove.mc.field_71439_g;
                field_71439_g4.field_70177_z += 2.0f;
            }
        }
    }
}
