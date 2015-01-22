package com.ollieread.technomagi.client.gui.elements;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class GuiElementSlot extends GuiElement
{

    public GuiElementSlot(String name, IGuiElement parent, int x, int y)
    {
        this.name = name;
        this.parent = parent;
        this.xOffset = x;
        this.yOffset = y;
        this.width = 20;
        this.height = 20;
    }

    @Override
    public void drawBackground(GuiBuilder builder, int xPadding, int yPadding)
    {
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;

        builder.drawTextureArea(x, y, 0, 90, this.width, this.height);
    }

    @Override
    public void draw(GuiBuilder builder, int xPadding, int yPadding)
    {

    }

}
