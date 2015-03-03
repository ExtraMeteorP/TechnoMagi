package com.ollieread.technomagi.client.gui.component;

import net.minecraft.util.ResourceLocation;

public class ComponentImage extends Component
{

    protected ResourceLocation image;

    public ComponentImage(int width, int height)
    {
        super(width, height);
    }

    public ComponentImage setImage(ResourceLocation image)
    {
        this.image = image;

        return this;
    }

    @Override
    public void draw(int x, int y)
    {
        x += getOffsetX();
        y += getOffsetY();

        builder.bindTexture(image);
        builder.drawImage(x, y, 0, 0, getWidth(), getHeight(), getWidth(), getHeight());
    }

}
