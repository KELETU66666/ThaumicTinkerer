package com.nekokittygames.thaumictinkerer.common.items.Kami;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.rendering.ModelIchoriumFortressArmor;
import com.nekokittygames.thaumictinkerer.common.items.CustomArmorHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.items.IGoggles;
import thaumcraft.api.items.IWarpingGear;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.lib.events.PlayerEvents;

import java.util.List;

public class ItemIchoriumFortressArmor extends ItemArmor implements IGoggles, ISpecialArmor, IWarpingGear {

    ModelBiped model1;
    ModelBiped model2;
    ModelBiped model;
    private final int[] warps = new int[]{0, 0, 2, 3, 3, 2};

    public ItemIchoriumFortressArmor(String name, ArmorMaterial armorMaterial, int renderIndex, EntityEquipmentSlot armorSlot) {
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
        if(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemIchoriumFortressArmor && player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemIchoriumFortressArmor && player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemIchoriumFortressArmor) {
            if (player.ticksExisted % 200 == 0 && player.world.isDaytime() && player.world.canBlockSeeSky(new BlockPos(MathHelper.floor(player.posX), MathHelper.floor(player.posY), MathHelper.floor(player.posZ)))) {
                player.heal(0.5F);
                player.getFoodStats().addStats(1, 0.5F);
               }
        }

        if(itemStack.getTagCompound() == null)
            return;

        if (itemStack.getTagCompound().getByte("kami_upgrade") == 0) {//Water
            player.setAir(300);
            if (player.getEntityWorld().getBlockState(player.getPosition().up()).getBlock() == Blocks.WATER || player.getEntityWorld().getBlockState(player.getPosition().up()).getBlock() == Blocks.FLOWING_WATER) {
                player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 400, 0, true, false));
            }
            if ((player.getEntityWorld().getBlockState(player.getPosition().up()).getBlock() == Blocks.LAVA || player.getEntityWorld().getBlockState(player.getPosition().up()).getBlock() == Blocks.FLOWING_LAVA) && player.ticksExisted % 10 == 0)
                player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 31, 0, true, false));
            int food = player.getFoodStats().getFoodLevel();
            if (food > 0 && food < 18 && player.shouldHeal()
                    && player.ticksExisted % 80 == 0)
                player.heal(1F);
            }
            if (itemStack.getTagCompound().getByte("kami_upgrade") == 1) {//Air
                player.getEntityData().setBoolean("can_fly", true);
                doProjectileEffect(player);
            }
            if (itemStack.getTagCompound().getByte("kami_upgrade") == 2) {//Fire
                if (player.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) == null || player.getActivePotionEffect(MobEffects.FIRE_RESISTANCE).getDuration() <= 1) {
                    player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1, 0, false, false));
                    if (player.isBurning()) {
                        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20, 1, true, false));
                        player.extinguish();
                    }
                }
            }
            if (itemStack.getTagCompound().getByte("kami_upgrade") == 3) {//Earth
                tickPlayer(player);
            }
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("kami_upgrade")) {
            tooltip.add(TextFormatting.GOLD + I18n.format("item.ichorium_fortress.upgrade." + stack.getTagCompound().getInteger("kami_upgrade")));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    void tickPlayer(EntityPlayer player) {
        ItemStack armor = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        if (armor.getItemDamage() == 1)
            return;

        player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 2, 1, true, false));

        if (player.world.isRemote)
            player.stepHeight = player.isSneaking() ? 0.5F : 1F;
        if (!player.capabilities.isFlying && player.moveForward > 0.0f) {
            if (player.world.isRemote && !player.isSneaking()) {
                if (!PlayerEvents.prevStep.containsKey(player.getEntityId())) {
                    PlayerEvents.prevStep.put(player.getEntityId(), player.stepHeight);
                }
                player.stepHeight = 1.0f;
            }
            if (player.onGround) {
                float bonus = 0.15f;
                if (player.isInWater()) {
                    bonus *= 1.25f;
                }
                player.moveRelative(0.0f, 0.0f, bonus, 1.0f);
            }
            else {
                if (player.isInWater()) {
                    player.moveRelative(0.0f, 0.0f, 0.075F, 1.0f);
                }
            }
        }
        //if ((player.onGround || player.capabilities.isFlying) && player.moveForward > 0F)
        //    player.moveRelative(0F, 0F, player.capabilities.isFlying ? 0.075F : 0.15F, 1.0F);
        player.jumpMovementFactor = player.isSprinting() ? 0.05F : 0.04F;
        player.fallDistance = 0F;

        if (player.world.getBlockState(player.getPosition().down()).getBlock() == Blocks.DIRT && Blocks.DIRT.getMetaFromState(player.world.getBlockState(player.getPosition().down())) == 0)
            player.world.setBlockState(player.getPosition().down(), Blocks.GRASS.getDefaultState(), 2);
    }

    private void doProjectileEffect(EntityPlayer mp) {
        if (!mp.isSneaking()) {
            List<Entity> projectiles = mp.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(mp.posX - 2, mp.posY - 2, mp.posZ - 2, mp.posX + 2, mp.posY + 2, mp.posZ + 2), e -> e instanceof IProjectile);
            for (Entity potion : projectiles) {
                Vector3 motionVec = new Vector3(potion.motionX, potion.motionY, potion.motionZ).normalize().multiply(Math.sqrt((potion.posX - mp.posX) * (potion.posX - mp.posX) + (potion.posY - mp.posY) * (potion.posY - mp.posY) + (potion.posZ - mp.posZ) * (potion.posZ - mp.posZ)) * 2);

                if(mp.world.isRemote)
                    for (int i = 0; i < 6; i++)
                        FXDispatcher.INSTANCE.sparkle((float) potion.posX, (float) potion.posY, (float) potion.posZ, 6, 0, 0);

                potion.posX += motionVec.x;
                potion.posY += motionVec.y;
                potion.posZ += motionVec.z;
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.EPIC;
    }

    @Override
    public boolean showIngamePopups(ItemStack stack, EntityLivingBase owner) {
        return stack.hasTagCompound() && stack.getTagCompound().getByte("kami_upgrade") == 0;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return "thaumictinkerer:textures/models/armor/ichorium_fortress_armor.png";
    }

    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        if (model1 == null) {
            model1 = new ModelIchoriumFortressArmor(1.0f);
        }
        if (model2 == null) {
            model2 = new ModelIchoriumFortressArmor(0.5f);
        }
        return model = CustomArmorHelper.getCustomArmorModel(entityLiving, itemStack, armorSlot, model, model1, model2);
    }

    @Override
    public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        int priority = 0;
        double ratio = this.damageReduceAmount / 25.0D;
        if (source.isMagicDamage()) {
            priority = 1;
            ratio = this.damageReduceAmount / 35.0D;
        } else if (source.isFireDamage() || source.isExplosion()) {
            priority = 1;
            ratio = this.damageReduceAmount / 20.0D;
        } else if (source.isUnblockable()) {
            priority = 0;
            ratio = 0.0D;
        }
        ISpecialArmor.ArmorProperties ap = new ISpecialArmor.ArmorProperties(priority, ratio, armor.getMaxDamage() + 1 - armor.getItemDamage());
        if (player instanceof EntityPlayer) {
            double set = 0.750D;
            int q = 0;
            for (int a = 1; a < 4; a++) {
                ItemStack piece = ((EntityPlayer) player).inventory.armorInventory.get(a);
                if (piece != null && !piece.isEmpty() && piece.getItem() instanceof ItemIchoriumFortressArmor) {
                    set += 0.150D;
                    q++;
                    if (q <= 1) {
                        ap.Armor++;
                        ap.Toughness++;
                    }
                }
            }
            ratio *= set;
        }
        return ap;
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, @NotNull ItemStack armor, int slot) {
        return getArmorMaterial().getDamageReductionAmount(armorType);
    }

    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        if (source != DamageSource.FALL)
            stack.damageItem(damage, entity);
    }

    @Override
    public int getWarp(ItemStack itemStack, EntityPlayer entityPlayer) {
        return warps[armorType.ordinal()];
    }
}