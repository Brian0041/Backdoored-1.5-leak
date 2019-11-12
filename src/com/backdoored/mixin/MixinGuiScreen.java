package com.backdoored.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import com.backdoored.event.IsKeyboardCreated;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiScreen.class })
public class MixinGuiScreen
{
    public MixinGuiScreen() {
        super();
    }
    
    @Redirect(method = { "handleInput" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isCreated()Z"))
    public boolean isKeyboardCreated() {
        final IsKeyboardCreated event = new IsKeyboardCreated((GuiScreen)this);
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event.getResult() == Event.Result.ALLOW || (event.getResult() != Event.Result.DENY && Keyboard.isCreated());
    }
}
