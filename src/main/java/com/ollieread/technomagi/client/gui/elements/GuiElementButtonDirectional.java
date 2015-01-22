package com.ollieread.technomagi.client.gui.elements;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class GuiElementButtonDirectional extends GuiElement
{

    protected int direction;
    protected boolean disabled = false;
    protected boolean invisible = false;

    public GuiElementButtonDirectional(String name, IGuiElement parent, int x, int y, int d)
    {
        this.xOffset = x;
        this.yOffset = y;
        this.direction = d;
        this.width = d == 0 || d == 1 ? 11 : 7;
        this.height = d == 0 || d == 1 ? 7 : 11;
        this.name = name;
        this.parent = parent;
    }

    @Override
    public String getName()
    {
        return name;
    }

    public void setDisabled()
    {
        disabled = true;
    }

    public void setInvisible()
    {
        invisible = true;
    }

    @Override
    public void draw(GuiBuilder builder, int xPadding, int yPadding)
    {
    }

    @Override
    public void drawBackground(GuiBuilder builder, int xPadding, int yPadding)
    {
        if (!invisible) {
            int x = xOffset + xPadding;
            int y = yOffset + yPadding;
            int u = direction == 0 || direction == 1 ? 176 : 187;
            int v = direction == 0 || direction == 2 ? 0 : direction == 1 ? 7 : 11;

            builder.drawTextureArea(xOffset + xPadding, yOffset + yPadding, u, v, this.width, this.height);
        }
    }

    @Override
    public IGuiElement clicked(GuiBuilder builder, int xPadding, int yPadding, int xPosition, int yPosition)
    {
        if (!invisible && !disabled) {
            int x = xPadding + xOffset;
            int y = yPadding + yOffset;

            if (xPosition >= x && xPosition <= (x + this.width)) {
                if (yPosition >= y && yPosition <= (y + this.height)) {
                    return this;
                }
            }
        }

        return null;
    }

    @Override
    public void setLink(String link)
    {

    }

    @Override
    public String getLink()
    {
        return getName();
    }

}
