package com.nekokittygames.thaumictinkerer.common.proxy;

import static com.nekokittygames.thaumictinkerer.ThaumicTinkerer.instance;
import com.nekokittygames.thaumictinkerer.common.dim.OreClusterGenerator;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;

public class CommonProxy implements ITTProxy {

    public static EnumRarity kamiRarity;

    @Override
    public void registerRenderers() {
        // Empty
    }

    @SuppressWarnings("deprecation")
    @Override
    public String localize(String translationKey, Object... args) {
        return I18n.translateToLocalFormatted(translationKey, args);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiProxy());

    }

    @Override
    public void preInit( FMLPreInitializationEvent event ) {
        LootTableList.register(new ResourceLocation("modid", "loot_table_name"));
        GameRegistry.registerWorldGenerator(new OreClusterGenerator(), 3);

      //  kamiRarity = EnumHelper.addEnum(EnumRarity.class,  "KAMI",new Class[]{EnumRarity.class, TextFormatting.class, String.class}, TextFormatting.LIGHT_PURPLE, "Kami");
    }

    @SubscribeEvent
    public static void registerAspects(AspectRegistryEvent event) {
        AspectEventProxy proxy = event.register;
        proxy.registerComplexObjectTag(new ItemStack(ModItems.kamiresource, 1, 1), new AspectList().add(Aspect.FIRE, 2).add(Aspect.UNDEAD, 2).add(Aspect.DEATH, 2));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.kamiresource, 1, 0), new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.DESIRE, 2).add(Aspect.DARKNESS, 2));
    }
}
