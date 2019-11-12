package com.backdoored.hacks.client;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.world.storage.MapData;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.world.World;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import com.backdoored.gui.CategoriesInit;
import net.minecraft.util.ResourceLocation;
import com.backdoored.hacks.BaseHack;

public class MapTooltip extends BaseHack
{
    private static final ResourceLocation MAP;
    
    public MapTooltip() {
        super("Map Tooltip", CategoriesInit.CLIENT, "Tooltips to preview maps");
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void makeTooltip(final ItemTooltipEvent event) {
    }
    
    @SubscribeEvent
    public void renderTooltip(final RenderTooltipEvent.PostText event) {
        if (!this.getEnabled()) {
            return;
        }
        if (!event.getStack().func_190926_b() && event.getStack().func_77973_b() instanceof ItemMap) {
            final MapData mapdata = Items.field_151098_aY.func_77873_a(event.getStack(), (World)MapTooltip.mc.field_71441_e);
            if (mapdata != null) {
                GlStateManager.func_179094_E();
                GlStateManager.func_179124_c(1.0f, 1.0f, 1.0f);
                RenderHelper.func_74518_a();
                MapTooltip.mc.func_110434_K().func_110577_a(MapTooltip.MAP);
                final Tessellator tessellator = Tessellator.func_178181_a();
                final BufferBuilder buffer = tessellator.func_178180_c();
                final int pad = 7;
                final float size = 135.0f;
                final float scale = 0.5f;
                GlStateManager.func_179109_b((float)event.getX(), event.getY() - size * scale - 5.0f, 0.0f);
                GlStateManager.func_179152_a(scale, scale, scale);
                buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
                buffer.func_181662_b((double)(-pad), (double)size, 0.0).func_187315_a(0.0, 1.0).func_181675_d();
                buffer.func_181662_b((double)size, (double)size, 0.0).func_187315_a(1.0, 1.0).func_181675_d();
                buffer.func_181662_b((double)size, (double)(-pad), 0.0).func_187315_a(1.0, 0.0).func_181675_d();
                buffer.func_181662_b((double)(-pad), (double)(-pad), 0.0).func_187315_a(0.0, 0.0).func_181675_d();
                tessellator.func_78381_a();
                MapTooltip.mc.field_71460_t.func_147701_i().func_148250_a(mapdata, false);
                GlStateManager.func_179145_e();
                GlStateManager.func_179121_F();
            }
        }
    }
    
    static {
        MAP = new ResourceLocation("textures/map/map_background.png");
    }
}
