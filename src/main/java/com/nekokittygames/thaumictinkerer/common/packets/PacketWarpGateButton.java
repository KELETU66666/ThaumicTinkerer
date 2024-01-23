/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Jan 10, 2014, 5:59:27 PM (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.packets;

import com.nekokittygames.thaumictinkerer.common.tileentity.Kami.TileWarpGate;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketWarpGateButton extends PacketTile<TileWarpGate>
        implements IMessageHandler<PacketWarpGateButton, IMessage> {

    private static final long serialVersionUID = 1497188581985763661L;
    boolean locked;

    public PacketWarpGateButton() {
        super();
    }

    public PacketWarpGateButton(TileWarpGate tile) {
        super(tile);
        locked = tile.locked;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        super.toBytes(byteBuf);
        byteBuf.writeBoolean(locked);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        super.fromBytes(byteBuf);
        locked = byteBuf.readBoolean();
    }

    @Override
    public IMessage onMessage(PacketWarpGateButton message, MessageContext ctx) {
        super.onMessage(message, ctx);
        if (!ctx.side.isServer())
            throw new IllegalStateException("received PacketWarpgateButton " + message + "on client side!");
        message.tile.locked = message.locked;
        return null;
    }
}