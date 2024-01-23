package com.nekokittygames.thaumictinkerer.common.items.Kami;


import com.nekokittygames.thaumictinkerer.common.items.TTItem;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemProtoclay extends TTItem {

    public ItemProtoclay() {
        super(LibItemNames.Protoclay);
        setMaxStackSize(1);
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.EPIC;
    }
}
