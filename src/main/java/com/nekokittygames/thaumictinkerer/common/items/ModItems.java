package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftMaterials;

import java.util.HashSet;
import java.util.Set;

import static com.nekokittygames.thaumictinkerer.common.utils.MiscUtils.nullz;

@SuppressWarnings("WeakerAccess")
@GameRegistry.ObjectHolder(LibMisc.MOD_ID)
public class ModItems {

    public static final ItemShareBook share_book = nullz();
    public static final ItemCleansingTalisman cleaning_talisman = nullz();
    public static final ItemBlackQuartz black_quartz = nullz();
    public static final ItemConnector connector = nullz();
    public static final ItemSoulMould soul_mould = nullz();
    public static final ItemEnergeticNitor energetic_nitor = nullz();
    public static final ItemSpellbindingCloth spellbinding_cloth=nullz();
    public static final ItemBloodSword blood_sword=nullz();
    public static final ItemMobAspect mob_aspect=nullz();
    public static final Item revealing_helm = null;
    public static final Item gas_remover = new ItemGasRemover();
    public static final ItemGas gas_shadow_item = nullz();
    public static final ItemGas gas_light_item = nullz();

    //public static final ItemFormRevealer form_revealer = nullz();

    @Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
    public static class RegistrationHandler {
        public static final Set<Item> ITEMS = new HashSet<>();
        /**
         * Register this mod's {@link Item}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            final Item[] items = {
                    new ItemShareBook(),
                    new ItemBlackQuartz(),
                    new ItemCleansingTalisman(),
                    new ItemConnector(),
                    new ItemSoulMould(),
                    new ItemEnergeticNitor(),
                    new ItemGas(),
                    new ItemGas(ModBlocks.light_gas),
                    new ItemGasRemover(),
                    new ItemSpellbindingCloth(),
                    new ItemBloodSword(),
                    new ItemMobAspect(),
                    new ItemRevealingHelm("revealing_helm", ThaumcraftMaterials.ARMORMAT_THAUMIUM, 1, EntityEquipmentSlot.HEAD, ThaumicTinkerer.getTab())
                   // new ItemFormRevealer()
            };
            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final Item item : items) {
                registry.register(item);
                ITEMS.add(item);
            }


        }
    }
}
