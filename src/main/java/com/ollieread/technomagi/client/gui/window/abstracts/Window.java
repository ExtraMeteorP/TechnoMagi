package com.ollieread.technomagi.client.gui.window.abstracts;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.client.gui.component.Component;
import com.ollieread.technomagi.client.gui.component.ComponentCollection;
import com.ollieread.technomagi.client.gui.component.ComponentHeading;

public abstract class Window extends ComponentCollection
{

    protected ComponentHeading heading;
    protected boolean background;

    public Window(int width, int height)
    {
        super(width, height);
    }

    public abstract void updateContent();

    public void setHeading(String text)
    {
        this.heading = new ComponentHeading(text, width - this.paddingX);
    }

    public void setBackground(boolean background)
    {
        this.background = background;
    }

    @Override
    public Component setPadding(int x, int y)
    {
        this.paddingX = x;
        this.paddingY = y;

        if (this.heading != null) {
            this.heading.setWidth(width - this.paddingX);
        }

        return this;
    }

    @Override
    public void draw(int x, int y)
    {
        if (background) {
            GuiBuilder.instance.drawBackground(x, y, this.getWidth(), this.getHeight());
        }

        x += offsetX + paddingX;
        y += offsetY + paddingY;

        if (this.heading != null) {
            this.heading.draw(x, y);
            y += this.heading.getHeight() + paddingY;
        }

        for (Component component : components.values()) {
            component.draw(x, y);
        }
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int clickedButton)
    {
        if (this.heading != null) {
            y += this.heading.getHeight() + paddingY;
        }

        return super.mouseClicked(x, y, mouseX, mouseY, clickedButton);
    }

    @Override
    public void mouseScrolled(int x, int y, int mouseX, int mouseY, int dwheel)
    {
        if (this.heading != null) {
            y += this.heading.getHeight() + paddingY;
        }

        super.mouseScrolled(x, y, mouseX, mouseY, dwheel);
    }

    @Override
    public void mouseHovered(int x, int y, int mouseX, int mouseY)
    {
        if (this.heading != null) {
            y += this.heading.getHeight() + paddingY;
        }

        super.mouseHovered(x, y, mouseX, mouseY);
    }

}
