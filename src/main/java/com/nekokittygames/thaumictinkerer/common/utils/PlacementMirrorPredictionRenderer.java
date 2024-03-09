/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [27/10/2016, 17:55:20 (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.utils;

import com.nekokittygames.thaumictinkerer.common.items.Kami.ItemPlacementMirror;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;

import java.util.List;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = LibMisc.MOD_ID)
public final class PlacementMirrorPredictionRenderer {
	@SubscribeEvent
	public static void onWorldRenderLast(RenderWorldLastEvent event) {
		World world = Minecraft.getMinecraft().world;
		List<EntityPlayer> playerEntities = world.playerEntities;
		for (EntityPlayer player : playerEntities) {
			ItemStack currentStack = player.getHeldItemMainhand();
			if(currentStack.isEmpty() || !(currentStack.getItem() instanceof ItemPlacementMirror))
				currentStack = player.getHeldItemOffhand();

			if(!currentStack.isEmpty() && currentStack.getItem() instanceof ItemPlacementMirror) {
				Block block = ItemPlacementMirror.getBlock(currentStack);
				if(block != Blocks.AIR)
					renderPlayerLook(player, currentStack);
			}
		}
	}

	private static void renderPlayerLook(EntityPlayer player, ItemStack stack) {
		BlockPos[] coords = ItemPlacementMirror.getBlocksToPlace(stack, player);
		if (ItemPlacementMirror.hasBlocks(stack, player, coords)) {
			ItemStack block = new ItemStack(
					ItemPlacementMirror.getBlock(stack),
					1,
					ItemPlacementMirror.getBlockMeta(stack));
			BlockPos lastCoords = new BlockPos(0, 0, 0);

			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			for (BlockPos coord : coords) {
				renderBlockAt(block, coord, lastCoords);
				lastCoords = coord;
			}
			GL11.glPopMatrix();
		}
	}

	private static void renderBlockAt(ItemStack block, BlockPos pos, BlockPos last) {
		if (block == ItemStack.EMPTY) return;

		double renderPosX = Minecraft.getMinecraft().getRenderManager().renderPosX;
		double renderPosY = Minecraft.getMinecraft().getRenderManager().renderPosY;
		double renderPosZ = Minecraft.getMinecraft().getRenderManager().renderPosZ;

		GlStateManager.pushMatrix();
		GlStateManager.translate(-renderPosX, -renderPosY, -renderPosZ);
		GlStateManager.disableDepth();

		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		BlockRendererDispatcher brd = Minecraft.getMinecraft().getBlockRendererDispatcher();
		GlStateManager.translate(pos.getX(), pos.getY(), pos.getZ() + 1);
		GlStateManager.color(1, 1, 1, 1);
		brd.renderBlockBrightness(Block.getBlockFromItem(block.getItem()).getStateFromMeta(block.getItemDamage()), 1.0F);

		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableDepth();
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}

}