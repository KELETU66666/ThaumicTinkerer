/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Jan 10, 2014, 3:56:13 PM (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.tileentity.Kami;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.items.Kami.ItemSkyPearl;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.util.Constants;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.lib.SoundsTC;

import java.util.Arrays;
import java.util.List;

public class TileWarpGate extends TileEntity implements IInventory, ITickable {

    private static final String TAG_LOCKED = "locked";

    public boolean locked = false;
    boolean teleportedThisTick = false;
    ItemStack[] inventorySlots = new ItemStack[10];

    public TileWarpGate (){
        Arrays.fill(inventorySlots, ItemStack.EMPTY);
    }

    public static boolean teleportPlayer(EntityPlayer player, BlockPos coords) {
        int x = coords.getX();
        int y = coords.getY();
        int z = coords.getZ();

        TileEntity tile = player.world.getTileEntity(coords);
        if (tile != null && tile instanceof TileWarpGate) {
            TileWarpGate destGate = (TileWarpGate) tile;
            if (!destGate.locked) {
                if(player.world.isRemote) {
                    player.world.playSound(null, player.getPosition(), SoundsTC.wand, SoundCategory.PLAYERS, 1F, 1F);

                    for (int i = 0; i < 20; i++)
                        FXDispatcher.INSTANCE.sparkle(
                                (float) player.posX + player.world.rand.nextFloat() - 0.5F,
                                (float) player.posY + player.world.rand.nextFloat(),
                                (float) player.posZ + player.world.rand.nextFloat() - 0.5F,
                                6, 3, 3);
                }

                    player.dismountRidingEntity();
                    if (player instanceof EntityPlayerMP)
                        player.setPositionAndUpdate(x + 0.5, y + 1.6, z + 0.5);

                if(player.world.isRemote) {
                    for (int i = 0; i < 20; i++)
                        FXDispatcher.INSTANCE.sparkle(
                                (float) player.posX + player.world.rand.nextFloat() - 0.5F,
                                (float) player.posY + player.world.rand.nextFloat(),
                                (float) player.posZ + player.world.rand.nextFloat() - 0.5F,
                                6, 3, 3);
                }

                player.world.playSound(null, player.getPosition(), SoundsTC.wand, SoundCategory.PLAYERS, 1F, 0.1F);
                return true;
            } else
                if (!player.world.isRemote) player.sendMessage(new TextComponentString("ttmisc.noTeleport"));
        } else if (!player.world.isRemote) player.sendMessage(new TextComponentString("ttmisc.noDest"));

        return false;
    }

    @Override
    public void update() {
        List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 1.5, pos.getZ() + 1));

        if(world.isBlockLoaded(pos))
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);

        for (EntityPlayer player : players)
            for(int i = -1;i < 1;i++)
                for(int k = -1;k < 1;k++)
                    if (player != null && player.isSneaking() && world.getTileEntity(player.getPosition().down().add(i, 0, k)) == this) {
                        player.openGui(ThaumicTinkerer.instance, 2, world, pos.getX(), pos.getY(), pos.getZ());
            break;
        }

        teleportedThisTick = false;
    }

    public void teleportPlayer(EntityPlayer player, int index) {
        if (teleportedThisTick) return;

        ItemStack stack = index < getSizeInventory() ? getStackInSlot(index) : ItemStack.EMPTY;
        if (stack != ItemStack.EMPTY && ItemSkyPearl.isAttuned(stack)) {
            int x = ItemSkyPearl.getX(stack);
            int y = ItemSkyPearl.getY(stack);
            int z = ItemSkyPearl.getZ(stack);

            if (teleportPlayer(player, new BlockPos(x, y, z))) teleportedThisTick = true;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);

        readCustomNBT(par1NBTTagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        writeCustomNBT(par1NBTTagCompound);

        return par1NBTTagCompound;
    }

    public void readCustomNBT(NBTTagCompound par1NBTTagCompound) {
        locked = par1NBTTagCompound.getBoolean(TAG_LOCKED);

        NBTTagList var2 = par1NBTTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        inventorySlots = new ItemStack[getSizeInventory()];
        Arrays.fill(inventorySlots, ItemStack.EMPTY);
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < inventorySlots.length) inventorySlots[var5] = new ItemStack(var4);
        }
    }

    public void writeCustomNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setBoolean(TAG_LOCKED, locked);

        NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < inventorySlots.length; ++var3) {
            if (inventorySlots[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                inventorySlots[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        par1NBTTagCompound.setTag("Items", var2);
    }

    @Override
    public int getSizeInventory() {
        return inventorySlots.length;
    }

    @Override
    public boolean isEmpty() {
        return inventorySlots==null;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventorySlots[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (inventorySlots[i] != ItemStack.EMPTY) {
            ItemStack stackAt;

            if (inventorySlots[i].getCount() <= j) {
                stackAt = inventorySlots[i];
                inventorySlots[i] = ItemStack.EMPTY;
                return stackAt;
            } else {
                stackAt = inventorySlots[i].splitStack(j);

                if (inventorySlots[i].getCount() == 0) inventorySlots[i] = ItemStack.EMPTY;

                return stackAt;
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int i) {
        ItemStack itemStack= ItemStackHelper.getAndRemove(Arrays.asList(inventorySlots),i);
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventorySlots[i] = itemstack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityplayer) {
        return world.getTileEntity(pos) == this
                && entityplayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
    }

    @Override
    public void openInventory(EntityPlayer var1) {}

    @Override
    public void closeInventory(EntityPlayer var1) {}

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return itemstack != null && itemstack.getItem() == ModItems.sky_pearl;
    }

    @Override
    public int getField(int i) {
        return 0;
    }

    @Override
    public void setField(int i, int i1) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

   @Override
   public SPacketUpdateTileEntity getUpdatePacket() {
       NBTTagCompound nbttagcompound = new NBTTagCompound();
       writeCustomNBT(nbttagcompound);
       return new SPacketUpdateTileEntity(pos, -999, nbttagcompound);
   }

    @Override
    public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
        super.onDataPacket(manager, packet);
        readCustomNBT(packet.getNbtCompound());
    }

    @Override
    public String getName() {
        return "container.warpgate";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}