package com.nekokittygames.thaumictinkerer.common.compat.Tconstruct.fluid;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidMoltenIchorium extends Fluid {
    public static final FluidMoltenIchorium INSTANCE = new FluidMoltenIchorium();

    private FluidMoltenIchorium() {
        super("molten_ichorium", new ResourceLocation("tconstruct:blocks/fluids/molten_metal"), new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow"));
        this.setLuminosity(15).setTemperature(1700);
    }
    
    @Override
    public int getColor() {
        return 0xB26507;
    }

    public Fluid setBlock(Block block) {
        return null;
    }
}