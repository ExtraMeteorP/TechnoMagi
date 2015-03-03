package com.ollieread.technomagi.client.gui.content.element;

import com.ollieread.technomagi.client.gui.component.Component.ComponentAlignment;

public abstract class Element
{

    public String name;
    public ComponentAlignment alignment = ComponentAlignment.LEFT;

    public int width;
    public int height;

    public int offsetX;
    public int offsetY;

    public int paddingX = 0;
    public int paddingY = 0;

    public Element(String name)
    {
        this.name = name;
    }

    public Element setAlignment(ComponentAlignment alignment)
    {
        this.alignment = alignment;
        return this;
    }

    public Element setOffset(int x, int y)
    {
        this.offsetX = x;
        this.offsetY = y;

        return this;
    }

    public Element setPadding(int x, int y)
    {
        this.paddingX = x;
        this.paddingY = y;

        return this;
    }

}
