package com.ollieread.technomagi.client.gui.builder;

import net.minecraft.entity.EntityLivingBase;

public class GuiElementEntity implements IGuiElement
{

    protected String name;
    protected IGuiElement parent;
    protected EntityLivingBase entity;
    protected int xOffset;
    protected int yOffset;
    protected int scale;

    public GuiElementEntity(String name, IGuiElement parent, EntityLivingBase entity, int x, int y, int s)
    {
        this.name = name;
        this.parent = parent;
        this.entity = entity;
        this.xOffset = x;
        this.yOffset = y;
        this.scale = s;
    }

    @Override
    public void drawBackground(int xPadding, int yPadding)
    {

    }

    @Override
    public void draw(int xPadding, int yPadding)
    {
        GuiBuilder.instance.drawEntity(xPadding + xOffset, yPadding + yOffset, scale, entity);
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
    public int getMinX()
    {
        return xOffset;
    }

    @Override
    public int getMaxX()
    {
        return xOffset + 50;
    }

    @Override
    public int getMinY()
    {
        return yOffset;
    }

    @Override
    public int getMaxY()
    {
        return yOffset + 50;
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

    @Override
    public int getHeight(boolean includeY)
    {
        return 0 + (includeY ? yOffset : 0);
    }

}
