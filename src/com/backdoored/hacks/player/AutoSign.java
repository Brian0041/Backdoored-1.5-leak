package com.backdoored.hacks.player;

import net.minecraft.util.math.BlockPos;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUpdateSign;
import com.backdoored.utils.WorldUtils;
import net.minecraft.init.Items;
import net.minecraft.util.text.TextComponentString;
import com.backdoored.gui.CategoriesInit;
import net.minecraft.util.text.ITextComponent;
import com.backdoored.hacks.BaseHack;

public class AutoSign extends BaseHack
{
    private ITextComponent[] text;
    
    public AutoSign() {
        super("AutoSign", CategoriesInit.PLAYER, "Automatically place signs with text");
        this.text = new ITextComponent[] { (ITextComponent)new TextComponentString("test"), (ITextComponent)new TextComponentString("test"), (ITextComponent)new TextComponentString("test"), (ITextComponent)new TextComponentString("test") };
    }
    
    public void onEnabled() {
        final BlockPos target = AutoSign.mc.field_71476_x.func_178782_a().func_177972_a(AutoSign.mc.field_71476_x.field_178784_b);
        final int oldSlot = AutoSign.mc.field_71439_g.field_71071_by.field_70461_c;
        AutoSign.mc.field_71439_g.field_71071_by.field_70461_c = WorldUtils.findItem(Items.field_151155_ap);
        if (AutoSign.mc.field_71441_e.func_180495_p(target).func_185904_a().func_76222_j()) {
            WorldUtils.placeBlockMainHand(target);
        }
        AutoSign.mc.field_71439_g.func_71053_j();
        AutoSign.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUpdateSign(target, this.text));
        AutoSign.mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
        this.setEnabled(false);
    }
}
