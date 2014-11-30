package com.ollieread.technomagi.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.ContainerFurnace;
import com.ollieread.technomagi.tileentity.TileEntityFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFurnace extends GuiEnergyContainer
{

    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/furnace.png");
    protected TileEntityFurnace furnace;
    protected GuiTMButton analyseButton;

    public GuiFurnace(InventoryPlayer playerInventory, TileEntityFurnace tile)
    {
        super(new ContainerFurnace(playerInventory, tile));

        furnace = tile;

        xSize = 175;
        ySize = 145;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(I18n.format("technomagi.furnace.gui"), 7, 9, 16777215);

        int progress = furnace.getProgress();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        int progress = furnace.getProgress();

        if (progress > 0) {
            this.drawTexturedModalRect(this.guiLeft + 38, this.guiTop + 35, 0, 176, progress, 3);
        }

        this.drawPowerLayer(furnace, this.guiLeft + xSize, this.guiTop);
    }

}