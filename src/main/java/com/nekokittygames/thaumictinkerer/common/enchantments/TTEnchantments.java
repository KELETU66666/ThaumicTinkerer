package com.nekokittygames.thaumictinkerer.common.enchantments;

import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid= LibMisc.MOD_ID)
public class TTEnchantments {

    public static final List<Enchantment> ENCHANTNENTS = new ArrayList<Enchantment>();

    public static final Enchantment ascentboost = new EnchantmentAscentBoost();
    public static final Enchantment pounce = new EnchantmentPounce();
    public static final Enchantment shockwave = new EnchantmentShockwave();
    public static final Enchantment slowfall = new EnchantmentSlowFall();
    public static final Enchantment finalStrike = new EnchantmentFinalStrike();
    public static final Enchantment valiance = new EnchantmentValiance();
    public static final Enchantment vamprisim = new EnchantmentVampirsim();
    public static final Enchantment dispersedStrikes = new EnchantmentDispersedStrikes();
    public static final Enchantment focusedStrikes = new EnchantmentFocusedStrikes();
    public static final Enchantment quickdraw = new EnchantmentQuickDraw();
    public static final Enchantment tunnel = new EnchantmentTunnel();
    public static final Enchantment shatter = new EnchantmentShatter();
    public static final Enchantment desintegrate = new EnchantmentDesIntegrate();
    public static final Enchantment autosmelt = new EnchantmentAutoSmelt();
}
