/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntitySummon;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class ItemMobAspect extends TTItem implements IEssentiaContainerItem {
    public static String ASPECT_NAME="aspectName";
    public ItemMobAspect(String name) {
        super(name);
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return ThaumicTinkerer.getTabAspects();
    }


    public String GetVariant(ItemStack stack) {
        return stack.getTagCompound() != null ? stack.getTagCompound().getString(ASPECT_NAME) : "aer";
    }

    public static boolean isInfused(ItemStack item) {
        return item.getItem() instanceof ItemCondensedMobAspect;
    }

    public Set<String> GetVariants() {
        return Aspect.aspects.keySet();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey(ASPECT_NAME)) {
            Aspect aspect=Aspect.getAspect(stack.getTagCompound().getString(ASPECT_NAME));
            if(aspect!=null) {
                tooltip.add(I18n.format("thaumictinkerer.mobaspect.type", aspect.getName()));
            }
            else
            {
                tooltip.add(I18n.format("thaumictinkerer.mobaspect.invalid"));
            }
        }
        else {
            tooltip.add(I18n.format("thaumictinkerer.mobaspect.invalid"));
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab))
        {
            SortedSet<String> sortedAspects = new TreeSet<>(GetVariants());
            for(String aspect:sortedAspects) {
                ItemStack itemStack=new ItemStack(this);
                NBTTagCompound cmp=itemStack.getTagCompound();
                if(cmp==null)
                    cmp=new NBTTagCompound();
                cmp.setString(ASPECT_NAME,aspect);
                itemStack.setTagCompound(cmp);
                items.add(itemStack);
            }
        }
    }

    public static boolean lastUsedTabletMatches(ItemStack stack, TileEntitySummon tablet) {
        if (stack.getTagCompound() == null) {
            return true;
        }

        return (stack.getTagCompound().getInteger("LastX") == tablet.getPos().getX() &&
                stack.getTagCompound().getInteger("LastY") == tablet.getPos().getY() &&
                stack.getTagCompound().getInteger("LastZ") == tablet.getPos().getZ());
    }

    public static boolean isReadyForSummon(ItemStack stack, long currentTime) {
        return stack.getTagCompound() == null || stack.getTagCompound().getLong("NextSummonTime") <= currentTime;
    }

    public static ItemStack setAspectType(ItemStack stack, Aspect aspect) {
        NBTTagCompound cmp=stack.getTagCompound();
        if(cmp==null)
            cmp=new NBTTagCompound();
        cmp.setString(ASPECT_NAME,aspect.getTag());
        stack.setTagCompound(cmp);
        return stack;
    }

    public static void markLastUsedTablet(ItemStack stack, TileEntitySummon tablet) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setInteger("LastX", tablet.getPos().getX());
        stack.getTagCompound().setInteger("LastY", tablet.getPos().getY());
        stack.getTagCompound().setInteger("LastZ", tablet.getPos().getZ());
    }

    public static void markNextSummon(ItemStack stack, long nextTime){
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setLong("NextSummonTime", nextTime);
    }

    public static Aspect getAspectType(ItemStack stack) {
        if(stack == ItemStack.EMPTY)
            return null;

        NBTTagCompound cmp=stack.getTagCompound();
        if(cmp==null)
            return null;
        String aspectName=cmp.getString(ASPECT_NAME);
        return Aspect.getAspect(aspectName);
    }

    public AspectList getAspects(ItemStack itemstack) {
        if (itemstack.hasTagCompound()) {
            AspectList aspects = new AspectList();
            aspects.readFromNBT(itemstack.getTagCompound());
            return (aspects.size() > 0) ? aspects : null;
        }
        return null;
    }

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