package com.backdoored.hacks.player;

import java.util.Iterator;
import java.util.Collection;
import net.minecraft.world.chunk.Chunk;
import com.google.common.collect.UnmodifiableIterator;
import com.backdoored.utils.Utils;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.server.SPacketChunkData;
import com.backdoored.event.PacketRecieved;
import net.minecraft.tileentity.TileEntity;
import java.util.List;
import com.backdoored.gui.CategoriesInit;
import java.io.File;
import com.backdoored.hacks.BaseHack;

public class ChunkLogger extends BaseHack
{
    private File file;
    
    public ChunkLogger() {
        super("Chunk Logger", CategoriesInit.PLAYER, "Log chunks that contain a specified ammount of chests");
        this.file = new File("Backdoored/ChunkLogs.txt");
    }
    
    public void onEnabled() {
        this.setEnabled(false);
    }
    
    public void onUpdate() {
        final List<TileEntity> tileEntities = (List<TileEntity>)ChunkLogger.mc.field_71441_e.field_147482_g;
        tileEntities.stream().filter(p -> ChunkLogger.mc.field_71439_g.func_70011_f((double)p.func_174877_v().func_177958_n(), ChunkLogger.mc.field_71439_g.field_70163_u, (double)p.func_174877_v().func_177952_p()) < 500.0);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketRecieved event) {
        if (event.packet instanceof SPacketChunkData) {
            final SPacketChunkData sPacketChunkData = (SPacketChunkData)event.packet;
        }
    }
    
    @SubscribeEvent
    public void onLoadChunk(final ChunkEvent.Load event) {
        if (!this.getEnabled()) {
            return;
        }
        for (final ChunkPos chunkPos : ChunkLogger.mc.field_71441_e.getPersistentChunks().keys()) {
            final Chunk chunk = ChunkLogger.mc.field_71441_e.func_72964_e(chunkPos.field_77276_a, chunkPos.field_77275_b);
            final Collection<TileEntity> tilesInChunk = chunk.func_177434_r().values();
            System.out.println(chunk.field_76635_g * 16 + " " + chunk.field_76647_h * 16);
            System.out.println(chunk.func_177434_r().size());
            System.out.println(chunk.func_177434_r().values().size());
            if (chunk.func_177434_r().size() < 1) {
                return;
            }
            int chests = 0;
            System.out.println("tiles: " + chunk.func_177434_r().size());
            for (final TileEntity tileEntity : tilesInChunk) {
                System.out.println(tileEntity instanceof TileEntityChest);
                System.out.println(tileEntity.func_174877_v());
                if (tileEntity instanceof TileEntityChest) {
                    ++chests;
                }
            }
            int beds = 0;
            for (final TileEntity tileEntity2 : tilesInChunk) {
                if (tileEntity2 instanceof TileEntityBed) {
                    ++beds;
                }
            }
            boolean endPortals = false;
            for (final TileEntity tileEntity3 : tilesInChunk) {
                if (tileEntity3 instanceof TileEntityEndPortal) {
                    endPortals = true;
                    break;
                }
            }
            System.out.printf("\nChunk Loaded %d %d %s", chests, beds, String.valueOf(endPortals));
            if (chests <= 0 && beds <= 0 && !endPortals) {
                continue;
            }
            final long millis = System.currentTimeMillis();
            final Date date = new Date(millis);
            String ip = "Singleplayer";
            if (ChunkLogger.mc.func_147104_D() != null) {
                ip = ChunkLogger.mc.func_147104_D().field_78845_b;
            }
            final String line = String.format("(%s) %s %s: %d chests, %d beds, %d end portals", date, ip, "(" + chunk.field_76635_g * 16 + ", " + chunk.field_76647_h * 16 + ")", chests, beds, endPortals ? 1 : 0);
            try {
                final FileWriter fr = new FileWriter(this.file, true);
                final BufferedWriter br = new BufferedWriter(fr);
                br.write(line + "\n");
                br.close();
                fr.close();
                System.out.println("Found Chunk " + line);
                Utils.printMessage("Found Chunk " + line);
            }
            catch (Exception e) {
                System.out.println(line);
                e.printStackTrace();
            }
        }
    }
    
    private static /* synthetic */ boolean lambda$onUpdate$0(final TileEntity p) {
        return ChunkLogger.mc.field_71439_g.func_70011_f((double)p.func_174877_v().func_177958_n(), ChunkLogger.mc.field_71439_g.field_70163_u, (double)p.func_174877_v().func_177952_p()) < 500.0;
    }
}
