package com.backdoored.hacks.client;

import net.minecraft.entity.Entity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import com.backdoored.Globals;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class DebugCrosshair extends BaseHack
{
    public DebugCrosshair() {
        super("Debug Crosshair", "Show f3 crosshair", CategoriesInit.CLIENT);
    }
    
    @SubscribeEvent
    public void stopRegularCrosshair(final RenderGameOverlayEvent event) {
        if (this.getEnabled() && event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            event.setCanceled(true);
            final int width = new ScaledResolution(DebugCrosshair.mc).func_78326_a();
            final int height = new ScaledResolution(DebugCrosshair.mc).func_78328_b();
            final float zLevel = (float)ObfuscationReflectionHelper.getPrivateValue((Class)Gui.class, (Object)Globals.mc.field_71456_v, new String[] { "zLevel", "field_73735_i" });
            renderCrosshair(event.getPartialTicks(), width, height, zLevel);
        }
    }
    
    public static void renderCrosshair(final float partialTicks, final int width, final int height, final float zLevel) {
        final GameSettings gamesettings = DebugCrosshair.mc.field_71474_y;
        if (gamesettings.field_74320_O == 0) {
            if (DebugCrosshair.mc.field_71442_b.func_78747_a() && DebugCrosshair.mc.field_147125_j == null) {
                final RayTraceResult raytraceresult = DebugCrosshair.mc.field_71476_x;
                if (raytraceresult == null || raytraceresult.field_72313_a != RayTraceResult.Type.BLOCK) {
                    return;
                }
                final BlockPos blockpos = raytraceresult.func_178782_a();
                final IBlockState state = DebugCrosshair.mc.field_71441_e.func_180495_p(blockpos);
                if (!state.func_177230_c().hasTileEntity(state) || !(DebugCrosshair.mc.field_71441_e.func_175625_s(blockpos) instanceof IInventory)) {
                    return;
                }
            }
            if (!gamesettings.field_74319_N) {
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b((float)(width / 2), (float)(height / 2), zLevel);
                final Entity entity = DebugCrosshair.mc.func_175606_aa();
                if (entity != null) {
                    GlStateManager.func_179114_b(entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * partialTicks, -1.0f, 0.0f, 0.0f);
                    GlStateManager.func_179114_b(entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * partialTicks, 0.0f, 1.0f, 0.0f);
                    GlStateManager.func_179152_a(-1.0f, -1.0f, -1.0f);
                    OpenGlHelper.func_188785_m(10);
                    GlStateManager.func_179121_F();
                }
            }
        }
    }
}
