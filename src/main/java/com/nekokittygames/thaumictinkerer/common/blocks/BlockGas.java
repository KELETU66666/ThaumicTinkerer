package com.nekokittygames.thaumictinkerer.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockGas extends TTBlock {

    public static final PropertyInteger VARIANT = PropertyInteger.create("variant", 0, 4);

    public BlockGas(String name) {
        super(name, Material.AIR);
        //(0, 0, 0, 0, 0, 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, 0));
        setTickRandomly(true);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public void updateTick(World par1World, BlockPos pos, IBlockState state, Random rand) {
        int meta = this.getMetaFromState(state);
        if (meta != 0) {
            setAt(par1World, pos.add(-1, 0, 0), meta - 1);
            setAt(par1World, pos.add(1, 0, 0), meta - 1);

            setAt(par1World, pos.add(0, -1, 0), meta - 1);
            setAt(par1World, pos.add(0, 1, 0), meta - 1);

            setAt(par1World, pos.add(0, 0, -1), meta - 1);
            setAt(par1World, pos.add(0, 0, 1), meta - 1);

            // Just in case...
            par1World.setBlockState(pos, this.getDefaultState(), 2);

            if(par1World.isRemote) {
                placeParticle(par1World, pos.getX(), pos.getY(), pos.getZ());
            }
        }
    }

    public void placeParticle(World world, int par2, int par3, int par4) {
        // NO-OP, override
    }

    private void setAt(World world, BlockPos pos, int meta) {
        if (world.isAirBlock(pos) && world.getBlockState(pos).getBlock() != this) {
            if (!world.isRemote)
                world.setBlockState(pos, this.getStateFromMeta(meta), 2);
            world.scheduleBlockUpdate(pos, this, 10, 3);
        }
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess access, BlockPos pos, Entity entity) {
        return false;
    }

    @Override
    public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
        return false;
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosion) {
        return false;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        // Empty
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isAir(IBlockState state, IBlockAccess access, BlockPos pos) {
        return true;
    }
}