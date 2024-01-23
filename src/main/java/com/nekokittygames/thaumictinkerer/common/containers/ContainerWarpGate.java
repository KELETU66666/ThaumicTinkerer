/**
 * This class was created by <Vazkii>. It's distributed as part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0
 * License (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4. Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Jan 10, 2014, 5:45:28 PM (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.containers;

import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.tileentity.Kami.TileWarpGate;
import com.nekokittygames.thaumictinkerer.common.utils.ContainerPlayerInv;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWarpGate extends ContainerPlayerInv {

    TileWarpGate gate;

    public ContainerWarpGate(TileWarpGate gate, InventoryPlayer playerInv) {
        super(playerInv);
        this.gate = gate;

        for (int y = 0; y < 2; y++)
            for (int x = 0; x < 5; x++) addSlotToContainer(new SlotSkyPearl(gate, y * 5 + x, 30 + x * 25, 27 + y * 25));

        initPlayerInv();
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return gate.isUsableByPlayer(entityplayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = ItemStack.EMPTY;
        Slot var4 = inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();

            if (par2 < 10 || var5 != ItemStack.EMPTY) {
                var3 = var5.copy();

                if (par2 < 10) {
                    if (!mergeItemStack(var5, 10, 36, false)) return ItemStack.EMPTY;
                } else if (var3.getItem() == ModItems.sky_pearl
                        && !mergeItemStack(var5, 0, 10, false))
                    return ItemStack.EMPTY;

                if (var5.getCount() == 0) var4.putStack(ItemStack.EMPTY);
                else var4.onSlotChanged();

                if (var5.getCount() == var3.getCount()) return ItemStack.EMPTY;

                var4.onTake(par1EntityPlayer, var5);
            }
        }

        return var3;
    }
}