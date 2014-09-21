package com.ollieread.technomagi.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

public class GuiTMTextButton extends GuiButton
{
    /** The x position of this control. */
    public int xPosition;
    /** The y position of this control. */
    public int yPosition;

    public int xOffset = 0;

    public int yOffset = 0;

    public GuiTMTextButton(int id, int x, int y, String s)
    {
        super(id, x, y, 200, 10, s);

        xPosition = x;
        yPosition = y;
    }

    public GuiTMTextButton(int id, int x, int y, int xOffset, int yOffset, String s)
    {
        super(id, xOffset + x, yOffset + y, 200, 10, s);

        this.xOffset = xOffset;
        this.yOffset = yOffset;

        xPosition = x;
        yPosition = y;
    }

    public void drawButton(Minecraft mc, int mx, int my)
    {
        if (this.visible) {
            width = mc.fontRenderer.getStringWidth(displayString) + 2;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.fontRenderer.drawString(displayString, xPosition + 1, this.yPosition + 1, 16777215);
        }
    }

}
