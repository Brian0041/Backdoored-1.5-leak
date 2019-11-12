package com.backdoored.hacks.combat;

import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.math.AxisAlignedBB;
import com.backdoored.utils.RenderUtils;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import java.util.Comparator;
import net.minecraft.entity.item.EntityEnderCrystal;
import java.util.Iterator;
import java.util.List;
import com.backdoored.utils.WorldUtils;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import com.backdoored.utils.FriendUtils;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import net.minecraft.init.Items;
import com.backdoored.gui.CategoriesInit;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class AutoCrystal extends BaseHack
{
    private Setting placePerTick;
    private Setting shouldPlace;
    private Setting shouldBreak;
    private Setting shouldSwitch;
    private Setting noGappleSwitch;
    private Setting dontCancelMining;
    private Setting placeRange;
    private Setting breakRange;
    private Setting throughWallsRange;
    private Setting minDamage;
    private BlockPos currentTarget;
    private Entity currentEntTarget;
    private long systemTime;
    private double[] damage;
    
    public AutoCrystal() {
        super("Auto Crystal", CategoriesInit.COMBAT, "Auto Place Crystals, Modified Kami paste");
        this.systemTime = -1L;
        this.damage = new double[] { 0.0, 0.0 };
        this.placePerTick = new Setting("Place Per Tick", this, 1, 0, 6);
        this.shouldPlace = new Setting("Place", this, true);
        this.shouldBreak = new Setting("Break", this, true);
        this.shouldSwitch = new Setting("Switch", this, true);
        this.noGappleSwitch = new Setting("No Gapple Switch", this, true);
        this.dontCancelMining = new Setting("Dont cancel mining", this, true);
        this.placeRange = new Setting("Place Range", this, 4.0, 0.0, 10.0);
        this.breakRange = new Setting("Break Range", this, 4.0, 0.0, 10.0);
        this.throughWallsRange = new Setting("Raytrace Place Range", this, 3.0, 0.0, 10.0);
        this.minDamage = new Setting("Min Damage", this, 4.0, 0.0, 20.0);
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        if (this.shouldBreak.getValBoolean()) {
            this.breakCrystals();
        }
        if (this.shouldPlace.getValBoolean()) {
            for (int i = 0; i < this.placePerTick.getValInt(); ++i) {
                this.placeCrystals();
            }
        }
    }
    
    private void placeCrystals() {
        this.currentTarget = null;
        this.currentEntTarget = null;
        final boolean gapplingAllow = !this.noGappleSwitch.getValBoolean() || AutoCrystal.mc.field_71439_g.func_184607_cu().func_77973_b() != Items.field_151153_ao;
        final boolean miningAllow = !this.dontCancelMining.getValBoolean() || AutoCrystal.mc.field_71439_g.func_184607_cu().func_77973_b() != Items.field_151046_w;
        if (!gapplingAllow || !miningAllow) {
            return;
        }
        final List<BlockPos> validBlocks = this.findAvailableCrystalBlocks();
        final List<EntityPlayer> targets = new ArrayList<EntityPlayer>();
        for (final EntityPlayer player : AutoCrystal.mc.field_71441_e.field_73010_i) {
            if (!FriendUtils.isFriend(player)) {
                targets.add(player);
            }
        }
        double bestDamage = 0.1;
        double bestSelfDamage = 1000.0;
        BlockPos bestSpot = null;
        for (final EntityPlayer player2 : targets) {
            if (!player2.func_110124_au().equals(AutoCrystal.mc.field_71439_g.func_110124_au())) {
                if (player2.field_70128_L) {
                    continue;
                }
                for (final BlockPos blockPos : validBlocks) {
                    if (player2.func_174818_b(blockPos) >= 169.0) {
                        continue;
                    }
                    final double enemyDamage = calculateDamage(blockPos.func_177958_n() + 0.5, blockPos.func_177956_o() + 1, blockPos.func_177952_p() + 0.5, (Entity)player2) / 10.0f;
                    final double selfDamage = calculateDamage(blockPos.func_177958_n() + 0.5, blockPos.func_177956_o() + 1, blockPos.func_177952_p() + 0.5, (Entity)AutoCrystal.mc.field_71439_g) / 10.0f;
                    if (enemyDamage < this.minDamage.getValDouble()) {
                        continue;
                    }
                    boolean matchesThroughWallsRange = true;
                    final RayTraceResult result = AutoCrystal.mc.field_71441_e.func_72933_a(new Vec3d(AutoCrystal.mc.field_71439_g.field_70165_t, AutoCrystal.mc.field_71439_g.field_70163_u + AutoCrystal.mc.field_71439_g.func_70047_e(), AutoCrystal.mc.field_71439_g.field_70161_v), new Vec3d(blockPos.func_177958_n() + 0.5, blockPos.func_177956_o() - 0.5, blockPos.func_177952_p() + 0.5));
                    matchesThroughWallsRange = ((result != null && result.field_72313_a == RayTraceResult.Type.BLOCK) || AutoCrystal.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p()) <= this.throughWallsRange.getValDouble());
                    final boolean matchesRange = AutoCrystal.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p()) <= this.placeRange.getValDouble();
                    if (!matchesRange || !matchesThroughWallsRange) {
                        continue;
                    }
                    if (enemyDamage > bestDamage) {
                        bestDamage = enemyDamage;
                        bestSelfDamage = selfDamage;
                        bestSpot = blockPos;
                        this.currentEntTarget = (Entity)player2;
                        this.currentTarget = blockPos;
                    }
                    else {
                        if (enemyDamage != bestDamage || selfDamage >= bestSelfDamage) {
                            continue;
                        }
                        bestDamage = enemyDamage;
                        bestSelfDamage = selfDamage;
                        bestSpot = blockPos;
                        this.currentEntTarget = (Entity)player2;
                        this.currentTarget = blockPos;
                    }
                }
            }
        }
        if (bestDamage < this.minDamage.getValDouble() || bestSpot == null) {
            return;
        }
        final boolean isHoldingCrystal = AutoCrystal.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP || AutoCrystal.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP;
        if (!isHoldingCrystal && !this.shouldSwitch.getValBoolean()) {
            return;
        }
        if (isHoldingCrystal) {
            this.damage[0] = bestDamage;
            this.damage[1] = bestSelfDamage;
            final RayTraceResult result2 = AutoCrystal.mc.field_71441_e.func_72933_a(new Vec3d(AutoCrystal.mc.field_71439_g.field_70165_t, AutoCrystal.mc.field_71439_g.field_70163_u + AutoCrystal.mc.field_71439_g.func_70047_e(), AutoCrystal.mc.field_71439_g.field_70161_v), new Vec3d(bestSpot.func_177958_n() + 0.5, bestSpot.func_177956_o() - 0.5, bestSpot.func_177952_p() + 0.5));
            EnumFacing face;
            if (result2 == null || result2.field_178784_b == null) {
                face = EnumFacing.UP;
            }
            else {
                face = result2.field_178784_b;
            }
            EnumHand hand = EnumHand.MAIN_HAND;
            if (AutoCrystal.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) {
                hand = EnumHand.OFF_HAND;
            }
            final Vec3d hitVec = new Vec3d((Vec3i)bestSpot).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(face.func_176730_m()).func_186678_a(0.5));
            AutoCrystal.mc.field_71442_b.func_187099_a(AutoCrystal.mc.field_71439_g, AutoCrystal.mc.field_71441_e, bestSpot, face, hitVec, hand);
            AutoCrystal.mc.field_71439_g.func_184609_a(hand);
            return;
        }
        final int index = WorldUtils.findItem(Items.field_185158_cP);
        if (index >= 0) {
            AutoCrystal.mc.field_71439_g.field_71071_by.field_70461_c = index;
        }
    }
    
    private void breakCrystals() {
        final EntityEnderCrystal crystal = (EntityEnderCrystal)AutoCrystal.mc.field_71441_e.field_72996_f.stream().filter(entity -> entity instanceof EntityEnderCrystal).map(entity -> entity).min(Comparator.comparing(c -> AutoCrystal.mc.field_71439_g.func_70032_d(c))).orElse(null);
        if (crystal != null && AutoCrystal.mc.field_71439_g.func_70032_d((Entity)crystal) <= this.breakRange.getValDouble() && System.nanoTime() / 1000000L - this.systemTime >= 250L) {
            AutoCrystal.mc.field_71442_b.func_78764_a((EntityPlayer)AutoCrystal.mc.field_71439_g, (Entity)crystal);
            AutoCrystal.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            this.systemTime = System.nanoTime() / 1000000L;
        }
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        RenderUtils.glStart(255.0f, 255.0f, 255.0f, 1.0f);
        if (this.currentTarget != null) {
            final AxisAlignedBB bb = RenderUtils.getBoundingBox(this.currentTarget);
            RenderUtils.drawOutlinedBox(bb);
        }
        if (this.currentEntTarget != null) {
            RenderUtils.drawOutlinedBox(this.currentEntTarget.func_174813_aQ());
        }
        RenderUtils.glEnd();
    }
    
    public void onRender() {
    }
    
    public void onDisabled() {
        this.currentTarget = null;
        this.currentEntTarget = null;
    }
    
    private List<BlockPos> findAvailableCrystalBlocks() {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.func_191196_a();
        positions.addAll((Collection)this.getSphere(new BlockPos(Math.floor(AutoCrystal.mc.field_71439_g.field_70165_t), Math.floor(AutoCrystal.mc.field_71439_g.field_70163_u), Math.floor(AutoCrystal.mc.field_71439_g.field_70161_v)), (float)this.placeRange.getValDouble(), (int)Math.round(this.placeRange.getValDouble()), false, true, 0).stream().filter((Predicate<? super Object>)this::canPlaceCrystal).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
    }
    
    private List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.func_177958_n();
        final int cy = loc.func_177956_o();
        final int cz = loc.func_177952_p();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }
    
    private boolean canPlaceCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.func_177982_a(0, 1, 0);
        final BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
        return (AutoCrystal.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150357_h || AutoCrystal.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150343_Z) && AutoCrystal.mc.field_71441_e.func_180495_p(boost).func_177230_c() == Blocks.field_150350_a && AutoCrystal.mc.field_71441_e.func_180495_p(boost2).func_177230_c() == Blocks.field_150350_a && AutoCrystal.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty();
    }
    
    private static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.func_70011_f(posX, posY, posZ) / 12.0;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        final double blockDensity = entity.field_70170_p.func_72842_a(vec3d, entity.func_174813_aQ());
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * 12.0 + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)AutoCrystal.mc.field_71441_e, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    private static float getBlastReduction(final EntityLivingBase entity, float damage, final Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            damage = CombatRules.func_189427_a(damage, (float)ep.func_70658_aO(), (float)ep.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
            return damage;
        }
        damage = CombatRules.func_189427_a(damage, (float)entity.func_70658_aO(), (float)entity.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
        return damage;
    }
    
    private static float getDamageMultiplied(final float damage) {
        final int diff = AutoCrystal.mc.field_71441_e.func_175659_aa().func_151525_a();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    private static /* synthetic */ Float lambda$breakCrystals$2(final EntityEnderCrystal c) {
        return AutoCrystal.mc.field_71439_g.func_70032_d((Entity)c);
    }
    
    private static /* synthetic */ EntityEnderCrystal lambda$breakCrystals$1(final Entity entity) {
        return (EntityEnderCrystal)entity;
    }
    
    private static /* synthetic */ boolean lambda$breakCrystals$0(final Entity entity) {
        return entity instanceof EntityEnderCrystal;
    }
    
    public static final class GeometryMasks
    {
        public GeometryMasks() {
            super();
        }
        
        final class Quad
        {
            static final int DOWN = 1;
            static final int UP = 2;
            static final int NORTH = 4;
            static final int SOUTH = 8;
            static final int WEST = 16;
            static final int EAST = 32;
            static final int ALL = 63;
            final /* synthetic */ GeometryMasks this$0;
            
            Quad(final GeometryMasks this$0) {
                this.this$0 = this$0;
                super();
            }
        }
        
        public final class Line
        {
            static final int DOWN_WEST = 17;
            static final int UP_WEST = 18;
            static final int DOWN_EAST = 33;
            static final int UP_EAST = 34;
            static final int DOWN_NORTH = 5;
            static final int UP_NORTH = 6;
            static final int DOWN_SOUTH = 9;
            static final int UP_SOUTH = 10;
            static final int NORTH_WEST = 20;
            static final int NORTH_EAST = 36;
            static final int SOUTH_WEST = 24;
            static final int SOUTH_EAST = 40;
            static final int ALL = 63;
            final /* synthetic */ GeometryMasks this$0;
            
            public Line(final GeometryMasks this$0) {
                this.this$0 = this$0;
                super();
            }
        }
    }
}
