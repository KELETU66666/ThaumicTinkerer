/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Dec 29, 2013, 9:33:23 PM (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.utils;

import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.packets.PacketHandler;
import com.nekokittygames.thaumictinkerer.common.packets.PacketSoulHearts;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoulHeartHandler {

    private static final String COMPOUND = LibMisc.MOD_ID;
    private static final String TAG_HP = "soulHearts";
    private static final int MAX_HP = 20;

    public static void addHearts(EntityPlayer player) {
        addHP(player, 1);
        updateClient(player);
    }

    public static boolean addHP(EntityPlayer player, int hp) {
        int current = getHP(player);
        if (current >= MAX_HP) return false;

        setHP(player, Math.min(MAX_HP, current + hp));
        return true;
    }

    // Returns overflow damage
    public static int removeHP(EntityPlayer player, int hp) {
        int current = getHP(player);
        int newHp = current - hp;
        setHP(player, Math.max(0, newHp));

        return Math.max(0, -newHp);
    }

    public static void setHP(EntityPlayer player, int hp) {
        NBTTagCompound cmp = getCompoundToSet(player);
        cmp.setInteger(TAG_HP, hp);
    }

    public static int getHP(EntityPlayer player) {
        NBTTagCompound cmp = getCompoundToSet(player);
        return cmp.hasKey(TAG_HP) ? cmp.getInteger(TAG_HP) : 0;
    }

    private static NBTTagCompound getCompoundToSet(EntityPlayer player) {
        NBTTagCompound cmp = player.getEntityData();
        if (!cmp.hasKey(COMPOUND)) cmp.setTag(COMPOUND, new NBTTagCompound());

        return cmp.getCompoundTag(COMPOUND);
    }

    public static void updateClient(EntityPlayer player) {
        if (player instanceof EntityPlayerMP && ((EntityPlayerMP) player).getServer() != null) {
            PacketHandler.INSTANCE.sendTo(new PacketSoulHearts(getHP(player)), (EntityPlayerMP) player);
        }
    }

    @SubscribeEvent
    public void onPlayerDamage(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer && event.getAmount() > 0) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            event.setAmount(removeHP(player, (int) event.getAmount()));
            updateClient(player);
        }
    }

    // =============== METHODS COPIED FROM ENTITYLIVING ==================== //

    protected float applyArmorCalculations(EntityLivingBase entity, DamageSource par1DamageSource, float par2) {
        if (!par1DamageSource.isUnblockable()) {
            int i = 25 - entity.getTotalArmorValue();
            float f1 = par2 * i;
            // this.damageArmor(par2);
            par2 = f1 / 25.0F;
        }

        return par2;
    }

    public float applyPotionDamageCalculations(EntityLivingBase entity, DamageSource par1DamageSource, float par2) {
        if (par1DamageSource.isDamageAbsolute()) {
            return par2;
        } else {
            int k;
            if (entity.isPotionActive(MobEffects.RESISTANCE) && par1DamageSource != DamageSource.OUT_OF_WORLD) {
                k = (entity.getActivePotionEffect(MobEffects.RESISTANCE).getAmplifier() + 1) * 5;
                int j = 25 - k;
                float f = par2 * (float)j;
                par2 = f / 25.0F;
            }

            if (par2 <= 0.0F) {
                return 0.0F;
            } else {
                k = EnchantmentHelper.getEnchantmentModifierDamage(entity.getArmorInventoryList(), par1DamageSource);
                if (k > 0) {
                    par2 = CombatRules.getDamageAfterMagicAbsorb(par2, (float)k);
                }

                return par2;
            }
        }
    }
}