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
 * File Created @ [8 Sep 2013, 22:20:30 (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.client.fx.FXDispatcher;

import java.util.Random;

public class BlockGaseousLight extends BlockGas {

    public BlockGaseousLight() {
        super(LibBlockNames.GASEOUS_LIGHT);
        setLightLevel(0.85F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextFloat() < 0.0075F)
            FXDispatcher.INSTANCE.sparkle(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 1F, 1, rand.nextFloat() / 2);
    }

    @Override
    public void placeParticle(World world, int par2, int par3, int par4) {
        FXDispatcher.INSTANCE.sparkle(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, 1, 0, 0);
    }
}