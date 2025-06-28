package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import com.nekokittygames.thaumictinkerer.common.items.ItemInfusedSeeds;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.text.WordUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.resources.ItemCrystalEssence;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AspectCropLootManager {

    private static HashMap<Aspect, HashMap<ItemStack, Integer>> lootMap = new HashMap<>();

    public static ItemStack getLootForAspect(Aspect aspect) {
        HashMap<ItemStack, Integer> aspectHashmap = lootMap.get(aspect);
        // Find total value of the possible ItemStacks for the aspect
        if (aspectHashmap == null) return ItemStack.EMPTY;
        int sum = 0;

        for (Integer i : aspectHashmap.values()) {
            sum += i;
        }
        if (sum > 0) {
            Random rand = new Random();
            int randInt = rand.nextInt(sum);
            for (Map.Entry<ItemStack, Integer> pair : aspectHashmap.entrySet()) {
                if (randInt <= 0) {
                    return pair.getKey().copy();
                }
                randInt -= pair.getValue();
            }
        }
        return ItemStack.EMPTY;
    }

    public static void addAspectLoot(Aspect aspect, ItemStack stack) {
        addAspectLoot(aspect, stack, 1);
    }

    public static void addAspectLoot(Aspect aspect, String target) {
        if (TTConfig.balancedCrop)
            return;

        for (String ore : OreDictionary.getOreNames()) {
            if (ore.contains(WordUtils.capitalizeFully(target)) || ore.contains(target)) {
                NonNullList<ItemStack> ores = OreDictionary.getOres(ore);
                for (ItemStack stack : ores) {
                    addAspectLoot(aspect, stack);
                }
            }
        }
    }

    public static void addAspectLoot(Aspect aspect, String target, int count) {
        for (String ore : OreDictionary.getOreNames()) {
            if (ore.contains(WordUtils.capitalizeFully(target)) || ore.contains(target)) {
                NonNullList<ItemStack> ores = OreDictionary.getOres(ore);
                for (ItemStack stack : ores) {
                    ItemStack newStack = stack.copy();
                    newStack.setCount(count);
                    addAspectLoot(aspect, newStack);
                }
            }
        }
    }

    public static void addAspectLoot(Aspect aspect, ItemStack stack, int power) {
        lootMap.get(aspect).put(stack, power);
    }

    public static void populateLootMap() {
        // Initialize map for all aspects
        Aspect.aspects.values().forEach(a -> lootMap.put(a, new HashMap<>()));

        // Primal Aspects
        addPrimalAspectSeeds();

        // Basic aspects
        addAspectLoot(Aspect.ORDER, new ItemStack(Blocks.GLASS, 4));
        addAspectLoot(Aspect.ENTROPY, new ItemStack(Blocks.SAND, 4));

        // Compound aspects
        addElditchAspectLoot();
        addTreeAspectLoot();
        addAuraAspectLoot();
        addBeastAspectLoot();
        addMindAspectLoot();
        addFleshAndUndeadAspectLoot();
        addCraftAspectLoot();
        addElementalAspectLoot();
        addNatureAspectLoot();
        addHumanAspectLoot();
        addToolsAndArmorAspectLoot();
        addMaterialAspectLoot();
        addMechanicalAspectLoot();
        addEnergyAspectLoot();
        addMagicAspectLoot();
        addSensoryAspectLoot();
        addOtherAspectLoot();
    }

    private static void addPrimalAspectSeeds() {
        Aspect[] primalAspects = {Aspect.AIR, Aspect.FIRE, Aspect.EARTH, Aspect.WATER};
        for (Aspect aspect : primalAspects) {
            ItemStack seedStack = new ItemStack(ModItems.infused_seeds);
            ItemInfusedSeeds.setAspect(seedStack, aspect);
            addAspectLoot(aspect, seedStack);
        }
        addAspectLoot(Aspect.AIR, new ItemStack(ModItems.fruit_aer));
        addAspectLoot(Aspect.FIRE, new ItemStack(ModItems.fruit_ignis));
        addAspectLoot(Aspect.EARTH, new ItemStack(ModItems.fruit_terra));
        addAspectLoot(Aspect.WATER, new ItemStack(ModItems.fruit_aqua));
    }

    private static void addElditchAspectLoot() {
        addAspectLoot(Aspect.ELDRITCH, new ItemStack(Items.ENDER_PEARL, 1), 10);
        addAspectLoot(Aspect.ELDRITCH, new ItemStack(Items.ENDER_EYE, 1), 5);
        addAspectLoot(Aspect.ELDRITCH, "bucketEnder");
    }

    private static void addTreeAspectLoot() {
        addAspectLoot(Aspect.PLANT, "wood");
        addAspectLoot(Aspect.PLANT, new ItemStack(Blocks.LOG));
    }

    private static void addAuraAspectLoot() {
        for (Aspect tag : Aspect.aspects.values()) {
            ItemStack wisp = new ItemStack(ItemsTC.crystalEssence);
            ((ItemCrystalEssence) ItemsTC.crystalEssence).setAspects(wisp, new AspectList().add(tag, 2));
            addAspectLoot(Aspect.AURA, wisp);
        }
    }

    private static void addBeastAspectLoot() {
        addAspectLoot(Aspect.BEAST, new ItemStack(Items.EGG, 1));
    }

    private static void addMindAspectLoot() {
        addAspectLoot(Aspect.MIND, new ItemStack(Items.PAPER, 4), 15);
        addAspectLoot(Aspect.MIND, new ItemStack(Items.BOOK, 2), 10);
        addAspectLoot(Aspect.MIND, new ItemStack(Blocks.BOOKSHELF, 1), 5);

        //for (Enchantment enchant : Enchantment.REGISTRY) {
        //    addAspectLoot(Aspect.MIND, Items.ENCHANTED_BOOK.getEnchantedItemStack(new EnchantmentData(enchant, 1)), 1);
        //}
    }

    private static void addFleshAndUndeadAspectLoot() {
        addAspectLoot(Aspect.DEATH, new ItemStack(ItemsTC.tallow, 1), 4);
        addAspectLoot(Aspect.UNDEAD, new ItemStack(Items.ROTTEN_FLESH, 2));
    }

    private static void addCraftAspectLoot() {
        addAspectLoot(Aspect.CRAFT, new ItemStack(Item.getItemFromBlock(ModBlocks.black_quartz_block), 2));
        addAspectLoot(Aspect.CRAFT, new ItemStack(BlocksTC.arcaneWorkbench, 1));
    }

    private static void addElementalAspectLoot() {
        addAspectLoot(Aspect.COLD, new ItemStack(Items.SNOWBALL, 1));
        addAspectLoot(Aspect.COLD, "rodBlizz");
    }

    private static void addNatureAspectLoot() {
        addAspectLoot(Aspect.PLANT, "sapling");
        for (int i = 0; i < 6; i++) {
            addAspectLoot(Aspect.PLANT, new ItemStack(Blocks.SAPLING, 1, i));
        }
    }

    private static void addHumanAspectLoot() {
        // Note: You'll need to verify the correct number of golem cores in TC6
        for (int i = 0; i < 12; i++) {
            addAspectLoot(Aspect.MAN, new ItemStack(ItemsTC.seals, 1, i));
        }
    }

    private static void addToolsAndArmorAspectLoot() {
        addAspectLoot(Aspect.PROTECT, new ItemStack(Items.DIAMOND_BOOTS));
        addAspectLoot(Aspect.PROTECT, new ItemStack(Items.DIAMOND_LEGGINGS));
        addAspectLoot(Aspect.PROTECT, new ItemStack(Items.DIAMOND_CHESTPLATE));
        addAspectLoot(Aspect.PROTECT, new ItemStack(Items.DIAMOND_HELMET));

        addAspectLoot(Aspect.TOOL, new ItemStack(ItemsTC.thaumiumPick));
        addAspectLoot(Aspect.TOOL, new ItemStack(ItemsTC.thaumiumAxe));
        addAspectLoot(Aspect.TOOL, new ItemStack(ItemsTC.thaumiumHoe));
        addAspectLoot(Aspect.TOOL, new ItemStack(ItemsTC.thaumiumSword));
        addAspectLoot(Aspect.TOOL, new ItemStack(ItemsTC.thaumiumShovel));
    }

    private static void addMaterialAspectLoot() {
        addAspectLoot(Aspect.METAL, new ItemStack(Items.IRON_INGOT, 1), 100);
        addAspectLoot(Aspect.METAL, new ItemStack(Items.GOLD_INGOT, 1));
        addAspectLoot(Aspect.CRYSTAL, new ItemStack(Items.DIAMOND));
    }

    private static void addMechanicalAspectLoot() {
        addAspectLoot(Aspect.MECHANISM, new ItemStack(Blocks.PISTON, 1));
        addAspectLoot(Aspect.MECHANISM, new ItemStack(Blocks.PISTON, 1, 1));
        addAspectLoot(Aspect.MECHANISM, "gear");
        addAspectLoot(Aspect.MOTION, new ItemStack(Blocks.RAIL), 1);
        addAspectLoot(Aspect.MOTION, new ItemStack(Blocks.ACTIVATOR_RAIL));
    }

    private static void addEnergyAspectLoot() {
        addAspectLoot(Aspect.ENERGY, new ItemStack(Items.REDSTONE, 2));
    }

    private static void addMagicAspectLoot() {
        addAspectLoot(Aspect.MAGIC, new ItemStack(ItemsTC.salisMundus));
    }

    private static void addSensoryAspectLoot() {
        addAspectLoot(Aspect.LIGHT, new ItemStack(Items.GLOWSTONE_DUST, 1), 5);
        addAspectLoot(Aspect.LIGHT, new ItemStack(ItemsTC.alumentum, 4));
        addAspectLoot(Aspect.LIGHT, new ItemStack(ModItems.energetic_nitor));
    }

    private static void addOtherAspectLoot() {
        // Add remaining aspect loots
        addAspectLoot(Aspect.DARKNESS, new ItemStack(Blocks.OBSIDIAN, 1));
        addAspectLoot(Aspect.VOID, new ItemStack(Items.BUCKET));
        addAspectLoot(Aspect.VOID, "bucket");
        addAspectLoot(Aspect.LIFE, new ItemStack(Items.EGG, 1));
        addAspectLoot(Aspect.TRAP, new ItemStack(Blocks.WEB, 1));
        addAspectLoot(Aspect.FLUX, new ItemStack(ItemsTC.voidSeed, 1));
    }
}