package com.nekokittygames.thaumictinkerer.common.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import thaumcraft.api.aspects.AspectList;

public interface ITTProxy {


    void registerRenderers();

    String localize(String translationKey, Object... args);

    void init(FMLInitializationEvent event);

    void preInit(FMLPreInitializationEvent event);

    EntityPlayer getClientPlayer();

    void drawEntitySummonerParticle(World world, BlockPos ped1, BlockPos ped2, BlockPos ped3, BlockPos pos, AspectList aspects);

    void spawnXPTalismanParticle(EntityPlayer player);
}
