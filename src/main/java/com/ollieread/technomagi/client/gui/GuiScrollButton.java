package com.ollieread.technomagi.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.common.Reference;

public class GuiScrollButton extends GuiButton
{

    protected ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/overlay.png");
    protected int dir;

    public GuiScrollButton(int par1, int par2, int par3, int par4, int par5, int dir)
    {
        super(par1, par2, par3, par4, par5, "");
        this.dir = dir;
        enabled = false;
    }

    @Override
    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
    {
        if (enabled) {
            p_146112_1_.getTextureManager().bindTexture(this.texture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            int y;

            if (dir == 0) {
                y = 24;
            } else {
                y = 36;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, 37, y, this.width, this.height);
        }
    }
}
