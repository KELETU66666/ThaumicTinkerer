package com.nekokittygames.thaumictinkerer.common.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import thaumcraft.api.aspects.AspectRegistryEvent;

public interface ITTProxy {


    void registerRenderers();

    String localize(String translationKey, Object... args);

    void init(FMLInitializationEvent event);

    void preInit(FMLPreInitializationEvent event);

    void sparkle(float posX, float posY, float posZ, int i);
}
