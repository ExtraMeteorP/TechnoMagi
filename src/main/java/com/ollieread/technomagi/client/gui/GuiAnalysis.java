package com.ollieread.technomagi.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.ContainerAnalysis;
import com.ollieread.technomagi.tileentity.TileEntityMachineAnalysis;
import com.ollieread.technomagi.util.PacketHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAnalysis extends GuiEnergyContainer
{

    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/analysis.png");
    protected TileEntityMachineAnalysis analysis;
    protected GuiTMButton analyseButton;

    public GuiAnalysis(InventoryPlayer playerInventory, TileEntityMachineAnalysis tile)
    {
        super(new ContainerAnalysis(playerInventory, tile));

        analysis = tile;

        xSize = 175;
        ySize = 176;
    }

    public void initGui()
    {
        super.initGui();

        analyseButton = new GuiTMButton(1, this.guiLeft + 65, this.guiTop + 24, 102, 15, I18n.format("technomagi.analyse.button"));
        this.buttonList.add(analyseButton);

        if (analysis.inProgress() || !analysis.canProcess()) {
            analyseButton.enabled = false;
        } else if (!analysis.inProgress() && analysis.canProcess()) {
            analyseButton.enabled = true;
        }
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(I18n.format("technomagi.analysis.gui"), 7, 9, 16777215);

        int progress = analysis.getProgress(100);
        int data = analysis.getData();

        this.fontRendererObj.drawString(I18n.format("technomagi.progress"), 65, 44, 16777215);
        this.fontRendererObj.drawString(I18n.format("technomagi.data"), 65, 62, 16777215);

        this.fontRendererObj.drawString(progress + "%", 165 - this.fontRendererObj.getStringWidth(progress + "%"), 44, 4118771);
        this.fontRendererObj.drawString(data + "%", 165 - this.fontRendererObj.getStringWidth(data + "%"), 62, 15944766);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        int progress = analysis.getProgress(100);
        int data = analysis.getData();

        if (progress > 0) {
            this.drawTexturedModalRect(this.guiLeft + 65, this.guiTop + 55, 0, 176, progress, 3);
        }

        if (data > 0) {
            this.drawTexturedModalRect(this.guiLeft + 65, this.guiTop + 73, 0, 179, data, 3);
        }

        this.drawPowerLayer(analysis, this.guiLeft + xSize, this.guiTop);
    }

    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1) {
            analysis.setInProgress(true);
            button.enabled = false;
            PacketHelper.setProgress(analysis, true);
        }
    }

    public void updateScreen()
    {
        if (analysis.inProgress() || !analysis.canProcess()) {
            analyseButton.enabled = false;
        } else if (!analysis.inProgress() && analysis.canProcess()) {
            analyseButton.enabled = true;
        }
    }

}