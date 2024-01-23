package com.nekokittygames.thaumictinkerer.common.dim;


import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;

public class ProviderBedrock extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.BedrockDimensionType;
    }

    @Override
    public String getSaveFolder() {
        return "DIM" + TTConfig.BedRockDimensionID;
    }

    @Override
    public ChunkGeneratorMining createChunkGenerator() {
        return new ChunkGeneratorMining(world, getSeed());
    }

    protected void generateLightBrightnessTable() {
        float f = 12.0F;
        for (int i = 0; i <= 15; i++) {
            float f1 = 12.0F - i / 15.0F;
            this.lightBrightnessTable[i] = ((1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f);
        }
    }

    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public float getCloudHeight() {
        return 0F;
    }

    public boolean canCoordinateBeSpawn(int par1, int par2) {
        return false;
    }
}