package com.backdoored.utils.csgo;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import com.backdoored.Globals;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.Vec3d;

public class CsgoRenderUtils
{
    public CsgoRenderUtils() {
        super();
    }
    
    public static void renderText3d(final String txt, final Vec3d loc) {
        final float playerViewX = (float)ObfuscationReflectionHelper.getPrivateValue((Class)RenderManager.class, (Object)Globals.mc.func_175598_ae(), new String[] { "playerViewX", "field_78732_j" });
        final float playerViewY = (float)ObfuscationReflectionHelper.getPrivateValue((Class)RenderManager.class, (Object)Globals.mc.func_175598_ae(), new String[] { "playerViewY", "field_78735_i" });
        final float var13 = 1.6f;
        final float distance = (float)(0.01666666753590107 * Globals.mc.field_71439_g.func_70011_f(loc.field_72450_a, loc.field_72448_b, loc.field_72449_c) / 2.0);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)loc.field_72450_a, (float)loc.field_72448_b, (float)loc.field_72449_c);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-distance, -distance, distance);
        GL11.glDepthMask(false);
        GL11.glDisable(2896);
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferBuilder = tessellator.func_178180_c();
        final int y = (int)(-Globals.mc.field_71439_g.func_70011_f(loc.field_72450_a, loc.field_72448_b, loc.field_72449_c)) / (int)var13;
        GL11.glDisable(3553);
        final int x = Globals.mc.field_71466_p.func_78256_a(txt) / 2;
        Globals.mc.field_71466_p.func_175063_a(txt, (float)(-x), (float)y, 16777215);
        Globals.mc.field_71460_t.func_175072_h();
        GL11.glLineWidth(1.0f);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2896);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }
}
