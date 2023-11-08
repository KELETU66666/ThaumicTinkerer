/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [11 Sep 2013, 15:45:16 (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.items;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import com.nekokittygames.thaumictinkerer.common.misc.ItemNBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.potions.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public class ItemCleansingTalisman extends TTItem implements IBauble {

    private static final String TAG_ENABLED = "enabled";

    public ItemCleansingTalisman() {
        super(LibItemNames.CLEANING_TALISMAN);
        setMaxStackSize(1);
        setMaxDamage(TTConfig.talismanUses);

        this.addPropertyOverride(new ResourceLocation("active"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (isEnabled(stack)) {
                    return 1.0F;
                }
                return 0.0F;
            }
        });
    }

    public static boolean isEnabled(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, TAG_ENABLED, false);
    }

    public static void flipEnabled(ItemStack stack) {
        ItemNBTHelper.setBoolean(stack, TAG_ENABLED, !isEnabled(stack));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, @Nonnull EnumHand hand) {
       ItemStack stack = par3EntityPlayer.getHeldItem(hand);
        if (par3EntityPlayer.isSneaking()) {
            flipEnabled(stack);
            par2World.playSound(null, par3EntityPlayer.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.3F, 0.1F);
        }

        return super.onItemRightClick(par2World, par3EntityPlayer, hand);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, World world, List<String> stacks, ITooltipFlag flags) {
        if (isEnabled(par1ItemStack))
            stacks.add(I18n.translateToLocal("ttmisc.active"));
        else stacks.add(I18n.translateToLocal("ttmisc.inactive"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.CHARM;
    }

    @Override
    public void onWornTick(ItemStack par1ItemStack, EntityLivingBase player) {
        World par2World = player.world;
        if (isEnabled(par1ItemStack) && !par2World.isRemote && par1ItemStack.getItem() == this) {
            if (player.ticksExisted % 20 == 0) {
                if (player instanceof EntityPlayer) {
                    boolean removed = false;
                    int damage = 1;

                    Collection<PotionEffect> potions = player.getActivePotionEffects();

                    if (player.isBurning()) {
                        player.extinguish();
                        removed = true;
                    } else for (PotionEffect potion : potions) {
                        Potion id = potion.getPotion();
                        boolean badEffect;
                        badEffect = ReflectionHelper.getPrivateValue(Potion.class, id, new String[]{"isBadEffect", "field_76418_K"});
                        if (id instanceof PotionWarpWard) {
                            badEffect = false;
                        }
                        if (badEffect) {
                            player.removePotionEffect(id);
                            removed = true;
                            Potion[] warpPotionIDs = new Potion[]{PotionBlurredVision.instance, PotionDeathGaze.instance, PotionInfectiousVisExhaust.instance, PotionSunScorned.instance, PotionUnnaturalHunger.instance};
                            if (ArrayUtils.contains(warpPotionIDs, potion.getPotion())) {
                                damage = 10;
                            }
                            break;
                        }
                    }

                    if (removed) {


                        par1ItemStack.damageItem(damage, player);
                        if(par1ItemStack.getItemDamage()<=0) {
                            BaublesApi.getBaubles((EntityPlayer) player).setInventorySlotContents(BaubleType.CHARM.getValidSlots()[0], ItemStack.EMPTY);
                        }
                        par2World.playSound(null, player.getPosition(), SoundsTC.wand, SoundCategory.PLAYERS, 0.3F, 0.1F);
                    }
                }
            }
        }
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }
}