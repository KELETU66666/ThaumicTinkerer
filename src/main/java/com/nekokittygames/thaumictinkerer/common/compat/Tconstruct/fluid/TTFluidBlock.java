package com.nekokittygames.thaumictinkerer.common.compat.Tconstruct.fluid;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class TTFluidBlock extends BlockFluidClassic {

    public TTFluidBlock(Fluid fluid, Material material) {
        super(fluid, material);

        this.setRegistryName(fluid.getName());
        this.setTranslationKey(fluid.getName());
    }
}