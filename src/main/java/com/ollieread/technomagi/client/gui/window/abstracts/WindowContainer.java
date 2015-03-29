package com.ollieread.technomagi.client.gui.window.abstracts;

import net.minecraft.inventory.Container;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.client.gui.component.Component;

public abstract class WindowContainer extends Window
{

    protected Container container = null;
    protected boolean hasInventory = false;

    public WindowContainer(int width, int height)
    {
        super(width, height);
    }

    public WindowContainer setContainer(Container container)
    {
        this.container = container;
        return this;
    }

    public WindowContainer setHasInventory(boolean hasInventory)
    {
        this.hasInventory = hasInventory;
        return this;
    }

    @Override
    public void draw(int x, int y)
    {
        if (background) {
            GuiBuilder.instance.drawBackground(x, y, this.getWidth(), this.getHeight() - 93);
        }

        if (this.hasInventory) {
            int x2 = x;
            int y2 = y + (this.getHeight() - 90);

            if (this.getWidth() != 176) {
                x2 += ((this.getWidth() - 176) / 2);
            }

            builder.instance.bindTexture();
            builder.instance.drawTextureArea(x2, y2, 0, 0, 176, 90);
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
}
