package com.ollieread.technomagi.client.gui.component;

import java.util.List;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class Component
{

    public static enum ComponentAlignment {
        LEFT, CENTER, RIGHT
    }

    public static enum ComponentSize {
        SMALL, MEDIUM, LARGE
    }

    public static enum ComponentOrientation {
        HORIZONTAL, VERTICAL
    }

    protected GuiBuilder builder = GuiBuilder.instance;

    protected String name;

    protected int width;
    protected int height;

    protected int offsetX;
    protected int offsetY;

    public int paddingX = 0;
    public int paddingY = 0;

    protected Component parent;
    protected ComponentAlignment alignment;
    protected IClickHandler handler;

    protected List<String> tooltip = null;

    public Component(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public Component setName(String name)
    {
        this.name = name;
        return this;
    }

    public Component setOffset(int x, int y)
    {
        this.offsetX = x;
        this.offsetY = y;

        return this;
    }

    public Component setAlignment(ComponentAlignment alignment)
    {
        this.alignment = alignment;
        return this;
    }

    public Component setWidth(int width)
    {
        this.width = width;
        return this;
    }

    public Component setClickHandler(IClickHandler handler)
    {
        this.handler = handler;
        return this;
    }

    public Component setParent(Component parent)
    {
        this.parent = parent;
        return this;
    }

    public Component setPadding(int x, int y)
    {
        this.paddingX = x;
        this.paddingY = y;

        return this;
    }

    public Component getParent()
    {
        return this.parent;
    }

    public Component setTooltip(List<String> tooltip)
    {
        this.tooltip = tooltip;
        return this;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public int getOffsetX()
    {
        return this.offsetX;
    }

    public int getOffsetY()
    {
        return this.offsetY;
    }

    public int getMaxX()
    {
        return this.offsetX + this.width + (this.paddingX * 2);
    }

    public int getMaxY()
    {
        return this.offsetY + this.height + (this.paddingY * 2);
    }

    public IClickHandler getHandler()
    {
        return this.handler;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean isHovered(int x, int y, int mouseX, int mouseY)
    {
        return isInsideRegion(mouseX, mouseY, x, y, x + getWidth(), y + getHeight());
    }

    public boolean isClicked(int x, int y, int mouseX, int mouseY, int clickedButton)
    {
        return false;
    }

    public boolean isInsideRegion(int x, int y, int minX, int minY, int maxX, int maxY)
    {
        return x > minX && y > minY && x < maxX && y < maxY;
    }

    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int clickedButton)
    {
        x += offsetX + paddingX;
        y += offsetY + paddingY;

        if (isClicked(x, y, mouseX, mouseY, clickedButton)) {
            if (getHandler() != null) {
                getHandler().onClick(this);
                return true;
            }
        }

        return false;
    }

    public void mouseHovered(int x, int y, int mouseX, int mouseY)
    {
        x += offsetX + paddingX;
        y += offsetY + paddingY;

        if (isHovered(x, y, mouseX, mouseY)) {
            if (tooltip != null) {
                GuiBuilder.instance.drawHoveringText(tooltip, mouseX, mouseY);
            }
        }
    }

    public void mouseScrolled(int x, int y, int mouseX, int mouseY, int dwheel)
    {
        x += offsetX + paddingX;
        y += offsetY + paddingY;

        if (isHovered(x, y, mouseX, mouseY)) {
            scroll(dwheel);
        }
    }

    public void scroll(int dwheel)
    {
    }

    public void draw(int x, int y)
    {
    }

}
