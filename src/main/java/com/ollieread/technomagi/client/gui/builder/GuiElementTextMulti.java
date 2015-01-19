package com.ollieread.technomagi.client.gui.builder;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class GuiElementTextMulti extends GuiElementText
{

    public int width;
    public int height;
    public List<String> lines;
    public int padding = 3;

    public GuiElementTextMulti(String name, IGuiElement parent, int x, int y, int w, String s, int c)
    {
        this(name, parent, x, y, w, GuiBuilder.instance.mc.fontRenderer.listFormattedStringToWidth(s, w), c);
    }

    public GuiElementTextMulti(String name, IGuiElement parent, int x, int y, int w, List<String> s, int c)
    {
        super(name, parent, x, y, "", 0, c);

        this.width = w;

        FontRenderer font = GuiBuilder.instance.mc.fontRenderer;

        this.lines = s;
        this.height = 0;
        int p = font.FONT_HEIGHT + this.padding;

        for (String line : lines) {
            if (line.indexOf("\n\n") > -1) {
                this.height += (p * 2);
            }

            this.height += p;
        }
    }

    @Override
    public void draw(int xPadding, int yPadding)
    {
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;

        for (String line : lines) {
            GuiBuilder.instance.drawString(line, x, y, this.colour);
            y += font.FONT_HEIGHT + this.padding;
        }
    }

    @Override
    public int getHeight(boolean includeY)
    {
        return height + (includeY ? yOffset : 0);
    }

}
