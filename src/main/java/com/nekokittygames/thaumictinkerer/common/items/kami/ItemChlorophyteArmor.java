package com.nekokittygames.thaumictinkerer.common.items.Kami;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.ModelChlorophyteArmor;
import com.nekokittygames.thaumictinkerer.common.items.CustomArmorHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.items.IGoggles;

public class ItemChlorophyteArmor extends ItemArmor implements ISpecialArmor, IGoggles {

    ModelBiped model1;
    ModelBiped model2;
    ModelBiped model;

    public ItemChlorophyteArmor(String name, ArmorMaterial armorMaterial, int renderIndex, EntityEquipmentSlot armorSlot) {
        super(armorMaterial, renderIndex, armorSlot);
        model1 = null;
        model2 = null;
        model = null;
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(ThaumicTinkerer.getTab());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        ItemStack stack;
        boolean headWorn = !(stack = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD)).isEmpty() && stack.getItem() instanceof ItemChlorophyteArmor;
        boolean bodyWorn = !(stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST)).isEmpty() && stack.getItem() instanceof ItemChlorophyteArmor;
        boolean legsWorn = !(stack = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS)).isEmpty() && stack.getItem() instanceof ItemChlorophyteArmor;
        boolean fullSet = headWorn && bodyWorn && legsWorn;

        if(fullSet) {
            if (player.ticksExisted % 10 == 0 && player.world.isDaytime() && player.world.canBlockSeeSky(new BlockPos(MathHelper.floor(player.posX), MathHelper.floor(player.posY), MathHelper.floor(player.posZ)))) {
                    player.heal(0.5f);
                    player.getFoodStats().addStats(1, 0.5f);
                }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.EPIC;
    }

    @Override
    public boolean showIngamePopups(ItemStack stack, EntityLivingBase owner) {
        return armorType == EntityEquipmentSlot.HEAD;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return "thaumictinkerer:textures/models/armor/chlorophyte_armor.png";
    }

    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        if (model1 == null) {
            model1 = new ModelChlorophyteArmor(1.0f);
        }
        if (model2 == null) {
            model2 = new ModelChlorophyteArmor(0.5f);
        }
        return model = CustomArmorHelper.getCustomArmorModel(entityLiving, itemStack, armorSlot, model, model1, model2);
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase entityLivingBase, @NotNull ItemStack itemStack, DamageSource damageSource, double v, int i) {
        return null;
    }

    @Override
    public int getArmorDisplay(EntityPlayer entityPlayer, @NotNull ItemStack itemStack, int i) {
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entityLivingBase, @NotNull ItemStack itemStack, DamageSource damageSource, int i, int i1) {

    }
}