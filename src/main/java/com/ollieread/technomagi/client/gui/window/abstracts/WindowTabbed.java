package com.ollieread.technomagi.client.gui.window.abstracts;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.client.gui.GuiBuilder.TabLocation;
import com.ollieread.technomagi.client.gui.component.Component;
import com.ollieread.technomagi.client.gui.component.ComponentTab;

public abstract class WindowTabbed extends Window
{

    protected TabLocation location;

    protected Map<String, ComponentTab> tabs = new LinkedHashMap<String, ComponentTab>();

    public WindowTabbed(int width, int height, TabLocation location)
    {
        super(width, height);

        this.location = location;
    }

    public void setActiveTab(String name)
    {
        for (String tab : tabs.keySet()) {
            if (tab.equals(name)) {
                tabs.get(tab).setActive(true);
            } else {
                tabs.get(tab).setActive(false);
            }
        }
    }

    @Override
    public void draw(int x, int y)
    {
        int ox = x + offsetX;
        int oy = y + offsetY;

        if (background) {
            GuiBuilder.instance.drawBackground(ox, oy, this.getWidth(), this.getHeight());
        }

        int tx = x;
        int ty = y;

        ox += paddingX;
        oy += paddingY;

        if (this.heading != null) {
            this.heading.draw(ox, oy);
            oy += this.heading.getHeight() + paddingY;
            ty += this.heading.getHeight() + paddingY;
        }

        if (location.equals(TabLocation.LEFT)) {
            tx -= 28;
        } else if (location.equals(TabLocation.RIGHT)) {
            tx += this.getWidth();
        } else if (location.equals(TabLocation.TOP)) {
            ty -= 28;
        } else {
            ty += this.getHeight();
        }

        for (ComponentTab tab : tabs.values()) {
            tab.draw(tx, ty);

            ty += tab.getHeight() + 3;
        }

        for (Component component : components.values()) {
            component.draw(ox, oy);
        }
    }

    public ComponentTab addTab(String name, ResourceLocation image)
    {
        ComponentTab tab = new ComponentTab(location);
        tab.setImage(image).setName(name).setParent(this);

        tabs.put(name, tab);

        return tab;
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int clickedButton)
    {
        int tx = x;
        int ty = y;

        if (this.heading != null) {
            ty += this.heading.getHeight() + paddingY;
        }

        if (location.equals(TabLocation.LEFT)) {
            tx -= 28;
        } else if (location.equals(TabLocation.RIGHT)) {
            tx += this.getWidth();
        } else if (location.equals(TabLocation.TOP)) {
            ty -= 28;
        } else {
            ty += this.getHeight();
        }

        for (ComponentTab tab : tabs.values()) {
            if (tab.mouseClicked(tx, ty, mouseX, mouseY, clickedButton)) {
                return true;
            }
            ty += tab.getHeight() + 3;
        }

        return super.mouseClicked(x, y, mouseX, mouseY, clickedButton);
    }

    @Override
    public void mouseHovered(int x, int y, int mouseX, int mouseY)
    {
        int tx = x;
        int ty = y;

        if (this.heading != null) {
            ty += this.heading.getHeight() + paddingY;
        }

        if (location.equals(TabLocation.LEFT)) {
            tx -= 28;
        } else if (location.equals(TabLocation.RIGHT)) {
            tx += this.getWidth();
        } else if (location.equals(TabLocation.TOP)) {
            ty -= 28;
        } else {
            ty += this.getHeight();
        }

        for (ComponentTab tab : tabs.values()) {
            tab.mouseHovered(tx, ty, mouseX, mouseY);
            ty += tab.getHeight() + 3;
        }

        super.mouseHovered(x, y, mouseX, mouseY);
    }

}
