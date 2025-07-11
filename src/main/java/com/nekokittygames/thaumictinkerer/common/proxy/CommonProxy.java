package com.nekokittygames.thaumictinkerer.common.proxy;

import static com.nekokittygames.thaumictinkerer.ThaumicTinkerer.instance;
import com.nekokittygames.thaumictinkerer.common.dim.OreClusterGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thaumcraft.api.aspects.AspectList;

public class CommonProxy implements ITTProxy {

    public static EnumRarity kamiRarity;

    @Override
    public void registerRenderers() {
        // Empty
    }

    @SuppressWarnings("deprecation")
    @Override
    public String localize(String translationKey, Object... args) {
        return I18n.translateToLocalFormatted(translationKey, args);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiProxy());

    }

    @Override
    public void preInit( FMLPreInitializationEvent event ) {
        LootTableList.register(new ResourceLocation("modid", "loot_table_name"));
        GameRegistry.registerWorldGenerator(new OreClusterGenerator(), 3);

      //  kamiRarity = EnumHelper.addEnum(EnumRarity.class,  "KAMI",new Class[]{EnumRarity.class, TextFormatting.class, String.class}, TextFormatting.LIGHT_PURPLE, "Kami");
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }

    @Override
    public void drawEntitySummonerParticle(World world, BlockPos ped1, BlockPos ped2, BlockPos ped3, BlockPos pos, AspectList aspects) {

    }

    @Override
    public void spawnXPTalismanParticle(EntityPlayer player) {

    }
}
