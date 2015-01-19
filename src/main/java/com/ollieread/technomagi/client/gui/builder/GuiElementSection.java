package com.ollieread.technomagi.client.gui.builder;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.GuiButton;

public class GuiElementSection implements IGuiElement
{

    protected String name;
    protected IGuiElement parent;
    protected int xOffset;
    protected int yOffset;
    public int width;
    protected int height;
    protected boolean background;
    protected Map<String, IGuiElement> elements = new HashMap<String, IGuiElement>();

    public GuiElementSection(String name, IGuiElement parent, int x, int y, int w, int h)
    {
        this(name, parent, x, y, w, h, false);
    }

    public GuiElementSection(String name, IGuiElement parent, int x, int y, int w, int h, boolean background)
    {
        this.name = name;
        this.parent = parent;
        this.xOffset = x;
        this.yOffset = y;
        this.width = w;
        this.height = h;
        this.background = background;
    }

    public void addElement(IGuiElement element)
    {
        elements.put(element.getName(), element);
    }

    public GuiButton addButtonCentered(String name, int y, int w, String s)
    {
        return addButton(name, ((background ? width - 6 : width) - w) / 2, y, w, s);
    }

    public GuiButton addButton(String name, int x, int y, String s)
    {
        return addButton(name, x, y, background ? width - 6 : width, s);
    }

    public GuiButton addButton(String name, int x, int y, int w, String s)
    {
        if (background) {
            if (w > (width - 6)) {
                w = width - 6;
            }
        } else {
            if (w > width) {
                w = width;
            }
        }

        IGuiElement button = new GuiElementButton(name, this, x, y, w, s);
        addElement(button);

        return (GuiButton) button;
    }

    @Override
    public void drawBackground(int xPadding, int yPadding)
    {
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;

        if (background) {
            GuiBuilder.instance.drawSectionBackground(x, y, width, height);
            x += 3;
            y += 3;
        }

        for (IGuiElement element : elements.values()) {
            element.drawBackground(x, y);
        }
    }

    @Override
    public void draw(int xPadding, int yPadding)
    {
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;

        if (background) {
            x += 3;
            y += 3;
        }

        for (IGuiElement element : elements.values()) {
            element.draw(x, y);
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
        int retHeight = height + (includeY ? yOffset : 0);

        for (IGuiElement element : elements.values()) {
            retHeight += element.getHeight(includeY);
        }

        return retHeight;
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
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;

        if (background) {
            x += 3;
            y += 3;
        }

        for (IGuiElement element : elements.values()) {
            if (element.hover(x, y, xPosition, yPosition)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String clicked(int xPadding, int yPadding, int xPosition, int yPosition)
    {
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;

        if (background) {
            x += 3;
            y += 3;
        }

        for (IGuiElement element : elements.values()) {
            String link = element.clicked(x, y, xPosition, yPosition);

            if (link != null && !link.isEmpty()) {
                return getName() + "/" + link;
            }
        }

        return null;
    }

}
