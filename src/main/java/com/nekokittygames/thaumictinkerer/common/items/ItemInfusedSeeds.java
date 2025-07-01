package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.common.blocks.BlockInfusedGrain;
import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileInfusedGrain;
import net.minecraft.block.BlockFarmland;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;

import javax.annotation.Nullable;
import java.util.List;

public class ItemInfusedSeeds extends ItemSeeds implements IEssentiaContainerItem {

    private static final String NBT_MAIN_ASPECT = "mainAspect";
    private static final String NBT_ASPECT_TENDENCIES = "aspectTendencies";

    public ItemInfusedSeeds() {
        super(ModBlocks.infused_grain, Blocks.FARMLAND);
        setRegistryName(new ResourceLocation(LibMisc.MOD_ID, LibItemNames.INFUSED_SEEDS));
        setTranslationKey(LibItemNames.INFUSED_SEEDS);

        this.addPropertyOverride(new ResourceLocation("meta"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (getAspect(stack) == Aspect.AIR) {
                    return 1.0F;
                }
                if (getAspect(stack) == Aspect.FIRE) {
                    return 2.0F;
                }
                if (getAspect(stack) == Aspect.WATER) {
                    return 3.0F;
                }
                if (getAspect(stack) == Aspect.EARTH) {
                    return 4.0F;
                }
                if (getAspect(stack) == Aspect.ENTROPY) {
                    return 5.0F;
                }
                if (getAspect(stack) == Aspect.ORDER) {
                    return 6.0F;
                }
                return 0.0F;
            }
        });
    }

    public static Aspect getAspect(ItemStack stack) {
        AspectList aspectList = new AspectList();
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        aspectList.readFromNBT(stack.getTagCompound().getCompoundTag(NBT_MAIN_ASPECT));
        return aspectList.size() == 0 ? null : aspectList.getAspects()[0];
    }

    public static void setAspect(ItemStack stack, Aspect aspect) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        AspectList aspectList = new AspectList().add(aspect, 1);
        NBTTagCompound nbt = new NBTTagCompound();
        aspectList.writeToNBT(nbt);
        stack.getTagCompound().setTag(NBT_MAIN_ASPECT, nbt);
        ModItems.infused_seeds.setAspects(stack, new AspectList().add(aspectList.getAspects()[0], 2));
    }

    public static AspectList getAspectTendencies(ItemStack stack) {
        AspectList aspectList = new AspectList();
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        aspectList.readFromNBT(stack.getTagCompound().getCompoundTag(NBT_ASPECT_TENDENCIES));
        return aspectList;
    }

    public static void setAspectTendencies(ItemStack stack, AspectList aspectList) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound nbt = new NBTTagCompound();
        aspectList.writeToNBT(nbt);
        stack.getTagCompound().setTag(NBT_ASPECT_TENDENCIES, nbt);
    }

    public static ItemStack getStackFromAspect(Aspect a) {
        ItemStack stack = new ItemStack(ModItems.infused_seeds);
        setAspect(stack, a);
        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        AspectList aspectList = getAspectTendencies(stack);
        if (aspectList != null && aspectList.getAspects() != null && aspectList.getAspects().length > 0) {
            for (Aspect a : aspectList.getAspects()) {
                tooltip.add(a.getName() + ": " + aspectList.getAmount(a));
            }
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for (Aspect primal : Aspect.getPrimalAspects()) {
                ItemStack itemStack = new ItemStack(this);
                setAspect(itemStack, primal);
                setAspectTendencies(itemStack, new AspectList());
                items.add(itemStack);
            }
        }
    }

    public void registerModels() {
        // Register models for each primal aspect variant
        for (Aspect primal : Aspect.getPrimalAspects()) {
            ModelLoader.setCustomModelResourceLocation(this, BlockInfusedGrain.getNumberFromAspectForTexture(primal), new ModelResourceLocation(getRegistryName() + "_" + primal.getTag().toLowerCase(), "inventory"));
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);

        if (facing != net.minecraft.util.EnumFacing.UP) {
            return EnumActionResult.FAIL;
        }

        if (world.getBlockState(pos).getBlock() instanceof BlockFarmland
                && player.canPlayerEdit(pos, facing, stack)
                && player.canPlayerEdit(pos.up(), facing, stack)) {

            world.setBlockState(pos, ModBlocks.infused_farmland.getDefaultState());
            world.setBlockState(pos.up(), ModBlocks.infused_grain.getDefaultState());

            BlockInfusedGrain.setAspect(world, pos.up(), getAspect(stack));
            TileInfusedGrain tile = (TileInfusedGrain) world.getTileEntity(pos.up());
            if (tile != null) {
                tile.primalTendencies = getAspectTendencies(stack);
            }

            stack.shrink(1);
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.FAIL;
    }

    @Override
    public AspectList getAspects(ItemStack itemstack) {
        if (itemstack.hasTagCompound()) {
            AspectList aspects = new AspectList();
            aspects.readFromNBT(itemstack.getTagCompound());
            return (aspects.size() > 0) ? aspects : null;
        }
        return null;
    }

    @Override
    public void setAspects(ItemStack itemstack, AspectList aspects) {
        if (!itemstack.hasTagCompound())
            itemstack.setTagCompound(new NBTTagCompound());
        aspects.writeToNBT(itemstack.getTagCompound());
    }

    @Override
    public boolean ignoreContainedAspects() {
        return false;
    }
}