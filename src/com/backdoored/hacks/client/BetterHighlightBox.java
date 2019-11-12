package com.backdoored.hacks.client;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import com.backdoored.extensions.RenderGlobalWrapper;
import net.minecraft.world.World;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class BetterHighlightBox extends BaseHack
{
    private static Setting width;
    private static Setting redS;
    private static Setting greenS;
    private static Setting blueS;
    private static Setting alphaS;
    
    public BetterHighlightBox() {
        super("BetterHighlightBox", CategoriesInit.CLIENT, "Better Highlight Box");
        BetterHighlightBox.width = new Setting("Width", this, 5.0, 0.0, 50.0);
        BetterHighlightBox.redS = new Setting("Red", this, 0, 0, 255);
        BetterHighlightBox.greenS = new Setting("Green", this, 0, 0, 255);
        BetterHighlightBox.blueS = new Setting("Blue", this, 0, 0, 255);
        BetterHighlightBox.alphaS = new Setting("Alpha", this, 0.4, 0.0, 1.0);
    }
    
    @SubscribeEvent
    public void onDrawBlockHighlight(final DrawBlockHighlightEvent event) {
        if (this.getEnabled()) {
            final float partialTicks = event.getPartialTicks();
            final EntityPlayer player = event.getPlayer();
            final RayTraceResult movingObjectPositionIn = event.getTarget();
            if (movingObjectPositionIn.field_72313_a == RayTraceResult.Type.BLOCK) {
                GlStateManager.func_179147_l();
                GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.func_187441_d((float)BetterHighlightBox.width.getValDouble());
                GlStateManager.func_179090_x();
                GlStateManager.func_179132_a(false);
                final BlockPos blockpos = movingObjectPositionIn.func_178782_a();
                final IBlockState iblockstate = BetterHighlightBox.mc.field_71441_e.func_180495_p(blockpos);
                if (iblockstate.func_185904_a() != Material.field_151579_a && BetterHighlightBox.mc.field_71441_e.func_175723_af().func_177746_a(blockpos)) {
                    final double d3 = player.field_70142_S + (player.field_70165_t - player.field_70142_S) * partialTicks;
                    final double d4 = player.field_70137_T + (player.field_70163_u - player.field_70137_T) * partialTicks;
                    final double d5 = player.field_70136_U + (player.field_70161_v - player.field_70136_U) * partialTicks;
                    RenderGlobalWrapper.func_189697_a(iblockstate.func_185918_c((World)BetterHighlightBox.mc.field_71441_e, blockpos).func_186662_g(0.0020000000949949026).func_72317_d(-d3, -d4, -d5), (float)Math.min(Math.abs(BetterHighlightBox.redS.getValInt() - 255), 244), (float)Math.min(Math.abs(BetterHighlightBox.greenS.getValInt() - 255), 244), (float)Math.min(Math.abs(BetterHighlightBox.blueS.getValInt() - 255), 244), (float)BetterHighlightBox.alphaS.getValDouble());
                }
                GlStateManager.func_179132_a(true);
                GlStateManager.func_179098_w();
                GlStateManager.func_179084_k();
            }
            event.setCanceled(true);
        }
    }
}
