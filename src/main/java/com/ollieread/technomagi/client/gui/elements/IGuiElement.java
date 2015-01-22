package com.ollieread.technomagi.client.gui.elements;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public interface IGuiElement
{

    public void drawBackground(GuiBuilder builder, int xPadding, int yPadding);

    public void draw(GuiBuilder builder, int xPadding, int yPadding);

    public String getName();

    public IGuiElement getParent();

    public boolean hover(GuiBuilder builder, int xPadding, int yPadding, int xPosition, int yPosition);

    public IGuiElement clicked(GuiBuilder builder, int xPadding, int yPadding, int xPosition, int yPosition);

    public void setWidth(int width);

    public void setHeight(int height);

    public int getWidth();

    public int getHeight();

    public int getMinX();

    public int getMaxX();

    public int getMinY();

    public int getMaxY();

    public void setLink(String link);

    public String getLink();

}
