/*
 * Copyright (c) 2022. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.tileentity;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.api.MobAspect;
import com.nekokittygames.thaumictinkerer.api.MobAspects;
import com.nekokittygames.thaumictinkerer.common.items.ItemMobAspect;
import com.nekokittygames.thaumictinkerer.common.utils.CombinationGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.tiles.crafting.TilePedestal;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static com.nekokittygames.thaumictinkerer.common.items.ItemMobAspect.ASPECT_NAME;

public class TileEntitySummon extends TileEntityThaumicTinkerer implements ITickable {
    @Override
    public boolean respondsToPulses() {
        return false;
    }

    private boolean pedestalAvailable(TileEntity tile){
        return tile instanceof TilePedestal
            && ((TilePedestal) tile).getStackInSlot(0) != null
            && ((TilePedestal) tile).getStackInSlot(0).getItem() instanceof ItemMobAspect
            && ItemMobAspect.getAspectType(((TilePedestal) tile).getStackInSlot(0)) != null
            && ItemMobAspect.isReadyForSummon(((TilePedestal) tile).getStackInSlot(0), world.getTotalWorldTime());
    }

    @Override
    public void update() {
        if (world.getTotalWorldTime() % 100 != 0 || world.isBlockPowered(pos)) {
            return;
        }
        ArrayList<TilePedestal> pedestals = new ArrayList<TilePedestal>();
        HashMap<String, Short> aspectUsageMap = new HashMap<>();

        int[] dx = {1, 0, -1, 0};
        int[] dz = {0, 1, 0, -1};
        for (int radius = 1; radius < 6; radius++) {
            int x = pos.getX() - radius + 1;
            int z = pos.getZ() - radius;
            int endX = x - 1;
            int endZ = z - 1;
            int direction = 0;

            while (x != endX || z != endZ) {
                TileEntity tile = world.getTileEntity(new BlockPos(x, pos.getY(), z));
                if (this.pedestalAvailable(tile)) {
                    String aspectName = ((TilePedestal) tile).getStackInSlot(0).getTagCompound().getString(ASPECT_NAME);
                    aspectUsageMap.put(aspectName, (short) (aspectUsageMap.getOrDefault(aspectName, (short) 0) + 1));
                    if (aspectUsageMap.get(aspectName) <= 3){
                        pedestals.add((TilePedestal) tile);
                    }
                }
                if (!(x != pos.getX() + radius && direction == 0
                    || z != pos.getZ() + radius && direction == 1
                    || x != pos.getX() - radius && direction == 2
                    || direction == 3)) {
                    direction = (direction + 1) % 4;
                }
                x += dx[direction];
                z += dz[direction];
            }
        }
        int maxCombinationSearchCount = CombinationGenerator.combination(pedestals.size(), 3);
        for (int i = 0; i < maxCombinationSearchCount; i++) {
            ArrayList<Short> indices = CombinationGenerator.getIncreasingCombinations(i, (short) 3);
            AspectList aspects = new AspectList();

            TilePedestal ped1 = pedestals.get(indices.get(0));
            TilePedestal ped2 = pedestals.get(indices.get(1));
            TilePedestal ped3 = pedestals.get(indices.get(2));

            aspects.add(ItemMobAspect.getAspectType(ped1.getStackInSlot(0)), 1);
            aspects.add(ItemMobAspect.getAspectType(ped2.getStackInSlot(0)), 1);
            aspects.add(ItemMobAspect.getAspectType(ped3.getStackInSlot(0)), 1);

            MobAspect aspect = MobAspects.getByAspects(aspects);

            if (aspect == null) {
                continue;
            }

            boolean isInfused = ItemMobAspect.isInfused(ped1.getStackInSlot(0))
                    && ItemMobAspect.isInfused(ped2.getStackInSlot(0))
                    && ItemMobAspect.isInfused(ped3.getStackInSlot(0));

            if (isInfused && world.getTotalWorldTime() % 400 != 0) {
                continue;
            }

            if (!isInfused) {
                ped1.setInventorySlotContents(0, ItemStack.EMPTY);
                ped2.setInventorySlotContents(0, ItemStack.EMPTY);
                ped3.setInventorySlotContents(0, ItemStack.EMPTY);
            }else {
                ItemMobAspect.markNextSummon(ped1.getStackInSlot(0),world.getTotalWorldTime() + 400);
                ItemMobAspect.markNextSummon(ped2.getStackInSlot(0),world.getTotalWorldTime() + 400);
                ItemMobAspect.markNextSummon(ped3.getStackInSlot(0),world.getTotalWorldTime() + 400);
            }

            if (!world.isRemote) {
                ResourceLocation entityToSpawn = aspect.getEntityName();
                ThaumicTinkerer.logger.info("Spawning a " + entityToSpawn.toString());
                spawnMob(entityToSpawn);
            }

            if (world.isRemote) {
                //if ((ped1 != ped2) && (ped2 != ped3) && (ped1 != ped3)) {
                //    ShowSparks(ped1);
                //    ShowSparks(ped2);
                //    ShowSparks(ped3);
                //}
                //for (TileEntity pedestal : pedestals)
                //    FXDispatcher.INSTANCE.drawSlash(pedestal.getPos().getX() + .5, pos.getY() + 1, pos.getZ() + .5, pedestal.getPos().getX() + 1, pos.getY() + 2, pos.getZ() + 1, 60);

            }
            break;
        }
    }

    private void spawnMob(ResourceLocation entityToSpawn) {
        Entity spawn = EntityList.createEntityByIDFromName(entityToSpawn, world);
        if (spawn != null) {
            spawn.setLocationAndAngles(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5, 0, 0);
            world.spawnEntity(spawn);
            ((EntityLiving) spawn).onInitialSpawn(world.getDifficultyForLocation(pos), null);
            ((EntityLiving) spawn).playLivingSound();
        }
    }

    private void ShowSparks(TilePedestal pedastal) {
        ItemStack stack = pedastal.getStackInSlot(0);
        Aspect aspect = ItemMobAspect.getAspectType(stack);
        if (aspect != null) {
            Color color = new Color(aspect.getColor());

            if (world.isRemote)
                FXDispatcher.INSTANCE.arcLightning(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, pedastal.getPos().getX() + 0.5f, pedastal.getPos().getY() + 1, pedastal.getPos().getZ() + 0.5f, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0.1f);
        }
    }

}