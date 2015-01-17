package com.ollieread.technomagi.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.ContainerSeparator;
import com.ollieread.technomagi.tileentity.TileEntityMachineProcessor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSeparator extends GuiEnergyContainer
{

    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/separator.png");
    protected TileEntityMachineProcessor separator;

    public GuiSeparator(InventoryPlayer playerInventory, TileEntityMachineProcessor tile)
    {
        super(new ContainerSeparator(playerInventory, tile));

        separator = tile;

        xSize = 175;
        ySize = 145;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(I18n.format("technomagi.separator.gui"), 7, 9, 16777215);

        int progress = separator.getProgress();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        int progress = separator.getProgress(100);

        if (progress > 0) {
            this.drawTexturedModalRect(this.guiLeft + 27, this.guiTop + 35, 0, 176, progress, 3);
        }

        this.drawPowerLayer(separator, this.guiLeft + xSize, this.guiTop);
    }

}