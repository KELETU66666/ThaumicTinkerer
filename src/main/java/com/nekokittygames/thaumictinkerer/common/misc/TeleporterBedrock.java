package com.nekokittygames.thaumictinkerer.common.misc;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ITeleporter;

public class TeleporterBedrock implements ITeleporter {
    public TeleporterBedrock() {

    }

    public TeleporterBedrock(EntityPlayerMP serverPlayer, double x, double y, double z) {
        serverPlayer.attemptTeleport(x, y, z);
    }

    @Override
    public void placeEntity(World currentWorld, Entity entity, float yaw) {
    }

    private void fireTriggers(WorldServer p_213846_1_, EntityPlayerMP player) {
        DimensionType registrykey = p_213846_1_.provider.getDimensionType();
        DimensionType registrykey1 = player.world.provider.getDimensionType();
        CriteriaTriggers.CHANGED_DIMENSION.trigger(player, registrykey, registrykey1);
    }
}