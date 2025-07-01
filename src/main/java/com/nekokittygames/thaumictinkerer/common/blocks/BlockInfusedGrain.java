package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.items.ItemInfusedSeeds;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileInfusedFarmland;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileInfusedGrain;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.aspects.AspectList;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockInfusedGrain extends BlockCrops {
    private TileInfusedGrain tileEntity;
    public static final int BREEDING_CHANCE = 10;
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);

    public BlockInfusedGrain() {
        super();
        setRegistryName(LibMisc.MOD_ID, LibBlockNames.INFUSED_GRAIN_BLOCK);
        setTranslationKey(LibBlockNames.INFUSED_GRAIN_BLOCK);
    }

    @Override
    protected PropertyInteger getAgeProperty() {
        return AGE;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE);
    }

    public static int getNumberFromAspectForTexture(Aspect aspect) {
        if (aspect == Aspect.AIR) return 0;
        if (aspect == Aspect.FIRE) return 1;
        if (aspect == Aspect.WATER) return 2;
        if (aspect == Aspect.EARTH) return 3;
        if (aspect == Aspect.ORDER) return 4;
        if (aspect == Aspect.ENTROPY) return 5;
        return 6;
    }

    @Nullable
    public static Aspect getAspect(IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        return te instanceof TileInfusedGrain ? ((TileInfusedGrain) te).aspect : null;
    }

    public static void setAspect(IBlockAccess world, BlockPos pos, Aspect aspect) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileInfusedGrain) {
            ((TileInfusedGrain) te).aspect = aspect;
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        // Prevent normal growth from occurring
        // Growth takes place in the tile entity
        this.checkAndDropBlock(world, pos, state);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world1, BlockPos pos, IBlockState state, int fortune) {
        World world = (World) world1;
        if (world == null)
            return;
        Random rand = new Random();
        int metadata = getAge(state);

        int count = 1;
        for (int i = 0; i < count; i++) {
            TileInfusedGrain tileGrain = getTileEntitySafe(world, pos);
            ItemStack seedStack = new ItemStack(ModItems.infused_seeds);
            ItemInfusedSeeds.setAspect(seedStack, getAspectDropped(world, pos, metadata));
            if (tileGrain != null) {
                ItemInfusedSeeds.setAspectTendencies(seedStack, tileGrain.primalTendencies);
            }
            while (rand.nextInt(10000) < Math.pow(getPrimalTendencyCount(world, pos, Aspect.ENTROPY), 2)) {
                seedStack.setCount(seedStack.getCount() + 1);
            }
            drops.add(seedStack);
            fertilizeSoil(world, pos, metadata);
        }
        if (getMetaFromState(state) >= 7) {
            do {
                Aspect aspect = getAspectSafe(world, pos);
                ItemStack retItem = AspectCropLootManager.getLootForAspect(aspect);
                if (retItem != null) drops.add(retItem);

            } while (world.rand.nextInt(75) < getPrimalTendencyCount(world, pos, Aspect.ORDER));
        }
        tileEntity = null;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (willHarvest) {
            // If it will be harvested, delay the destruction
            return true;
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(world, player, pos, state, te, stack);
        world.setBlockToAir(pos);
    }

    public int getPrimalTendencyCount(World world, BlockPos pos, Aspect aspect) {
        TileInfusedGrain tileGrain = getTileEntitySafe(world, pos);
        return tileGrain != null ? tileGrain.primalTendencies.getAmount(aspect) : 0;
    }

    private int getPrimalTendencyCount(TileInfusedGrain grain, Aspect aspect) {
        return grain.primalTendencies != null ? grain.primalTendencies.getAmount(aspect) : 0;
    }

    private void fertilizeSoil(World world, BlockPos pos, int metadata) {
        if (metadata >= 7) {
            TileEntity te = world.getTileEntity(pos.down());
            if (!(te instanceof TileInfusedFarmland)) return;

            TileInfusedFarmland farmland = (TileInfusedFarmland) te;
            TileInfusedGrain grain = (TileInfusedGrain) world.getTileEntity(pos);
            if (grain == null) return;

            do {
                Aspect currentAspect = getAspect(world, pos);
                if (currentAspect != null) {
                    farmland.aspectList.add(currentAspect, 1);
                    farmland.reduceSaturatedAspects();
                    world.notifyBlockUpdate(pos.down(),
                            world.getBlockState(pos.down()),
                            world.getBlockState(pos.down()), 3);
                }
            } while (world.rand.nextInt(55) < getPrimalTendencyCount(grain, Aspect.EARTH));
        }
    }

    public Aspect getAspectDropped(World world, BlockPos pos, int metadata) {
        Aspect currentAspect = getAspect(world, pos);
        if (metadata >= 7 && currentAspect != null) {
            TileEntity te = world.getTileEntity(pos.down());
            if (te instanceof TileInfusedFarmland) {
                TileInfusedFarmland farmland = (TileInfusedFarmland) te;
                AspectList farmlandAspectList = farmland.aspectList;

                for (Aspect aspect : farmlandAspectList.getAspects()) {
                    Random rand = new Random();
                    TileInfusedGrain grain = (TileInfusedGrain) world.getTileEntity(pos);
                    if (rand.nextInt(BREEDING_CHANCE) < (getPrimalTendencyCount(grain, Aspect.FIRE) + 1) *
                            farmlandAspectList.getAmount(aspect) * farmlandAspectList.getAmount(aspect)) {
                        Aspect result = AspectHelper.getCombinationResult(aspect, currentAspect);
                        if (result != null) {
                            return result;
                        }
                    }
                }
            }
        }
        return currentAspect;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileInfusedGrain();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    private TileInfusedGrain getTileEntitySafe(World world, BlockPos pos) {
        if (tileEntity != null) {
            return tileEntity;
        }

        TileEntity tile = world.getTileEntity(pos);

        if (tile != null && tile instanceof TileInfusedGrain) {
            return (TileInfusedGrain) tile;
        }

        return null;
    }

    private Aspect getAspectSafe(World world, BlockPos pos) {
        if (tileEntity != null) {
            return tileEntity.aspect;
        }

        TileEntity tile = world.getTileEntity(pos);

        if (tile != null && tile instanceof TileInfusedGrain) {
            return ((TileInfusedGrain) tile).aspect;
        }

        return null;
    }

    @Override
    protected Item getSeed() {
        return ModItems.infused_seeds;
    }
}