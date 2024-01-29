package com.nekokittygames.thaumictinkerer.common.items.Kami;

import com.nekokittygames.thaumictinkerer.client.misc.ModelWings;
import com.nekokittygames.thaumictinkerer.common.items.ItemEnergeticNitor;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
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
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IGoggles;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.lib.events.PlayerEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class ItemKamiArmor extends ItemIchorArmor implements IGoggles{

    public static List<String> playersWith1Step = new ArrayList<>();
    private static final WeakHashMap<EntityLivingBase, Object> armorModels = new WeakHashMap<>();

    public ItemKamiArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
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

    private void setNearBrightNitor(EntityPlayer player) {

        int x = (int) Math.floor(player.posX);
        int y = (int) player.posY + 1;
        int z = (int) Math.floor(player.posZ);

        float yaw = MathHelper.wrapDegrees(player.rotationYaw + 90F) * (float) Math.PI / 180F;
        Vector3 lookVector = new Vector3(Math.cos(yaw), Math.sin(yaw), 0).normalize();
        Vector3 newVector = new Vector3(lookVector.x, lookVector.y, 0);

        for (int i = 0; i < 5; i++) {
            newVector = newVector.add(lookVector);

            int x1 = x + (int) newVector.x;
            int z1 = z + (int) newVector.y;
            ItemEnergeticNitor.setBlock(new BlockPos(x1, y, z1), player.world);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer && event.getEntityLiving().world.isRemote) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();

            boolean highStepListed = playersWith1Step.contains(player.getGameProfile().getName());
            boolean hasHighStep = player.getItemStackFromSlot(EntityEquipmentSlot.FEET) != ItemStack.EMPTY && player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == this;

            if (!highStepListed && (hasHighStep && player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItemDamage() == 0))
                playersWith1Step.add(player.getGameProfile().getName());


            if ((!hasHighStep || player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItemDamage() == 1) && highStepListed) {
                playersWith1Step.remove(player.getGameProfile().getName());
                player.stepHeight = 0.5F;
            }
        }
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        switch (armorType) {
            case HEAD: {
                if(itemStack.getItemDamage() != 1){
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
                }}
            break;

            case CHEST: {
                if(itemStack.getItemDamage() != 1) {
                    player.getEntityData().setBoolean("can_fly", true);
                    doProjectileEffect(player);
                }
            }
            break;
            case LEGS: {
                if (itemStack.getItemDamage() != 1) {
                    if (player.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) == null || player.getActivePotionEffect(MobEffects.FIRE_RESISTANCE).getDuration() <= 1) {
                        player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1, 0, false, false));
                        if (player.isBurning()) {
                            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20, 1, true, false));
                            player.extinguish();
                        }
                    }
                    setNearBrightNitor(player);
                }
            }
            break;

            case FEET: {
                tickPlayer(player);
            }
            break;

        }
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.EPIC;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return LibMisc.MOD_ID + ":textures/models/armor/kami_layer_" + (slot == EntityEquipmentSlot.LEGS ? "2" : "1") + ".png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        return (ModelWings) armorModels.computeIfAbsent(entityLiving, ignored -> new ModelWings());
    }

    @Override
    public boolean showIngamePopups(ItemStack stack, EntityLivingBase owner) {
        return armorType == EntityEquipmentSlot.HEAD;
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

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            int dmg = stack.getItemDamage();
            stack.setItemDamage(~dmg & 1);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.getItemDamage() == 1) {
            tooltip.add(TextFormatting.RED +
                    I18n.format("tip.awakenarmor.name1"));
        } else {
            tooltip.add(TextFormatting.DARK_GREEN +
                    I18n.format("tip.awakenarmor.name0"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
