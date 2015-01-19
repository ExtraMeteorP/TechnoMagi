package com.ollieread.technomagi.client.gui.builder;

import net.minecraft.client.Minecraft;
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

    public GuiElementText(String name, IGuiElement parent, int x, int y, String s, int a, int c)
    {
        this.name = name;
        this.parent = parent;
        this.xOffset = x;
        this.yOffset = y;
        this.text = s;
        this.alignment = a;
        this.colour = c;
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
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        int w = font.getStringWidth(text);

        if (this.alignment == 1) {
            x += w / 2;
        } else if (this.alignment == 2) {
            x += (((GuiElementSection) parent).width - w) - 6;
        }

        GuiBuilder.instance.drawString(this.text, x, y, this.colour);
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
        return xOffset + GuiBuilder.instance.mc.fontRenderer.getStringWidth(text);
    }

    @Override
    public int getMinY()
    {
        return yOffset;
    }

    @Override
    public int getMaxY()
    {
        return yOffset + GuiBuilder.instance.mc.fontRenderer.FONT_HEIGHT;
    }

    @Override
    public int getHeight(boolean includeY)
    {
        return Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + (includeY ? yOffset : 0);
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
