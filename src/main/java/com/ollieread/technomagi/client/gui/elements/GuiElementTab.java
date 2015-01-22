package com.ollieread.technomagi.client.gui.elements;

import java.util.Arrays;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class GuiElementTab extends GuiElement
{

    protected ResourceLocation image;
    protected int type;
    protected boolean selected;
    protected String hover = null;

    public GuiElementTab(String name, IGuiElement parent, ResourceLocation image, int x, int y, int t, boolean selected)
    {
        this.name = name;
        this.parent = parent;
        this.image = image;
        this.xOffset = x;
        this.yOffset = y;
        this.type = t;
        this.selected = selected;

        if (t == 0 || t == 1) {
            this.width = 28;
            this.height = 30;
        } else if (t == 2 || t == 3) {
            this.width = 30;
            this.height = 28;
        }
    }

    public GuiElementTab setHover(String hover)
    {
        this.hover = hover;

        return this;
    }

    @Override
    public void drawBackground(GuiBuilder builder, int xPadding, int yPadding)
    {
        builder.drawTab(xOffset, yOffset, type, image, selected);
    }

    @Override
    public void draw(GuiBuilder builder, int xPadding, int yPadding)
    {
    }

    @Override
    public boolean hover(GuiBuilder builder, int xPadding, int yPadding, int xPosition, int yPosition)
    {
        if (hover != null && !hover.isEmpty()) {
            int x = builder.xOffset + xOffset;
            int y = builder.yOffset + yOffset;

            if (this.type == 0) {
                y -= this.height;
            } else if (this.type == 3) {
                x -= this.width;
            }

            if (xPosition >= x && xPosition <= (x + this.width)) {
                if (yPosition >= y && yPosition <= (y + this.height)) {
                    builder.drawHoveringText(Arrays.asList(new String[] { hover }), xPosition - 37, yPosition - 22);

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public IGuiElement clicked(GuiBuilder builder, int xPadding, int yPadding, int xPosition, int yPosition)
    {
        int x = builder.xOffset + xOffset;
        int y = builder.yOffset + yOffset;

        if (this.type == 0) {
            y -= this.height;
        } else if (this.type == 3) {
            x -= this.width;
        }

        if (xPosition >= x && xPosition <= (x + this.width)) {
            if (yPosition >= y && yPosition <= (y + this.height)) {
                return this;
            }
        }

        return null;
    }
}
