package com.ollieread.technomagi.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiArchive extends GuiScreen
{

    protected int xSize = 325;
    protected int ySize = 209;
    protected int xOffset;
    protected int yOffset;
    private static final ResourceLocation texture1 = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive1.png");
    private static final ResourceLocation texture2 = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive2.png");

    public void initGui()
    {
        this.buttonList.clear();
        this.xOffset = (this.width - this.xSize) / 2;
        this.yOffset = (this.height - this.ySize) / 2;

        this.buttonList.add(new GuiListButton(1, this.xOffset + 6, this.yOffset + 41, "Button Text"));
        this.buttonList.add(new GuiListButton(1, this.xOffset + 6, this.yOffset + 56, "Button Text"));
        this.buttonList.add(new GuiListButton(1, this.xOffset + 6, this.yOffset + 71, "Button Text"));
        this.buttonList.add(new GuiListButton(1, this.xOffset + 6, this.yOffset + 86, "Button Text"));
        this.buttonList.add(new GuiListButton(1, this.xOffset + 6, this.yOffset + 101, "Button Text"));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture1);
        this.drawTexturedModalRect(this.xOffset, this.yOffset, 0, 0, 132, this.ySize);
        this.mc.getTextureManager().bindTexture(texture2);
        this.drawTexturedModalRect(this.xOffset + 135, this.yOffset, 0, 0, 190, this.ySize);

        this.fontRendererObj.drawString(I18n.format("technomagi.archive.gui"), this.xOffset + 7, this.yOffset + 9, 16777215);

        this.fontRendererObj.FONT_HEIGHT = 7;
        this.fontRendererObj.drawString(I18n.format("technomagi.archive.entries"), this.xOffset + 11, this.yOffset + 30, 16777215);

        super.drawScreen(par1, par2, par3);
    }

    private void drawGui()
    {

    }

    protected void actionPerformed(GuiButton button)
    {
        ((GuiListButton) button).selected = true;
    }

    public void onGuiClosed()
    {

    }

}