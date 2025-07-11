/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [28 Sep 2013, 18:27:56 (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import com.nekokittygames.thaumictinkerer.common.utils.ItemNBTHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.lib.SoundsTC;

import javax.annotation.Nullable;
import java.util.List;

public class ItemXPTalisman extends TTItem implements IBauble {

    private static final String TAG_XP = "xp";

    public ItemXPTalisman() {
        super(LibItemNames.EXPERIENCE_CHARM);
        setMaxStackSize(1);

        this.addPropertyOverride(new ResourceLocation("active"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return getMetadata(stack);
            }
        });
    }

    public static boolean hasCmp(ItemStack stack) {
        return ItemNBTHelper.detectNBT(stack);
    }

    public static int getXP(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_XP, 0);
    }

    public static void setXP(ItemStack stack, int xp) {
        ItemNBTHelper.setInt(stack, TAG_XP, xp);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, EnumHand hand) {
        ItemStack par1ItemStack = par3EntityPlayer.getHeldItem(hand);

        if (par3EntityPlayer.isSneaking()) {
            if (getXP(par1ItemStack) < 1500) {
                int dmg = par1ItemStack.getItemDamage();
                par1ItemStack.setItemDamage(~dmg & 1);
                par2World.playSound(null, par3EntityPlayer.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 0.3F, 0.1F);
            }
        } else if (getXP(par1ItemStack) >= 10) {
            ItemStack stack = new ItemStack(Items.GLASS_BOTTLE);
            boolean has = par3EntityPlayer.inventory.hasItemStack(stack);
            if (has) {
                if(!par2World.isRemote) {
                    par3EntityPlayer.inventory.getStackInSlot(par3EntityPlayer.inventory.getSlotFor(stack)).shrink(1);
                    if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.EXPERIENCE_BOTTLE, 1))) {
                        par3EntityPlayer.dropItem(Items.EXPERIENCE_BOTTLE, 1);
                    }
                }
                int xp = getXP(par1ItemStack);
                setXP(par1ItemStack, xp - 10);
                par2World.playSound(null, par3EntityPlayer.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 0.1F, (float) (0.1F + Math.random() / 2F));
                ThaumicTinkerer.proxy.spawnXPTalismanParticle(par3EntityPlayer);
            }
        }

        return super.onItemRightClick(par2World, par3EntityPlayer, hand);
    }

    private void consumeXPOrb(EntityXPOrb orb) {
        orb.setDead();
        orb.world.playSound(null, orb.getPosition(), SoundsTC.zap, SoundCategory.NEUTRAL, orb.getXpValue() / 10F, 1F);
        //FXDispatcher.INSTANCE.wispFXEG(orb.posX, orb.posY, orb.posZ, orb);
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4) {
        par3List.add("XP: " + getXP(par1ItemStack));
        if (getXP(par1ItemStack) >= 1500)
            par3List.add(I18n.format("ttmisc.full"));
        else if (par1ItemStack.getItemDamage() == 0)
            par3List.add(I18n.format("ttmisc.notAbsorbing"));
        else par3List.add(I18n.format("ttmisc.absorbing"));
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
        if (par1ItemStack.getItemDamage() == 1 && !par2World.isRemote) {
            int r = 3;
            int currentXP = getXP(par1ItemStack);
            int xpToAdd = 0;
            int maxXP = 1500 - currentXP; // Max, to prevent overflow.
            if (maxXP <= 0) {
                par1ItemStack.setItemDamage(0);
                return; // Can't take any XP.
            }

            AxisAlignedBB boundingBox = new AxisAlignedBB(
                    player.posX - r,
                    player.posY - r,
                    player.posZ - r,
                    player.posX + r,
                    player.posY + r,
                    player.posZ + r);
            List<EntityXPOrb> orbs = par2World.getEntitiesWithinAABB(EntityXPOrb.class, boundingBox);

            for (EntityXPOrb orb : orbs) {
                if (!orb.isDead) {
                    int xp = orb.getXpValue();
                    if (xpToAdd + xp <= maxXP) {
                        xpToAdd += xp;
                        consumeXPOrb(orb);
                    }

                    maxXP -= xpToAdd;

                    if (maxXP <= 0) break;
                }
            }

            setXP(par1ItemStack, Math.min(1500, currentXP + xpToAdd));
        }
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }
}