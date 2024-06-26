package com.nekokittygames.thaumictinkerer.common.proxy;

import com.nekokittygames.thaumictinkerer.client.gui.*;
import com.nekokittygames.thaumictinkerer.common.containers.AnimationTabletContainer;
import com.nekokittygames.thaumictinkerer.common.containers.ContainerWarpGate;
import com.nekokittygames.thaumictinkerer.common.containers.EnchanterContainer;
import com.nekokittygames.thaumictinkerer.common.containers.MagnetContainer;
import com.nekokittygames.thaumictinkerer.common.items.Kami.ichorpouch.ContainerPouch;
import com.nekokittygames.thaumictinkerer.common.items.Kami.ichorpouch.GuiPouch;
import com.nekokittygames.thaumictinkerer.common.tileentity.Kami.TileWarpGate;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityAnimationTablet;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMagnet;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMobMagnet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiProxy implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int i, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityMagnet) {
            return new MagnetContainer(entityPlayer.inventory, (TileEntityMagnet) te);
        }
        if (te instanceof TileEntityEnchanter) {
            return new EnchanterContainer(entityPlayer.inventory, (TileEntityEnchanter) te, entityPlayer);
        }
        if (te instanceof TileEntityAnimationTablet) {
            return new AnimationTabletContainer(entityPlayer.inventory, (TileEntityAnimationTablet) te);
        }
        if (i == 0)
            return new ContainerPouch(entityPlayer);
        if(i == 1)
            return new ContainerWarpGate((TileWarpGate) te, entityPlayer.inventory);
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int i, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityMagnet) {
            TileEntityMagnet magnet = (TileEntityMagnet) te;
            if(te instanceof TileEntityMobMagnet)
                return new GuiMobMagnet((TileEntityMobMagnet)magnet, new MagnetContainer(entityPlayer.inventory, magnet));
            else
                return new GuiMagnet(magnet, new MagnetContainer(entityPlayer.inventory, magnet));
        }
        if (te instanceof TileEntityEnchanter) {
            TileEntityEnchanter enchanter = (TileEntityEnchanter) te;
            return new GuiEnchanter(enchanter, new EnchanterContainer(entityPlayer.inventory, enchanter, entityPlayer));
        }
        if (te instanceof TileEntityAnimationTablet) {
            TileEntityAnimationTablet animationTablet = (TileEntityAnimationTablet) te;
            return new GuiAnimationTablet(animationTablet, new AnimationTabletContainer(entityPlayer.inventory, animationTablet));
        }
        if (i == 0)
            return new GuiPouch(new ContainerPouch(entityPlayer));
        if(i == 1)
            return new GuiWarpGate((TileWarpGate) te, entityPlayer.inventory);
        if(i == 2)
            return new GuiWarpGateDestinations((TileWarpGate) te);
        return null;
    }
}
