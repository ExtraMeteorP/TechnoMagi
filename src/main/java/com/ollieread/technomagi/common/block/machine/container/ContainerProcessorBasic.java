package com.ollieread.technomagi.common.block.machine.container;

import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.common.block.machine.tile.TileResourceProcessorBasic;
import com.ollieread.technomagi.common.inventory.SlotApplicable;
import com.ollieread.technomagi.util.ItemStackHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerProcessorBasic extends ContainerProcessor
{

    public int lastFuelTime;
    public int lastMaxFuelTime;

    public ContainerProcessorBasic(IInventory playerInventory, TileResourceProcessorBasic tile)
    {
        super(playerInventory, tile);

        addSlotToContainer(new Slot(tile, 0, 7, 30));
        addSlotToContainer(new Slot(tile, 1, 75, 55));
        addSlotToContainer(new Slot(tile, 2, 150, 30));
        addSlotToContainer(new Slot(tile, 3, 150, 55));
        addSlotToContainer(new SlotApplicable(tile, 4, 7, 55, ItemStackHelper.fuelSlot));

        addPlayerSlots(playerInventory, 8, 70);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting)
    {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 2, ((TileResourceProcessorBasic) tile).getFuelTime());
        crafting.sendProgressBarUpdate(this, 3, ((TileResourceProcessorBasic) tile).getMaxFuelTime());
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (lastFuelTime != ((TileResourceProcessorBasic) tile).getFuelTime()) {
                icrafting.sendProgressBarUpdate(this, 2, ((TileResourceProcessorBasic) tile).getFuelTime());
            }

            if (lastMaxFuelTime != ((TileResourceProcessorBasic) tile).getMaxFuelTime()) {
                icrafting.sendProgressBarUpdate(this, 3, ((TileResourceProcessorBasic) tile).getMaxFuelTime());
            }
        }

        lastFuelTime = ((TileResourceProcessorBasic) tile).getFuelTime();
        lastMaxFuelTime = ((TileResourceProcessorBasic) tile).getMaxFuelTime();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int i, int v)
    {
        super.updateProgressBar(i, v);

        if (i == 2) {
            ((TileResourceProcessorBasic) tile).setFuelTime(v);
            GuiBuilder.instance.currentWindow.updateContent();
        }

        if (i == 3) {
            ((TileResourceProcessorBasic) tile).setMaxFuelTime(v);
            GuiBuilder.instance.currentWindow.updateContent();
        }
    }

}
