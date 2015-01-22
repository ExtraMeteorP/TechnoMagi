package com.ollieread.technomagi.client.gui.elements;

import java.util.Arrays;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class GuiElementProgressBar extends GuiElement
{

    protected int type;
    protected boolean vertical;
    protected int percentage;
    protected String hover = null;

    public GuiElementProgressBar(String name, IGuiElement parent, int x, int y, int w, int t, int p, boolean vertical)
    {
        this.name = name;
        this.parent = parent;
        this.xOffset = x;
        this.yOffset = y;
        this.width = w;
        this.type = t;
        this.percentage = p;
        this.vertical = vertical;
    }

    public GuiElementProgressBar setHover(String hover)
    {
        this.hover = hover;

        return this;
    }

    @Override
    public void drawBackground(GuiBuilder builder, int xPadding, int yPadding)
    {
        if (this.vertical) {
            builder.drawVerticalProgressBarBackground(this.xOffset + xPadding, this.yOffset + yPadding, this.width, this.percentage);
        } else {
            builder.drawHorizontalProgressBarBackground(this.xOffset + xPadding, this.yOffset + yPadding, this.width, this.percentage);
        }
    }

    @Override
    public void draw(GuiBuilder builder, int xPadding, int yPadding)
    {
        if (this.vertical) {
            builder.drawVerticalProgressBarForeground(this.xOffset + xPadding, this.yOffset + yPadding, this.width, this.type, this.percentage);
        } else {
            builder.drawHorizontalProgressBarForeground(this.xOffset + xPadding, this.yOffset + yPadding, this.width, this.type, this.percentage);
        }
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public IGuiElement getParent()
    {
        return parent;
    }

    @Override
    public boolean hover(GuiBuilder builder, int xPadding, int yPadding, int xPosition, int yPosition)
    {
        if (hover != null && !hover.isEmpty()) {
            int x = builder.xOffset + xOffset;
            int y = builder.yOffset + yOffset;

            if (this.type == 0) {
                y -= this.height;
            } else if (this.type == 3) {
                x -= this.width;
            }

            if (xPosition >= x && xPosition <= (x + this.width)) {
                if (yPosition >= y && yPosition <= (y + this.height)) {
                    builder.drawHoveringText(Arrays.asList(new String[] { hover }), xPosition - 37, yPosition - 22);

                    return true;
                }
            }
        }

        return false;
    }

}
