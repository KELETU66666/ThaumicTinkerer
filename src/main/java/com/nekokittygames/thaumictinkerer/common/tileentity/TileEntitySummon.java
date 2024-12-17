/*
 * Copyright (c) 2022. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.tileentity;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.api.MobAspect;
import com.nekokittygames.thaumictinkerer.api.MobAspects;
import com.nekokittygames.thaumictinkerer.common.items.ItemMobAspect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
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

public class TileEntitySummon extends TileEntityThaumicTinkerer implements ITickable {
    @Override
    public boolean respondsToPulses() {
        return false;
    }

    @Override
    public void update() {

        if (world.getTotalWorldTime() % 300 == 0) {
            if (world.isBlockPowered(pos)) {
                return;
            }
            for (int radius = 1; radius < 6; radius++) {
                ArrayList<TileEntity> pedestals = new ArrayList<TileEntity>();
                for (int x = pos.getX() - radius; x < pos.getX() + radius; x++) {
                    for (int z = pos.getZ() - radius; z < pos.getZ() + radius; z++) {
                        TileEntity tile = world.getTileEntity(new BlockPos(x, pos.getY(), z));
                        if (tile instanceof TilePedestal && ((TilePedestal) tile).getStackInSlot(0) != null && ((TilePedestal) tile).getStackInSlot(0).getItem() instanceof ItemMobAspect) {
                            pedestals.add(tile);
                        }
                    }
                }

                for (int i = 0; i < pedestals.size(); i++) {
                    for (int j = 0; j < pedestals.size(); j++) {
                        for (int k = 0; k < pedestals.size(); k++) {
                            TilePedestal ped1 = (TilePedestal) pedestals.get(i);
                            TilePedestal ped2 = (TilePedestal) pedestals.get(j);
                            TilePedestal ped3 = (TilePedestal) pedestals.get(k);

                            if ((ped1 != ped2) && (ped2 != ped3) && (ped1 != ped3)) {
                                AspectList aspects = new AspectList();
                                aspects.add(ItemMobAspect.getAspectType(ped1.getStackInSlot(0)), 1);

                                aspects.add(ItemMobAspect.getAspectType(ped2.getStackInSlot(0)), 1);

                                aspects.add(ItemMobAspect.getAspectType(ped3.getStackInSlot(0)), 1);

                                MobAspect aspect = MobAspects.getByAspects(aspects);
                                if (aspect != null) {

                                    boolean isInfused = ItemMobAspect.isInfused(ped1.getStackInSlot(0)) && ItemMobAspect.isInfused(ped2.getStackInSlot(0)) && ItemMobAspect.isInfused(ped3.getStackInSlot(0));

                                    if (isInfused && world.getTotalWorldTime() % 1200 != 0) {
                                        return;
                                    }

                                    if (!isInfused) {
                                        ped1.setInventorySlotContents(0, ItemStack.EMPTY);
                                        ped2.setInventorySlotContents(0, ItemStack.EMPTY);
                                        ped3.setInventorySlotContents(0, ItemStack.EMPTY);
                                    }


                                    if (!isInfused || ItemMobAspect.lastUsedTabletMatches(ped1.getStackInSlot(0), this)
                                            && ItemMobAspect.lastUsedTabletMatches(ped2.getStackInSlot(0), this)
                                            && ItemMobAspect.lastUsedTabletMatches(ped3.getStackInSlot(0), this)) {

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
                                    }
                                    if (isInfused) {
                                        ItemMobAspect.markLastUsedTablet(ped1.getStackInSlot(0), this);

                                        ItemMobAspect.markLastUsedTablet(ped2.getStackInSlot(0), this);

                                        ItemMobAspect.markLastUsedTablet(ped3.getStackInSlot(0), this);
                                    }

                                    return;

                                }

                            }

                        }
                    }
                }
            }
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