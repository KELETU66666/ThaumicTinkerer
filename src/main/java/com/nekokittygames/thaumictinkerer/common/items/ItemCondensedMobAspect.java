/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.libs.LibClientMisc;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
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

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class ItemCondensedMobAspect extends ItemMobAspect {
    public static String ASPECT_NAME="aspectName";
    public ItemCondensedMobAspect() {
        super("condensed_mob_aspect");
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return ThaumicTinkerer.getTabAspects();
    }


    public String GetVariant(ItemStack stack) {
        return stack.getTagCompound() != null ? stack.getTagCompound().getString(ASPECT_NAME) : "aer";
    }


    public Set<String> GetVariants() {
        return Aspect.aspects.keySet();
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
}