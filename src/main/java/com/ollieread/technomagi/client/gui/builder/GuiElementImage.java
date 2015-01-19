package com.ollieread.technomagi.client.gui.builder;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class GuiElementImage implements IGuiElement
{

    protected String name;
    protected IGuiElement parent;
    protected ResourceLocation image;
    protected int xOffset;
    protected int yOffset;
    protected int width;
    protected int height;

    public GuiElementImage(String name, IGuiElement parent, ResourceLocation image, int x, int y, int w, int h)
    {
        this.name = name;
        this.parent = parent;
        this.image = image;
        this.xOffset = x;
        this.yOffset = y;
        this.width = w;
        this.height = h;
    }

    @Override
    public void drawBackground(int xPadding, int yPadding)
    {

    }

    @Override
    public void draw(int xPadding, int yPadding)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        GuiBuilder.instance.drawImage(xOffset + xPadding, yOffset + yPadding, 0, 0, width, height, width, height);
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
        return false;
    }

    @Override
    public String clicked(int xPadding, int yPadding, int xPosition, int yPosition)
    {
        return null;
    }

}
