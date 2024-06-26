package com.nekokittygames.thaumictinkerer.common.config;


import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = LibMisc.MOD_ID)
@Config.LangKey("thaumictinkerer.config.title")
public class TTConfig {
    @Config.Comment("This is the amount of uses given to the Talisman of Remedium.")
    @Config.Name("Talisman of Remedium Uses")
    @Config.LangKey("thaumictinkerer.config.talisman")
    @Config.RangeInt(min = 0)
    public static int talismanUses = 100;


    @Config.Comment("This is the distance a block can be from a Transvector Interface")
    @Config.Name("Transvector Interface Distance")
    @Config.LangKey("thaumictinkerer.config.transvector_interface.distance")
    @Config.RangeInt(min = 0)
    public static int transvectorInterfaceDistance = 4;

    @Config.Comment("This is the distance a block can be from a Transvector Dislocator")
    @Config.Name("Transvector Dislocator Distance")
    @Config.LangKey("thaumictinkerer.config.transvector_dislocator.distance")
    @Config.RangeInt(min = 0)
    public static int transvectorDislocatorDistance = 10;

    @Config.Comment("This is the amount of times a spellbinding cloth may be used")
    @Config.Name("Spellbinding Cloth Uses")
    @Config.LangKey("thaumictinkerer.config.spellbinding_cloth.uses")
    @Config.RangeInt(min = 0)
    @Config.RequiresMcRestart()
    public static int spellbindingClothUses = 10;

    @Config.Comment("Can Tinkers Construct tools be repaired in the repairer?")
    @Config.Name("Tinkers Construct Compatibility")
    @Config.LangKey("thaumictinkerer.config.ticon.compatibility")
    public static boolean TiConCompatibility = true;

    @Config.Comment("Can Energetic Nitor and Kami Leggings glow")
    @Config.Name("Enable Nitor Vapor")
    public static boolean EnableNitorVapor = true;

    @Config.Comment("Are Shaders allowed? This effects purely cosmetic visual effects like the transparency for multiblock placeholder")
    @Config.Name("Shaders Enabled")
    @Config.RequiresMcRestart()
    public static boolean ShadersEnabled = true;

    @Config.Comment("Is the dislocation focus enabled?")
    @Config.Name("Dislocation Focus Enabled")
    @Config.RequiresMcRestart()
    public static boolean DislocationFocusEnabled=true;

    @Config.Comment("Is the telekenesis focus enabled?")
    @Config.Name("Telekenesis Focus Enabled")
    @Config.RequiresMcRestart()
    public static boolean TelekenesisFocusEnabled=true;

    @Config.Comment("Does the Osmotic Enchanter use the classic pillar structure")
    @Config.Name("Classic Enchanter")
    public static boolean ClassicEnchanter = false;

    @Config.Comment("Any Enchant ID's in this list can not be added by the Osmotic Enchanter")
    @Config.Name("Blacklisted Enchants")
    public static int[] blacklistedEnchants = new int[]{71, 10};

    @Config.Comment("Osmotic Enchanter only can enchants vanilla and tinkerer enchantments")
    @Config.Name("Enchanter WhiteList Mode")
    public static boolean balancedEnchanter = true;

    @Config.LangKey("Dimensional shard drop rate")
    @Config.Comment("Change dimensional shard drop rate, The higher the value the higher the drop rate")
    @Config.RangeInt(min = -1, max = 64)
    @Config.RequiresMcRestart
    public static double EndShardDropRate = 32;

    @Config.LangKey("Dimensional shard drop rate")
    @Config.Comment("Change dimensional shard drop rate, The higher the value the higher the drop rate")
    @Config.RangeInt(min = -1, max = 64)
    @Config.RequiresMcRestart
    public static double NetherShardDropRate = 16;

    @Config.Comment("BedrockDim DimID")
    @Config.Name("Bedrock Dimension ID")
    @Config.RequiresMcRestart
    public static int BedRockDimensionID = 19;

    @Config.Comment("Ichorium ToolPart Base Damage")
    @Config.Name("Ichorium Weapon DamageBase")
    @Config.RangeInt(min = 0)
    @Config.RequiresMcRestart
    public static float IWeaponDamage = 3.5F;

    @Config.Comment("\"AuraStrike\" Modifier Damage Bonus")
    @Config.Name("'AuraStrike' Modifier Damage Bonus")
    @Config.RangeInt(min = 0)
    @Config.RequiresMcRestart
    public static int AuraStrikeBonus = 5;

    @Mod.EventBusSubscriber
    private static class EventHandler {

        /**
         * Inject the new values and save to the config file when the config has been changed from the GUI.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(LibMisc.MOD_ID)) {
                ConfigManager.sync(LibMisc.MOD_ID, net.minecraftforge.common.config.Config.Type.INSTANCE);
            }
        }
    }
}

