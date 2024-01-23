/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Jan 10, 2014, 3:54:41 PM (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.blocks.Kami;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.tileentity.Kami.TileWarpGate;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockWarpGate extends BlockContainer {
    
    Random random;

    public BlockWarpGate() {
        super(Material.ROCK);
        setHardness(5.0F);
        setResistance(2000.0F);
        setRegistryName("warp_gate");
        setTranslationKey("warp_gate");
        setCreativeTab(ThaumicTinkerer.getTab());

        random = new Random();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumHand hand,
                                    EnumFacing par6, float par7, float par8, float par9) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null) {
                world.notifyBlockUpdate(pos, state, state, 3);
                par5EntityPlayer.openGui(ThaumicTinkerer.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }

        return true;
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileWarpGate warpGate = (TileWarpGate) world.getTileEntity(pos);

        if (warpGate != null) {
            InventoryHelper.dropInventoryItems(world, pos, warpGate);
            for (int j1 = 0; j1 < warpGate.getSizeInventory(); ++j1) {
                ItemStack itemstack = warpGate.getStackInSlot(j1);


                if (itemstack != ItemStack.EMPTY) {
                    float f = random.nextFloat() * 0.8F + 0.1F;
                    float f1 = random.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = random.nextFloat() * 0.8F + 0.1F; itemstack.getCount() > 0; world
                            .spawnEntity(entityitem)) {
                        int k1 = random.nextInt(21) + 10;

                        if (k1 > itemstack.getCount()) k1 = itemstack.getCount();

                        itemstack.setCount(itemstack.getCount() - k1);
                        entityitem = new EntityItem(
                                world,
                                pos.getX() + f,
                                pos.getY() + f1,
                                pos.getZ() + f2,
                                new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (float) random.nextGaussian() * f3;
                        entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float) random.nextGaussian() * f3;

                        if (itemstack.hasTagCompound())
                            entityitem.getItem().setTagCompound(itemstack.getTagCompound().copy());
                    }
                }
            }

            world.notifyBlockUpdate(pos, state, state, 3);
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int in) {
        return new TileWarpGate();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileWarpGate();
    }
}