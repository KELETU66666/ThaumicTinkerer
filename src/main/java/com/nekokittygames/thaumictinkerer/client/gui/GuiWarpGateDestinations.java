/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Jan 10, 2014, 6:08:23 PM (GMT)]
 */
package com.nekokittygames.thaumictinkerer.client.gui;

import com.nekokittygames.thaumictinkerer.common.items.Kami.ItemSkyPearl;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.packets.PacketHandler;
import com.nekokittygames.thaumictinkerer.common.packets.PacketWarpGateTeleport;
import com.nekokittygames.thaumictinkerer.common.tileentity.Kami.TileWarpGate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import thaumcraft.codechicken.lib.vec.Vector3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiWarpGateDestinations extends GuiScreen {

    private static final ResourceLocation enderField = new ResourceLocation("textures/entity/end_portal.png");
    TileWarpGate warpGate;
    RenderItem render = Minecraft.getMinecraft().getRenderItem();

    int lastMouseX, lastMouseY;
    int x, y;
    int ticks;
    List<String> tooltip = new ArrayList<>();

    public GuiWarpGateDestinations(TileWarpGate warpGate) {
        this.warpGate = warpGate;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = warpGate.getPos().getX() - width / 2;
        y = warpGate.getPos().getZ() - height / 2;
    }

    @Override
    public void updateScreen() {
        ++ticks;

        ScaledResolution res = new ScaledResolution(mc);
        int i = res.getScaledWidth();
        int j = res.getScaledHeight();
        int mx = Mouse.getX() * i / mc.displayWidth;
        int my = j - Mouse.getY() * j / mc.displayHeight - 1;

        if (Mouse.isButtonDown(0)) {
            int deltaX = mx - lastMouseX;
            int deltaY = my - lastMouseY;
            x -= deltaX;
            y -= deltaY;
        }

        lastMouseX = mx;
        lastMouseY = my;
    }

    @Override
    protected void keyTyped(char par1, int par2) throws IOException {
        super.keyTyped(par1, par2);

        if (par2 == 57) { // space
            x = warpGate.getPos().getX() - width / 2;
            y = warpGate.getPos().getZ() - height / 2;
            return;
        }
        if (par2 >= 2 && par2 < 12) {
            int num = par2 - 2;
            ItemStack stack = warpGate.getStackInSlot(num);
            if (stack != null && ItemSkyPearl.isAttuned(stack)
                    && ItemSkyPearl.getDim(stack) == warpGate.getWorld().provider.getDimension()) {
                int x = ItemSkyPearl.getX(stack);
                int z = ItemSkyPearl.getZ(stack);

                this.x = x - width / 2;
                y = z - height / 2;
            }
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        drawDefaultBackground();
        super.drawScreen(par1, par2, par3);

        tooltip.clear();

        int gateX = warpGate.getPos().getX() - x;
        int gateY = warpGate.getPos().getZ() - y;
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        List<Object[]> coords = new ArrayList<>();

        for (int i = 0; i < warpGate.getSizeInventory(); i++) {
            ItemStack stack = warpGate.getStackInSlot(i);
            if (stack != null && ItemSkyPearl.isAttuned(stack)) {
                int dim = ItemSkyPearl.getDim(stack);
                if (warpGate.getWorld().provider.getDimension() != dim) continue;

                int x = ItemSkyPearl.getX(stack);
                int y = ItemSkyPearl.getY(stack);
                int z = ItemSkyPearl.getZ(stack);

                if (y != -1) coords.add(new Object[] { x - this.x, z - this.y, stack, i });
            }
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1F, 1F, 1F, (float) ((Math.sin(ticks / 10D) + 1F) / 4F + 0.25F));
        GL11.glLineWidth(2F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        for (Object[] coords_ : coords) {
            int x = (Integer) coords_[0];
            int y = (Integer) coords_[1];

            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex2i(gateX, gateY);
            GL11.glVertex2i(x, y);
            GL11.glEnd();
        }
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);

        fontRenderer.drawStringWithShadow(
                TextFormatting.UNDERLINE + I18n.translateToLocal("ttmisc.destinations"),
                3,
                40,
                0xFFFFFF);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        drawPearlAt(0, null, gateX, gateY, par1, par2);
        for (Object[] coords_ : coords) drawPearlAt(
                (Integer) coords_[3],
                (ItemStack) coords_[2],
                (Integer) coords_[0],
                (Integer) coords_[1],
                par1,
                par2);

        //if (!tooltip.isEmpty())
        //    ClientHelper.renderTooltip(par1, par2, tooltip);

        drawCenteredString(
                fontRenderer,
                I18n.translateToLocal("ttmisc.numberKeys"),
                width / 2,
                5,
                0xFFFFFF);
        drawCenteredString(
                fontRenderer,
                I18n.translateToLocal("ttmisc.spaceToReset"),
                width / 2,
                16,
                0xFFFFFF);
    }

    public void drawPearlAt(int index, ItemStack stack, int xp, int yp, int mx, int my) {
        int x = xp + this.x;
        int y = yp + this.y;

        String destName;

        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GL11.glPushMatrix();
        GL11.glTranslatef(xp, yp, 0);
        GL11.glScalef(0.5F, 0.5F, 1F);
        render.renderItemIntoGUI(
                ModItems.sky_pearl.getDefaultInstance(),
                -8,
                -8);
        GL11.glPopMatrix();

        String destNum = " " + TextFormatting.ITALIC
                + String.format(I18n.translateToLocal("ttmisc.destinationInd"), index + 1);
        if (stack != null && stack.hasDisplayName()) destName = stack.getDisplayName();
        else destName = I18n.translateToLocal(stack == ItemStack.EMPTY ? "ttmisc.entrancePoint" : "ttmisc.destination");

        if (stack != null) fontRenderer.drawString((index + 1) + ": " + destName, 5, 54 + index * 11, 0xFFFFFF);

        if (mx >= xp - 4 && mx <= xp + 4 && my >= yp - 4 && my < yp + 4) {
            tooltip.add(TextFormatting.AQUA + destName + destNum);

            if (stack != null) {
                ItemSkyPearl.addInfo(
                        stack,
                        warpGate.getWorld().provider.getDimension(),
                        Vector3.fromTile(warpGate),
                        tooltip,
                        true);
                tooltip.add(I18n.translateToLocal("ttmisc.clickToTeleport"));
            } else {
                tooltip.add("X: " + x);
                tooltip.add("Z: " + y);
            }

            if (Mouse.isButtonDown(0) && isShiftKeyDown() && stack != null) {
                PacketHandler.INSTANCE.sendToServer(new PacketWarpGateTeleport(warpGate, index));
                mc.displayGuiScreen(null);
            }
        }
    }

    //public void drawBackground(int tint)
    //{
    //    GlStateManager.disableLighting();
    //    GlStateManager.disableFog();
    //    Tessellator tessellator = Tessellator.getInstance();
    //    BufferBuilder bufferbuilder = tessellator.getBuffer();
    //    this.mc.getTextureManager().bindTexture(OPTIONS_BACKGROUND);
    //    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    //    float f = 32.0F;
    //    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
    //    bufferbuilder.pos(0.0D, this.height, 0.0D).tex(0.0D, (float)this.height / 32.0F + (float)tint).color(64, 64, 64, 255).endVertex();
    //    bufferbuilder.pos(this.width, this.height, 0.0D).tex((float)this.width / 32.0F, (float)this.height / 32.0F + (float)tint).color(64, 64, 64, 255).endVertex();
    //    bufferbuilder.pos(this.width, 0.0D, 0.0D).tex((float)this.width / 32.0F, tint).color(64, 64, 64, 255).endVertex();
    //    bufferbuilder.pos(0.0D, 0.0D, 0.0D).tex(0.0D, tint).color(64, 64, 64, 255).endVertex();
    //    tessellator.draw();
    //}

    @Override
    public void drawDefaultBackground() {
        int par1 = 0;
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        this.mc.getTextureManager().bindTexture(enderField);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      //  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 256.0F;
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(0.0D, this.height, 0.0D).tex(0.0D, (float)this.height / f + par1).color(64, 64, 64, 255).endVertex();
        buffer.pos(this.width, this.height, 0.0D).tex((float)this.width / f, (float)this.height / f + par1).color(64, 64, 64, 255).endVertex();
        buffer.pos(this.width, 0.0D, 0.0D).tex((float)this.width / f, par1).color(64, 64, 64, 255).endVertex();
        buffer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, par1).color(64, 64, 64, 255).endVertex();
        tessellator.draw();
    }
}