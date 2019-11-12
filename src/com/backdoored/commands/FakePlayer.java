package com.backdoored.commands;

import com.backdoored.utils.Utils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import com.backdoored.api.MojangWebApi;

public class FakePlayer extends CommandBase
{
    public FakePlayer() {
        super("fakeplayer");
    }
    
    @Override
    public boolean execute(final String[] args) {
        try {
            if (args.length < 1) {
                return false;
            }
            final UUID playerUUID = UUID.fromString(MojangWebApi.grabRealUUID(args[0]));
            System.out.print("UUID LOCATED: " + playerUUID.toString());
            final EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP((World)this.mc.field_71441_e, new GameProfile(playerUUID, args[0]));
            fakePlayer.func_82149_j((Entity)this.mc.field_71439_g);
            final NBTTagCompound nbttagcompound = this.mc.field_71439_g.func_189511_e(new NBTTagCompound());
            fakePlayer.func_70020_e(nbttagcompound);
            final int[] array;
            final int[] georgeBush420Did911 = array = new int[] { -21, -69, -911, -420, -666, -2003 };
            for (final int georgeBush : array) {
                if (this.mc.field_71441_e.func_73045_a(georgeBush) == null) {
                    this.mc.field_71441_e.func_73027_a(georgeBush, (Entity)fakePlayer);
                    return true;
                }
            }
            for (int casualties = -1; casualties > -400; --casualties) {
                if (this.mc.field_71441_e.func_73045_a(casualties) == null) {
                    this.mc.field_71441_e.func_73027_a(casualties, (Entity)fakePlayer);
                    return true;
                }
            }
            Utils.printMessage("No entity ids available", "gold");
            return false;
        }
        catch (Exception e) {
            Utils.printMessage(e.getMessage(), "gold");
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public String getSyntax() {
        return "-fakeplayer DanTDM";
    }
}
