package com.ollieread.technomagi.common.block.machine.container;

import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.common.block.machine.tile.TileCultivator;
import com.ollieread.technomagi.common.inventory.ContainerTechnomagi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerCultivator extends ContainerTechnomagi
{

    public TileCultivator tile;
    public int lastCurrentNanites = 0;
    public int lastCurrentSample = 0;
    public int lastCurrentCultivated = 0;

    public ContainerCultivator(IInventory playerInventory, TileCultivator tile)
    {
        this.tile = tile;

        addSlotToContainer(new Slot(tile, 0, 7, 30));
        addSlotToContainer(new Slot(tile, 1, 7, 55));
        addSlotToContainer(new Slot(tile, 2, 7, 80));
        addSlotToContainer(new Slot(tile, 3, 137, 80));

        addPlayerSlots(playerInventory, 0, 95);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting)
    {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, tile.getNanites());
        crafting.sendProgressBarUpdate(this, 1, tile.getSample());
        crafting.sendProgressBarUpdate(this, 2, tile.getCultivated());
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (lastCurrentNanites != tile.getNanites()) {
                icrafting.sendProgressBarUpdate(this, 0, tile.getNanites());
            }

            if (lastCurrentSample != tile.getSample()) {
                icrafting.sendProgressBarUpdate(this, 1, tile.getSample());
            }

            if (lastCurrentCultivated != tile.getCultivated()) {
                icrafting.sendProgressBarUpdate(this, 2, tile.getCultivated());
            }
        }

        lastCurrentNanites = tile.getNanites();
        lastCurrentSample = tile.getSample();
        lastCurrentCultivated = tile.getCultivated();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int i, int v)
    {
        super.updateProgressBar(i, v);

        if (i == 0) {
            tile.setNanites(v);
            GuiBuilder.instance.currentWindow.updateContent();
        }

        if (i == 1) {
            tile.setSample(v);
            GuiBuilder.instance.currentWindow.updateContent();
        }

        if (i == 2) {
            tile.setCultivated(v);
            GuiBuilder.instance.currentWindow.updateContent();
        }
    }

}
