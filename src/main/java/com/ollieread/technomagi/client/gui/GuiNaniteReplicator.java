package com.ollieread.technomagi.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.ContainerNaniteReplicator;
import com.ollieread.technomagi.tileentity.TileEntityNaniteReplicator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiNaniteReplicator extends GuiContainer
{

    protected int xSize = 185;
    protected int ySize = 177;
    protected int xOffset;
    protected int yOffset;
    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/replicator.png");

    public GuiNaniteReplicator(InventoryPlayer playerInventory, TileEntityNaniteReplicator tile)
    {
        super(new ContainerNaniteReplicator(playerInventory, tile));
    }

    public void initGui()
    {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(I18n.format("technomagi.replicator.gui"), 7, 9, 16777215);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

}