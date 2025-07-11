/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Dec 29, 2013, 9:41:05 PM (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.packets;

import com.nekokittygames.thaumictinkerer.client.misc.SoulHeartClientHandler;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSoulHearts implements IMessage, IMessageHandler<PacketSoulHearts, IMessage> {

    private static final long serialVersionUID = 8044672277674872323L;
    int hearts;

    public PacketSoulHearts(int hearts) {
        this.hearts = hearts;
    }

    public PacketSoulHearts() {
        super();
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        hearts = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(hearts);
    }

    @Override
    public IMessage onMessage(PacketSoulHearts message, MessageContext ctx) {
        if (!ctx.side.isClient())
            throw new IllegalStateException("received PacketSoulHearts " + message + "on server side!");
        SoulHeartClientHandler.clientPlayerHP = message.hearts;
        return null;
    }
}