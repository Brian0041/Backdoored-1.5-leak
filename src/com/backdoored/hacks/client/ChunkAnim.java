package com.backdoored.hacks.client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.BlockPos;
import com.backdoored.event.ChunkRender;
import com.backdoored.gui.CategoriesInit;
import net.minecraft.client.renderer.chunk.RenderChunk;
import java.util.WeakHashMap;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class ChunkAnim extends BaseHack
{
    private Setting duration;
    private WeakHashMap<RenderChunk, ChunkAnimation> playingAnimations;
    
    public ChunkAnim() {
        super("Chunk Anim", CategoriesInit.CLIENT, "Animate chunk loading");
        this.playingAnimations = new WeakHashMap<RenderChunk, ChunkAnimation>();
        this.duration = new Setting("Duration", this, 2, 0, 10);
    }
    
    @SubscribeEvent
    public void onSetChunkPosition(final ChunkRender.SetOrigin event) {
        if (!this.getEnabled()) {
            return;
        }
        if (ChunkAnim.mc.field_71439_g != null) {
            BlockPos playerPos = ChunkAnim.mc.field_71439_g.func_180425_c();
            BlockPos chunkPos = new BlockPos(event.x, event.y, event.z);
            playerPos = playerPos.func_177982_a(0, -playerPos.func_177956_o(), 0);
            chunkPos = chunkPos.func_177982_a(8, -chunkPos.func_177956_o(), 8);
            EnumFacing chunkFacing = null;
            final Vec3i dif = (Vec3i)playerPos.func_177973_b((Vec3i)chunkPos);
            chunkFacing = this.getFacing(dif, Math.abs(dif.func_177958_n()), Math.abs(dif.func_177952_p()));
            final ChunkAnimation animationData = new ChunkAnimation(-1L, chunkFacing);
            this.playingAnimations.put(event.renderChunk, animationData);
        }
    }
    
    @SubscribeEvent
    public void preChunkRender(final ChunkRender.Pre event) {
        if (!this.getEnabled()) {
            return;
        }
        if (this.playingAnimations.containsKey(event.renderChunk)) {
            final ChunkAnimation animationData = this.playingAnimations.get(event.renderChunk);
            long time = animationData.time;
            if (time == -1L) {
                time = System.currentTimeMillis();
                animationData.time = time;
                BlockPos playerPos = ChunkAnim.mc.field_71439_g.func_180425_c();
                BlockPos chunkPos = event.renderChunk.func_178568_j();
                playerPos = playerPos.func_177982_a(0, -playerPos.func_177956_o(), 0);
                chunkPos = chunkPos.func_177982_a(8, -chunkPos.func_177956_o(), 8);
                final Vec3i distanceVec = (Vec3i)playerPos.func_177973_b((Vec3i)chunkPos);
                final int distanceX = Math.abs(distanceVec.func_177958_n());
                final int distanceZ = Math.abs(distanceVec.func_177952_p());
                animationData.facing = this.getFacing(distanceVec, distanceX, distanceZ);
            }
            final long currentTime = System.currentTimeMillis() - time;
            final int animationDuration = this.duration.getValInt();
            if (currentTime < animationDuration) {
                if (animationData.facing != null) {
                    final Vec3i vec = animationData.facing.func_176730_m();
                    final double d = -(200.0 - 200.0 / animationDuration * currentTime);
                    GlStateManager.func_179137_b(vec.func_177958_n() * d, 0.0, vec.func_177952_p() * d);
                }
            }
            else {
                this.playingAnimations.remove(event.renderChunk);
            }
        }
    }
    
    private EnumFacing getFacing(final Vec3i dif, final int difX, final int difZ) {
        if (difX > difZ) {
            if (dif.func_177958_n() > 0) {
                return EnumFacing.EAST;
            }
            return EnumFacing.WEST;
        }
        else {
            if (dif.func_177952_p() > 0) {
                return EnumFacing.SOUTH;
            }
            return EnumFacing.NORTH;
        }
    }
    
    private static class ChunkAnimation
    {
        long time;
        EnumFacing facing;
        
        ChunkAnimation(final long time, final EnumFacing facing) {
            super();
            this.time = time;
            this.facing = facing;
        }
    }
    
    public static class RenderChunk0
    {
        public RenderChunk chunk;
        
        public RenderChunk0(final RenderChunk chunk) {
            super();
            this.chunk = chunk;
        }
    }
}
