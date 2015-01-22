package com.ollieread.technomagi.client.gui.elements;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class GuiElementSelector extends GuiElement
{

    protected ResourceLocation image;
    protected boolean large = false;
    protected boolean selected = false;

    public GuiElementSelector(String name, IGuiElement parent, ResourceLocation image, int x, int y, boolean large, boolean selected)
    {
        this.name = name;
        this.parent = parent;
        this.image = image;
        this.xOffset = x;
        this.yOffset = y;

        if (!large) {
            this.width = 18;
            this.height = 18;
        } else {
            this.width = 34;
            this.height = 34;
        }

        this.large = large;
        this.selected = selected;
    }

    @Override
    public void drawBackground(GuiBuilder builder, int xPadding, int yPadding)
    {
        builder.mc.getTextureManager().bindTexture(builder.texture);
        builder.drawStretchedRect(builder.xOffset + xOffset + xPadding, builder.yOffset + yOffset + yPadding, 0, 110, 18, 18, width, height);
        builder.mc.getTextureManager().bindTexture(image);
        builder.drawImage(xOffset + xPadding + 1, yOffset + yPadding + 1, 0, 0, width - 2, height - 2, width - 2, height - 2);

        if (selected) {
            int u = 0;
            int v = 128;
            int w = 16;
            int h = 16;

            if (large) {
                u = 74;
                v = 104;
                w = 30;
                h = 30;
            }

            builder.mc.getTextureManager().bindTexture(builder.texture);
            builder.drawTextureArea(xOffset + xPadding + 2, yOffset + yPadding + 2, u, v, w, h);
        }
    }

    @Override
    public void draw(GuiBuilder builder, int xPadding, int yPadding)
    {
    }

    @Override
    public IGuiElement clicked(GuiBuilder builder, int xPadding, int yPadding, int xPosition, int yPosition)
    {
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;

        if (xPosition >= x && xPosition <= (x + this.width)) {
            if (yPosition >= y && yPosition <= (y + this.height)) {
                return this;
            }
        }

        return null;
    }

}
