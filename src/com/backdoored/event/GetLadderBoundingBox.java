package com.backdoored.event;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;

public class GetLadderBoundingBox extends BackdooredEvent
{
    public IBlockState state;
    public IBlockAccess source;
    public BlockPos pos;
    public CallbackInfoReturnable<AxisAlignedBB> cir;
    public PropertyDirection facing;
    
    public GetLadderBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos, final PropertyDirection facing, final CallbackInfoReturnable<AxisAlignedBB> cir) {
        super();
        this.state = state;
        this.source = source;
        this.pos = pos;
        this.facing = facing;
        this.cir = cir;
    }
}
