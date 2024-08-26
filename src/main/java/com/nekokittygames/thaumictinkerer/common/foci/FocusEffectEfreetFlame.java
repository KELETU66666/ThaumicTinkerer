package com.nekokittygames.thaumictinkerer.common.foci;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.ICaster;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXGeneric;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXFocusPartImpact;

@SuppressWarnings("deprecation")
public class FocusEffectEfreetFlame extends FocusEffect {


    @Override
    public boolean execute(RayTraceResult target, Trajectory trajectory, float finalPower, int num) {
        PacketHandler.INSTANCE.sendToAllAround(new PacketFXFocusPartImpact(target.hitVec.x, target.hitVec.y, target.hitVec.z, new String[]{this.getKey()}), new NetworkRegistry.TargetPoint(this.getPackage().world.provider.getDimension(), target.hitVec.x, target.hitVec.y, target.hitVec.z, 64.0D));
        this.getPackage().world.playSound(null, target.hitVec.x, target.hitVec.y, target.hitVec.z, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.PLAYERS, 1.0F, 1.5F + (float) (this.getPackage().world.rand.nextGaussian() * 0.05F));

        if (target.typeOfHit == RayTraceResult.Type.BLOCK) {
            ItemStack casterStack = ItemStack.EMPTY;
            getPackage().getCaster().getHeldItemMainhand();
            if (this.getPackage().getCaster().getHeldItemMainhand() != ItemStack.EMPTY && this.getPackage().getCaster().getHeldItemMainhand().getItem() instanceof ICaster) {
                casterStack = getPackage().getCaster().getHeldItemMainhand();
            } else {
                getPackage().getCaster().getHeldItemOffhand();
                if (this.getPackage().getCaster().getHeldItemOffhand() != ItemStack.EMPTY && this.getPackage().getCaster().getHeldItemOffhand().getItem() instanceof ICaster) {
                    casterStack = getPackage().getCaster().getHeldItemOffhand();
                }
            }

            BlockPos pos = target.getBlockPos();

            if (casterStack.isEmpty())
                return false;
            if (ReplaceBlock(getPackage().world, (EntityPlayerMP) getPackage().getCaster(), pos, target.sideHit, EnumHand.MAIN_HAND, pos.getX(), pos.getY(), pos.getZ()) == EnumActionResult.SUCCESS)
                return true;
        }
        return false;
    }

    private static EnumActionResult ReplaceBlock(World world, EntityPlayerMP entityPlayerMP, BlockPos pos, EnumFacing side, EnumHand hand, float hitX, float hitY, float hitZ) {
        IBlockState state = world.getBlockState(pos);
        ItemStack toSmelt = state.getBlock().getPickBlock(state, new RayTraceResult(RayTraceResult.Type.BLOCK, new Vec3d(hitX, hitY, hitZ), side, pos), world, pos, entityPlayerMP);
        ItemStack result = FurnaceRecipes.instance().getSmeltingResult(toSmelt);
        if (result.getItem() instanceof ItemBlock) {
            IBlockState toPlace = ((ItemBlock) result.getItem()).getBlock().getStateForPlacement(world, pos, side, hitX, hitY, hitZ, result.getItemDamage(), entityPlayerMP, hand);
            if (world.setBlockState(pos, toPlace, 3)) {
                world.playSound(entityPlayerMP, pos, new SoundEvent(new ResourceLocation("block.fire.ignite")), SoundCategory.BLOCKS, 1, 1);
                if (!world.isRemote) {
                    return EnumActionResult.FAIL;
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public void onCast(Entity caster) {
        caster.world.playSound(null, caster.getPosition().up(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0F, 0.5F + caster.world.rand.nextFloat() * 0.05F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderParticleFX(World world, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        final FXGeneric pp = new FXGeneric(world, posX, posY, posZ, velX, velY, velZ);
        int color = 25565230;
        pp.setAlphaF(0.7F);
        pp.setGravity(-0.2F);
        pp.setMaxAge(10);
        pp.setParticles(640, 10, 1);
        pp.setRBGColorF(((color >> 16) & 0xFF) / 255.0F, ((color >> 8) & 0xFF) / 255.0F, (color & 0xFF) / 255.0F);
        pp.setSlowDown(0.75D);
        pp.setScale((float) (1.5F + world.rand.nextGaussian() * 0.2F));
        ParticleEngine.addEffect(world, pp);
    }

    @Override
    public int getComplexity() {
        return 5;
    }

    @Override
    public Aspect getAspect() {
        return Aspect.FIRE;
    }

    @Override
    public String getKey() {
        return "thaumictinkerer.efreetflame";
    }

    @Override
    public String getResearch() {
        return "TT_EFREET_FLAME";
    }
}
