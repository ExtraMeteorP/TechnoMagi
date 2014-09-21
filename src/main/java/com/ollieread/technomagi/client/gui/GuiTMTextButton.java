package com.ollieread.technomagi.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

public class GuiTMTextButton extends GuiButton
{

    public GuiTMTextButton(int id, int x, int y, String s)
    {
        super(id, x, y, 200, 10, s);
    }

    public void drawButton(Minecraft mc, int mx, int my)
    {
        if (this.visible) {
            width = mc.fontRenderer.getStringWidth(displayString) + 2;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawString(mc.fontRenderer, displayString, xPosition + 1, this.yPosition + 1, 16777215);
        }
    }

}
