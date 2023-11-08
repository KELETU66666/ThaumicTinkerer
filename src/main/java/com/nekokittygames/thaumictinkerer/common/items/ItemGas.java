/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [8 Sep 2013, 22:12:14 (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.items.ItemsTC;

public class ItemGas extends TTItem {

    private Block setBlock;

    public ItemGas(Block setBlock) {
        super(setBlock == ModBlocks.shadow_gas ? LibItemNames.GASEOUS_SHADOW : LibItemNames.GASEOUS_LIGHT);
        this.setBlock = setBlock;
        this.setContainerItem(ItemsTC.phial);
    }

    public ItemGas() {
        this(ModBlocks.shadow_gas);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, EnumHand hand) {
        int x = (int) par3EntityPlayer.posX;
        int y = (int) par3EntityPlayer.posY + 1;
        int z = (int) par3EntityPlayer.posZ;
        BlockPos blockPos = new BlockPos(x, y ,z);
        boolean air = par2World.isAirBlock(blockPos);

        if (!par3EntityPlayer.capabilities.isCreativeMode)
            par3EntityPlayer.getHeldItem(hand).shrink(1);

        par2World.playSound(null, par3EntityPlayer.getPosition(), SoundEvents.BLOCK_LAVA_POP, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (air) {
            if (!par2World.isRemote)
                par2World.setBlockState(blockPos, setBlock.getStateFromMeta(4), 2);
            else
                par3EntityPlayer.swingArm(hand);

            par2World.scheduleBlockUpdate(blockPos, setBlock, 10, 3);
        }

        return super.onItemRightClick(par2World, par3EntityPlayer, hand);
    }

}