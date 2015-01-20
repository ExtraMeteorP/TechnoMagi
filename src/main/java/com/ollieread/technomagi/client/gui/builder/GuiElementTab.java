package com.ollieread.technomagi.client.gui.builder;

import java.util.Arrays;

import net.minecraft.util.ResourceLocation;

public class GuiElementTab implements IGuiElement
{

    protected String name;
    protected IGuiElement parent;
    protected ResourceLocation image;
    protected int xOffset;
    protected int yOffset;
    protected int width;
    protected int height;
    protected int type;
    protected boolean selected;
    protected String hover = null;

    public GuiElementTab(String name, IGuiElement parent, ResourceLocation image, int x, int y, int t, boolean selected)
    {
        this.name = name;
        this.parent = parent;
        this.image = image;
        this.xOffset = x;
        this.yOffset = y;
        this.type = t;
        this.selected = selected;

        if (t == 0 || t == 1) {
            this.width = 28;
            this.height = 30;
        } else if (t == 2 || t == 3) {
            this.width = 30;
            this.height = 28;
        }
    }

    public void setHover(String hover)
    {
        this.hover = hover;
    }

    @Override
    public void drawBackground(int xPadding, int yPadding)
    {
    }

    @Override
    public void draw(int xPadding, int yPadding)
    {
        GuiBuilder.instance.drawTab(xOffset, yOffset, type, image, selected);
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
    public int getHeight(boolean includeY)
    {
        return height + (includeY ? yOffset : 0);
    }

    @Override
    public int getMinX()
    {
        return xOffset;
    }

    @Override
    public int getMaxX()
    {
        return xOffset + width;
    }

    @Override
    public int getMinY()
    {
        return yOffset;
    }

    @Override
    public int getMaxY()
    {
        return yOffset + height;
    }

    @Override
    public boolean hover(int xPadding, int yPadding, int xPosition, int yPosition)
    {
        if (hover != null && !hover.isEmpty()) {
            int x = GuiBuilder.instance.xOffset + xOffset;
            int y = GuiBuilder.instance.yOffset + yOffset;

            if (this.type == 0) {
                y -= this.height;
            } else if (this.type == 3) {
                x -= this.width;
            }

            if (xPosition >= x && xPosition <= (x + this.width)) {
                if (yPosition >= y && yPosition <= (y + this.height)) {
                    GuiBuilder.instance.drawHoveringText(Arrays.asList(new String[] { hover }), xPosition - 37, yPosition - 22);

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String clicked(int xPadding, int yPadding, int xPosition, int yPosition)
    {
        int x = GuiBuilder.instance.xOffset + xOffset;
        int y = GuiBuilder.instance.yOffset + yOffset;

        if (this.type == 0) {
            y -= this.height;
        } else if (this.type == 3) {
            x -= this.width;
        }

        if (xPosition >= x && xPosition <= (x + this.width)) {
            if (yPosition >= y && yPosition <= (y + this.height)) {
                return getName();
            }
        }

        return null;
    }
}
