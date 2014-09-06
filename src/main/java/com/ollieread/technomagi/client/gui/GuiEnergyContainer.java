package com.ollieread.technomagi.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import cofh.api.energy.IEnergyStorage;

import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiEnergyContainer extends GuiContainer
{

    private static final ResourceLocation overlay = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/overlay.png");

    public GuiEnergyContainer(Container container)
    {
        super(container);
    }

    public void drawPowerLayer(TileEntity tile, int x, int y)
    {
        if (tile != null && tile instanceof IEnergyStorage) {
            IEnergyStorage handler = (IEnergyStorage) tile;

            int capacity = handler.getMaxEnergyStored();
            int stored = handler.getEnergyStored();
            int total = 0;

            this.mc.getTextureManager().bindTexture(overlay);

            this.drawTexturedModalRect(x + 2, y + 3, 151, 0, 5, 77);

            if (stored == capacity) {
                total = 75;
            } else {
                total = (int) Math.ceil(stored / (capacity / 75));
            }

            this.drawTexturedModalRect(x + 3, y + 4 + (75 - total), 157, 1, 3, total);
        }
    }

}
