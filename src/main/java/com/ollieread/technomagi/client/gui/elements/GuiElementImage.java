package com.ollieread.technomagi.client.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class GuiElementImage extends GuiElement
{

    protected ResourceLocation image;

    public GuiElementImage(String name, IGuiElement parent, ResourceLocation image, int x, int y, int w, int h)
    {
        this.name = name;
        this.parent = parent;
        this.image = image;
        this.xOffset = x;
        this.yOffset = y;
        this.width = w;
        this.height = h;
    }

    @Override
    public void drawBackground(GuiBuilder builder, int xPadding, int yPadding)
    {

    }

    @Override
    public void draw(GuiBuilder builder, int xPadding, int yPadding)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        builder.drawImage(xOffset + xPadding, yOffset + yPadding, 0, 0, width, height, width, height);
    }

}
