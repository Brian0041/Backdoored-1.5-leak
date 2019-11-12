package com.backdoored.subguis;

import com.backdoored.api.MojangWebApi;
import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import com.backdoored.Globals;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;

public class MainMenuGui extends GuiMainMenu
{
    public MainMenuGui() {
        super();
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146292_n.add(new GuiButton(62, 2, 2, 98, 20, "Login"));
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        System.out.println("Button pressed: " + button.field_146126_j);
        if (button.field_146126_j.equals("Login")) {
            Globals.mc.func_147108_a((GuiScreen)new LoginGui((GuiScreen)this));
        }
        else {
            super.func_146284_a(button);
        }
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        super.func_73863_a(mouseX, mouseY, partialTicks);
        String authStatus = "[ONLINE]";
        Color authColor = Color.GREEN;
        if (!MojangWebApi.isAuthUp()) {
            authStatus = "[OFFLINE]";
            authColor = Color.RED;
        }
        String seshStatus = "[ONLINE]";
        Color seshColor = Color.GREEN;
        if (!MojangWebApi.isSeshUp()) {
            seshStatus = "[OFFLINE]";
            seshColor = Color.RED;
        }
        this.field_146297_k.field_71466_p.func_175065_a("Auth Server:     " + authStatus, 2.0f, 25.0f, authColor.getRGB(), true);
        this.field_146297_k.field_71466_p.func_175065_a("Session Server: " + seshStatus, 2.0f, (float)(25 + this.field_146297_k.field_71466_p.field_78288_b + 2), seshColor.getRGB(), true);
    }
}
