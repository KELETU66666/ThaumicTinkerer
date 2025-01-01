package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityFunnel;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaContainerItem;

public class BlockFunnel extends TTTileEntity<TileEntityFunnel> {

    public static final PropertyBool JAR = PropertyBool.create("jar");

    public BlockFunnel() {
        super(LibBlockNames.FUNNEL, Material.ROCK, true);
        setHardness(3.0F);
        setResistance(8.0f);
        setDefaultState(this.getBlockState().getBaseState().withProperty(JAR, false));

    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isTranslucent(IBlockState state) {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, JAR);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityFunnel) {
            TileEntityFunnel funnel = (TileEntityFunnel) te;
            if (funnel.getInventory().getStackInSlot(0) == ItemStack.EMPTY)
                return state.withProperty(JAR, false);
            else
                return state.withProperty(JAR, true);
        }
        return state.withProperty(JAR, false);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.blockState.getBaseState().withProperty(JAR, false);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.down()).getBlock() == Blocks.HOPPER;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityFunnel();
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (hand == EnumHand.OFF_HAND) 	return false;
        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof TileEntityFunnel)) 	return false;

        TileEntityFunnel funnel = (TileEntityFunnel) te;
        ItemStack stack = funnel.getInventory().getStackInSlot(0);
        ItemStack playerStack = playerIn.getHeldItem(hand);

        if (stack == ItemStack.EMPTY) { //no jar
            if (worldIn.isRemote) 	return true;

            if ( playerIn.getHeldItemMainhand().isEmpty()) { //change POWER
                //changePower(worldIn, pos, state);
                funnel.markDirty();
                return true;
            }

            if (funnel.isItemValidForSlot(0, playerStack)) { //Put JAR
                funnel.getInventory().insertItem(0, playerStack.copy(), false);
                playerStack.setCount(playerStack.getCount() - 1);
                if (playerStack.isEmpty()) {
                    playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, ItemStack.EMPTY);
                }
                funnel.markDirty();
                return true;
            }

        } else { //jar

            if ( !playerStack.isEmpty()) {
                Item playerItem = playerStack.getItem();

                //Switch Jar

                if (playerStack.getCount()==1&& funnel.isItemValidForSlot(0, playerStack)) {

                    ItemStack jar = stack.copy();

                    funnel.getInventory().setStackInSlot(0, playerStack);
                    clearTagsFromEmptyJar(jar);
                    playerIn.setHeldItem(hand, jar);
                    funnel.markDirty();
                    return true;

                }	else return false;
            }

            //playerStack.isEmpty()

            if (!playerIn.isSneaking()) {
                if (worldIn.isRemote) return true;

                ItemStack jar = stack.copy();
                clearTagsFromEmptyJar(jar);
                if (!playerIn.inventory.addItemStackToInventory(jar)) {
                    playerIn.dropItem(jar, false);

                }
                funnel.getInventory().setStackInSlot(0, ItemStack.EMPTY);
            }else{ //TODO Change POWER
                if (worldIn.isRemote) return true;
                //changePower(worldIn, pos, state);
            }
            funnel.markDirty();
            return true;
        }
        return false;

    }

    private ItemStack clearTagsFromEmptyJar(ItemStack jar) {
        IEssentiaContainerItem item = (IEssentiaContainerItem) jar.getItem();
        if (item.getAspects(jar) == null || item.getAspects(jar).getAspects().length == 0) {
            Aspect aspectFromTag = getAspectFromTag(jar);
            NBTTagCompound itemTags = null;
            if (aspectFromTag != null) {
                itemTags = new NBTTagCompound();
                itemTags.setString("AspectFilter", aspectFromTag.getTag());
            }
            jar.setTagCompound(itemTags);
        }
        return jar;

    }

    public static Aspect getAspectFromTag(ItemStack jar) {
        NBTTagCompound itemTags = jar.getTagCompound();
        if(itemTags!=null) {
            String aspectName = itemTags.getString("AspectFilter");
            return Aspect.getAspect(aspectName);
        }
        return null;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityFunnel funnel = (TileEntityFunnel) worldIn.getTileEntity(pos);
        ItemStack inv = funnel.getInventory().getStackInSlot(0);
        if (inv != ItemStack.EMPTY) {
            EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), inv);
            worldIn.spawnEntity(item);
        }
        super.breakBlock(worldIn, pos, state);

    }
}
