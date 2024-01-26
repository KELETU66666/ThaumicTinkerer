//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.nekokittygames.thaumictinkerer.common.items;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomArmorHelper {
    public CustomArmorHelper() {
    }

    public static ModelBiped getCustomArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model, ModelBiped model1, ModelBiped model2) {
        if (model == null) {
            EntityEquipmentSlot type = ((ItemArmor)itemStack.getItem()).armorType;
            if (type != EntityEquipmentSlot.CHEST && type != EntityEquipmentSlot.FEET) {
                model = model2;
            } else {
                model = model1;
            }
        }

        if (model != null) {
            model.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
            model.bipedHeadwear.showModel = armorSlot == EntityEquipmentSlot.HEAD;
            model.bipedBody.showModel = armorSlot == EntityEquipmentSlot.CHEST || armorSlot == EntityEquipmentSlot.LEGS;
            model.bipedRightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
            model.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
            model.bipedRightLeg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
            model.bipedLeftLeg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
            model.isSneak = entityLiving.isSneaking();
            model.isRiding = entityLiving.isRiding();
            model.isChild = entityLiving.isChild();
            ItemStack itemstack = entityLiving.getHeldItemMainhand();
            ItemStack itemstack1 = entityLiving.getHeldItemOffhand();
            ModelBiped.ArmPose modelbiped$armpose = ArmPose.EMPTY;
            ModelBiped.ArmPose modelbiped$armpose1 = ArmPose.EMPTY;
            EnumAction enumaction1;
            if (itemstack != null && !itemstack.isEmpty()) {
                modelbiped$armpose = ArmPose.ITEM;
                if (entityLiving.getItemInUseCount() > 0) {
                    enumaction1 = itemstack.getItemUseAction();
                    if (enumaction1 == EnumAction.BLOCK) {
                        modelbiped$armpose = ArmPose.BLOCK;
                    } else if (enumaction1 == EnumAction.BOW) {
                        modelbiped$armpose = ArmPose.BOW_AND_ARROW;
                    }
                }
            }

            if (itemstack1 != null && !itemstack1.isEmpty()) {
                modelbiped$armpose1 = ArmPose.ITEM;
                if (entityLiving.getItemInUseCount() > 0) {
                    enumaction1 = itemstack1.getItemUseAction();
                    if (enumaction1 == EnumAction.BLOCK) {
                        modelbiped$armpose1 = ArmPose.BLOCK;
                    }
                }
            }

            if (entityLiving.getPrimaryHand() == EnumHandSide.RIGHT) {
                model.rightArmPose = modelbiped$armpose;
                model.leftArmPose = modelbiped$armpose1;
            } else {
                model.rightArmPose = modelbiped$armpose1;
                model.leftArmPose = modelbiped$armpose;
            }
        }

        return model;
    }
}
