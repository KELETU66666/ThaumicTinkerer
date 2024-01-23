/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Jan 10, 2014, 5:43:53 PM (GMT)]
 */
package com.nekokittygames.thaumictinkerer.client.gui;

import com.nekokittygames.thaumictinkerer.common.containers.ContainerWarpGate;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.packets.PacketHandler;
import com.nekokittygames.thaumictinkerer.common.packets.PacketWarpGateButton;
import com.nekokittygames.thaumictinkerer.common.tileentity.Kami.TileWarpGate;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.opengl.GL11;

public class GuiWarpGate extends GuiContainer {

    private static final ResourceLocation gui = new ResourceLocation(LibMisc.MOD_ID, "textures/misc/warp_gate.png");

    TileWarpGate warpGate;
    int x, y;

    public GuiWarpGate(TileWarpGate warpGate, InventoryPlayer inv) {
        super(new ContainerWarpGate(warpGate, inv));
        this.warpGate = warpGate;
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        ((GuiButtonWG) par1GuiButton).enabled = !((GuiButtonWG) par1GuiButton).enabled;
        warpGate.locked = ((GuiButtonWG) par1GuiButton).enabled;

        PacketHandler.INSTANCE.sendToServer(new PacketWarpGateButton(warpGate));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void initGui() {
        super.initGui();
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        buttonList.clear();
        buttonList.add(new GuiButtonWG(0, x + 5, y + 5, warpGate.locked));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(gui);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        fontRenderer.drawStringWithShadow(I18n.translateToLocal("ttmisc.lockedGate"), x + 20, y + 7, 0x999999);
    }
}