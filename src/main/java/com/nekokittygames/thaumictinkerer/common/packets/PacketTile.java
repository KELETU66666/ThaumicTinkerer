/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [9 Sep 2013, 17:00:10 (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.packets;

import com.nekokittygames.thaumictinkerer.client.proxy.ClientProxy;
import com.nekokittygames.thaumictinkerer.common.misc.MiscHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class PacketTile<T extends TileEntity> implements IMessage {

    private static final long serialVersionUID = -1447633008013055477L;
    protected int dim, x, y, z;
    protected transient T tile;
    protected transient EntityPlayer player;

    public PacketTile() {}

    public PacketTile(T tile) {
        this.tile = tile;

        this.x = tile.getPos().getX();
        this.y = tile.getPos().getY();
        this.z = tile.getPos().getZ();
        this.dim = tile.getWorld().provider.getDimension();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(x);
        byteBuf.writeInt(y);
        byteBuf.writeInt(z);
        byteBuf.writeInt(dim);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        x = byteBuf.readInt();
        y = byteBuf.readInt();
        z = byteBuf.readInt();
        dim = byteBuf.readInt();
    }

    public IMessage onMessage(PacketTile message, MessageContext ctx) {
        MinecraftServer server = MiscHelper.server();
        if (ctx.side.isClient())
            message.player = ClientProxy.getPlayer();
        else {
            message.player = ctx.getServerHandler().player;
        }
        if (server != null) {
            World world = server.getWorld(message.dim);

            if (world == null) {
                MiscHelper.printCurrentStackTrace("No world found for dimension " + message.dim + "!");
                return null;
            }

            TileEntity tile = world.getTileEntity(new BlockPos(message.x, message.y, message.z));
            if (tile != null) {
                message.tile = tile;
            }
        }
        return null;
    }
}