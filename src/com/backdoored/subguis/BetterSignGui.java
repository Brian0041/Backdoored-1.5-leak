package com.backdoored.subguis;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.init.Blocks;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentString;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import java.util.LinkedList;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiTextField;
import java.util.List;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.client.gui.GuiScreen;

public class BetterSignGui extends GuiScreen
{
    public final TileEntitySign sign;
    private static int focusedField;
    private List<GuiTextField> textFields;
    private String[] defaultStrings;
    
    public BetterSignGui(final TileEntitySign sign) {
        super();
        this.sign = sign;
    }
    
    public void func_73866_w_() {
        this.field_146292_n.clear();
        Keyboard.enableRepeatEvents(true);
        this.textFields = new LinkedList<GuiTextField>();
        this.defaultStrings = new String[4];
        for (int i = 0; i < 4; ++i) {
            final GuiTextField field = new GuiTextField(i, this.field_146289_q, this.field_146294_l / 2 + 4, 75 + i * 24, 120, 20);
            field.func_175205_a(this::validateText);
            field.func_146203_f(100);
            final String text = this.sign.field_145915_a[i].func_150260_c();
            field.func_146180_a(this.defaultStrings[i] = text);
            this.textFields.add(field);
        }
        this.textFields.get(BetterSignGui.focusedField).func_146195_b(true);
        this.func_189646_b(new GuiButton(4, this.field_146294_l / 2 + 5, this.field_146295_m / 4 + 120, 120, 20, "Done"));
        this.func_189646_b(new GuiButton(5, this.field_146294_l / 2 - 125, this.field_146295_m / 4 + 120, 120, 20, "Cancel"));
        this.func_189646_b(new GuiButton(6, this.field_146294_l / 2 - 41, 147, 40, 20, "Shift"));
        this.func_189646_b(new GuiButton(7, this.field_146294_l / 2 - 41, 123, 40, 20, "Clear"));
        this.sign.func_145913_a(false);
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        final int currentFocus = BetterSignGui.focusedField;
        this.textFields.forEach(field -> field.func_146192_a(mouseX, mouseY, mouseButton));
        this.updateFocusField();
        if (BetterSignGui.focusedField == currentFocus && !this.textFields.get(BetterSignGui.focusedField).func_146206_l()) {
            this.textFields.get(BetterSignGui.focusedField).func_146195_b(true);
        }
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) {
        switch (keyCode) {
            case 1: {
                this.exit();
                return;
            }
            case 15: {
                final int change = func_146272_n() ? -1 : 1;
                this.tabFocus(change);
                return;
            }
            case 200: {
                this.tabFocus(-1);
                return;
            }
            case 28:
            case 156:
            case 208: {
                this.tabFocus(1);
                break;
            }
        }
        this.textFields.forEach(field -> field.func_146201_a(typedChar, keyCode));
        this.sign.field_145915_a[BetterSignGui.focusedField] = (ITextComponent)new TextComponentString(this.textFields.get(BetterSignGui.focusedField).func_146179_b());
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        super.func_146284_a(button);
        switch (button.field_146127_k) {
            case 5: {
                for (int i = 0; i < 4; ++i) {
                    this.sign.field_145915_a[i] = (ITextComponent)new TextComponentString(this.defaultStrings[i]);
                }
            }
            case 4: {
                this.exit();
                break;
            }
            case 6: {
                final String[] replacements = new String[4];
                for (int j = 0; j < 4; ++j) {
                    final int change = func_146272_n() ? 1 : -1;
                    final int target = this.wrapLine(j + change);
                    replacements[j] = this.sign.field_145915_a[target].func_150260_c();
                }
                final int id;
                final Object o;
                this.textFields.forEach(field -> {
                    id = field.func_175206_d();
                    field.func_146180_a(o[id]);
                    this.sign.field_145915_a[id] = (ITextComponent)new TextComponentString(o[id]);
                    return;
                });
                break;
            }
            case 7: {
                final int id2;
                this.textFields.forEach(field -> {
                    id2 = field.func_175206_d();
                    field.func_146180_a("");
                    this.sign.field_145915_a[id2] = (ITextComponent)new TextComponentString("");
                    return;
                });
                break;
            }
        }
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        this.func_73732_a(this.field_146289_q, I18n.func_135052_a("sign.edit", new Object[0]), this.field_146294_l / 2, 40, 16777215);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b(this.field_146294_l / 2 - 63.0f, 0.0f, 50.0f);
        final float f = 93.75f;
        GlStateManager.func_179152_a(-93.75f, -93.75f, -93.75f);
        GlStateManager.func_179114_b(180.0f, 0.0f, 1.0f, 0.0f);
        final Block block = this.sign.func_145838_q();
        if (block == Blocks.field_150472_an) {
            final float f2 = this.sign.func_145832_p() * 360 / 16.0f;
            GlStateManager.func_179114_b(f2, 0.0f, 1.0f, 0.0f);
            GlStateManager.func_179109_b(0.0f, -1.0625f, 0.0f);
        }
        else {
            final int i = this.sign.func_145832_p();
            float f3 = 0.0f;
            if (i == 2) {
                f3 = 180.0f;
            }
            if (i == 4) {
                f3 = 90.0f;
            }
            if (i == 5) {
                f3 = -90.0f;
            }
            GlStateManager.func_179114_b(f3, 0.0f, 1.0f, 0.0f);
            GlStateManager.func_179109_b(0.0f, -0.7625f, 0.0f);
        }
        this.sign.field_145918_i = -1;
        TileEntityRendererDispatcher.field_147556_a.func_147549_a((TileEntity)this.sign, -0.5, -0.75, -0.5, 0.0f);
        GlStateManager.func_179121_F();
        this.textFields.forEach(GuiTextField::func_146194_f);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }
    
    void updateFocusField() {
        this.textFields.forEach(field -> {
            if (field.func_146206_l()) {
                BetterSignGui.focusedField = field.func_175206_d();
            }
        });
    }
    
    void exit() {
        this.sign.func_70296_d();
        this.field_146297_k.func_147108_a((GuiScreen)null);
    }
    
    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
        final NetHandlerPlayClient nethandlerplayclient = this.field_146297_k.func_147114_u();
        if (nethandlerplayclient != null) {
            nethandlerplayclient.func_147297_a((Packet)new CPacketUpdateSign(this.sign.func_174877_v(), this.sign.field_145915_a));
        }
        this.sign.func_145913_a(true);
    }
    
    void tabFocus(final int change) {
        this.textFields.get(BetterSignGui.focusedField).func_146195_b(false);
        BetterSignGui.focusedField = this.wrapLine(BetterSignGui.focusedField + change);
        this.textFields.get(BetterSignGui.focusedField).func_146195_b(true);
    }
    
    int wrapLine(final int line) {
        if (line > 3) {
            return 0;
        }
        if (line < 0) {
            return 3;
        }
        return line;
    }
    
    boolean validateText(final String s) {
        if (this.field_146289_q.func_78256_a(s) > 90) {
            return false;
        }
        final char[] charArray;
        final char[] arr = charArray = s.toCharArray();
        for (final char c : charArray) {
            if (!ChatAllowedCharacters.func_71566_a(c)) {
                return false;
            }
        }
        return true;
    }
    
    private static /* synthetic */ void lambda$updateFocusField$4(final GuiTextField field) {
        if (field.func_146206_l()) {
            BetterSignGui.focusedField = field.func_175206_d();
        }
    }
    
    private /* synthetic */ void lambda$actionPerformed$3(final GuiTextField field) {
        final int id = field.func_175206_d();
        field.func_146180_a("");
        this.sign.field_145915_a[id] = (ITextComponent)new TextComponentString("");
    }
    
    private /* synthetic */ void lambda$actionPerformed$2(final String[] replacements, final GuiTextField field) {
        final int id = field.func_175206_d();
        field.func_146180_a(replacements[id]);
        this.sign.field_145915_a[id] = (ITextComponent)new TextComponentString(replacements[id]);
    }
    
    private static /* synthetic */ void lambda$keyTyped$1(final char typedChar, final int keyCode, final GuiTextField field) {
        field.func_146201_a(typedChar, keyCode);
    }
    
    private static /* synthetic */ void lambda$mouseClicked$0(final int mouseX, final int mouseY, final int mouseButton, final GuiTextField field) {
        field.func_146192_a(mouseX, mouseY, mouseButton);
    }
    
    static {
        BetterSignGui.focusedField = 0;
    }
}
