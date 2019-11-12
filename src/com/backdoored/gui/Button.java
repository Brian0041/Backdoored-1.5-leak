package com.backdoored.gui;

import java.util.List;
import org.lwjgl.opengl.GL11;
import com.backdoored.Globals;
import java.util.ArrayList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;

public class Button extends GuiScreen
{
    protected static final ResourceLocation white;
    public boolean leftClickToggled;
    public boolean rightClickToggled;
    public int x;
    public int y;
    public int field_146294_l;
    public int field_146295_m;
    public String text;
    private boolean CConClick;
    public boolean isVisible;
    private float[] color;
    public static ArrayList<Button> Objects;
    
    public Button(final int x, final int y, final int width, final int height, final String text, final boolean changeColorOnClick, final boolean isVisible, final float[] color) {
        super();
        this.leftClickToggled = false;
        this.rightClickToggled = false;
        this.x = x;
        this.y = y;
        this.field_146294_l = width;
        this.field_146295_m = height;
        this.text = text;
        this.CConClick = changeColorOnClick;
        this.isVisible = isVisible;
        this.color = color;
        Button.Objects.add(this);
        this.field_146297_k = Globals.mc;
    }
    
    void drawButton(final int mouseX, final int mouseY) {
        final int borderWidth = 1;
        Globals.mc.field_71446_o.func_110577_a(Button.white);
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
        GL11.glColor4f(this.color[0], this.color[1], this.color[2], this.color[3]);
        final List<String> lines = (List<String>)this.field_146297_k.field_71466_p.func_78271_c(this.text, this.field_146294_l - (borderWidth + 1));
        boolean isWrapped = false;
        final int wrappedHeight = lines.size() * this.field_146297_k.field_71466_p.field_78288_b + 15;
        if (wrappedHeight > this.field_146297_k.field_71466_p.field_78288_b + 15) {
            isWrapped = true;
            this.field_146295_m = wrappedHeight;
        }
        this.func_73729_b(this.x, this.y, 0, 0, this.field_146294_l, this.field_146295_m);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        this.func_73729_b(this.x + borderWidth, this.y + borderWidth, 0, 0, this.field_146294_l - borderWidth * 2, this.field_146295_m - borderWidth * 2);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
        String textColor;
        if (this.CConClick && this.leftClickToggled) {
            textColor = "FF0000";
        }
        else {
            textColor = "FFFFFF";
        }
        this.field_146297_k.field_71466_p.func_78279_b(this.text, this.x + (borderWidth + 1) + (this.field_146294_l - (borderWidth + 1) - this.field_146297_k.field_71466_p.func_78256_a((String)lines.get(0))) / 2, this.y + this.field_146295_m / 2 - this.field_146297_k.field_71466_p.field_78288_b * lines.size() / 2, this.field_146294_l - (borderWidth + 1), Integer.parseInt(textColor, 16));
    }
    
    static {
        white = new ResourceLocation("backdoored", "textures/white.png");
        Button.Objects = new ArrayList<Button>();
    }
}
