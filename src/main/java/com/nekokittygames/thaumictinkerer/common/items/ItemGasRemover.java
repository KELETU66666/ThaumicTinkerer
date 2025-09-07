/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 * <p>
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * <p>
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * <p>
 * File Created @ [9 Sep 2013, 00:02:52 (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.common.blocks.BlockGas;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.lib.SoundsTC;

public class ItemGasRemover extends TTItem {

    public ItemGasRemover() {
        super(LibItemNames.GAS_REMOVER);
        setMaxStackSize(1);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, EnumHand handIn) {
        if (par3EntityPlayer.isSneaking()) {
            int xs = (int) par3EntityPlayer.posX;
            int ys = (int) par3EntityPlayer.posY;
            int zs = (int) par3EntityPlayer.posZ;

            for (int x = xs - 3; x < xs + 3; x++)
                for (int y = ys - 3; y < ys + 3; y++)
                    for (int z = zs - 3; z < zs + 3; z++) {
                        Block block = par2World.getBlockState(new BlockPos(x, y, z)).getBlock();
                        if (block != null && block instanceof BlockGas) {
                            BlockGas gas = (BlockGas) block;
                            if (par2World.isRemote)
                                gas.placeParticle(par2World, x, y, z);
                            par2World.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 1 | 2);
                        }
                    }

            par2World.playSound(null, par3EntityPlayer.getPosition(), SoundsTC.wand, SoundCategory.PLAYERS, 0.2F, 1F);
        }

        return super.onItemRightClick(par2World, par3EntityPlayer, handIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.UNCOMMON;
    }
}