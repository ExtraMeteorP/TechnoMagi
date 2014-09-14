package com.ollieread.technomagi.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.common.Reference;

public class GuiTMTab extends GuiButton
{
    protected static final ResourceLocation buttonTextures = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/overlay.png");
    /** Button width in pixels */
    protected int width;
    /** Button height in pixels */
    protected int height;
    /** The x position of this control. */
    public int xPosition;
    /** The y position of this control. */
    public int yPosition;

    public GuiTMTab(int par1, int par2, int par3)
    {
        super(par1, par2, par3, "");

        this.xPosition = par2;
        this.yPosition = par3;
        this.width = 26;
        this.height = 28;
    }

    @Override
    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
    {
        if (this.visible) {
            FontRenderer fontrenderer = p_146112_1_.fontRenderer;
            p_146112_1_.getTextureManager().bindTexture(buttonTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 174, 46, this.width, this.height);
            // this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 102
            // + k * 20, this.width / 2, this.height);
            // this.drawTexturedModalRect(this.xPosition + this.width / 2,
            // this.yPosition, 200 - this.width / 2, 102 + k * 20, this.width /
            // 2, this.height);
            this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
            int l = 14737632;

            if (packedFGColour != 0) {
                l = packedFGColour;
            } else if (!this.enabled) {
                l = 10526880;
            } else if (this.field_146123_n) {
                l = 16777120;
            }
        }
    }

}
