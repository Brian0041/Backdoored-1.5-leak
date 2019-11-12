package com.backdoored.hacks.player;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.NonNullList;
import com.backdoored.extensions.Wrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import net.minecraft.util.ResourceLocation;
import com.backdoored.hacks.BaseHack;

public class InvPreview extends BaseHack
{
    private static final ResourceLocation SHULKER_ICON;
    private static final int CORNER = 5;
    private static final int BUFFER = 1;
    private static final int EDGE = 18;
    private Setting xSetting;
    private Setting ySetting;
    
    public InvPreview() {
        super("Inventory Preview", CategoriesInit.PLAYER, "Shows you a preview of whats in your inv");
        this.xSetting = new Setting("x", this, 2, 0, InvPreview.mc.field_71443_c + 100);
        this.ySetting = new Setting("y", this, 2, 0, InvPreview.mc.field_71440_d + 100);
    }
    
    public void onRender() {
        if (!this.getEnabled()) {
            return;
        }
        final int x = this.xSetting.getValInt();
        final int y = this.ySetting.getValInt();
        final NonNullList<ItemStack> inventory = (NonNullList<ItemStack>)InvPreview.mc.field_71439_g.field_71071_by.field_70462_a;
        GlStateManager.func_179094_E();
        RenderHelper.func_74519_b();
        GlStateManager.func_179091_B();
        GlStateManager.func_179124_c(1.0f, 1.0f, 1.0f);
        GlStateManager.func_179109_b(0.0f, 0.0f, 700.0f);
        InvPreview.mc.func_110434_K().func_110577_a(InvPreview.SHULKER_ICON);
        RenderHelper.func_74518_a();
        this.renderBackground(x, y, 9, 3, 1973019);
        RenderHelper.func_74520_c();
        for (int i = 9; i < inventory.size(); ++i) {
            final ItemStack itemstack = (ItemStack)inventory.get(i);
            final int xp = x + 6 + i % 9 * 18;
            final int yp = y + 6 + i / 9 * 18 - 18;
            if (!itemstack.func_190926_b()) {
                InvPreview.mc.func_175599_af().func_180450_b(itemstack, xp, yp);
                InvPreview.mc.func_175599_af().func_175030_a(Wrapper.fontRenderer, itemstack, xp, yp);
            }
        }
        RenderHelper.func_74518_a();
        GlStateManager.func_179097_i();
        GlStateManager.func_179101_C();
        GlStateManager.func_179121_F();
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void renderBackground(final int x, final int y, final int width, final int height, final int color) {
        InvPreview.mc.func_110434_K().func_110577_a(InvPreview.SHULKER_ICON);
        GlStateManager.func_179124_c(((color & 0xFF0000) >> 16) / 255.0f, ((color & 0xFF00) >> 8) / 255.0f, (color & 0xFF) / 255.0f);
        RenderHelper.func_74518_a();
        Gui.func_146110_a(x, y, 0.0f, 0.0f, 5, 5, 256.0f, 256.0f);
        Gui.func_146110_a(x + 5 + 18 * width, y + 5 + 18 * height, 25.0f, 25.0f, 5, 5, 256.0f, 256.0f);
        Gui.func_146110_a(x + 5 + 18 * width, y, 25.0f, 0.0f, 5, 5, 256.0f, 256.0f);
        Gui.func_146110_a(x, y + 5 + 18 * height, 0.0f, 25.0f, 5, 5, 256.0f, 256.0f);
        for (int row = 0; row < height; ++row) {
            Gui.func_146110_a(x, y + 5 + 18 * row, 0.0f, 6.0f, 5, 18, 256.0f, 256.0f);
            Gui.func_146110_a(x + 5 + 18 * width, y + 5 + 18 * row, 25.0f, 6.0f, 5, 18, 256.0f, 256.0f);
            for (int col = 0; col < width; ++col) {
                if (row == 0) {
                    Gui.func_146110_a(x + 5 + 18 * col, y, 6.0f, 0.0f, 18, 5, 256.0f, 256.0f);
                    Gui.func_146110_a(x + 5 + 18 * col, y + 5 + 18 * height, 6.0f, 25.0f, 18, 5, 256.0f, 256.0f);
                }
                Gui.func_146110_a(x + 5 + 18 * col, y + 5 + 18 * row, 6.0f, 6.0f, 18, 18, 256.0f, 256.0f);
            }
        }
        GlStateManager.func_179124_c(1.0f, 1.0f, 1.0f);
    }
    
    static {
        SHULKER_ICON = new ResourceLocation("backdoored", "textures/inv_slot.png");
    }
}
