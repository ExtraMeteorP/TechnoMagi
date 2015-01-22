package com.ollieread.technomagi.client.gui.elements;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class GuiElementText extends GuiElement
{

    protected String text;
    protected List<String> lines;
    protected int padding = 3;
    protected FontRenderer font;
    protected int colour;
    protected int alignment = 0;

    public GuiElementText(String name, IGuiElement parent, int x, int y, String s, int a, int c, FontRenderer f)
    {
        this(name, parent, x, y, f.getStringWidth(s), s, a, c, f);
    }

    public GuiElementText(String name, IGuiElement parent, int x, int y, int w, String s, int a, int c, FontRenderer f)
    {
        this.name = name;
        this.parent = parent;
        this.xOffset = x;
        this.yOffset = y;
        this.font = f;
        this.text = s;
        this.height = 0;
        this.colour = c;
        this.alignment = a;
        this.lines = font.listFormattedStringToWidth(s, w);
        this.height = 0;

        for (String line : lines) {
            this.height += font.FONT_HEIGHT + this.padding;
        }

        this.setWidth(w);
    }

    public void setAlignment(int a)
    {
        this.alignment = a;
    }

    @Override
    public int getHeight()
    {
        return this.height + (padding * 2);
    }

    @Override
    public void setWidth(int width)
    {
        this.height = 0;
        this.lines = this.font.listFormattedStringToWidth(this.text, width);

        for (String line : lines) {
            this.height += font.FONT_HEIGHT + this.padding;
        }

        this.width = width;
    }

    @Override
    public void setHeight(int height)
    {
    }

    @Override
    public void drawBackground(GuiBuilder builder, int xPadding, int yPadding)
    {

    }

    @Override
    public void draw(GuiBuilder builder, int xPadding, int yPadding)
    {
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;

        for (String line : lines) {
            int xo = 0;

            if (this.alignment == 1) {
                xo = (this.width - this.font.getStringWidth(line)) / 2;
            } else if (this.alignment == 2) {
                xo = this.width - (xOffset * 2) - this.font.getStringWidth(line);
            }

            builder.drawString(line, x + xo, y, this.colour, font);
            y += font.FONT_HEIGHT + this.padding;
        }
    }

    @Override
    public boolean hover(GuiBuilder builder, int xPadding, int yPadding, int xPosition, int yPosition)
    {
        return false;
    }

    @Override
    public String getLink()
    {
        return null;
    }

}
