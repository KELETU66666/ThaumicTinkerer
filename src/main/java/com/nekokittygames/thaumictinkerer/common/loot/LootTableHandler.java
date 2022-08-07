package com.nekokittygames.thaumictinkerer.common.loot;

import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.KilledByPlayer;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LootTableHandler {
    @SubscribeEvent
    public static void onLootTablesLoaded(LootTableLoadEvent event) {
        if (TTConfig.shardDropRate != 0) {
            if (event.getName().equals(LootTableList.ENTITIES_ENDERMAN)) {
                LootPool table = new LootPool(
                        new LootEntry[]{
                                new LootEntryItem(ModItems.kamiresource, 1, 1, new LootFunction[]{new SetMetadata(new LootCondition[]{new KilledByPlayer(false)}, new RandomValueRange(0))},
                                        new LootCondition[]{new KilledByPlayer(false)},
                                        LibMisc.MOD_ID + ":ender_shard")},
                        new LootCondition[]{new RandomChance(TTConfig.shardDropRate)}, new RandomValueRange(1),
                        new RandomValueRange(0), LibMisc.MOD_ID + "_enderman");
                event.getTable().addPool(table);
            } else if (event.getName().equals(LootTableList.ENTITIES_ENDERMITE)) {
                LootPool table = new LootPool(
                        new LootEntry[]{
                                new LootEntryItem(ModItems.kamiresource, 1, 1, new LootFunction[]{new SetMetadata(new LootCondition[]{new KilledByPlayer(false)}, new RandomValueRange(0))},
                                        new LootCondition[]{new KilledByPlayer(false)},
                                        LibMisc.MOD_ID + ":ender_shard1")},
                        new LootCondition[]{new RandomChance(TTConfig.shardDropRate)}, new RandomValueRange(1),
                        new RandomValueRange(0), LibMisc.MOD_ID + "_endermite");
                event.getTable().addPool(table);
            } else if (event.getName().equals(LootTableList.ENTITIES_SHULKER)) {
                LootPool table = new LootPool(
                        new LootEntry[]{
                                new LootEntryItem(ModItems.kamiresource, 1, 1, new LootFunction[]{new SetMetadata(new LootCondition[]{new KilledByPlayer(false)}, new RandomValueRange(0))},
                                        new LootCondition[]{new KilledByPlayer(false)},
                                        LibMisc.MOD_ID + ":ender_shard2")},
                        new LootCondition[]{new RandomChance(TTConfig.shardDropRate)}, new RandomValueRange(1),
                        new RandomValueRange(0), LibMisc.MOD_ID + "_shulker");
                event.getTable().addPool(table);
            } else if (event.getName().equals(LootTableList.ENTITIES_ZOMBIE_PIGMAN)) {
                LootPool table = new LootPool(
                        new LootEntry[]{
                                new LootEntryItem(ModItems.kamiresource, 1, 1, new LootFunction[]{new SetMetadata(new LootCondition[]{new KilledByPlayer(false)}, new RandomValueRange(1))},
                                        new LootCondition[]{new KilledByPlayer(false)},
                                        LibMisc.MOD_ID + ":nether_shard")},
                        new LootCondition[]{new RandomChance(TTConfig.shardDropRate)}, new RandomValueRange(1),
                        new RandomValueRange(0), LibMisc.MOD_ID + "_pigzombie");
                event.getTable().addPool(table);
            } else if (event.getName().equals(LootTableList.ENTITIES_BLAZE)) {
                LootPool table = new LootPool(
                        new LootEntry[]{
                                new LootEntryItem(ModItems.kamiresource, 1, 1, new LootFunction[]{new SetMetadata(new LootCondition[]{new KilledByPlayer(false)}, new RandomValueRange(1))},
                                        new LootCondition[]{new KilledByPlayer(false)},
                                        LibMisc.MOD_ID + ":nether_shard1")},
                        new LootCondition[]{new RandomChance(TTConfig.shardDropRate)}, new RandomValueRange(1),
                        new RandomValueRange(0), LibMisc.MOD_ID + "_blaze");
                event.getTable().addPool(table);
            } else if (event.getName().equals(LootTableList.ENTITIES_WITHER_SKELETON)) {
                LootPool table = new LootPool(
                        new LootEntry[]{
                                new LootEntryItem(ModItems.kamiresource, 1, 1, new LootFunction[]{new SetMetadata(new LootCondition[]{new KilledByPlayer(false)}, new RandomValueRange(1))},
                                        new LootCondition[]{new KilledByPlayer(false)},
                                        LibMisc.MOD_ID + ":nether_shard2")},
                        new LootCondition[]{new RandomChance(TTConfig.shardDropRate)}, new RandomValueRange(1),
                        new RandomValueRange(0), LibMisc.MOD_ID + "_witherskeleton");
                event.getTable().addPool(table);
            }
        }
    }
}