package com.nekokittygames.thaumictinkerer.common.recipes;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.compat.botania.BotaniaCompat;
import com.nekokittygames.thaumictinkerer.common.foci.FocusEffectTelekenesis;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.libs.LibOreDict;
import com.nekokittygames.thaumictinkerer.common.libs.LibRecipes;
import com.nekokittygames.thaumictinkerer.common.libs.LibResearch;
import com.nekokittygames.thaumictinkerer.common.recipes.ing.TTFocusIngredient;
import com.nekokittygames.thaumictinkerer.common.recipes.ing.TTIngredientNBT;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.casters.FocusPackage;
import thaumcraft.api.crafting.*;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.blocks.misc.BlockNitor;
import thaumcraft.common.items.casters.ItemFocus;
import thaumcraft.common.items.resources.ItemCrystalEssence;

import java.util.Objects;

import static com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks.black_quartz_block;
import static com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks.ichor_block;
import static thaumcraft.api.ThaumcraftApi.*;

public class ModRecipes {


    private static final ResourceLocation defaultGroup = new ResourceLocation("");


    public static void initializeRecipes(IForgeRegistry<IRecipe> registry) {
        registerOreDict();
        initializeCraftingRecipes(registry);
        initializeCauldronRecipes();
        initializeArcaneRecipes();
        initializeInfusionRecipes();
        initializeMultiblockRecipes();
        BotaniaCompat.addOreDict();

    }

    private static void registerOreDict() {
        OreDictionary.registerOre(LibOreDict.BLACK_QUARTZ_BLOCK, black_quartz_block);
        OreDictionary.registerOre(LibOreDict.BLACK_QUARTZ, ModItems.black_quartz);
    }

    private static void initializeMultiblockRecipes() {
        Part AB = new Part(BlocksTC.stoneArcaneBrick, BlocksTC.stoneArcaneBrick);
        Part BQ = new Part(LibOreDict.BLACK_QUARTZ_BLOCK, black_quartz_block);
        Part DBQ = new Part(black_quartz_block, black_quartz_block);

        Part[] pillars = new Part[16];

        for (int i = 0; i < 16; i++) {
            pillars[i] = new Part(BlocksTC.stoneArcane, new ItemStack(Objects.requireNonNull(ModBlocks.enchantment_pillar), 1, i));
        }
        Part NR = new Part(BlockNitor.class, "AIR");
        Part DN = new Part(ModBlocks.dummy_nitor, "AIR");
        Part OE = new Part(ModBlocks.osmotic_enchanter, ModBlocks.osmotic_enchanter);
        Part[][][] enchanterBP = new Part[][][]{{{null, null, null, NR, null, null, null}, {null, NR, null, null, null, NR, null}, {null, null, null, null, null, null, null}, {NR, null, null, null, null, null, NR}, {null, null, null, null, null, null, null}, {null, NR, null, null, null, NR, null}, {null, null, null, NR, null, null, null}},
                {{null, null, null, pillars[15], null, null, null}, {null, pillars[14], null, null, null, pillars[8], null}, {null, null, null, null, null, null, null}, {pillars[13], null, null, null, null, null, pillars[9]}, {null, null, null, null, null, null, null}, {null, pillars[12], null, null, null, pillars[10], null}, {null, null, null, pillars[11], null, null, null}},
                {{null, null, null, pillars[7], null, null, null}, {null, pillars[6], null, null, null, pillars[0], null}, {null, null, null, null, null, null, null}, {pillars[5], null, null, OE, null, null, pillars[1]}, {null, null, null, null, null, null, null}, {null, pillars[4], null, null, null, pillars[2], null}, {null, null, null, pillars[3], null, null, null}},
                {{null, null, AB, AB, AB, null, null}, {null, AB, AB, BQ, AB, AB, null}, {AB, AB, BQ, BQ, BQ, AB, AB}, {AB, BQ, BQ, BQ, BQ, BQ, AB}, {AB, AB, BQ, BQ, BQ, AB, AB}, {null, AB, AB, BQ, AB, AB, null}, {null, null, AB, AB, AB, null, null}}

        };

        Part[][][] dummyenchanterBP = new Part[][][]{{{null, null, null, DN, null, null, null}, {null, DN, null, null, null, DN, null}, {null, null, null, null, null, null, null}, {DN, null, null, null, null, null, DN}, {null, null, null, null, null, null, null}, {null, DN, null, null, null, DN, null}, {null, null, null, DN, null, null, null}},
                {{null, null, null, pillars[15], null, null, null}, {null, pillars[14], null, null, null, pillars[8], null}, {null, null, null, null, null, null, null}, {pillars[13], null, null, null, null, null, pillars[9]}, {null, null, null, null, null, null, null}, {null, pillars[12], null, null, null, pillars[10], null}, {null, null, null, pillars[11], null, null, null}},
                {{null, null, null, pillars[7], null, null, null}, {null, pillars[6], null, null, null, pillars[0], null}, {null, null, null, null, null, null, null}, {pillars[5], null, null, OE, null, null, pillars[1]}, {null, null, null, null, null, null, null}, {null, pillars[4], null, null, null, pillars[2], null}, {null, null, null, pillars[3], null, null, null}},
                {{null, null, AB, AB, AB, null, null}, {null, AB, AB, DBQ, AB, AB, null}, {AB, AB, DBQ, DBQ, DBQ, AB, AB}, {AB, DBQ, DBQ, DBQ, DBQ, DBQ, AB}, {AB, AB, DBQ, DBQ, DBQ, AB, AB}, {null, AB, AB, DBQ, AB, AB, null}, {null, null, AB, AB, AB, null, null}}
        };
        IDustTrigger.registerDustTrigger(new TTDustTriggerMultiblock(LibResearch.ENCHANTER, enchanterBP));
        addMultiblockRecipeToCatalog(LibRecipes.OSMOTIC_ENCHANTER_MB,
                new BluePrint(LibResearch.ENCHANTER, dummyenchanterBP, new ItemStack(Objects.requireNonNull(black_quartz_block), 13), new ItemStack(BlocksTC.stoneArcaneBrick, 24), new ItemStack(BlocksTC.stoneArcane, 16), new ItemStack(BlocksTC.nitor.get(EnumDyeColor.YELLOW), 8)));

    }

    private static void initializeCauldronRecipes() {
        addCrucibleRecipe(LibRecipes.PRISMARINE, new CrucibleRecipe(LibResearch.PRISMARINE, new ItemStack(Items.PRISMARINE_SHARD), "paneGlass", new AspectList().add(Aspect.WATER, 5).add(Aspect.EARTH, 5)));
        addCrucibleRecipe(LibRecipes.SOUL_MOLD, new CrucibleRecipe(LibResearch.CORPOREAL_MAGNET, new ItemStack(Objects.requireNonNull(ModItems.soul_mould)), new ItemStack(Items.ENDER_PEARL), new AspectList().add(Aspect.BEAST, 5).add(Aspect.MIND, 10).add(Aspect.SENSES, 10)));
        addCrucibleRecipe(LibRecipes.SPELLBINDING_CLOTH, new CrucibleRecipe(LibResearch.SPELLBINDING_CLOTH, new ItemStack(Objects.requireNonNull(ModItems.spellbinding_cloth)), new ItemStack(ItemsTC.fabric), new AspectList().add(Aspect.EXCHANGE, 4).add(Aspect.ENTROPY, 6).add(Aspect.MAGIC, 10)));
        addCrucibleRecipe(LibRecipes.ENERGETIC_NITOR, new CrucibleRecipe(LibResearch.ENERGETIC_NITOR, new ItemStack(Objects.requireNonNull(ModItems.energetic_nitor)), "nitor", new AspectList().add(Aspect.FIRE, 10).add(Aspect.AIR, 10).add(Aspect.LIGHT, 25).add(Aspect.ENERGY, 25)));
        addCrucibleRecipe(LibRecipes.LIGHT_GAS, new CrucibleRecipe(
                "TT_GAS",
                new ItemStack(ModItems.gas_light_item),
                new ItemStack(ItemsTC.phial),
                new AspectList().add(Aspect.AIR, 10).add(Aspect.LIGHT, 20).add(Aspect.MOTION, 20)));
        addCrucibleRecipe(LibRecipes.SHADOW_GAS, new CrucibleRecipe(
                "TT_GAS",
                new ItemStack(ModItems.gas_shadow_item),
                new ItemStack(ItemsTC.phial),
                new AspectList().add(Aspect.AIR, 10).add(Aspect.DARKNESS, 20).add(Aspect.MOTION, 20)));


    }

    private static void initializeCraftingRecipes(IForgeRegistry<IRecipe> registry) {
        registry.register(new SpellClothRecipe().setRegistryName(LibMisc.MOD_ID,"spellbinding_cloth"));
    }

    private static void initializeArcaneRecipes() {
        addArcaneCraftingRecipe(LibRecipes.FUNNEL, new ShapedArcaneRecipe(defaultGroup, LibResearch.ESSENTIA_FUNNEL, 60, new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), new ItemStack(Objects.requireNonNull(ModBlocks.funnel)), "STS", 'S', Blocks.STONE, 'T', "ingotThaumium"));
        addArcaneCraftingRecipe(LibRecipes.DISSIMULATION, new ShapedArcaneRecipe(defaultGroup, LibResearch.DISSIMULATION, 30, new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), new ItemStack(Objects.requireNonNull(ModBlocks.dissimulation)), "BEB", "PBP", "BEB", 'B', new ItemStack(BlocksTC.stoneArcane), 'E', new ItemStack(Items.CLAY_BALL), 'P', new ItemStack(Items.PRISMARINE_SHARD)));
        addArcaneCraftingRecipe(LibRecipes.TRANSVECTOR_INTERFACE, new ShapedArcaneRecipe(defaultGroup, LibResearch.TRANSVECTOR_INTERFACE, 200, new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), new ItemStack(Objects.requireNonNull(ModBlocks.transvector_interface)), "BRB", "LEL", "BDB", 'B', new ItemStack(BlocksTC.stoneArcane), 'R', "dustRedstone", 'L', new ItemStack(Items.DYE, 1, 4), 'E', new ItemStack(Items.ENDER_PEARL), 'D', new ItemStack(Objects.requireNonNull(ModBlocks.dissimulation))));
        addArcaneCraftingRecipe(LibRecipes.TRANSVECTOR_DISLOCATOR, new ShapedArcaneRecipe(defaultGroup, LibResearch.TRANSVECTOR_DISLOCATOR, 200, new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 5), new ItemStack(Objects.requireNonNull(ModBlocks.transvector_dislocator)), " M ", " T ", " C ", 'M', new ItemStack(ItemsTC.mirroredGlass), 'T', new ItemStack(ModBlocks.transvector_interface), 'C', new ItemStack(Items.COMPARATOR)));
        addArcaneCraftingRecipe(LibRecipes.GAS_REMOVER, new ShapedArcaneRecipe(
                defaultGroup,
                "TT_GAS_REMOVER",
                10,
                new AspectList().add(Aspect.AIR, 2).add(Aspect.ORDER, 2),
                new ItemStack(ModItems.gas_remover),
                "BBB",
                "S L",
                "WWW",
                'S', ModItems.gas_shadow_item,
                'L', ModItems.gas_light_item,
                'B', "quartzDark",
                'W', "gemQuartz"));

        // Magnets
        ItemStack airCrystal = new ItemStack(ItemsTC.crystalEssence);
        ((ItemCrystalEssence) ItemsTC.crystalEssence).setAspects(airCrystal, new AspectList().add(Aspect.AIR, 1));
        ItemStack earthCrystal = new ItemStack(ItemsTC.crystalEssence);
        ((ItemCrystalEssence) ItemsTC.crystalEssence).setAspects(earthCrystal, new AspectList().add(Aspect.EARTH, 1));
        ItemStack focus = new ItemStack(ItemsTC.focus1);
        FocusPackage focusPackage = new FocusPackage();
        focusPackage.addNode(new FocusEffectTelekenesis());
        ItemFocus.setPackage(focus, focusPackage);
        addArcaneCraftingRecipe(LibRecipes.MOB_MAGNET, new ShapedArcaneRecipe(defaultGroup, LibResearch.CORPOREAL_MAGNET, 200, new AspectList().add(Aspect.AIR, 1).add(Aspect.ORDER, 1).add(Aspect.EARTH, 15).add(Aspect.ENTROPY, 1), new ItemStack(Objects.requireNonNull(ModBlocks.mob_magnet)), " C ", "ACE", "GFG", 'C', new ItemStack(ItemsTC.ingots, 1, 0), 'A', new TTIngredientNBT(airCrystal), 'E', new TTIngredientNBT(earthCrystal), 'G', new ItemStack(BlocksTC.logGreatwood), 'F', new TTFocusIngredient(FocusEffectTelekenesis.class, focus)));
        addArcaneCraftingRecipe(LibRecipes.ITEM_MAGNET, new ShapedArcaneRecipe(defaultGroup, LibResearch.KINETIC_MAGNET, 20, new AspectList().add(Aspect.AIR, 1).add(Aspect.ORDER, 1).add(Aspect.EARTH, 15).add(Aspect.ENTROPY, 1), new ItemStack(Objects.requireNonNull(ModBlocks.magnet)), " C ", "ACE", "GFG", 'C', "ingotIron", 'A', new TTIngredientNBT(airCrystal), 'E', new TTIngredientNBT(earthCrystal), 'G', new ItemStack(BlocksTC.logGreatwood), 'F', new TTFocusIngredient(FocusEffectTelekenesis.class, focus)));
        addArcaneCraftingRecipe(LibRecipes.TABLET, new ShapedArcaneRecipe(defaultGroup, LibResearch.TABLET, 20, new AspectList().add(Aspect.AIR, 3).add(Aspect.ORDER, 2).add(Aspect.FIRE, 1), new ItemStack(Objects.requireNonNull(ModBlocks.animation_tablet)), "GIG", "ISI", 'G', new ItemStack(Items.GOLD_INGOT), 'I', new ItemStack(Items.IRON_INGOT), 'S', new ItemStack(ItemsTC.seals)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "revealing_helm"), new ShapelessArcaneRecipe(
                defaultGroup,
                "TT_REVEALING",
                5,
                new AspectList(),
                new ItemStack(ModItems.revealing_helm),
                new Object[]{
                        new ItemStack(ItemsTC.goggles),
                        new ItemStack(ItemsTC.thaumiumHelm)
                }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichor_cloth"), new ShapedArcaneRecipe(
                defaultGroup,
                "TT_ICHOR",
                750,
                new AspectList().add(Aspect.AIR, 10).add(Aspect.WATER, 10).add(Aspect.ORDER, 10).add(Aspect.EARTH, 10).add(Aspect.FIRE, 10).add(Aspect.ENTROPY, 10),
                new ItemStack(ModItems.kamiresource,3,4),
                "CCC",
                "III",
                "DDD",
                'C', new ItemStack(ItemsTC.fabric),
                'I', new ItemStack(ModItems.kamiresource,1,2),
                'D', new ItemStack(Items.DIAMOND)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichor_ingot"), new ShapedArcaneRecipe(
                defaultGroup,
                "TT_ICHOR",
                500,
                new AspectList().add(Aspect.AIR, 5).add(Aspect.WATER, 5).add(Aspect.ORDER, 5).add(Aspect.EARTH, 5).add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 5),
                new ItemStack(ModItems.kamiresource,1,3),
                " T ",
                "IDI",
                " I ",
                'T', new ItemStack(ItemsTC.ingots, 1, 0),
                'I', new ItemStack(ModItems.kamiresource,1,2),
                'D', new ItemStack(Items.DIAMOND)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichorium_sword"), new ShapedArcaneRecipe(
                defaultGroup,
                "TT_ICHORIUMTOOLS",
                150,
                new AspectList().add(Aspect.AIR, 3).add(Aspect.WATER, 3).add(Aspect.ORDER, 3).add(Aspect.EARTH, 3).add(Aspect.FIRE, 3).add(Aspect.ENTROPY, 3),
                new ItemStack(ModItems.ichorium_sword),
                "I",
                "I",
                "S",
                'I', new ItemStack(ModItems.kamiresource,1,3),
                'S', new ItemStack(BlocksTC.logSilverwood)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichorium_pick"), new ShapedArcaneRecipe(
                defaultGroup,
                "TT_ICHORIUMTOOLS",
                150,
                new AspectList().add(Aspect.AIR, 3).add(Aspect.WATER, 3).add(Aspect.ORDER, 3).add(Aspect.EARTH, 3).add(Aspect.FIRE, 3).add(Aspect.ENTROPY, 3),
                new ItemStack(ModItems.ichorium_pick),
                "III",
                " S ",
                " S ",
                'I', new ItemStack(ModItems.kamiresource,1,3),
                'S', new ItemStack(BlocksTC.logSilverwood)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichorium_shovel"), new ShapedArcaneRecipe(
                defaultGroup,
                "TT_ICHORIUMTOOLS",
                150,
                new AspectList().add(Aspect.AIR, 3).add(Aspect.WATER, 3).add(Aspect.ORDER, 3).add(Aspect.EARTH, 3).add(Aspect.FIRE, 3).add(Aspect.ENTROPY, 3),
                new ItemStack(ModItems.ichorium_shovel),
                "I",
                "S",
                "S",
                'I', new ItemStack(ModItems.kamiresource,1,3),
                'S', new ItemStack(BlocksTC.logSilverwood)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichorium_axe"), new ShapedArcaneRecipe(
                defaultGroup,
                "TT_ICHORIUMTOOLS",
                150,
                new AspectList().add(Aspect.AIR, 3).add(Aspect.WATER, 3).add(Aspect.ORDER, 3).add(Aspect.EARTH, 3).add(Aspect.FIRE, 3).add(Aspect.ENTROPY, 3),
                new ItemStack(ModItems.ichorium_axe),
                "II",
                "IS",
                " S",
                'I', new ItemStack(ModItems.kamiresource,1,3),
                'S', new ItemStack(BlocksTC.logSilverwood)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichor_helm"), new ShapedArcaneRecipe(
                defaultGroup,
                "TT_ICHORARMOR",
                150,
                new AspectList().add(Aspect.AIR, 3).add(Aspect.WATER, 3).add(Aspect.ORDER, 3).add(Aspect.EARTH, 3).add(Aspect.FIRE, 3).add(Aspect.ENTROPY, 3),
                new ItemStack(ModItems.ichor_helm),
                "CCC",
                "C C",
                'C', new ItemStack(ModItems.kamiresource,1,4)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichor_chest"), new ShapedArcaneRecipe(
                defaultGroup,
                "TT_ICHORARMOR",
                150,
                new AspectList().add(Aspect.AIR, 3).add(Aspect.WATER, 3).add(Aspect.ORDER, 3).add(Aspect.EARTH, 3).add(Aspect.FIRE, 3).add(Aspect.ENTROPY, 3),
                new ItemStack(ModItems.ichor_chest),
                "C C",
                "CCC",
                "CCC",
                'C', new ItemStack(ModItems.kamiresource,1,4)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichor_legs"), new ShapedArcaneRecipe(
                defaultGroup,
                "TT_ICHORARMOR",
                150,
                new AspectList().add(Aspect.AIR, 3).add(Aspect.WATER, 3).add(Aspect.ORDER, 3).add(Aspect.EARTH, 3).add(Aspect.FIRE, 3).add(Aspect.ENTROPY, 3),
                new ItemStack(ModItems.ichor_legs),
                "CCC",
                "C C",
                "C C",
                'C', new ItemStack(ModItems.kamiresource,1,4)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichor_boots"), new ShapedArcaneRecipe(
                defaultGroup,
                "TT_ICHORARMOR",
                150,
                new AspectList().add(Aspect.AIR, 3).add(Aspect.WATER, 3).add(Aspect.ORDER, 3).add(Aspect.EARTH, 3).add(Aspect.FIRE, 3).add(Aspect.ENTROPY, 3),
                new ItemStack(ModItems.ichor_boots),
                "C C",
                "C C",
                'C', new ItemStack(ModItems.kamiresource,1,4)));
    }

    private static void initializeInfusionRecipes() {
        // Empty for now
        addInfusionCraftingRecipe(LibRecipes.CLEANSER, new InfusionRecipe(LibResearch.CLEANSER, new ItemStack(Objects.requireNonNull(ModItems.cleaning_talisman)), 1, new AspectList().add(Aspect.MAN, 20).add(Aspect.TOOL, 20).add(Aspect.LIFE, 20), new ItemStack(Items.ENDER_PEARL), Items.GHAST_TEAR, "quartzDark", "quartzDark", "quartzDark", "quartzDark", "nitor"));
        addInfusionCraftingRecipe(LibRecipes.ENCHANTER, new InfusionRecipe(LibResearch.ENCHANTER, new ItemStack(Objects.requireNonNull(ModBlocks.osmotic_enchanter)), 1, new AspectList().add(Aspect.ELDRITCH, 20).add(Aspect.MIND, 10).add(Aspect.ENERGY, 20).add(Aspect.MAGIC, 50).add(Aspect.VOID, 20), new ItemStack(Blocks.ENCHANTING_TABLE), new ItemStack(Blocks.OBSIDIAN), new ItemStack(Blocks.OBSIDIAN), new ItemStack(Blocks.OBSIDIAN), new ItemStack(Blocks.OBSIDIAN), new ItemStack(Blocks.OBSIDIAN), new ItemStack(Objects.requireNonNull(ModItems.spellbinding_cloth)), new ItemStack(ItemsTC.ingots, 1, 0), new ItemStack(ItemsTC.ingots, 1, 0)));
        addInfusionCraftingRecipe(LibRecipes.REPAIRER, new InfusionRecipe(LibResearch.REPAIRER, new ItemStack(Objects.requireNonNull(ModBlocks.repairer)), 1, new AspectList().add(Aspect.CRAFT, 20).add(Aspect.TOOL, 15).add(Aspect.ORDER, 10).add(Aspect.MAGIC, 15), new ItemStack(BlocksTC.metalBlockThaumium), "plankWood", new ItemStack(Items.LEATHER), new ItemStack(ItemsTC.fabric), new ItemStack(ItemsTC.ingots, 1, 0), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.DIAMOND), new ItemStack(Blocks.COBBLESTONE)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichor_block"), new InfusionRecipe(
                "TT_ICHOR@1",
                new ItemStack(Objects.requireNonNull(ichor_block)),
                8,
                new AspectList().add(Aspect.LIGHT, 125).add(Aspect.MAN, 125).add(Aspect.SOUL, 250),
                new ItemStack(Items.NETHER_STAR),
                new ItemStack(ModItems.kamiresource, 1, 1),
                new ItemStack(Items.DIAMOND),
                new ItemStack(ModItems.kamiresource, 1, 0),
                new ItemStack(Items.ENDER_EYE)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "kami_helm"), new InfusionRecipe(
                "TT_KAMIHELM",
                new ItemStack(ModItems.kami_helm),
                13,
                new AspectList().add(Aspect.WATER, 150).add(Aspect.AURA, 125).add(Aspect.MIND, 60).add(Aspect.LIFE, 60).add(Aspect.LIGHT, 250).add(Aspect.PROTECT, 125),
                new ItemStack(ModItems.ichor_helm),
                new ItemStack(Items.DIAMOND),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(ItemsTC.thaumonomicon),
                new ItemStack(Items.CHORUS_FRUIT_POPPED),
                new ItemStack(Items.GOLDEN_HELMET),
                PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.NIGHT_VISION),
                new ItemStack(ItemsTC.goggles),
                new ItemStack(Items.GHAST_TEAR),
                new ItemStack(Items.FISH),
                new ItemStack(Items.CAKE),
                new ItemStack(Items.ENDER_EYE)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "kami_chest"), new InfusionRecipe(
                "TT_KAMICHEST",
                new ItemStack(ModItems.kami_chest),
                13,
                new AspectList().add(Aspect.AIR, 150).add(Aspect.PROTECT, 125).add(Aspect.FLIGHT, 125).add(Aspect.ORDER, 125).add(Aspect.LIGHT, 250).add(Aspect.ELDRITCH, 60),
                new ItemStack(ModItems.ichor_chest),
                new ItemStack(Items.DIAMOND),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(ItemsTC.thaumonomicon),
                new ItemStack(Items.CHORUS_FRUIT_POPPED),
                new ItemStack(Items.GOLDEN_CHESTPLATE),
                new ItemStack(ItemsTC.ringCloud),
                new ItemStack(Items.ELYTRA),
                new ItemStack(Items.SHIELD),
                new ItemStack(Items.FEATHER),
                new ItemStack(Items.GHAST_TEAR),
                new ItemStack(Items.ARROW)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "kami_legs"), new InfusionRecipe(
                "TT_KAMILEGS",
                new ItemStack(ModItems.kami_legs),
                13,
                new AspectList().add(Aspect.AIR, 150).add(Aspect.PROTECT, 125).add(Aspect.FLIGHT, 125).add(Aspect.ORDER, 125).add(Aspect.LIGHT, 250).add(Aspect.ELDRITCH, 60),
                new ItemStack(ModItems.ichor_legs),
                new ItemStack(Items.DIAMOND),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(ItemsTC.thaumonomicon),
                new ItemStack(Items.CHORUS_FRUIT_POPPED),
                new ItemStack(Items.GOLDEN_CHESTPLATE),
                PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.FIRE_RESISTANCE),
                new ItemStack(Objects.requireNonNull(ModItems.energetic_nitor)),
                new ItemStack(BlocksTC.lampArcane),
                new ItemStack(Items.LAVA_BUCKET),
                new ItemStack(Items.FIRE_CHARGE),
                new ItemStack(Items.BLAZE_ROD)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "kami_boots"), new InfusionRecipe(
                "TT_KAMIBOOTS",
                new ItemStack(ModItems.kami_boots),
                13,
                new AspectList().add(Aspect.EARTH, 150).add(Aspect.PROTECT, 125).add(Aspect.TOOL, 125).add(Aspect.MOTION, 125).add(Aspect.LIGHT, 250).add(Aspect.PLANT, 60).add(Aspect.FLIGHT, 60),
                new ItemStack(ModItems.ichor_boots),
                new ItemStack(Items.DIAMOND),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(ItemsTC.thaumonomicon),
                new ItemStack(Items.CHORUS_FRUIT_POPPED),
                new ItemStack(Items.GOLDEN_BOOTS),
                new ItemStack(BlocksTC.grassAmbient),
                new ItemStack(Items.WHEAT_SEEDS),
                new ItemStack(BlocksTC.lampGrowth),
                new ItemStack(ItemsTC.turretPlacer, 1, 2),
                new ItemStack(Blocks.WOOL),
                new ItemStack(Items.LEAD)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichorium_pick_adv"), new InfusionRecipe(
                "TT_ICHOR_PICK_ADV",
                new ItemStack(ModItems.ichorium_pick_adv),
                13,
                new AspectList().add(Aspect.FIRE, 150).add(Aspect.DESIRE, 60).add(Aspect.METAL, 125).add(Aspect.TOOL, 250).add(Aspect.SENSES, 60).add(Aspect.EARTH, 125),
                new ItemStack(ModItems.ichorium_pick),
                new ItemStack(ModItems.kamiresource, 1, 3),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(ItemsTC.elementalPick),
                new ItemStack(ItemsTC.mechanismComplex),
                new ItemStack(Blocks.TNT),
                new ItemStack(ItemsTC.clusters, 1, 6),
                new ItemStack(ItemsTC.clusters, 1, 0),
                new ItemStack(ItemsTC.clusters, 1, 1),
                new ItemStack(Items.DIAMOND),
                new ItemStack(ItemsTC.mechanismComplex),
                new ItemStack(ItemsTC.elementalPick),
                new ItemStack(ModItems.kamiresource, 1, 4)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichorium_shovel_adv"), new InfusionRecipe(
                "TT_ICHOR_SHOVEL_ADV",
                new ItemStack(ModItems.ichorium_shovel_adv),
                13,
                new AspectList().add(Aspect.TOOL, 250).add(Aspect.SENSES, 60).add(Aspect.EARTH, 125).add(Aspect.TRAP, 60),
                new ItemStack(ModItems.ichorium_shovel),
                new ItemStack(ModItems.kamiresource, 1, 3),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(ItemsTC.elementalShovel),
                new ItemStack(ItemsTC.mechanismComplex),
                new ItemStack(Blocks.TNT),
                new ItemStack(ItemsTC.clusters, 1, 6),
                new ItemStack(ItemsTC.clusters, 1, 0),
                new ItemStack(ItemsTC.clusters, 1, 1),
                new ItemStack(Items.DIAMOND),
                new ItemStack(ItemsTC.mechanismComplex),
                new ItemStack(ItemsTC.elementalShovel),
                new ItemStack(ModItems.kamiresource, 1, 4)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichorium_axe_adv"), new InfusionRecipe(
                "TT_ICHOR_AXE_ADV",
                new ItemStack(ModItems.ichorium_axe_adv),
                13,
                new AspectList().add(Aspect.WATER, 150).add(Aspect.PLANT, 125).add(Aspect.TOOL, 250).add(Aspect.SENSES, 60),
                new ItemStack(ModItems.ichorium_axe),
                new ItemStack(ModItems.kamiresource, 1, 3),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(ItemsTC.elementalAxe),
                new ItemStack(ItemsTC.mechanismComplex),
                new ItemStack(Blocks.TNT),
                new ItemStack(ItemsTC.clusters, 1, 6),
                new ItemStack(ItemsTC.clusters, 1, 0),
                new ItemStack(ItemsTC.clusters, 1, 1),
                new ItemStack(Items.DIAMOND),
                new ItemStack(ItemsTC.mechanismComplex),
                new ItemStack(ItemsTC.elementalAxe),
                new ItemStack(ModItems.kamiresource, 1, 4)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichorium_sword_adv"), new InfusionRecipe(
                "TT_ICHOR_SWORD_ADV",
                new ItemStack(ModItems.ichorium_sword_adv),
                13,
                new AspectList().add(Aspect.AIR, 150).add(Aspect.DESIRE, 250).add(Aspect.ORDER, 60).add(Aspect.ENERGY, 125).add(Aspect.CRYSTAL, 60).add(Aspect.SOUL, 125).add(Aspect.AVERSION, 125),
                new ItemStack(ModItems.ichorium_sword),
                new ItemStack(ModItems.kamiresource, 1, 3),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(ItemsTC.elementalSword),
                new ItemStack(ItemsTC.mechanismComplex),
                new ItemStack(Blocks.CACTUS),
                new ItemStack(ItemsTC.clusters, 1, 6),
                new ItemStack(ItemsTC.clusters, 1, 0),
                new ItemStack(ItemsTC.clusters, 1, 1),
                new ItemStack(Items.DIAMOND),
                new ItemStack(ItemsTC.mechanismComplex),
                new ItemStack(ItemsTC.elementalSword),
                new ItemStack(ModItems.kamiresource, 1, 4)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "block_talisman"), new InfusionRecipe(
                "TT_BLACKHOLE_RING",
                new ItemStack(ModItems.block_talisman),
                9,
                new AspectList().add(Aspect.ELDRITCH, 125).add(Aspect.MAGIC, 200).add(Aspect.DARKNESS, 125).add(Aspect.VOID, 250),
                new ItemStack(ItemsTC.focus2),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(Blocks.ENDER_CHEST),
                new ItemStack(Items.DIAMOND),
                new ItemStack(ModItems.kamiresource, 1, 2),
                ThaumcraftApiHelper.makeCrystal(Aspect.FLUX),
                new ItemStack(BlocksTC.jarVoid)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "proto_clay"), new InfusionRecipe(
                "TT_PROTO_CLAY",
                new ItemStack(ModItems.proto_clay),
                4,
                new AspectList().add(Aspect.TOOL, 30).add(Aspect.MAN, 30),
                new ItemStack(Items.CLAY_BALL),
                new ItemStack(Blocks.DIRT),
                new ItemStack(Blocks.LOG),
                new ItemStack(Blocks.STONE),
                new ItemStack(ModItems.kamiresource, 1, 0)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "ichor_pouch"), new InfusionRecipe(
                "TT_ICHOR_POUCH",
                new ItemStack(ModItems.focus_pouch),
                7,
                new AspectList().add(Aspect.AIR, 250).add(Aspect.ELDRITCH, 125).add(Aspect.MAN, 125).add(Aspect.CRAFT, 125).add(Aspect.VOID, 250),
                new ItemStack(ItemsTC.focusPouch),
                new ItemStack(ModItems.kamiresource, 1, 4),
                new ItemStack(BlocksTC.hungryChest),
                "gemDiamond",
                new ItemStack(ModItems.kamiresource, 1, 4),
                new ItemStack(ItemsTC.focus2),
                new ItemStack(BlocksTC.jarVoid)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "cat_amulet"), new InfusionRecipe(
                "TT_CAT_AMULET",
                new ItemStack(ModItems.cat_amulet),
                8,
                new AspectList().add(Aspect.DARKNESS, 75).add(Aspect.ORDER, 125).add(Aspect.MIND, 75),
                new ItemStack(Blocks.QUARTZ_BLOCK),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(Items.GOLD_INGOT),
                new ItemStack(Items.GOLD_INGOT),
                new ItemStack(Items.DYE, 1, 3),
                new ItemStack(Blocks.LEAVES, 1, 3),
                new ItemStack(Items.FISH)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "placement_mirror"), new InfusionRecipe(
                "TT_PLACEMENT_MIRROR",
                new ItemStack(ModItems.placement_mirror),
                12,
                new AspectList().add(Aspect.CRAFT, 65).add(Aspect.CRYSTAL, 32).add(Aspect.MAGIC, 50).add(Aspect.MIND, 32),
                new ItemStack(ModItems.block_talisman),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(Blocks.DROPPER),
                new ItemStack(Items.DIAMOND),
                new ItemStack(Blocks.GLASS),
                new ItemStack(Items.BLAZE_POWDER),
                new ItemStack(ModItems.kamiresource, 1, 2)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "sky_pearl"), new InfusionRecipe(
                "TT_WARP_SERIES@0",
                new ItemStack(ModItems.sky_pearl),
                6, new AspectList().add(Aspect.MOTION, 125).add(Aspect.ELDRITCH, 125).add(Aspect.FLIGHT, 125).add(Aspect.AIR, 65),
                new ItemStack(Items.ENDER_PEARL),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(ModItems.kamiresource, 1, 0),
                new ItemStack(Blocks.LAPIS_BLOCK),
                new ItemStack(Items.DIAMOND)));
        addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "warp_gate"), new InfusionRecipe(
                "TT_WARP_SERIES",
                new ItemStack(ModBlocks.warp_gate),
                8, new AspectList().add(Aspect.MOTION, 250).add(Aspect.ELDRITCH, 50).add(Aspect.FLIGHT, 50),
                new ItemStack(BlocksTC.pavingStoneTravel),
                new ItemStack(ModItems.kamiresource, 1, 2),
                new ItemStack(ModItems.kamiresource, 1, 1),
                new ItemStack(ModBlocks.transvector_dislocator),
                new ItemStack(ModItems.kamiresource),
                new ItemStack(Items.DIAMOND),
                new ItemStack(Items.FEATHER)));
        /*
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "chlorophyte_ore"), new InfusionRecipe(
                "TT_CHLOROPHYTE@0",
                new ItemStack(ModBlocks.chlorophyte_ore),
                8,
                new AspectList().add(Aspect.PLANT, 250).add(Aspect.METAL, 250).add(Aspect.CRAFT, 125),
                new ItemStack(Blocks.EMERALD_ORE),
                new ItemStack(BlocksTC.lampGrowth),
                new ItemStack(BlocksTC.shimmerleaf),
                new ItemStack(BlocksTC.lampGrowth),
                new ItemStack(BlocksTC.cinderpearl),
                new ItemStack(BlocksTC.lampGrowth),
                new ItemStack(BlocksTC.vishroom),
                new ItemStack(BlocksTC.lampGrowth),
                new ItemStack(Items.NETHER_STAR)));
         */
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "IchoriumFortressHelm"), new InfusionRecipe(
                "TT_ICHORIUM_FORTRESS",
                new ItemStack(ModItems.ichorium_fortress_helmet),
                13,
                new AspectList().add(Aspect.PLANT, 500).add(Aspect.METAL, 100).add(Aspect.PROTECT, 40).add(Aspect.ENERGY, 50),
                new ItemStack(ItemsTC.fortressHelm, 1, 32767),
                "ingotIchorium",
                "ingotIchorium",
                new ItemStack(Items.GOLD_INGOT),
                new ItemStack(Items.GOLD_INGOT),
                new ItemStack(Items.EMERALD)));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "IchoriumFortressChest"), new InfusionRecipe(
                "TT_ICHORIUM_FORTRESS",
                new ItemStack(ModItems.ichorium_fortress_chestplate),
                13, new AspectList().add(Aspect.PLANT, 500).add(Aspect.METAL, 100).add(Aspect.PROTECT, 60).add(Aspect.ENERGY, 50),
                new ItemStack(ItemsTC.fortressChest, 1, 32767),
                "ingotIchorium",
                "ingotIchorium",
                "ingotIchorium",
                "ingotIchorium",
                new ItemStack(Items.GOLD_INGOT),
                "leather"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "IchoriumFortressLegs"), new InfusionRecipe(
                "TT_ICHORIUM_FORTRESS",
                new ItemStack(ModItems.ichorium_fortress_leggings),
                13,
                new AspectList().add(Aspect.PLANT, 500).add(Aspect.METAL, 100).add(Aspect.PROTECT, 50).add(Aspect.ENERGY, 50),
                new ItemStack(ItemsTC.fortressLegs, 1, 32767),
                "ingotIchorium",
                "ingotIchorium",
                "ingotIchorium",
                new ItemStack(Items.GOLD_INGOT),
                "leather"));

        Item[] stacks = new Item[]{ModItems.ichorium_fortress_helmet, ModItems.ichorium_fortress_chestplate, ModItems.ichorium_fortress_leggings};

        for (Item item : stacks) {
            addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "air_upgrade" + item.getTranslationKey()), new InfusionRecipe(
                    "",
                    new Object[]{"kami_upgrade", new NBTTagByte((byte) 1)},
                    1, new AspectList().add(Aspect.AIR, 1),
                    item,
                    new ItemStack(Items.FEATHER),
                    new ItemStack(Items.FEATHER)));
            addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "water_upgrade" + item.getTranslationKey()), new InfusionRecipe(
                    "",
                    new Object[]{"kami_upgrade", new NBTTagByte((byte) 0)},
                    1, new AspectList().add(Aspect.WATER, 1),
                    item,
                    new ItemStack(Items.SNOWBALL),
                    new ItemStack(Items.SNOWBALL)));
            addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "fire_upgrade" + item.getTranslationKey()), new InfusionRecipe(
                    "",
                    new Object[]{"kami_upgrade", new NBTTagByte((byte) 2)},
                    1, new AspectList().add(Aspect.FIRE, 1),
                    item,
                    new ItemStack(Items.FLINT),
                    new ItemStack(Items.FLINT)));
            addInfusionCraftingRecipe(new ResourceLocation(LibMisc.MOD_ID, "earth_upgrade" + item.getTranslationKey()), new InfusionRecipe(
                    "",
                    new Object[]{"kami_upgrade", new NBTTagByte((byte) 3)},
                    1, new AspectList().add(Aspect.EARTH, 1),
                    item,
                    new ItemStack(Items.APPLE),
                    new ItemStack(Items.APPLE)));
        }
    }
}