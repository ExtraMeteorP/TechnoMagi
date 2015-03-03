package com.ollieread.technomagi.client.gui.component;

public class ComponentPlayerInventory extends Component
{

    public ComponentPlayerInventory(int width, int height)
    {
        super(176, 90);
    }

    @Override
    public void draw(int x, int y)
    {
        x += getOffsetX();
        y += getOffsetY();

        builder.drawTextureArea(x, y, 0, 0, getWidth(), getHeight());
    }

}
