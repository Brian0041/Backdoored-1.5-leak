package com.backdoored.hacks.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Iterator;
import com.backdoored.utils.RenderUtils;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class ChestESP extends BaseHack
{
    private Setting noNether;
    private Setting chams;
    private Setting outline;
    private Setting chests;
    private Setting chestsR;
    private Setting chestsG;
    private Setting chestsB;
    private Setting chestsA;
    private Setting beds;
    private Setting bedsR;
    private Setting bedsG;
    private Setting bedsB;
    private Setting bedsA;
    private Setting enderChests;
    private Setting enderChestsR;
    private Setting enderChestsG;
    private Setting enderChestsB;
    private Setting enderChestsA;
    
    public ChestESP() {
        super("Chest ESP", CategoriesInit.RENDER, "yes");
        this.noNether = new Setting("No Nether", this, false);
        this.chams = new Setting("Chams", this, true);
        this.outline = new Setting("Outlines", this, true);
        this.chests = new Setting("Chests", this, true);
        this.enderChests = new Setting("Ender Chests", this, true);
        this.beds = new Setting("Beds", this, true);
        this.chestsR = new Setting("Chests R", this, 1.0, 0.0, 1.0);
        this.chestsG = new Setting("Chests G", this, 1.0, 0.0, 1.0);
        this.chestsB = new Setting("Chests B", this, 0.0, 0.0, 1.0);
        this.chestsA = new Setting("Chests A", this, 1.0, 0.0, 1.0);
        this.bedsR = new Setting("Beds R", this, 1.0, 0.0, 1.0);
        this.bedsG = new Setting("Beds G", this, 1.0, 0.0, 1.0);
        this.bedsB = new Setting("Beds B", this, 0.0, 0.0, 1.0);
        this.bedsA = new Setting("Beds A", this, 1.0, 0.0, 1.0);
        this.enderChestsR = new Setting("E Chests R", this, 0.0, 0.0, 1.0);
        this.enderChestsG = new Setting("E Chests G", this, 1.0, 0.0, 1.0);
        this.enderChestsB = new Setting("E Chests B", this, 0.0, 0.0, 1.0);
        this.enderChestsA = new Setting("E Chests A", this, 1.0, 0.0, 1.0);
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        if (!this.getEnabled()) {
            return;
        }
        if (this.noNether.getValBoolean() && ChestESP.mc.field_71439_g.field_71093_bK == -1) {
            return;
        }
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(2929);
        final double renderPosX = ChestESP.mc.func_175598_ae().field_78730_l;
        final double renderPosY = ChestESP.mc.func_175598_ae().field_78731_m;
        final double renderPosZ = ChestESP.mc.func_175598_ae().field_78728_n;
        GL11.glPushMatrix();
        GL11.glTranslated(-renderPosX, -renderPosY, -renderPosZ);
        for (final TileEntity tileEntity : ChestESP.mc.field_71441_e.field_147482_g) {
            final boolean lChest = this.chests.getValBoolean() && tileEntity instanceof TileEntityChest;
            final boolean lBed = this.beds.getValBoolean() && tileEntity instanceof TileEntityBed;
            final boolean lEnderChest = this.enderChests.getValBoolean() && tileEntity instanceof TileEntityEnderChest;
            if (!lChest && !lBed && !lEnderChest) {
                continue;
            }
            float r = 0.0f;
            float g = 0.0f;
            float b = 0.0f;
            float a = 0.0f;
            if (lChest) {
                r = (float)this.chestsR.getValDouble();
                g = (float)this.chestsG.getValDouble();
                b = (float)this.chestsB.getValDouble();
                a = (float)this.chestsA.getValDouble();
            }
            if (lBed) {
                r = (float)this.bedsR.getValDouble();
                g = (float)this.bedsG.getValDouble();
                b = (float)this.bedsB.getValDouble();
                a = (float)this.bedsA.getValDouble();
            }
            if (lEnderChest) {
                r = (float)this.enderChestsR.getValDouble();
                g = (float)this.enderChestsG.getValDouble();
                b = (float)this.enderChestsB.getValDouble();
                a = (float)this.enderChestsA.getValDouble();
            }
            AxisAlignedBB bb = RenderUtils.getBoundingBox(tileEntity.func_174877_v());
            if (tileEntity instanceof TileEntityChest) {
                final TileEntityChest chest = (TileEntityChest)tileEntity;
                if (chest.field_145990_j != null) {
                    continue;
                }
                if (chest.field_145988_l != null) {
                    continue;
                }
                if (chest.field_145991_k != null) {
                    bb = bb.func_111270_a(RenderUtils.getBoundingBox(chest.field_145991_k.func_174877_v()));
                }
                else if (chest.field_145992_i != null) {
                    bb = bb.func_111270_a(RenderUtils.getBoundingBox(chest.field_145992_i.func_174877_v()));
                }
            }
            GL11.glColor4f(r, g, b, a);
            if (this.chams.getValBoolean()) {
                RenderUtils.drawSolidBox(bb);
            }
            if (this.outline.getValBoolean()) {
                RenderUtils.drawOutlinedBox(bb);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        GL11.glPopMatrix();
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
}
