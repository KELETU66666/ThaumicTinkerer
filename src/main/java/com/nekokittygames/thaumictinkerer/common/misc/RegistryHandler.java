package com.nekokittygames.thaumictinkerer.common.misc;

import com.nekokittygames.thaumictinkerer.common.enchantments.TTEnchantments;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)

public final class RegistryHandler {
    @SubscribeEvent
    public static void onEnchantmentRegister(RegistryEvent.Register<Enchantment> event)
    {
        event.getRegistry().registerAll(TTEnchantments.ENCHANTNENTS.toArray(new Enchantment[0]));
    }
}