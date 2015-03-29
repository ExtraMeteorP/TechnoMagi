package com.ollieread.technomagi.client.gui.component;

public class ComponentSlot extends Component
{

    public ComponentSlot()
    {
        super(20, 20);
    }

    @Override
    public void draw(int x, int y)
    {
        x += getOffsetX();
        y += getOffsetY();

        builder.drawTextureArea(x, y, 0, 90, getWidth(), getHeight());
    }

}
