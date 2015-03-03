package com.ollieread.technomagi.client.gui.layout;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.client.gui.component.Component;
import com.ollieread.technomagi.client.gui.component.ComponentText;

public class Layout
{

    protected Map<String, Component> components = new LinkedHashMap<String, Component>();

    protected int paddingX = 0;
    protected int paddingY = 0;
    protected boolean background;
    protected int width;
    protected int height;
    protected int offsetX;
    protected int offsetY;

    public Layout(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public Component addComponent(String name, Component component)
    {
        components.put(name, component);
        component.setName(name);

        return component;
    }

    public Component addText(String name, String content)
    {
        int width = this.width - (paddingX * 2);

        ComponentText component = new ComponentText(width, GuiBuilder.instance.mc.fontRenderer);
        component.setContent(content);

        return component;
    }

    public void clearComponents()
    {
        components.clear();
    }

    public void removeComponent(String name)
    {
        if (components.containsKey(name)) {
            components.remove(name);
        }
    }

    public Component getComponent(String name)
    {
        if (components.containsKey(name)) {
            return components.get(name);
        }

        return null;
    }

    public Layout setPadding(int x, int y)
    {
        this.paddingX = x;
        this.paddingY = y;

        return this;
    }

    public Layout setBackground(boolean background)
    {
        this.background = background;
        return this;
    }

    public void draw(int x, int y, int scale)
    {
        for (Component component : components.values()) {
            component.draw(x, y);
        }
    }

}
