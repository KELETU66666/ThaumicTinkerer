package com.nekokittygames.thaumictinkerer.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public abstract class TTEnchantment extends Enchantment {

    protected TTEnchantment(Rarity p_i46731_1_, EnumEnchantmentType p_i46731_2_, EntityEquipmentSlot[] p_i46731_3_) {
        super(p_i46731_1_, p_i46731_2_, p_i46731_3_);
    }

    public boolean isTreasureEnchantment() {
        return true;
    }
}
