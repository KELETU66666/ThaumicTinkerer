package com.nekokittygames.thaumictinkerer.common.foci;

import com.nekokittygames.thaumictinkerer.common.items.Kami.ItemSkyPearl;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.tileentity.Kami.TileWarpGate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXGeneric;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXFocusPartImpact;

public class FocusEnderRift extends FocusEffect {
    @Override
    public Aspect getAspect() {
        return Aspect.ELDRITCH;
    }

    @Override
    public String getKey() {
        return "thaumictinkerer.enderrift";
    }

    @Override
    public int getComplexity() {
        return 25;
    }

    @Override
    public String getResearch() {
        return "TT_ENDER_RIFT";
    }

    @Override
    public boolean execute(final RayTraceResult target, final Trajectory trajectory, final float finalPower, final int num) {
        EntityLivingBase base = this.getPackage().getCaster();
        PacketHandler.INSTANCE.sendToAllAround(new PacketFXFocusPartImpact(target.hitVec.x, target.hitVec.y, target.hitVec.z, new String[]{this.getKey()}), new NetworkRegistry.TargetPoint(this.getPackage().world.provider.getDimension(), target.hitVec.x, target.hitVec.y, target.hitVec.z, 64.0D));
        this.getPackage().world.playSound(null, target.hitVec.x, target.hitVec.y, target.hitVec.z, SoundsTC.wand, SoundCategory.PLAYERS, 0.33F, 5.0F + (float) (this.getPackage().world.rand.nextGaussian() * 0.05F));

        if (target.typeOfHit == RayTraceResult.Type.ENTITY && target.entityHit instanceof EntityPlayer && base instanceof EntityPlayer) {
            ((EntityPlayer) base).displayGUIChest(((EntityPlayer) target.entityHit).getInventoryEnderChest());
        }

        return false;
    }

    @Override
    public void onCast(Entity caster) {
        caster.world.playSound(null, caster.getPosition().up(), SoundsTC.wand, SoundCategory.PLAYERS, 0.33F, 0.9F + caster.world.rand.nextFloat() * 0.1F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderParticleFX(World world, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        final FXGeneric pp = new FXGeneric(world, posX, posY, posZ, motionX, motionY, motionZ);
        pp.setMaxAge(16 + world.rand.nextInt(16));
        pp.setParticles(384 + world.rand.nextInt(16), 1, 1);
        pp.setSlowDown(0.75D);
        pp.setAlphaF(new float[]{1.0F, 0.0F});
        pp.setScale(new float[]{(float) (0.7F + world.rand.nextGaussian() * 0.3F)});
        pp.setRBGColorF(0.25F, 0.25F, 1.0F);
        pp.setRandomMovementScale(0.01F, 0.01F, 0.01F);
        ParticleEngine.addEffectWithDelay(world, (Particle) pp, 0);
    }
}