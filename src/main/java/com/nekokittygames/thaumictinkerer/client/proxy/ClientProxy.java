/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.proxy;

import static com.nekokittygames.thaumictinkerer.ThaumicTinkerer.instance;
import com.nekokittygames.thaumictinkerer.client.misc.AspectColors;
import com.nekokittygames.thaumictinkerer.client.misc.ClientHelper;
import com.nekokittygames.thaumictinkerer.client.misc.Shaders;
import com.nekokittygames.thaumictinkerer.client.misc.SoulHeartClientHandler;
import com.nekokittygames.thaumictinkerer.client.rendering.special.multi.NitorRenderer;
import com.nekokittygames.thaumictinkerer.client.rendering.tileentities.*;
import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.commands.CommandThaumicTinkererClient;
import com.nekokittygames.thaumictinkerer.common.intl.MultiBlockPreviewRendering;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.proxy.GuiProxy;
import com.nekokittygames.thaumictinkerer.common.proxy.ITTProxy;
import com.nekokittygames.thaumictinkerer.common.tileentity.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.other.FXEssentiaStream;
import thaumcraft.common.blocks.misc.BlockNitor;

import java.awt.*;

/**
 * Client side proxy
 */
@SideOnly(Side.CLIENT)
public class ClientProxy implements ITTProxy {


    public static EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().player;
    }

    /**
     * register any renderers or shaders needed on client side
     */
    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFunnel.class, new TileEntityFunnelRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRepairer.class, new TileEntityRepairerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExample.class, new TileEntityExampleRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentPillar.class, new TileEntityEnchantmentPillarRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchanter.class, new TileEntityEnchanterRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnimationTablet.class, new TileEntityAnimationTabletRenderer());
        Shaders.initShaders();
        MultiBlockPreviewRendering.registerRenderer(BlockNitor.class, new NitorRenderer());
    }

    /**
     * initialization phase of the mod
     *
     * @param event initialization event
     */
    @Override
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiProxy());

        Minecraft.getMinecraft().getItemColors().registerItemColorHandler((s, t) -> Color.HSBtoRGB(0.75F, ((float) s.getMaxDamage() - (float) s.getItemDamage()) / s.getMaxDamage() * 0.5F, 1F), ModItems.spellbinding_cloth);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new AspectColors(), ModItems.condensed_mob_aspect, ModItems.mob_aspect);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new AspectColors(), ModItems.infused_seeds);
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new AspectColors(), ModBlocks.infused_grain);

        MinecraftForge.EVENT_BUS.register(new SoulHeartClientHandler());
    }

    /**
     * Pre-Initialization phase of the mod
     *
     * @param event pre-initialization event
     */
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new CommandThaumicTinkererClient());
    }

    /**
     * Localize a string
     *
     * @param translationKey unlocalised string
     * @param args           arguments to the localisation
     * @return the string fully localised to current locale
     */
    @Override
    public String localize(String translationKey, Object... args) {
        return I18n.format(translationKey, args);
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return ClientHelper.clientPlayer();
    }

    @Override
    public void drawEntitySummonerParticle(World world, BlockPos ped1, BlockPos ped2, BlockPos ped3, BlockPos pos, AspectList aspects) {
        ParticleEngine.addEffect(world, new FXEssentiaStream(world, ped1.getX(), ped1.getY(), ped1.getZ(), pos.getX(), pos.getY(), pos.getZ(), 1, aspects.getAspects()[0].getColor(), 0.1f, 0, 0.2));
        ParticleEngine.addEffect(world, new FXEssentiaStream(world, ped2.getX(), ped2.getY(), ped2.getZ(), pos.getX(), pos.getY(), pos.getZ(), 1, aspects.getAspects()[1].getColor(), 0.1f, 0, 0.2));
        ParticleEngine.addEffect(world, new FXEssentiaStream(world, ped3.getX(), ped3.getY(), ped3.getZ(), pos.getX(), pos.getY(), pos.getZ(), 1, aspects.getAspects()[2].getColor(), 0.1f, 0, 0.2));
    }

    @Override
    public void spawnXPTalismanParticle(EntityPlayer player) {
        for (int i = 0; i < 6; i++)
            FXDispatcher.INSTANCE.sparkle(
                    (float) (player.posX + (Math.random() - 0.5)),
                    (float) (player.posY + Math.random() - 0.5),
                    (float) (player.posZ + (Math.random() - 0.5)),
                    3, 0, 0);
    }
}
