package com.ollieread.technomagi.common.block.machine.container;

import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.common.block.machine.tile.TileResourceProcessorElectric;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerProcessorElectric extends ContainerProcessor
{

    public int lastEnergy;
    public int lastMaxEnergy;

    public ContainerProcessorElectric(IInventory playerInventory, TileResourceProcessorElectric tile)
    {
        super(playerInventory, tile);

        addSlotToContainer(new Slot(tile, 0, 7, 30));
        addSlotToContainer(new Slot(tile, 1, 7, 55));
        addSlotToContainer(new Slot(tile, 2, 150, 30));
        addSlotToContainer(new Slot(tile, 3, 150, 55));

        addPlayerSlots(playerInventory, 8, 70);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting)
    {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 2, ((TileResourceProcessorElectric) tile).getEnergyStored(null));
        crafting.sendProgressBarUpdate(this, 3, ((TileResourceProcessorElectric) tile).getMaxEnergyStored(null));
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (lastEnergy != ((TileResourceProcessorElectric) tile).getEnergyStored(null)) {
                icrafting.sendProgressBarUpdate(this, 2, ((TileResourceProcessorElectric) tile).getEnergyStored(null));
            }

            if (lastMaxEnergy != ((TileResourceProcessorElectric) tile).getMaxEnergyStored(null)) {
                icrafting.sendProgressBarUpdate(this, 3, ((TileResourceProcessorElectric) tile).getMaxEnergyStored(null));
            }
        }

        lastEnergy = ((TileResourceProcessorElectric) tile).getEnergyStored(null);
        lastMaxEnergy = ((TileResourceProcessorElectric) tile).getMaxEnergyStored(null);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int i, int v)
    {
        super.updateProgressBar(i, v);

        if (i == 2) {
            ((TileResourceProcessorElectric) tile).setEnergyStored(v);
            GuiBuilder.instance.currentWindow.updateContent();
        }

        if (i == 3) {
            ((TileResourceProcessorElectric) tile).setCapacity(v);
            GuiBuilder.instance.currentWindow.updateContent();
        }
    }
}
