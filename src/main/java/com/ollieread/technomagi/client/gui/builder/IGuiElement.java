package com.ollieread.technomagi.client.gui.builder;

public interface IGuiElement
{

    public void drawBackground(int xPadding, int yPadding);

    public void draw(int xPadding, int yPadding);

    public String getName();

    public IGuiElement getParent();

    public boolean hover(int xPadding, int yPadding, int xPosition, int yPosition);

    public String clicked(int xPadding, int yPadding, int xPosition, int yPosition);

    public int getHeight(boolean includeY);

    public int getMinX();

    public int getMaxX();

    public int getMinY();

    public int getMaxY();

}
