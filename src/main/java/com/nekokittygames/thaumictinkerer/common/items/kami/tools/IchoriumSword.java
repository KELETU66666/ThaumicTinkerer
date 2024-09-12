package com.nekokittygames.thaumictinkerer.common.items.Kami.Tools;


import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import static com.nekokittygames.thaumictinkerer.common.items.ModItems.RegistrationHandler.MATERIAL_ICHORIUM;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class IchoriumSword extends ItemSword  {
    public IchoriumSword(String name) {

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