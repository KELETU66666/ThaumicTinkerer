package com.nekokittygames.thaumictinkerer.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

import javax.annotation.Nullable;
import java.util.Random;

public class TileInfusedFarmland extends TileEntity implements IAspectContainer {
    
    public static final int MAX_ASPECTS = 20;
    private static final String NBT_ASPECT_LIST = "aspectList";
    
    public AspectList aspectList = new AspectList();

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeCustomNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readCustomNBT(compound);
    }

    public void writeCustomNBT(NBTTagCompound compound) {
        NBTTagCompound aspectCompound = new NBTTagCompound();
        aspectList.writeToNBT(aspectCompound);
        compound.setTag(NBT_ASPECT_LIST, aspectCompound);
    }

    public void readCustomNBT(NBTTagCompound compound) {
        if (compound.hasKey(NBT_ASPECT_LIST)) {
            aspectList = new AspectList();
            aspectList.readFromNBT(compound.getCompoundTag(NBT_ASPECT_LIST));
        }
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

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        writeCustomNBT(compound);
        return new SPacketUpdateTileEntity(pos, -999, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readCustomNBT(pkt.getNbtCompound());
        world.markBlockRangeForRenderUpdate(pos, pos);
    }

    public void reduceSaturatedAspects() {
        int sum = aspectList.aspects.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
                
        if (sum > MAX_ASPECTS) {
            int toRemove = sum - MAX_ASPECTS;
            Random rand = new Random();
            
            while (toRemove > 0) {
                Aspect[] aspects = aspectList.getAspects();
                if (aspects.length == 0) break;
                
                Aspect target = aspects[rand.nextInt(aspects.length)];
                aspectList.remove(target, 1);
                toRemove--;
            }
            markDirty();
        }
    }

    // IAspectContainer implementation
    @Override
    public AspectList getAspects() {
        return aspectList;
    }

    @Override
    public void setAspects(AspectList aspects) {
        this.aspectList = aspects;
        markDirty();
    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return false;
    }

    @Override
    public int addToContainer(Aspect aspect, int amount) {
        return 0;
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int amount) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList aspects) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int amount) {
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList aspects) {
        return false;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return 0;
    }
}