/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.misc;

import com.nekokittygames.thaumictinkerer.common.blocks.BlockInfusedGrain;
import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.items.ItemInfusedSeeds;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.aspects.Aspect;

public class AspectColors implements IItemColor, IBlockColor {
    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        if (stack.getItem() == ModItems.mob_aspect || stack.getItem() == ModItems.condensed_mob_aspect) {
            return Aspect.getAspect(ModItems.condensed_mob_aspect.GetVariant(stack)).getColor();
        }
        if (stack.getItem() == ModItems.infused_seeds) {
            if(Aspect.getPrimalAspects().contains(ItemInfusedSeeds.getAspect(stack)))
                return 0xFFFFFF;
            return ItemInfusedSeeds.getAspect(stack) == null ? 0xFFFFFF : ItemInfusedSeeds.getAspect(stack).getColor();
        }
        return 0;
    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
        if (world == null || pos == null) {
            return 0xFFFFFF;
        }

        if (state.getBlock() == ModBlocks.infused_grain) {
            Aspect aspect = BlockInfusedGrain.getAspect(world, pos);
            if (aspect != null) {
                return aspect.getColor();
            }
        }

        return 0xFFFFFF;
    }
}