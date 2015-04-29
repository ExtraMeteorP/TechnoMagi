package com.ollieread.technomagi.common.block.machine.container;

import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.common.block.machine.tile.TileCultivatorElectric;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerCultivatorElectric extends ContainerCultivator
{

    public int lastEnergy = 0;
    public int lastMaxEnergy = 0;

    public ContainerCultivatorElectric(IInventory playerInventory, TileCultivatorElectric tile)
    {
        super(playerInventory, tile);

        addPlayerSlots(playerInventory, 5, 95);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting)
    {
        super.addCraftingToCrafters(crafting);

        crafting.sendProgressBarUpdate(this, 3, ((TileCultivatorElectric) tile).getEnergyStored(null));
        crafting.sendProgressBarUpdate(this, 4, ((TileCultivatorElectric) tile).getMaxEnergyStored(null));
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (lastEnergy != ((TileCultivatorElectric) tile).getEnergyStored(null)) {
                icrafting.sendProgressBarUpdate(this, 3, ((TileCultivatorElectric) tile).getEnergyStored(null));
            }

            if (lastMaxEnergy != tile.getSample()) {
                icrafting.sendProgressBarUpdate(this, 4, ((TileCultivatorElectric) tile).getMaxEnergyStored(null));
            }
        }

        lastEnergy = ((TileCultivatorElectric) tile).getEnergyStored(null);
        lastMaxEnergy = ((TileCultivatorElectric) tile).getMaxEnergyStored(null);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int i, int v)
    {
        super.updateProgressBar(i, v);

        if (i == 3) {
            ((TileCultivatorElectric) tile).setEnergyStored(v);
            Technomagi.proxy.updateContent();
        }

        if (i == 4) {
            ((TileCultivatorElectric) tile).setCapacity(v);
            Technomagi.proxy.updateContent();
        }
    }

}
