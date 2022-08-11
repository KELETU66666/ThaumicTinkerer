package com.nekokittygames.thaumictinkerer;

import com.nekokittygames.thaumictinkerer.api.ThaumicTinkererAPI;
import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.commands.CommandDumpEnchants;
import com.nekokittygames.thaumictinkerer.common.commands.CommandRefreshMultiblocks;
import com.nekokittygames.thaumictinkerer.common.compat.botania.BotaniaCompat;
import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import com.nekokittygames.thaumictinkerer.common.dim.ModDimensions;
import com.nekokittygames.thaumictinkerer.common.dim.OreClusterGenerator;
import com.nekokittygames.thaumictinkerer.common.enchantments.TTEnchantments;
import com.nekokittygames.thaumictinkerer.common.foci.FocusEffectDislocate;
import com.nekokittygames.thaumictinkerer.common.foci.FocusEffectEfreetFlame;
import com.nekokittygames.thaumictinkerer.common.foci.FocusEffectTelekenesis;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.loot.LootTableHandler;
import com.nekokittygames.thaumictinkerer.common.misc.ThaumicTInkererCreativeTab;
import com.nekokittygames.thaumictinkerer.common.multiblocks.MultiblockManager;
import com.nekokittygames.thaumictinkerer.common.packets.PacketHandler;
import com.nekokittygames.thaumictinkerer.common.proxy.ITTProxy;
import com.nekokittygames.thaumictinkerer.common.research.theorycraft.AidBlackQuartz;
import com.nekokittygames.thaumictinkerer.common.research.theorycraft.CardExperience;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.casters.FocusEngine;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.theorycraft.TheorycraftManager;

import java.io.IOException;
import java.net.URISyntaxException;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.MOD_VERSION, dependencies = LibMisc.MOD_DEPENDENCIES)
public class ThaumicTinkerer {
    public static Logger logger;

    private static CreativeTabs tab;

    @SidedProxy(serverSide = "com.nekokittygames.thaumictinkerer.common.proxy.CommonProxy", clientSide = "com.nekokittygames.thaumictinkerer.client.proxy.ClientProxy")
    public static ITTProxy proxy;


    @Mod.Instance(LibMisc.MOD_ID)
    public static ThaumicTinkerer instance;

    public static CreativeTabs getTab() {
        return tab;
    }

    public static void setTab(CreativeTabs tab) {
        ThaumicTinkerer.tab = tab;
    }

    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        tab = new ThaumicTInkererCreativeTab();
        logger = event.getModLog();

        proxy.preInit(event);
        PacketHandler.registerMessages(LibMisc.MOD_ID);
        GameRegistry.registerWorldGenerator(new OreClusterGenerator(), 3);
        ModDimensions.init();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandRefreshMultiblocks());
        event.registerServerCommand(new CommandDumpEnchants());

    }

    @EventHandler
    public void processIMC(FMLInterModComms.IMCEvent event) {
        for(FMLInterModComms.IMCMessage message:event.getMessages()) {
            if(message.key.equalsIgnoreCase("addDislocateBlacklist") && message.isStringMessage())
            {
                ThaumicTinkererAPI.getDislocationBlacklist().add(message.getStringValue());
            }
            if(message.key.equalsIgnoreCase("addTabletBlacklist") && message.isStringMessage())
            {
                ThaumicTinkererAPI.getAnimationTabletBlacklist().add(message.getStringValue());
            }
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            MultiblockManager.initMultiblocks();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        proxy.init(event);
        ResearchCategories.registerCategory("THAUMIC_TINKERER", null, new AspectList(), new ResourceLocation("thaumictinkerer", "textures/items/share_book.png"), new ResourceLocation("thaumictinkerer", "textures/misc/sky1.png"), new ResourceLocation("thaumictinkerer", "textures/misc/sky1.png"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumictinkerer", "research/misc"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumictinkerer", "research/baubles"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumictinkerer", "research/machines"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumictinkerer", "research/foci"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumictinkerer", "research/kami"));
        TheorycraftManager.registerCard(CardExperience.class);
        TheorycraftManager.registerAid(new AidBlackQuartz(ModBlocks.black_quartz_block));
        BotaniaCompat.addTheorycraft();
        proxy.registerRenderers();
        initFoci();
        MinecraftForge.EVENT_BUS.register(LootTableHandler.class);
        //IDustTrigger.registerDustTrigger(ModBlocks.osmotic_enchanter);

    }

    private void initFoci() {
        if(TTConfig.DislocationFocusEnabled)
            FocusEngine.registerElement(FocusEffectDislocate.class, new ResourceLocation("thaumictinkerer", "textures/foci_icons/dislocation.png"), 15121988);
        if(TTConfig.TelekenesisFocusEnabled) {
            logger.info("Initializing Telekenetic powers");
            FocusEngine.registerElement(FocusEffectTelekenesis.class, new ResourceLocation("thaumictinkerer", "textures/foci_icons/telekenesis.png"), 13566207);
        }
        FocusEngine.registerElement(FocusEffectEfreetFlame.class, new ResourceLocation("thaumictinkerer", "textures/foci_icons/efreetflame.png"), 25565230);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public static void onEnchantmentRegister(RegistryEvent.Register<Enchantment> event)
    {
        event.getRegistry().registerAll(TTEnchantments.ENCHANTNENTS.toArray(new Enchantment[0]));
    }
}

