package com.backdoored.commands;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.utils.Utils;
import com.backdoored.utils.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.util.math.BlockPos;

public class NomadBase extends CommandBase
{
    boolean isBuilding;
    int tickDelay;
    boolean delay;
    int tickCount;
    BlockPos[] base;
    
    public NomadBase() {
        super(new String[] { "nomadbase", "fitbase", "autonomadbase" });
        this.isBuilding = false;
        this.tickDelay = 0;
        this.delay = false;
        this.tickCount = 0;
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length == 0) {
            this.tickCount = 0;
            this.isBuilding = true;
        }
        if ((args.length > 1 && args[0].equalsIgnoreCase("delay")) || args[0].equalsIgnoreCase("setdelay")) {
            this.tickDelay = Integer.valueOf(args[1]);
            if (this.tickDelay == 0) {
                this.delay = false;
            }
            else {
                this.delay = true;
            }
        }
        return true;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (!this.isBuilding) {
            return;
        }
        if (this.delay && this.tickCount % this.tickDelay != 0) {
            ++this.tickCount;
            return;
        }
        final BlockPos[] floor = { new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, -1, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, -1, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, -1, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, -1, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, -1, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, -1, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, -1, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, -1, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, -1, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, -1, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, -1, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, -1, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, -1, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, -1, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, -1, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, -1, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, -1, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, -1, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, -1, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, -1, -1)) };
        final BlockPos[] secondLayer = { new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 1, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 1, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 1, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 1, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 1, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 1, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 1, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 1, -2)) };
        final BlockPos[] thirdLayer1 = { new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 2, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 2, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 2, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 2, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 2, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 2, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 2, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 2, -2)) };
        final BlockPos[] thirdLayer2 = { new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 2, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 2, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 2, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 2, -2)) };
        final int direction = MathHelper.func_76128_c(this.mc.field_71439_g.field_70177_z * 4.0f / 360.0f + 0.5) & 0x3;
        BlockPos[] base;
        BlockPos[] roof;
        if (direction == 0) {
            base = new BlockPos[] { new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 0, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 0, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 0, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 0, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 0, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 0, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 0, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 0, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 0, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 0, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 0, -2)) };
            roof = new BlockPos[] { new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 3, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 3, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 3, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 3, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 3, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 3, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 3, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 3, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 3, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 3, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 3, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 3, 1)) };
        }
        else if (direction == 1) {
            base = new BlockPos[] { new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 0, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 0, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 0, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 0, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 0, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 0, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 0, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 0, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 0, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 0, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 0, -2)) };
            roof = new BlockPos[] { new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 3, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 3, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 3, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 3, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 3, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 3, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 3, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 3, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 3, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 3, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 3, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 3, -1)) };
        }
        else if (direction == 2) {
            base = new BlockPos[] { new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 0, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 0, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 0, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 0, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 0, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 0, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 0, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 0, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 0, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 0, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 0, -2)) };
            roof = new BlockPos[] { new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 3, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 3, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 3, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 3, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 3, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 3, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 3, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 3, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 3, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 3, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 3, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 3, -1)) };
        }
        else {
            base = new BlockPos[] { new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 0, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 0, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(2, 0, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 0, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 0, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 0, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 0, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 0, 2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 0, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 0, -2)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 0, -2)) };
            roof = new BlockPos[] { new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 3, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 3, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-2, 3, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 3, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 3, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(-1, 3, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 3, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 3, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(0, 3, -1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 3, 0)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 3, 1)), new BlockPos((Vec3i)this.mc.field_71439_g.func_180425_c().func_177982_a(1, 3, -1)) };
        }
        final int oldSlot = this.mc.field_71439_g.field_71071_by.field_70461_c;
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            if (this.mc.field_71439_g.field_71071_by.func_70301_a(i) != ItemStack.field_190927_a && this.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() instanceof ItemBlock && Block.func_149634_a(this.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b()).func_176223_P().func_185913_b()) {
                newSlot = i;
                break;
            }
        }
        if (newSlot != -1) {
            this.mc.field_71439_g.field_71071_by.field_70461_c = newSlot;
            for (final BlockPos x : floor) {
                if (this.mc.field_71441_e.func_180495_p(x).func_185904_a().func_76222_j()) {
                    WorldUtils.placeBlockMainHand(x);
                    if (this.delay) {
                        ++this.tickCount;
                        return;
                    }
                }
            }
            for (final BlockPos x : base) {
                if (this.mc.field_71441_e.func_180495_p(x).func_185904_a().func_76222_j()) {
                    WorldUtils.placeBlockMainHand(x);
                    if (this.delay) {
                        ++this.tickCount;
                        return;
                    }
                }
            }
            for (final BlockPos x : secondLayer) {
                if (this.mc.field_71441_e.func_180495_p(x).func_185904_a().func_76222_j()) {
                    WorldUtils.placeBlockMainHand(x);
                    if (this.delay) {
                        ++this.tickCount;
                        return;
                    }
                }
            }
            for (final BlockPos x : thirdLayer1) {
                if (this.mc.field_71441_e.func_180495_p(x).func_185904_a().func_76222_j()) {
                    WorldUtils.placeBlockMainHand(x);
                    if (this.delay) {
                        ++this.tickCount;
                        return;
                    }
                }
            }
            for (final BlockPos x : thirdLayer2) {
                if (this.mc.field_71441_e.func_180495_p(x).func_185904_a().func_76222_j()) {
                    WorldUtils.placeBlockMainHand(x);
                    if (this.delay) {
                        ++this.tickCount;
                        return;
                    }
                }
            }
            for (final BlockPos x : roof) {
                if (this.mc.field_71441_e.func_180495_p(x).func_185904_a().func_76222_j()) {
                    WorldUtils.placeBlockMainHand(x);
                    if (this.delay) {
                        ++this.tickCount;
                        return;
                    }
                }
            }
            this.mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
            this.isBuilding = false;
            return;
        }
        Utils.printMessage("No blocks found in hotbar!", "red");
        this.isBuilding = false;
    }
    
    @Override
    public String getSyntax() {
        return "-nomadbase or -nomadbase setdelay <0/1/2/..> (6 is the best)";
    }
}
