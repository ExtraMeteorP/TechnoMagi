package com.ollieread.technomagi.client.gui.component;


public class ComponentGroup extends ComponentCollection
{

    public ComponentGroup(int width, int height)
    {
        super(width, height);
    }

    @Override
    public void draw(int x, int y)
    {
        x += offsetX + paddingX;
        y += offsetY + paddingY;

        for (Component component : components.values()) {
            component.draw(x, y);
        }
    }

}
