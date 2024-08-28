package com.nekokittygames.thaumictinkerer.common.foci;

import com.nekokittygames.thaumictinkerer.common.misc.MiscHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.NodeSetting;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXGeneric;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXFocusPartImpact;

import javax.annotation.Nullable;
import java.util.List;

public class FocusEffectTelekenesis extends FocusEffect {
    @Override
    public boolean execute(RayTraceResult paramRayTraceResult, @Nullable Trajectory paramTrajectory, float paramFloat, int paramInt) {
        PacketHandler.INSTANCE.sendToAllAround(new PacketFXFocusPartImpact(paramRayTraceResult.hitVec.x, paramRayTraceResult.hitVec.y, paramRayTraceResult.hitVec.z, new String[]{this.getKey()}), new NetworkRegistry.TargetPoint(this.getPackage().world.provider.getDimension(), paramRayTraceResult.hitVec.x, paramRayTraceResult.hitVec.y, paramRayTraceResult.hitVec.z, 64.0D));
        this.getPackage().world.playSound(null, paramRayTraceResult.hitVec.x, paramRayTraceResult.hitVec.y, paramRayTraceResult.hitVec.z, SoundsTC.scan, SoundCategory.PLAYERS, 0.7F, 1.4F + (float) (this.getPackage().getCaster().world.rand.nextGaussian() * 0.05F));

        if (paramRayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK)
            return false;
        if (paramTrajectory == null)
            return false;
        Vector3 target = new Vector3(paramTrajectory.source);
        final int range = 6 + getSettingValue("power");
        final double distance = range - 1;
        if (this.getPackage().getCaster().isSneaking())
            target.add(new Vector3(this.getPackage().getCaster().getLookVec()).multiply(distance));
        target.y += 0.5;
        List<EntityItem> entities = getPackage().world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(target.x - range, target.y - range, target.z - range, target.x + range, target.y + range, target.z + range));
        if (!entities.isEmpty()) {
            for (EntityItem item : entities) {
                MiscHelper.setEntityMotionFromVector(item, target, 0.3333F);
                //FXDispatcher.INSTANCE.sparkle((float) item.posX, (float) item.posY, (float) item.posZ, 0, 0, 0);
            }
        }

        return false;
    }

    @Override
    public NodeSetting[] createSettings() {
        return new NodeSetting[]{new NodeSetting("power", "focus.common.power", new NodeSetting.NodeSettingIntRange(1, 5))};
    }

    @Override
    public void onCast(Entity caster) {
        caster.world.playSound(null, caster.getPosition().up(), SoundsTC.hhoff, SoundCategory.PLAYERS, 0.8F, 1.15F + (float) (caster.world.rand.nextGaussian() * 0.05F));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderParticleFX(World world, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        final FXGeneric pp = new FXGeneric(world, posX, posY, posZ, velX, velY, velZ);
        pp.setMaxAge(9);
        pp.setRBGColorF(0.25f + world.rand.nextFloat() * 0.25F, 0.25F + world.rand.nextFloat() * 0.25F, 0.25F + world.rand.nextFloat() * 0.25F);
        pp.setAlphaF(0.0F, 0.6F, 0.6F, 0.0F);
        pp.setGridSize(64);
        pp.setParticles(448, 9, 1);
        pp.setScale(0.5F, 0.25F);
        pp.setGravity((float) (world.rand.nextGaussian() * 0.009F));
        pp.setRandomMovementScale(0.0025F, 0.0025F, 0.0025F);
        ParticleEngine.addEffect(world, pp);
    }

    @Override
    public int getComplexity() {
        return 3 * getSettingValue("power");
    }

    @Override
    public Aspect getAspect() {
        return Aspect.MAGIC;
    }

    @Override
    public String getKey() {
        return "thaumictinkerer.TELEKENESIS";
    }

    @Override
    public String getResearch() {
        return "TT_TELEKENESIS";
    }
}
