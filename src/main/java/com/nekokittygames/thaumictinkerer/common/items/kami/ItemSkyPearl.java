/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Jan 10, 2014, 5:26:45 PM (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.items.Kami;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.items.TTItem;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import com.nekokittygames.thaumictinkerer.common.misc.MiscHelper;
import com.nekokittygames.thaumictinkerer.common.utils.ItemNBTHelper;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.codechicken.lib.vec.Vector3;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ItemSkyPearl extends TTItem {

    public static final String TAG_X = "x";
    public static final String TAG_Y = "y";
    public static final String TAG_Z = "z";
    public static final String TAG_DIM = "dim";

    public ItemSkyPearl() {
        super(LibItemNames.SKY_PEARL);
        setMaxStackSize(1);
    }

    public static void addInfo(ItemStack stack, int dim, Vector3 pos, List<String> list, boolean simpleMode) {
        if (isAttuned(stack)) {
            int x = getX(stack);
            int y = getY(stack);
            int z = getZ(stack);
            list.add("X: " + x);
            if (!simpleMode) list.add("Y: " + y);
            list.add("Z: " + z);
            if (getDim(stack) != dim) {
                if (!simpleMode)
                    list.add(TextFormatting.RED + I18n.translateToLocal("ttmisc.differentDim"));
            } else list.add(
                    TextFormatting.BLUE + I18n.translateToLocal("ttmisc.distance")
                            + ": "
                            + BigDecimal.valueOf(
                                    MiscHelper.pointDistanceSpace(
                                            x,
                                            simpleMode ? 0 : y,
                                            z,
                                            pos.x,
                                            simpleMode ? 0 : pos.y,
                                            pos.z))
                                    .setScale(2, RoundingMode.UP)
                            + "m");
        }
    }

    public static void setValues(ItemStack stack, BlockPos pos, int dim) {
        ItemNBTHelper.setInt(stack, TAG_X, pos.getX());
        ItemNBTHelper.setInt(stack, TAG_Y, pos.getY());
        ItemNBTHelper.setInt(stack, TAG_Z, pos.getZ());
        ItemNBTHelper.setInt(stack, TAG_DIM, dim);
    }

    public static boolean isAttuned(ItemStack stack) {
        return stack.hasTagCompound() && ItemNBTHelper.getInt(stack, TAG_Y, -1) != -1;
    }

    public static int getX(ItemStack stack) {
        if (!isAttuned(stack)) return 0;

        return ItemNBTHelper.getInt(stack, TAG_X, 0);
    }

    public static int getY(ItemStack stack) {
        if (!isAttuned(stack)) return 0;

        return ItemNBTHelper.getInt(stack, TAG_Y, 0);
    }

    public static int getZ(ItemStack stack) {
        if (!isAttuned(stack)) return 0;

        return ItemNBTHelper.getInt(stack, TAG_Z, 0);
    }

    public static int getDim(ItemStack stack) {
        if (!isAttuned(stack)) return 0;

        return ItemNBTHelper.getInt(stack, TAG_DIM, 0);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        Block block = world.getBlockState(pos).getBlock();
        if (block == ModBlocks.warp_gate
                && !isAttuned(stack)) {
            setValues(stack, pos, player.dimension);
            world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.3F, 0.1F);
        }

        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking() && isAttuned(stack)) {
            world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.3F, 0.1F);
            ItemNBTHelper.setInt(stack, TAG_Y, -1);
        }

        return super.onItemRightClick(world, player, hand);
    }

   @Override
   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag) {
       if (isAttuned(stack)) {
           int x = getX(stack);
           int y = getY(stack);
           int z = getZ(stack);
           list.add("X: " + x);
           list.add("Y: " + y);
           list.add("Z: " + z);
       }
    }

    @Override
    public boolean hasEffect(ItemStack par1ItemStack) {
        return isAttuned(par1ItemStack);
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.EPIC;
    }
}