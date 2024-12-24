package com.nekokittygames.thaumictinkerer.common.tileentity;

import com.nekokittygames.thaumictinkerer.common.blocks.BlockInfusedGrain;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

import javax.annotation.Nullable;
import java.util.Random;

public class TileInfusedGrain extends TileEntity implements IAspectContainer, ITickable {

    private static final String NBT_MAIN_ASPECT = "mainAspect";
    private static final String NBT_ASPECT_TENDENCIES = "aspectTendencies";

    public Aspect aspect;
    public AspectList primalTendencies = new AspectList();

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
        return nbt;
    }

    @Override
    public void update() {
        if (aspect == null) {
            aspect = Aspect.AIR;
        }
        if (primalTendencies == null) {
            primalTendencies = new AspectList();
            primalTendencies.merge(aspect, 1);
        }

        //Growth
        if (!world.isRemote && world.getLightFromNeighbors(pos.up()) >= 9) {
            IBlockState state = world.getBlockState(pos);
            int age = state.getValue(BlockInfusedGrain.AGE);
            if (age < 7) {
                if (world.rand.nextInt((((2510 - (int) Math.pow(primalTendencies.getAmount(Aspect.WATER), 2))) * 6)) == 0) {
                    world.setBlockState(pos, state.withProperty(BlockInfusedGrain.AGE, age + 1), 3);
                }
            }
        }

        //Aspect Exchange
        if (world.rand.nextInt((2550 - ((int) Math.pow(primalTendencies.getAmount(Aspect.AIR), 2))) * 10) == 0
                && !aspect.isPrimal()) {

            for (EnumFacing dir : EnumFacing.VALUES) {
                BlockPos neighborPos = pos.offset(dir);
                TileEntity entity = world.getTileEntity(neighborPos);

                if (entity instanceof TileInfusedGrain) {
                    TileInfusedGrain neighbor = (TileInfusedGrain) entity;

                    // Exchange with primal aspects
                    if (neighbor.aspect.isPrimal()) {
                        if (primalTendencies.getAmount(neighbor.aspect) < 5) {
                            primalTendencies.add(neighbor.aspect, 1);
                            reduceSaturatedAspects();
                            markDirty();

                            if (world.isRemote) {
                                spawnParticles(neighborPos, neighbor.aspect);
                            }
                            return;
                        }
                    }
                    // Exchange with compound aspects
                    else {
                        AspectList targetList = neighbor.primalTendencies;
                        if (targetList.getAspects().length == 0 || targetList.getAspects()[0] == null) {
                            return;
                        }

                        Aspect exchangeAspect = targetList.getAspects()[world.rand.nextInt(targetList.getAspects().length)];
                        if (targetList.getAmount(exchangeAspect) >= primalTendencies.getAmount(exchangeAspect)) {
                            primalTendencies.add(exchangeAspect, 1);
                            targetList.reduce(exchangeAspect, 1);
                            reduceSaturatedAspects();

                            if (world.isRemote) {
                                spawnParticles(neighborPos, exchangeAspect);
                            }
                            markDirty();
                        }
                        return;
                    }
                }
            }
        }
    }

    private void spawnParticles(BlockPos neighborPos, Aspect aspect) {
        //for (int i = 0; i < 50; i++) {
        //    ThaumicTinkerer.tcProxy.essentiaTrailFx(world,
        //            neighborPos.getX(), neighborPos.getY(), neighborPos.getZ(),
        //            pos.getX(), pos.getY(), pos.getZ(),
        //            50, aspect.getColor(), 1F);
        //}
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    public void reduceSaturatedAspects() {
        int sum = primalTendencies.aspects.values().stream().mapToInt(Integer::intValue).sum();
        if (sum > 50) {
            int toRemove = sum - 50;
            Random rand = new Random();
            while (toRemove > 0) {
                Aspect[] aspects = primalTendencies.getAspects();
                if (aspects.length == 0) break;
                Aspect target = aspects[rand.nextInt(aspects.length)];
                primalTendencies.remove(target, 1);
                toRemove--;
            }
        }
    }

    public void writeCustomNBT(NBTTagCompound nbt) {
        NBTTagCompound aspectCompound = new NBTTagCompound();
        if (aspect != null) {
            new AspectList().add(aspect, 1).writeToNBT(aspectCompound);
        }
        nbt.setTag(NBT_MAIN_ASPECT, aspectCompound);

        NBTTagCompound tendencyCompound = new NBTTagCompound();
        primalTendencies.writeToNBT(tendencyCompound);
        nbt.setTag(NBT_ASPECT_TENDENCIES, tendencyCompound);
    }

    public void readCustomNBT(NBTTagCompound nbt) {
        AspectList aspectList = new AspectList();
        aspectList.readFromNBT(nbt.getCompoundTag(NBT_MAIN_ASPECT));
        aspect = aspectList.getAspects().length > 0 ? aspectList.getAspects()[0] : null;

        primalTendencies = new AspectList();
        primalTendencies.readFromNBT(nbt.getCompoundTag(NBT_ASPECT_TENDENCIES));
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readCustomNBT(pkt.getNbtCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        writeCustomNBT(compound);
        return new SPacketUpdateTileEntity(pos, -999, compound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = super.getUpdateTag();
        writeCustomNBT(compound);
        return compound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound compound) {
        super.handleUpdateTag(compound);
        readCustomNBT(compound);
    }

    // IAspectContainer implementation
    @Override
    public AspectList getAspects() {
        return aspect != null ? new AspectList().add(aspect, 1) : null;
    }

    @Override
    public void setAspects(AspectList paramAspectList) {
        // Not implemented - aspects are set through other means
    }

    @Override
    public boolean doesContainerAccept(Aspect paramAspect) {
        return false;
    }

    @Override
    public int addToContainer(Aspect paramAspect, int paramInt) {
        return 0;
    }

    @Override
    public boolean takeFromContainer(Aspect paramAspect, int paramInt) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList paramAspectList) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect paramAspect, int paramInt) {
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList paramAspectList) {
        return false;
    }

    @Override
    public int containerContains(Aspect paramAspect) {
        return 0;
    }
}