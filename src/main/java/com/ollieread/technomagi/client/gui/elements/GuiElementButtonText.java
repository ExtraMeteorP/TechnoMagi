package com.ollieread.technomagi.client.gui.elements;

import net.minecraft.client.gui.FontRenderer;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class GuiElementButtonText extends GuiElement
{

    protected String text;
    protected FontRenderer font;

    public GuiElementButtonText(String name, IGuiElement parent, int x, int y, String s, FontRenderer f)
    {
        this.xOffset = x;
        this.yOffset = y;
        this.font = f;
        this.width = font.getStringWidth(s);
        this.height = font.FONT_HEIGHT + 3;
        this.name = name;
        this.parent = parent;
        this.text = s;
    }

    @Override
    public void drawBackground(GuiBuilder builder, int xPadding, int yPadding)
    {

    }

    @Override
    public void draw(GuiBuilder builder, int xPadding, int yPadding)
    {
        boolean hover = false;
        int x = builder.xOffset + xOffset + xPadding;
        int y = builder.yOffset + yOffset + yPadding;

        int mx = builder.mouseX;
        int my = builder.mouseY;

        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                hover = true;
            }
        }

        builder.drawString(text, xOffset + xPadding, yOffset + yPadding + 3, !hover ? 16777215 : 2529246);
    }

    @Override
    public IGuiElement clicked(GuiBuilder builder, int xPadding, int yPadding, int xPosition, int yPosition)
    {
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;

        if (xPosition >= x && xPosition <= (x + this.width)) {
            if (yPosition >= y && yPosition <= (y + this.height)) {
                return this;
            }
        }

        return null;
    }

}
