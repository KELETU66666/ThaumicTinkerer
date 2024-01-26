/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.proxy;

import com.nekokittygames.thaumictinkerer.client.misc.Shaders;
import com.nekokittygames.thaumictinkerer.client.rendering.special.multi.NitorRenderer;
import com.nekokittygames.thaumictinkerer.client.rendering.tileentities.*;
import com.nekokittygames.thaumictinkerer.common.commands.CommandThaumicTinkererClient;
import com.nekokittygames.thaumictinkerer.common.intl.MultiBlockPreviewRendering;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.proxy.GuiProxy;
import com.nekokittygames.thaumictinkerer.common.proxy.ITTProxy;
import com.nekokittygames.thaumictinkerer.common.tileentity.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.blocks.misc.BlockNitor;

import java.awt.*;

import static com.nekokittygames.thaumictinkerer.ThaumicTinkerer.instance;

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
     * @param translationKey unlocalised string
     * @param args arguments to the localisation
     * @return the string fully localised to current locale
     */
    @Override
    public String localize(String translationKey, Object... args) {
        return I18n.format(translationKey, args);
    }
}
