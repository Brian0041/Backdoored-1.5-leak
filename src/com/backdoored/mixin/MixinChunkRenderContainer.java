package com.backdoored.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import com.backdoored.event.ChunkRender;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.ChunkRenderContainer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ChunkRenderContainer.class })
public class MixinChunkRenderContainer
{
    public MixinChunkRenderContainer() {
        super();
    }
    
    @Inject(method = { "preRenderChunk" }, at = { @At("HEAD") })
    public void preRenderChunk(final RenderChunk renderChunkIn, final CallbackInfo ci) {
        final ChunkRender.Pre event = new ChunkRender.Pre(renderChunkIn);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
}
