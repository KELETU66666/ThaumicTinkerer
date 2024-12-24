package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileInfusedFarmland;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class BlockInfusedFarmland extends BlockFarmland {

    public BlockInfusedFarmland() {
        super();
        setHardness(0.6F);
        setSoundType(blockSoundType.GROUND);
        setRegistryName(new ResourceLocation(LibMisc.MOD_ID, LibBlockNames.BLOCK_INFUSED_FARMLAND));
        setTranslationKey(LibBlockNames.BLOCK_INFUSED_FARMLAND);
        this.setDefaultState(this.blockState.getBaseState().withProperty(MOISTURE, 0));
        this.setTickRandomly(false);
    }

    @Override
    public boolean getUseNeighborBrightness(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileInfusedFarmland();
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return plantable instanceof BlockInfusedGrain;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    // Prevent farmland degrading
    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        // Do nothing to prevent trampling
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        // Do nothing to prevent moisture changes
    }

    // Model/texture handling is now done through blockstates and models
    // You'll need to create these files in your resources:
    // assets/thaumictinkerer/blockstates/infused_farmland.json
    // assets/thaumictinkerer/models/block/infused_farmland.json
    // assets/thaumictinkerer/models/block/infused_farmland_moist.json
    // assets/thaumictinkerer/models/item/infused_farmland.json
}