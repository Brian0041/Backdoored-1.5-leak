package com.backdoored.event;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;

public class RenderBlockSide extends BackdooredEvent
{
    public final IBlockState state;
    public final IBlockAccess world;
    public final BlockPos pos;
    public final EnumFacing side;
    
    public RenderBlockSide(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
        super();
        this.state = state;
        this.world = world;
        this.pos = pos;
        this.side = side;
    }
    
    public boolean isCancelable() {
        return true;
    }
}
