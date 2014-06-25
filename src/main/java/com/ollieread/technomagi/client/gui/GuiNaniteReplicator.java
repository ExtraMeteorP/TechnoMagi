package com.ollieread.technomagi.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
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
    protected TileEntityNaniteReplicator replicator;

    public GuiNaniteReplicator(InventoryPlayer playerInventory, TileEntityNaniteReplicator tile)
    {
        super(new ContainerNaniteReplicator(playerInventory, tile));

        replicator = tile;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(I18n.format("technomagi.replicator.gui"), 7, 9, 16777215);

        int progress = replicator.getProgress();
        int type = replicator.getSampleType();
        String typeName = null;

        switch (type) {
            case 2:
                typeName = EnumChatFormatting.DARK_GREEN + "Cow";
                break;
            case 3:
                typeName = EnumChatFormatting.DARK_GREEN + "Sheep";
                break;
            case 4:
                typeName = EnumChatFormatting.DARK_GREEN + "Pig";
                break;
            case 5:
                typeName = EnumChatFormatting.DARK_GREEN + "Chicken";
                break;
            default:
                typeName = "--";
                break;
        }

        this.fontRendererObj.drawString(I18n.format("technomagi.progress"), 6, 53, 16777215);
        this.fontRendererObj.drawString(I18n.format("technomagi.type"), 6, 66, 16777215);

        this.fontRendererObj.drawString(progress + "%", 140 - this.fontRendererObj.getStringWidth(progress + "%"), 53, 4118771);
        this.fontRendererObj.drawString(typeName, 140 - this.fontRendererObj.getStringWidth(typeName), 66, 15944766);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        int nanites = replicator.getNanites();
        int sample = replicator.getSample();

        if (nanites > 0) {
            this.drawTexturedModalRect(this.guiLeft + 42, this.guiTop + 38, 0, 183, nanites, 3);
        }

        if (sample > 0) {
            this.drawTexturedModalRect(this.guiLeft + 42, this.guiTop + 27, 0, 177, sample, 6);
        }
    }

}