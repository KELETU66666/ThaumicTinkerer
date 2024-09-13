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
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXFocusPartImpact;

public class FocusCelestialTeleport extends FocusEffect {
    @Override
    public Aspect getAspect() {
        return Aspect.MOTION;
    }

    @Override
    public String getKey() {
        return "focus." + LibMisc.MOD_ID + ".celestial_gateway";
    }

    @Override
    public int getComplexity() {
        return 35;
    }

    @Override
    public String getResearch() {
        //TODO : add it's requirements :)
        return "";
    }

    @Override
    public boolean execute(final RayTraceResult target, final Trajectory trajectory, final float finalPower, final int num) {
        EntityLivingBase base = this.getPackage().getCaster();
        PacketHandler.INSTANCE.sendToAllAround(new PacketFXFocusPartImpact(target.hitVec.x, target.hitVec.y, target.hitVec.z, new String[]{this.getKey()}), new NetworkRegistry.TargetPoint(this.getPackage().world.provider.getDimension(), target.hitVec.x, target.hitVec.y, target.hitVec.z, 64.0D));
        this.getPackage().world.playSound(null, target.hitVec.x, target.hitVec.y, target.hitVec.z, SoundsTC.wand, SoundCategory.PLAYERS, 0.33F, 5.0F + (float) (this.getPackage().world.rand.nextGaussian() * 0.05F));

        if (target.typeOfHit == RayTraceResult.Type.ENTITY && target.entityHit != null) {
            if (Integer.MAX_VALUE - num > 60) {
                ItemStack stackToCount = ItemStack.EMPTY;
                for (int i = 0; i < 9; i++) {
                    ItemStack stackInSlot = ((EntityPlayer) base).inventory.getStackInSlot(i);
                    if (stackInSlot != null && stackInSlot.getItem() instanceof ItemSkyPearl && ItemSkyPearl.isAttuned(stackInSlot)) {
                        stackToCount = stackInSlot;
                        break;
                    }
                }

                if (stackToCount != null) {
                    int dim = ItemSkyPearl.getDim(stackToCount);
                    if (dim == base.dimension) {
                        int x = ItemSkyPearl.getX(stackToCount);
                        int y = ItemSkyPearl.getY(stackToCount);
                        int z = ItemSkyPearl.getZ(stackToCount);

                        if (target.entityHit instanceof EntityLivingBase)
                            TileWarpGate.teleportEntity((EntityLivingBase) target.entityHit, new BlockPos(x, y, z));
                    }
                }
            }
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
        //final FXGeneric pp = new FXGeneric(world, posX, posY, posZ, velX, velY, velZ);
        //int color = 9039872;
        //pp.setAlphaF(0.7F);
        //pp.setGravity(-0.2F);
        //pp.setMaxAge(7 + world.rand.nextInt(5));
        //pp.setParticles(575, 8, 8);
        //pp.setRBGColorF(((color >> 16) & 0xFF) / 255.0F, ((color >> 8) & 0xFF) / 255.0F, (color & 0xFF) / 255.0F);
        //pp.setSlowDown(0.75D);
        //pp.setScale((float) (0.1F + world.rand.nextGaussian() * 0.2F), 2.0F);
        //ParticleEngine.addEffect(world, pp);
    }
}