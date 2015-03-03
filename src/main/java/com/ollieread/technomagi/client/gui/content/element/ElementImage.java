package com.ollieread.technomagi.client.gui.content.element;

import net.minecraft.util.ResourceLocation;

public class ElementImage extends Element
{

    protected ResourceLocation image;
    protected int u = 0;
    protected int v = 0;

    public ElementImage(String name)
    {
        super(name);
    }

    public ElementImage setImage(ResourceLocation image)
    {
        this.image = image;
        return this;
    }

    public ElementImage setUV(int u, int v)
    {
        this.u = u;
        this.v = v;

        return this;
    }

    public ResourceLocation getImage()
    {
        return this.image;
    }

    public int getU()
    {
        return this.u;
    }

    public int getV()
    {
        return this.v;
    }

}
