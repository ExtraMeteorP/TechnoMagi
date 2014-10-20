package com.ollieread.technomagi.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import cofh.api.energy.IEnergyHandler;

import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiEnergyContainer extends GuiContainer
{

    private static final ResourceLocation overlay = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/overlay.png");
    protected int mouseX;
    protected int mouseY;

    public GuiEnergyContainer(Container container)
    {
        super(container);
    }

    public void drawPowerLayer(TileEntity tile, int x, int y)
    {
        if (tile != null && tile instanceof IEnergyHandler) {
            IEnergyHandler handler = (IEnergyHandler) tile;

            int capacity = handler.getMaxEnergyStored(null);
            int stored = handler.getEnergyStored(null);
            int total = 0;

            this.mc.getTextureManager().bindTexture(overlay);

            this.drawTexturedModalRect(x + 2, y + 3, 151, 0, 5, 77);

            if (stored == capacity) {
                total = 75;
            } else {
                total = (int) Math.ceil(stored / (capacity / 75));
            }

            int k = (this.width - this.xSize) / 2; // X asis on GUI
            int l = (this.height - this.ySize) / 2; // Y asis on GUI

            this.drawTexturedModalRect(x + 3, y + 4 + (75 - total), 157, 1, 3, total);

            if (mouseX >= x + 3 && mouseX <= x + 6) {
                if (mouseY >= y + 4 && mouseY <= y + 79) {
                    List text = new ArrayList();
                    text.add(stored + " / " + capacity + " RF");

                    this.drawHoveringText(text, mouseX, mouseY, this.fontRendererObj);
                }
            }
        }
    }

    public void drawScreen(int par1, int par2, float par3)
    {
        this.mouseX = par1;
        this.mouseY = par2;
        super.drawScreen(par1, par2, par3);
    }

}
