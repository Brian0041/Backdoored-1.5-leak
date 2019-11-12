package com.backdoored.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import com.backdoored.event.ChunkRender;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.backdoored.hacks.client.ChunkAnim;
import net.minecraft.client.renderer.chunk.RenderChunk;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RenderChunk.class })
public class MixinRenderChunk
{
    private ChunkAnim.RenderChunk0 renderChunk;
    
    public MixinRenderChunk() {
        super();
        this.renderChunk = new ChunkAnim.RenderChunk0((RenderChunk)this);
    }
    
    @Inject(method = { "setPosition" }, at = { @At("HEAD") })
    public void setPosition(final int x, final int y, final int z, final CallbackInfo ci) {
        final ChunkRender.SetOrigin event = new ChunkRender.SetOrigin(this.renderChunk.chunk, x, y, z);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
}
