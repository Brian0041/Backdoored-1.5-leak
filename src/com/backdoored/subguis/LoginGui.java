package com.backdoored.subguis;

import java.io.IOException;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.backdoored.utils.AuthUtils;
import com.google.common.io.Files;
import com.google.common.base.Charsets;
import java.io.File;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiScreen;

public class LoginGui extends GuiScreen
{
    private GuiScreen original;
    private GuiTextField usernameField;
    private GuiTextField passwordField;
    private String error;
    
    public LoginGui(final GuiScreen original) {
        super();
        this.error = "";
        this.original = original;
    }
    
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        this.usernameField = new GuiTextField(0, this.field_146289_q, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 60 + 0, 202, 20);
        this.passwordField = new GuiTextField(2, this.field_146289_q, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 60 + 26, 202, 20);
        this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 60 + 52, "Login"));
        this.field_146292_n.add(new GuiButton(2, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 60 + 76, "Cancel"));
        this.usernameField.func_146203_f(500);
        this.passwordField.func_146203_f(500);
        super.func_73866_w_();
        try {
            final String txt = Files.asCharSource(new File("Backdoored/accounts.txt"), Charsets.UTF_8).read();
            if (!txt.isEmpty()) {
                final String[] parts = txt.split(":");
                try {
                    if (!AuthUtils.mcLogin(parts[0].trim(), parts[1].trim())) {
                        System.out.println("Could not log in");
                        this.error = "Could not log in";
                        return;
                    }
                }
                catch (AuthenticationException e) {
                    e.printStackTrace();
                    System.out.println("Could not log in: " + e.toString());
                    this.error = "Could not log in: " + e.toString();
                    return;
                }
                this.field_146297_k.func_147108_a(this.original);
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public void func_73876_c() {
        this.usernameField.func_146178_a();
        this.passwordField.func_146178_a();
    }
    
    public void func_73864_a(final int x, final int y, final int b) throws IOException {
        this.usernameField.func_146192_a(x, y, b);
        this.passwordField.func_146192_a(x, y, b);
        super.func_73864_a(x, y, b);
    }
    
    public void func_146284_a(final GuiButton button) {
        if (button.field_146127_k == 1) {
            System.out.println("Attempting subguis, username: " + this.usernameField.func_146179_b().trim());
            try {
                if (!AuthUtils.mcLogin(this.usernameField.func_146179_b().trim(), this.passwordField.func_146179_b().trim())) {
                    System.out.println("Could not log in");
                    this.error = "Could not log in";
                    return;
                }
            }
            catch (AuthenticationException e) {
                e.printStackTrace();
                System.out.println("Could not log in: " + e.toString());
                this.error = "Could not log in: " + e.toString();
                return;
            }
            this.field_146297_k.func_147108_a(this.original);
        }
        else if (button.field_146127_k == 2) {
            this.field_146297_k.func_147108_a(this.original);
        }
    }
    
    protected void func_73869_a(final char character, final int i) {
        this.usernameField.func_146201_a(character, i);
        this.passwordField.func_146201_a(character, i);
        if (character == '\t') {
            if (this.usernameField.func_146206_l()) {
                this.usernameField.func_146195_b(false);
                this.passwordField.func_146195_b(true);
            }
            else if (this.passwordField.func_146206_l()) {
                this.usernameField.func_146195_b(false);
                this.passwordField.func_146195_b(false);
            }
        }
        if (character == '\r') {
            this.func_146284_a(this.field_146292_n.get(0));
        }
    }
    
    public void func_73863_a(final int x, final int y, final float f) {
        this.func_146276_q_();
        this.func_73732_a(this.field_146289_q, "Backdoored Client: Login to Minecraft", this.field_146294_l / 2, 2, 16777215);
        this.func_73731_b(this.field_146289_q, "Email", this.field_146294_l / 2 - 100 - 50, this.field_146295_m / 4 + 60 + 0 + 6, 16777215);
        this.func_73731_b(this.field_146289_q, "Password", this.field_146294_l / 2 - 100 - 50, this.field_146295_m / 4 + 60 + 26 + 6, 16777215);
        this.func_73732_a(this.field_146289_q, this.error, this.field_146294_l / 2, this.field_146295_m / 4 + 60 + 100, 16711680);
        try {
            this.usernameField.func_146194_f();
            this.passwordField.func_146194_f();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        super.func_73863_a(x, y, f);
    }
}
