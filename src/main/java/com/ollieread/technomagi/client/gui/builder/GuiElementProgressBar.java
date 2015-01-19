package com.ollieread.technomagi.client.gui.builder;

public class GuiElementProgressBar implements IGuiElement
{

    protected String name;
    protected IGuiElement parent;
    protected int xOffset;
    protected int yOffset;
    protected int width;
    protected int type;
    protected boolean vertical;
    protected int percentage;

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

    @Override
    public void drawBackground(int xPadding, int yPadding)
    {
        if (this.vertical) {
            GuiBuilder.instance.drawVerticalProgressBarBackground(this.xOffset + xPadding, this.yOffset + yPadding, this.width, this.percentage);
        } else {
            GuiBuilder.instance.drawHorizontalProgressBarBackground(this.xOffset + xPadding, this.yOffset + yPadding, this.width, this.percentage);
        }
    }

    @Override
    public void draw(int xPadding, int yPadding)
    {
        if (this.vertical) {
            GuiBuilder.instance.drawVerticalProgressBarForeground(this.xOffset + xPadding, this.yOffset + yPadding, this.width, this.type, this.percentage);
        } else {
            GuiBuilder.instance.drawHorizontalProgressBarForeground(this.xOffset + xPadding, this.yOffset + yPadding, this.width, this.type, this.percentage);
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
    public int getHeight(boolean includeY)
    {
        return (this.vertical ? width : 5) + (includeY ? yOffset : 0);
    }

    @Override
    public int getMinX()
    {
        return xOffset;
    }

    @Override
    public int getMaxX()
    {
        return xOffset + (this.vertical ? 5 : width);
    }

    @Override
    public int getMinY()
    {
        return yOffset;
    }

    @Override
    public int getMaxY()
    {
        return yOffset + (this.vertical ? width : 5);
    }

    @Override
    public boolean hover(int xPadding, int yPadding, int xPosition, int yPosition)
    {
        return false;
    }

    @Override
    public String clicked(int xPadding, int yPadding, int xPosition, int yPosition)
    {
        return null;
    }

}
