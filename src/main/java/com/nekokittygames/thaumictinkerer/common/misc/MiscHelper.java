package com.nekokittygames.thaumictinkerer.common.misc;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import thaumcraft.codechicken.lib.vec.Vector3;


public class MiscHelper {


    public static double pointDistanceSpace(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
    }

    //public static boolean breakBlockWithCheck(World w, int x, int y, int z, Block block, int meta, int flag,
    //        EntityPlayer player) {
    //    if (!MinecraftForge.EVENT_BUS.post(new BlockEvent.BreakEvent(x, y, z, w, w.getBlock(x, y, z), meta, player))) {
    //        w.setBlock(x, y, z, block, meta, flag);
    //        return true;
    //    }
    //    return false;
    //}
//
    //public static boolean breakBlockToAirWithCheck(World w, int x, int y, int z, EntityPlayer player) {
    //    return breakBlockWithCheck(w, x, y, z, Blocks.air, 0, 3, player);
    //}
//
    //public static boolean breakBlockWithCheck(World w, int x, int y, int z, Block block, EntityPlayer player) {
    //    return breakBlockWithCheck(w, x, y, z, block, 0, 0, player);
    //}
//
    public static MinecraftServer server() {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }

    //public static void setEntityMotionFromVector(Entity entity, Vector3 originalPosVector, float modifier) {
    //    Vector3 entityVector = Vector3.fromEntityCenter(entity);
    //    Vector3 finalVector = originalPosVector.copy().subtract(entityVector);
//
    //    if (finalVector.mag() > 1) finalVector.normalize();
//
    //    entity.motionX = finalVector.x * modifier;
    //    entity.motionY = finalVector.y * modifier;
    //    entity.motionZ = finalVector.z * modifier;
    //}
//
    //public static AspectList multiplyAspectList(AspectList list, double multiplier) {
    //    AspectList newList = list.copy();
    //    if (multiplier == 1) return newList;
//
    //    newList.aspects.replaceAll((a, v) -> (int) ((double) newList.aspects.get(a) * multiplier));
//
    //    return newList;
    //}
//
    public static void printCurrentStackTrace(String message) {
        //  if (message != null) ThaumicTinkerer.log.info(message);

        //  for (StackTraceElement element : Thread.currentThread().getStackTrace()) ThaumicTinkerer.log.info(element);
    }

    public static void setEntityMotionFromVector(Entity entity, Vector3 originalPosVector, float modifier) {
        Vector3 entityVector = Vector3.fromEntityCenter(entity);
        Vector3 finalVector = originalPosVector.copy().subtract(entityVector);

        if (finalVector.mag() > 1)
            finalVector.normalize();

        entity.motionX = finalVector.x * modifier;
        entity.motionY = finalVector.y * modifier;
        entity.motionZ = finalVector.z * modifier;
    }


}
