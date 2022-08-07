package com.nekokittygames.thaumictinkerer.common.items.Kami.Tool;


import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

import static com.nekokittygames.thaumictinkerer.common.items.ModItems.RegistrationHandler.MATERIAL_ICHORIUM;

public class IchoriumShovel extends ItemSpade  {
    public IchoriumShovel(String name) {

        super(MATERIAL_ICHORIUM);
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(ThaumicTinkerer.getTab());

        
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.EPIC;
    }

    
}