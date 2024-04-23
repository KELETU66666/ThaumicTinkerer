package com.nekokittygames.thaumictinkerer.common.items.Kami;

import com.nekokittygames.thaumictinkerer.common.items.Kami.Tools.ToolHandler;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.items.TTItem;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import com.nekokittygames.thaumictinkerer.common.utils.ItemNBTHelper;
import com.nekokittygames.thaumictinkerer.common.utils.ToolModeHUDHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.lib.SoundsTC;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemPlacementMirror extends TTItem {

    @Deprecated
    private static final String TAG_BLOCK_ID = "blockID";

    private static final String TAG_BLOCK_NAME = "blockName";
    private static final String TAG_BLOCK_META = "blockMeta";
    private static final String TAG_SIZE = "size";

    public ItemPlacementMirror() {
        super(LibItemNames.PLACEMENT_MIRROR);
        setMaxStackSize(1);
    }

    public static boolean hasBlocks(ItemStack stack, EntityPlayer player, BlockPos[] blocks) {
        if (player.capabilities.isCreativeMode) return true;

        int required = blocks.length;
        int current = 0;
        ItemStack reqStack = new ItemStack(getBlock(stack), 1, getBlockMeta(stack));
        List<ItemStack> talismansToCheck = new ArrayList<>();
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stackInSlot = player.inventory.getStackInSlot(i);
            if (stackInSlot != ItemStack.EMPTY && stackInSlot.getItem() == reqStack.getItem()
                    && stackInSlot.getItemDamage() == reqStack.getItemDamage()) {
                current += stackInSlot.getCount();
                if (current >= required) return true;
            }
            if (stackInSlot != ItemStack.EMPTY
                    && stackInSlot.getItem() == ModItems.block_talisman)
                talismansToCheck.add(stackInSlot);
        }

        for (ItemStack talisman : talismansToCheck) {
            Block block = ItemBlockTalisman.getBlock(talisman);
            int meta = ItemBlockTalisman.getBlockMeta(talisman);

            if (Item.getItemFromBlock(block) == reqStack.getItem() && meta == reqStack.getItemDamage()) {
                current += ItemBlockTalisman.getBlockCount(talisman);

                if (current >= required) return true;
            }
        }

        return false;
    }

    public static BlockPos[] getBlocksToPlace(ItemStack stack, EntityPlayer player) {
        List<BlockPos> coords = new ArrayList<>();
        RayTraceResult pos = ToolHandler.raytraceFromEntity(player.world, player, true, 5);
        if (pos != null) {
            Block block = player.world.getBlockState(pos.getBlockPos()).getBlock();
            if (block != null && block.isReplaceable(player.world, pos.getBlockPos()))
                pos.subHit--;

            //TODO
            EnumFacing dir = pos.sideHit;
            int rotation = MathHelper.floor(player.rotationYaw * 4F / 360F + 0.5D) & 3;
            int range = (getSize(stack) ^ 1) / 2;

            boolean topOrBottom = dir == EnumFacing.UP || dir == EnumFacing.DOWN;

            int xOff = !(dir == EnumFacing.WEST || dir == EnumFacing.EAST)
                    ? topOrBottom ? player.rotationPitch > 75 || (rotation & 1) == 0 ? range : 0 : range
                    : 0;
            int yOff = topOrBottom ? player.rotationPitch > 75 ? 0 : range : range;
            int zOff = !(dir == EnumFacing.SOUTH || dir == EnumFacing.NORTH)
                    ? topOrBottom ? player.rotationPitch > 75 || (rotation & 1) == 1 ? range : 0 : range
                    : 0;

            for (int x = -xOff; x < xOff + 1; x++) for (int y = 0; y < yOff * 2 + 1; y++) {
                for (int z = -zOff; z < zOff + 1; z++) {
                    int xp = pos.getBlockPos().getX() + x + dir.getXOffset();
                    int yp = pos.getBlockPos().getY() + y + dir.getYOffset();
                    int zp = pos.getBlockPos().getZ() + z + dir.getZOffset();

                    Block block1 = player.world.getBlockState(new BlockPos(xp, yp, zp)).getBlock();
                    if (block1 == null || block1.isAir(block1.getDefaultState(), player.world, new BlockPos(xp, yp, zp))
                            || block1.isReplaceable(player.world, new BlockPos(xp, yp, zp)))
                        coords.add(new BlockPos(xp, yp, zp));
                }
            }
        }

        return coords.toArray(new BlockPos[coords.size()]);
    }

    private static void setSize(ItemStack stack, int size) {
        ItemNBTHelper.setInt(stack, TAG_SIZE, size | 1);
    }

    public static int getSize(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_SIZE, 3) | 1;
    }

    @Deprecated
    public static int getBlockID(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_BLOCK_ID, 0);
    }

    public static Block getBlock(ItemStack stack) {
        Block block = Block.getBlockFromName(getBlockName(stack));
        if (block == Blocks.AIR) block = Block.getBlockById(getBlockID(stack));

        return block;
    }

    public static String getBlockName(ItemStack stack) {
        return ItemNBTHelper.getString(stack, TAG_BLOCK_NAME, "");
    }

    public static int getBlockMeta(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_BLOCK_META, 0);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World par3World, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(par3World.isRemote)
            return EnumActionResult.FAIL;

        ItemStack stack = player.getHeldItem(hand);
        IBlockState block = par3World.getBlockState(pos);
        int meta = block.getBlock().getMetaFromState(par3World.getBlockState(pos));

        if (player.isSneaking()) {
            if (block != null && block.getRenderType() != EnumBlockRenderType.INVISIBLE)
                setBlock(stack, block.getBlock(), meta);
        }
        else placeAllBlocks(stack, player);

        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        if (player.isSneaking()) {
            int size = getSize(player.getHeldItem(handIn));
            int newSize = size == 11 ? 3 : size + 2;
            setSize(player.getHeldItem(handIn), newSize);
            ToolModeHUDHandler.setTooltip(newSize + " x " + newSize);
            worldIn.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.3F, 0.1F);
        }

        return super.onItemRightClick(worldIn, player, handIn);
    }

    public void placeAllBlocks(ItemStack stack, EntityPlayer player) {
        BlockPos[] blocksToPlace = getBlocksToPlace(stack, player);
        if (!hasBlocks(stack, player, blocksToPlace)) return;

        ItemStack stackToPlace = new ItemStack(getBlock(stack), 1, getBlockMeta(stack));
        for (BlockPos coords : blocksToPlace) placeBlockAndConsume(player, stackToPlace, coords);
        player.world.playSound(null, player.getPosition(), SoundsTC.wand, SoundCategory.BLOCKS, 1F, 1F);
    }

    private void placeBlockAndConsume(EntityPlayer player, ItemStack blockToPlace, BlockPos coords) {
        if (blockToPlace.getItem() == null) return;
        player.world.setBlockState(
                coords,
                Block.getBlockFromItem(blockToPlace.getItem()).getStateFromMeta(blockToPlace.getItemDamage()),
                1 | 2);

        if (player.capabilities.isCreativeMode) return;

        List<ItemStack> talismansToCheck = new ArrayList<>();
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stackInSlot = player.inventory.getStackInSlot(i);
            if (stackInSlot != ItemStack.EMPTY && stackInSlot.getItem() == blockToPlace.getItem()
                    && stackInSlot.getItemDamage() == blockToPlace.getItemDamage()) {
                stackInSlot.setCount(stackInSlot.getCount() - 1);
                if (stackInSlot.getCount() == 0) player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);

                return;
            }

            if (stackInSlot != ItemStack.EMPTY
                    && stackInSlot.getItem() == ModItems.block_talisman)
                talismansToCheck.add(stackInSlot);
        }

        for (ItemStack talisman : talismansToCheck) {
            Block block = ItemBlockTalisman.getBlock(talisman);
            int meta = ItemBlockTalisman.getBlockMeta(talisman);

            if (Item.getItemFromBlock(block) == blockToPlace.getItem() && meta == blockToPlace.getItemDamage()) {
                ItemBlockTalisman.remove(talisman, 1);
                return;
            }
        }
    }

    private void setBlock(ItemStack stack, Block block, int meta) {
        ItemNBTHelper.setString(stack, TAG_BLOCK_NAME, Block.REGISTRY.getNameForObject(block).toString());
        ItemNBTHelper.setInt(stack, TAG_BLOCK_META, meta);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        Block block = getBlock(stack);
        int size = getSize(stack);

        tooltip.add(size + " x " + size);
        if (block != null && block != Blocks.AIR) tooltip.add(I18n.translateToLocal(new ItemStack(block, 1, getBlockMeta(stack)).getTranslationKey() + ".name"));
    }
}