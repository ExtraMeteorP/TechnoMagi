package com.ollieread.technomagi.client.gui.component;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class ComponentText extends Component
{

    protected String content = "";
    protected List<String> lines;
    protected int colour = 16777215;
    protected FontRenderer font;

    public ComponentText(int width, FontRenderer font)
    {
        super(width, 0);

        this.font = font;
    }

    public ComponentText setContent(String content)
    {
        this.content = content;
        this.lines = font.listFormattedStringToWidth(content, this.width);
        this.height = (font.FONT_HEIGHT + 2) * this.lines.size();

        return this;
    }

    public ComponentText setColour(int colour)
    {
        this.colour = colour;
        return this;
    }

    @Override
    public void draw(int x, int y)
    {
        GuiBuilder builder = GuiBuilder.instance;

        x += offsetX + paddingX;
        y += offsetY + paddingY;

        for (String line : lines) {
            builder.drawString(line, x, y, colour, font);
            y += font.FONT_HEIGHT + 2;
        }
    }

}
