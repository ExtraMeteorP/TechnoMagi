package com.ollieread.technomagi.client.gui.component;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class ComponentHeading extends Component
{

    protected String text;

    public ComponentHeading(String text, int width)
    {
        super(width, 18);

        this.text = text;
    }

    @Override
    public void draw(int x, int y)
    {
        GuiBuilder.instance.drawHeading(text, x, y, getWidth(), getHeight());
    }

}
