package com.backdoored.hacks.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import com.backdoored.extensions.Wrapper;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import com.backdoored.utils.ItemNBTUtil;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import net.minecraftforge.registries.IForgeRegistryEntry;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import com.backdoored.gui.CategoriesInit;
import java.util.List;
import net.minecraft.util.ResourceLocation;
import com.backdoored.hacks.BaseHack;

public class ShulkerPreview extends BaseHack
{
    private static final ResourceLocation SHULKER_ICON;
    private static List<ResourceLocation> shulkerBoxes;
    private static final int[][] TARGET_RATIOS;
    private static final int CORNER = 5;
    private static final int BUFFER = 1;
    private static final int EDGE = 18;
    static final /* synthetic */ boolean $assertionsDisabled;
    
    public ShulkerPreview() {
        super("Shulker Preview", CategoriesInit.CLIENT, "Preview Shulkers via tooltip");
        final String[] shulkerArr = (String[])ImmutableSet.of((Object)Blocks.field_190977_dl, (Object)Blocks.field_190978_dm, (Object)Blocks.field_190979_dn, (Object)Blocks.field_190980_do, (Object)Blocks.field_190981_dp, (Object)Blocks.field_190982_dq, (Object[])new Block[] { Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA }).stream().map(IForgeRegistryEntry.Impl::getRegistryName).map(Objects::toString).toArray(String[]::new);
        ShulkerPreview.shulkerBoxes = Arrays.stream(shulkerArr).map((Function<? super String, ?>)ResourceLocation::new).collect((Collector<? super Object, ?, List<ResourceLocation>>)Collectors.toList());
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void makeTooltip(final ItemTooltipEvent event) {
        if (isShulkerBox(event.getItemStack(), ShulkerPreview.shulkerBoxes) && event.getItemStack().func_77942_o()) {
            NBTTagCompound cmp = ItemNBTUtil.getCompound(event.getItemStack(), "BlockEntityTag", true);
            if (cmp != null) {
                if (!cmp.func_150297_b("id", 8)) {
                    cmp = cmp.func_74737_b();
                    cmp.func_74778_a("id", "minecraft:shulker_box");
                }
                final TileEntity te = TileEntity.func_190200_a((World)ShulkerPreview.mc.field_71441_e, cmp);
                if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null)) {
                    final List<String> tooltip = (List<String>)event.getToolTip();
                    final List<String> tooltipCopy = new ArrayList<String>(tooltip);
                    for (int i = 1; i < tooltipCopy.size(); ++i) {
                        final String s = tooltipCopy.get(i);
                        if (!s.startsWith("§") || s.startsWith("§o")) {
                            tooltip.remove(s);
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void renderTooltip(final RenderTooltipEvent.PostText event) {
        if (!this.getEnabled()) {
            return;
        }
        if (isShulkerBox(event.getStack(), ShulkerPreview.shulkerBoxes) && event.getStack().func_77942_o()) {
            NBTTagCompound cmp = ItemNBTUtil.getCompound(event.getStack(), "BlockEntityTag", true);
            if (cmp != null) {
                if (!cmp.func_150297_b("id", 8)) {
                    cmp = cmp.func_74737_b();
                    cmp.func_74778_a("id", "minecraft:shulker_box");
                }
                final TileEntity te = TileEntity.func_190200_a((World)ShulkerPreview.mc.field_71441_e, cmp);
                if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null)) {
                    final ItemStack currentBox = event.getStack();
                    int currentX = event.getX() - 5;
                    int currentY = event.getY() - 70;
                    final IItemHandler capability = (IItemHandler)te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null);
                    assert capability != null;
                    final int size = capability.getSlots();
                    int[] dims = { Math.min(size, 9), Math.max(size / 9, 1) };
                    for (final int[] testAgainst : ShulkerPreview.TARGET_RATIOS) {
                        if (testAgainst[0] * testAgainst[1] == size) {
                            dims = testAgainst;
                            break;
                        }
                    }
                    final int texWidth = 10 + 18 * dims[0];
                    if (currentY < 0) {
                        currentY = event.getY() + event.getLines().size() * 10 + 5;
                    }
                    final ScaledResolution res = new ScaledResolution(ShulkerPreview.mc);
                    final int right = currentX + texWidth;
                    if (right > res.func_78326_a()) {
                        currentX -= right - res.func_78326_a();
                    }
                    GlStateManager.func_179094_E();
                    RenderHelper.func_74519_b();
                    GlStateManager.func_179091_B();
                    GlStateManager.func_179124_c(1.0f, 1.0f, 1.0f);
                    GlStateManager.func_179109_b(0.0f, 0.0f, 700.0f);
                    ShulkerPreview.mc.func_110434_K().func_110577_a(ShulkerPreview.SHULKER_ICON);
                    RenderHelper.func_74518_a();
                    int color = -1;
                    if (((ItemBlock)currentBox.func_77973_b()).func_179223_d() instanceof BlockShulkerBox) {
                        final EnumDyeColor dye = ((BlockShulkerBox)((ItemBlock)currentBox.func_77973_b()).func_179223_d()).func_190956_e();
                        color = ItemDye.field_150922_c[dye.func_176767_b()];
                    }
                    renderTooltipBackground(currentX, currentY, dims[0], dims[1], color);
                    final RenderItem render = ShulkerPreview.mc.func_175599_af();
                    RenderHelper.func_74520_c();
                    GlStateManager.func_179126_j();
                    for (int i = 0; i < size; ++i) {
                        final ItemStack itemstack = capability.getStackInSlot(i);
                        final int xp = currentX + 6 + i % 9 * 18;
                        final int yp = currentY + 6 + i / 9 * 18;
                        if (!itemstack.func_190926_b()) {
                            render.func_180450_b(itemstack, xp, yp);
                            render.func_175030_a(Wrapper.fontRenderer, itemstack, xp, yp);
                        }
                    }
                    GlStateManager.func_179097_i();
                    GlStateManager.func_179101_C();
                    GlStateManager.func_179121_F();
                }
            }
        }
    }
    
    private static void renderTooltipBackground(final int x, final int y, final int width, final int height, final int color) {
        ShulkerPreview.mc.func_110434_K().func_110577_a(ShulkerPreview.SHULKER_ICON);
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
    
    private static boolean isShulkerBox(final ItemStack stack, final List<ResourceLocation> boxes) {
        return !stack.func_190926_b() && isShulkerBox(stack.func_77973_b().getRegistryName(), boxes);
    }
    
    private static boolean isShulkerBox(final ResourceLocation loc, final List<ResourceLocation> boxes) {
        return loc != null && boxes.contains(loc);
    }
    
    private static /* synthetic */ String[] lambda$new$0(final int x$0) {
        return new String[x$0];
    }
    
    static {
        $assertionsDisabled = !ShulkerPreview.class.desiredAssertionStatus();
        SHULKER_ICON = new ResourceLocation("backdoored", "textures/inv_slot.png");
        TARGET_RATIOS = new int[][] { { 1, 1 }, { 9, 3 }, { 9, 5 }, { 9, 6 }, { 9, 8 }, { 9, 9 }, { 12, 9 } };
    }
}
