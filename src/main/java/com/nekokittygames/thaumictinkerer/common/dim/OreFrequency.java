package com.nekokittygames.thaumictinkerer.common.dim;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

public class OreFrequency {
    private static final Map<String, Integer> ORES = new Object2IntOpenHashMap<>();

    public static void init() {
        ORES.clear();
        try {
            for (String entry : TTConfig.BedRockDimensionOres) {
                String[] splits = entry.split(",");
                ORES.put(splits[0], Integer.valueOf(splits[1]));
                ThaumicTinkerer.logger.debug("OreClusterGenerator ore added via config: {}", splits[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getSum() {
        int total = 0;
        for (Map.Entry<String, Integer> entry : ORES.entrySet()) {
            total += entry.getValue();
        }
        return total;
    }

    public static List<String> getValidOres() {
        ArrayList<String> result = new ArrayList<>();
        for (String key : ORES.keySet()) {
            if (isValid(key)) {
                result.add(key);
            }
        }
        return result;
    }

    public static ItemStack getRandomOre(Random rand) {
        int randInt = rand.nextInt(getSum());

        for (String key : getValidOres()) {
            randInt -= ORES.get(key);
            if (randInt < 0) {
                return OreDictionary.getOres(key).get(0);
            }
        }
        return new ItemStack(Blocks.IRON_ORE);
    }

    public static boolean isValid(String name) {
        return !Arrays.asList(OreClusterGenerator.blacklist).contains(name) && !OreDictionary.getOres(name).isEmpty() && OreDictionary.getOres(name).get(0).getItem() instanceof ItemBlock;
    }
}
