package com.ollieread.technomagi.client.gui.elements;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class GuiElementButton extends GuiElement
{

    protected String text;
    protected boolean disabled = false;
    protected boolean invisible = false;

    public GuiElementButton(String name, IGuiElement parent, int x, int y, int w, String s)
    {
        this.xOffset = x;
        this.yOffset = y;
        this.width = w;
        this.height = 20;
        this.name = name;
        this.parent = parent;
        this.text = s;
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
    public String getName()
    {
        return name;
    }

    @Override
    public void draw(GuiBuilder builder, int xPadding, int yPadding)
    {
        if (!invisible) {
            boolean hover = false;

            if (!disabled) {
                int x = builder.xOffset + xOffset + xPadding;
                int y = builder.yOffset + yOffset + yPadding;

                int mx = builder.mouseX;
                int my = builder.mouseY;

                if (mx > x && mx < x + width) {
                    if (my > y && my < y + height) {
                        hover = true;
                    }
                }
            }

            builder.drawButton(text, xOffset + xPadding, yOffset + yPadding, width, hover);
        }
    }

    @Override
    public void drawBackground(GuiBuilder builder, int xPadding, int yPadding)
    {

    }

    @Override
    public IGuiElement clicked(GuiBuilder builder, int xPadding, int yPadding, int xPosition, int yPosition)
    {
        if (!invisible && !disabled) {
            int x = xOffset + xPadding;
            int y = yOffset + yPadding;

            if (xPosition >= x && xPosition <= (x + this.width)) {
                if (yPosition >= y && yPosition <= (y + this.height)) {
                    return this;
                }
            }
        }

        return null;
    }

}
