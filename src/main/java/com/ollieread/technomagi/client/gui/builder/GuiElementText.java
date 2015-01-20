package com.ollieread.technomagi.client.gui.builder;

import net.minecraft.client.gui.FontRenderer;

public class GuiElementText implements IGuiElement
{

    protected String name;
    protected IGuiElement parent;
    protected int xOffset;
    protected int yOffset;
    protected String text;
    protected int alignment;
    protected int colour;
    protected FontRenderer font;

    public GuiElementText(String name, IGuiElement parent, int x, int y, String s, int a, int c)
    {
        this.name = name;
        this.parent = parent;
        this.xOffset = x;
        this.yOffset = y;
        this.text = s;
        this.alignment = a;
        this.colour = c;
        this.font = GuiBuilder.instance.mc.fontRenderer;
    }

    @Override
    public void drawBackground(int xPadding, int yPadding)
    {

    }

    @Override
    public void draw(int xPadding, int yPadding)
    {
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;
        int w = font.getStringWidth(text);

        if (this.alignment == 1) {
            x += w / 2;
        } else if (this.alignment == 2) {
            x += (((GuiElementSection) parent).width - w) - 6;
        }

        GuiBuilder.instance.drawString(this.text, x, y, this.colour, font);
    }

    public void setFontRenderer(FontRenderer font)
    {
        this.font = font;
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
    public int getMinX()
    {
        return xOffset;
    }

    @Override
    public int getMaxX()
    {
        return xOffset + font.getStringWidth(text);
    }

    @Override
    public int getMinY()
    {
        return yOffset;
    }

    @Override
    public int getMaxY()
    {
        return yOffset + font.FONT_HEIGHT;
    }

    @Override
    public int getHeight(boolean includeY)
    {
        return font.FONT_HEIGHT + (includeY ? yOffset : 0);
    }

    @Override
    public boolean hover(int xPadding, int yPadding, int xPosition, int yPosition)
    {
        return false;
    }

    @Override
    public String clicked(int xPadding, int yPadding, int xPosition, int yPosition)
    {
        return null;
    }

}
