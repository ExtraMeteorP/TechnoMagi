package com.ollieread.technomagi.client.gui.builder;

public class GuiElementButtonDirectional implements IGuiElement
{

    protected String name;
    protected IGuiElement parent;
    protected int id;
    protected int xOffset;
    protected int yOffset;
    protected int width;
    protected int height;
    protected int direction;
    protected boolean disabled = false;
    protected boolean invisible = false;

    public GuiElementButtonDirectional(String name, IGuiElement parent, int x, int y, int d)
    {
        this.id = GuiBuilder.instance.getButtonId();
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
    public void draw(int xPadding, int yPadding)
    {
    }

    @Override
    public void drawBackground(int xPadding, int yPadding)
    {
        if (!invisible) {
            int x = xOffset + xPadding;
            int y = yOffset + yPadding;
            int u = direction == 0 || direction == 1 ? 176 : 187;
            int v = direction == 0 || direction == 2 ? 0 : direction == 1 ? 7 : 11;

            GuiBuilder.instance.drawTextureArea(xOffset + xPadding, yOffset + yPadding, u, v, this.width, this.height);
        }

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
        if (!invisible && !disabled) {
            int x = xPadding + xOffset;
            int y = yPadding + yOffset;

            if (xPosition >= x && xPosition <= (x + this.width)) {
                if (yPosition >= y && yPosition <= (y + this.height)) {
                    if (parent instanceof GuiElementSectionScrollable) {
                        if (direction == 0) {
                            ((GuiElementSectionScrollable) parent).scrollDown();
                        } else if (direction == 1) {
                            ((GuiElementSectionScrollable) parent).scrollUp();
                        }
                    }
                }
            }
        }

        return null;
    }

}
