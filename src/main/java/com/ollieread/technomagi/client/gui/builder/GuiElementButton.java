package com.ollieread.technomagi.client.gui.builder;

public class GuiElementButton implements IGuiElement
{

    protected String name;
    protected IGuiElement parent;
    protected int id;
    protected int xOffset;
    protected int yOffset;
    protected int width;
    protected int height;
    protected String text;

    public GuiElementButton(String name, IGuiElement parent, int x, int y, int w, String s)
    {
        this.id = GuiBuilder.instance.getButtonId();
        this.xOffset = x;
        this.yOffset = y;
        this.width = w;
        this.height = 20;
        this.name = name;
        this.parent = parent;
        this.text = s;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void draw(int xPadding, int yPadding)
    {
        boolean hover = false;
        int x = GuiBuilder.instance.xOffset + xOffset + xPadding;
        int y = GuiBuilder.instance.yOffset + yOffset + yPadding;

        int mx = GuiBuilder.instance.mouseX;
        int my = GuiBuilder.instance.mouseY;

        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                hover = true;
            }
        }

        GuiBuilder.instance.drawButton(text, xOffset + xPadding, yOffset + yPadding, width, hover);
    }

    @Override
    public void drawBackground(int xPadding, int yPadding)
    {

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
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;

        if (xPosition >= x && xPosition <= (x + this.width)) {
            if (yPosition >= y && yPosition <= (y + this.height)) {
                return getName();
            }
        }

        return null;
    }

}
