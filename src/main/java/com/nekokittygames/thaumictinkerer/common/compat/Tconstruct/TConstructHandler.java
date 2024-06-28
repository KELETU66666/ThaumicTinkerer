package com.nekokittygames.thaumictinkerer.common.compat.Tconstruct;

import com.nekokittygames.thaumictinkerer.common.compat.Tconstruct.fluid.TTFluid;
import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.tools.TinkerTraits;

public class TConstructHandler {

    public static final Fluid fluidIchorium = new TTFluid("molten_ichorium", new ResourceLocation("tconstruct:blocks/fluids/molten_metal"), new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow")).setColor(0xFFB26507).setLuminosity(15).setTemperature(1700);
    public static final AbstractTrait traitInstantBreak = new TraitInstantBreak();

    public static void preInit(FMLPreInitializationEvent e){

        MinecraftForge.EVENT_BUS.register(traitInstantBreak);

        if (TinkerRegistry.getMaterial("ichorium") == Material.UNKNOWN) {
            Material ichorium = new Material("ichorium", 0xA75110);
            ichorium.setCraftable(false).setCastable(true);
            ichorium.setFluid(fluidIchorium);
            ichorium.addTrait(traitInstantBreak, MaterialTypes.HEAD).addTrait(TinkerTraits.magnetic2, MaterialTypes.HEAD).addTrait(TinkerTraits.writable2, MaterialTypes.HEAD);
            ichorium.addTrait(TinkerTraits.writable);

            if (e.getSide() == Side.CLIENT)
                setMetalMaterialRenderInfo(ichorium, 0xA75110, 0.7f, 0f, 0.1f);

            TinkerRegistry.addMaterialStats(ichorium, new HeadMaterialStats(6375, 32, TTConfig.IWeaponDamage, HarvestLevels.COBALT), new HandleMaterialStats(2, 650), new ExtraMaterialStats(550), new BowMaterialStats(TTConfig.IWeaponDamage, 6.5f, 7));

            MaterialIntegration mi = new MaterialIntegration(ichorium, fluidIchorium);
            mi.oreSuffix = "Ichorium";
            mi.toolforge();
            mi.setRepresentativeItem("ingotIchorium");
            TinkerRegistry.integrate(mi).preInit();
        }
    }

    @SideOnly(Side.CLIENT)
    public static Material setMetalMaterialRenderInfo(Material material, int color, float shinyness, float brightness, float hueshift){
        material.setRenderInfo(new MaterialRenderInfo.Metal(color, 0.7f, 0f, 0.1f));
        return material;
    }
}