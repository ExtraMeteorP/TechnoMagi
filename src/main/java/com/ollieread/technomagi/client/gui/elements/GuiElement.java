package com.ollieread.technomagi.client.gui.elements;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public abstract class GuiElement implements IGuiElement
{

    protected String name;
    protected IGuiElement parent;
    protected int xOffset;
    protected int yOffset;
    protected int width;
    protected int height;
    protected String link;

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
        return false;
    }

    @Override
    public IGuiElement clicked(GuiBuilder builder, int xPadding, int yPadding, int xPosition, int yPosition)
    {
        return null;
    }

    @Override
    public void setWidth(int width)
    {
        this.width = width;
    }

    @Override
    public void setHeight(int height)
    {
        this.height = height;
    }

    @Override
    public int getWidth()
    {
        return this.width;
    }

    @Override
    public int getHeight()
    {
        return this.height;
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
    public String getLink()
    {
        return link;
    }

    @Override
    public void setLink(String link)
    {
        this.link = link;
    }

}
