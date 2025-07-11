/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Dec 29, 2013, 9:35:38 PM (GMT)]
 */
package com.nekokittygames.thaumictinkerer.client.misc;

import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class SoulHeartClientHandler {

    private static final ResourceLocation iconsResource = new ResourceLocation("textures/gui/icons.png");
    private static final ResourceLocation heartsResource = LibClientResources.GUI_SOUL_HEARTS;

    @SideOnly(Side.CLIENT)
    public static int clientPlayerHP = 0;

    @SideOnly(Side.CLIENT)
    private static void renderHeart(int x, int y, boolean full) {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        float size = 1 / 16F;

        float startX = full ? 0 : 9 * size;
        float endX = full ? 9 * size : 1;
        float startY = 0;
        float endY = 9 * size;

        buf.begin(7, DefaultVertexFormats.POSITION_TEX);
        buf.pos(x, y + 9, 0).tex(startX, endY).endVertex();
        buf.pos(x + (full ? 9 : 7), y + 9, 0).tex(endX, endY).endVertex();
        buf.pos(x + (full ? 9 : 7), y, 0).tex(endX, startY).endVertex();
        buf.pos(x, y, 0).tex(startX, startY).endVertex();
        tess.draw();
    }

    /*
     * @SideOnly(Side.CLIENT)
     * @SubscribeEvent public void renderHealthBar(RenderGameOverlayEvent event) { if (event.type == ElementType.FOOD &&
     * clientPlayerHP > 0) { if (event instanceof RenderGameOverlayEvent.Post) { Minecraft mc =
     * Minecraft.getMinecraft(); int x = event.resolution.getScaledWidth() / 2 + 10; int y =
     * event.resolution.getScaledHeight() - 39; //GL11.glTranslatef(0F, 10F, 0F);
     * mc.renderEngine.bindTexture(heartsResource); int it = 0; for (int i = 0; i < clientPlayerHP; i++) { boolean half
     * = i == clientPlayerHP - 1 && clientPlayerHP % 2 != 0; if (half || i % 2 == 0) { renderHeart(x + it * 8, y,
     * !half); it++; } } mc.renderEngine.bindTexture(iconsResource); } //GL11.glTranslatef(0F, -10F, 0F); } //if
     * (event.type == ElementType.AIR && event instanceof RenderGameOverlayEvent.Post && clientPlayerHP > 0)
     * //GL11.glTranslatef(0F, 10F, 0F); }
     */

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onDrawScreenPre(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            renderHUD(event.getResolution(), mc.player, null);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void renderHUD(ScaledResolution resolution, EntityPlayer player, ItemStack stack) {

        Minecraft mc = Minecraft.getMinecraft();
        mc.renderEngine.bindTexture(heartsResource);
        int x = resolution.getScaledWidth() / 2 + 10;
        int y = resolution.getScaledHeight() - TTConfig.soulHeartHeight;
        if (player.getAir() < 300) {
            y = resolution.getScaledHeight() - (TTConfig.soulHeartHeight + 10);
        }

        mc.renderEngine.bindTexture(heartsResource);
        int it = 0;
        for (int i = 0; i < clientPlayerHP; i++) {
            boolean half = i == clientPlayerHP - 1 && clientPlayerHP % 2 != 0;
            if (half || i % 2 == 0) {
                renderHeart(x + it * 8, y, !half);
                it++;
            }
        }

        mc.renderEngine.bindTexture(Gui.ICONS);
    }
}