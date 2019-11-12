package com.backdoored.event;

import net.minecraft.client.renderer.chunk.RenderChunk;

public abstract class ChunkRender extends BackdooredEvent
{
    public ChunkRender() {
        super();
    }
    
    public static class Pre extends ChunkRender
    {
        public RenderChunk renderChunk;
        
        public Pre(final RenderChunk renderChunk) {
            super();
            this.renderChunk = renderChunk;
        }
    }
    
    public static class SetOrigin extends ChunkRender
    {
        public RenderChunk renderChunk;
        public int x;
        public int y;
        public int z;
        
        public SetOrigin(final RenderChunk chunk, final int x, final int y, final int z) {
            super();
            this.renderChunk = chunk;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
