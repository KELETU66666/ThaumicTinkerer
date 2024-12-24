package com.nekokittygames.thaumictinkerer.common.misc;

import com.nekokittygames.thaumictinkerer.common.enchantments.TTEnchantments;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)

public final class RegistryHandler {

    @SubscribeEvent
    public static void registerAspects(AspectRegistryEvent event) {
        AspectEventProxy proxy = event.register;
        proxy.registerComplexObjectTag(new ItemStack(ModItems.fruit_terra, 1, 0), new AspectList().add(Aspect.EARTH, 5));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.fruit_aer, 1, 0), new AspectList().add(Aspect.AIR, 5));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.fruit_ignis, 1, 0), new AspectList().add(Aspect.FIRE, 5));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.fruit_aqua, 1, 0), new AspectList().add(Aspect.WATER, 5));

        proxy.registerComplexObjectTag(new ItemStack(ModItems.kamiresource, 1, 1), new AspectList().add(Aspect.FIRE, 2).add(Aspect.UNDEAD, 2).add(Aspect.DEATH, 2));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.kamiresource, 1, 0), new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.DESIRE, 2).add(Aspect.DARKNESS, 2));
    }

    @SubscribeEvent
    public static void onEnchantmentRegister(RegistryEvent.Register<Enchantment> event)
    {
        event.getRegistry().registerAll(TTEnchantments.ENCHANTNENTS.toArray(new Enchantment[0]));
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        OreDictionary.registerOre("ingotIchorium", new ItemStack(ModItems.kamiresource, 1, 3));
        OreDictionary.registerOre("nuggetIchorium", new ItemStack(ModItems.kamiresource, 1, 5));
    }
}