package com.backdoored.event;

import net.minecraft.util.math.BlockPos;

public class MakeCubeOpaque extends BackdooredEvent
{
    public final BlockPos pos;
    
    public MakeCubeOpaque(final BlockPos pos) {
        super();
        this.pos = pos;
    }
    
    public boolean isCancelable() {
        return true;
    }
}
