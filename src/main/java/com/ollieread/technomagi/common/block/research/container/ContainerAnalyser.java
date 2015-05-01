package com.ollieread.technomagi.common.block.research.container;

import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.common.block.research.tile.TileAnalyser;
import com.ollieread.technomagi.common.inventory.ContainerTechnomagi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerAnalyser extends ContainerTechnomagi
{

    public TileAnalyser tile;
    public int lastMaxProgress;
    public int lastProgress;
    public int lastData;
    public int lastMaxData;

    public ContainerAnalyser(IInventory playerInventory, TileAnalyser tile)
    {
        this.tile = tile;

        addSlotToContainer(new Slot(tile, 0, 7, 30));
        addSlotToContainer(new Slot(tile, 1, 137, 30));

        addPlayerSlots(playerInventory, 0, 45);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting)
    {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, tile.getMaxProgress());
        crafting.sendProgressBarUpdate(this, 1, tile.getProgress());
        crafting.sendProgressBarUpdate(this, 2, tile.getData());
        crafting.sendProgressBarUpdate(this, 3, tile.getMaxData());
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (lastMaxProgress != tile.getMaxProgress()) {
                icrafting.sendProgressBarUpdate(this, 0, tile.getMaxProgress());
            }

            if (lastProgress != tile.getProgress()) {
                icrafting.sendProgressBarUpdate(this, 1, tile.getProgress());
            }

            if (lastData != tile.getData()) {
                icrafting.sendProgressBarUpdate(this, 2, tile.getData());
            }

            if (lastMaxData != tile.getData()) {
                icrafting.sendProgressBarUpdate(this, 3, tile.getMaxData());
            }
        }

        lastMaxProgress = tile.getMaxProgress();
        lastProgress = tile.getProgress();
        lastData = tile.getData();
        lastMaxData = tile.getMaxData();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int i, int v)
    {
        if (i == 0) {
            tile.setMaxProgress(v);
            Technomagi.proxy.updateContent();
        }

        if (i == 1) {
            tile.setProgress(v);
            Technomagi.proxy.updateContent();
        }

        if (i == 2) {
            tile.setData(v);
            Technomagi.proxy.updateContent();
        }

        if (i == 3) {
            tile.setMaxData(v);
            Technomagi.proxy.updateContent();
        }
    }

}
