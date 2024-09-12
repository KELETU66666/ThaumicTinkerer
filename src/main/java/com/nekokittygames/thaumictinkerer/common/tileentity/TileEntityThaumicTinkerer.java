package com.nekokittygames.thaumictinkerer.common.tileentity;

import baubles.common.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import thaumcraft.common.lib.network.tiles.PacketTileToClient;

import javax.annotation.Nullable;

public abstract class TileEntityThaumicTinkerer extends TileEntity {

    private boolean redstonePowered;

    public void sendUpdates() {
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);

        markDirty();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        writeExtraNBT(compound);
        return super.writeToNBT(compound);

    }

    public void writeExtraNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setBoolean("redstone", redstonePowered);
    }

    public void sendMessageToClient(NBTTagCompound nbt, @Nullable EntityPlayerMP player) {
        if (player == null) {
            if (getWorld() != null) {
                PacketHandler.INSTANCE.sendToAllAround(new PacketTileToClient(getPos(), nbt), new NetworkRegistry.TargetPoint(getWorld().provider.getDimension(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 128.0));
            }
        }
        else {
            PacketHandler.INSTANCE.sendTo(new PacketTileToClient(getPos(), nbt), player);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        readExtraNBT(compound);
        super.readFromNBT(compound);
    }

    public void readExtraNBT(NBTTagCompound nbttagcompound) {
        // todo: remove if in a couple versions time
        if (nbttagcompound.hasKey("redstone"))
            redstonePowered = nbttagcompound.getBoolean("redstone");
        else
            redstonePowered = false;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound cmp = super.getUpdateTag();
        writeExtraNBT(cmp);
        return cmp;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
        readExtraNBT(tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
        sendUpdates();
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 5, this.getUpdateTag());
    }

    public abstract boolean respondsToPulses();

    public void activateOnPulse() {
        // Empty
    }

    public boolean getRedstonePowered() {
        return redstonePowered;
    }

    public void setRedstonePowered(boolean b) {
        boolean oldRedstone = redstonePowered;
        redstonePowered = b;
        if (redstonePowered != oldRedstone)
            this.sendUpdates();
    }

    public boolean canRedstoneConnect() {
        return false;
    }
}
