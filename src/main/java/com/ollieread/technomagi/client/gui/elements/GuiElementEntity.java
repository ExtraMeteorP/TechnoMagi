package com.ollieread.technomagi.client.gui.elements;

import net.minecraft.entity.EntityLivingBase;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class GuiElementEntity extends GuiElement
{

    protected EntityLivingBase entity;
    protected int scale;

    public GuiElementEntity(String name, IGuiElement parent, EntityLivingBase entity, int x, int y, int s)
    {
        this.name = name;
        this.parent = parent;
        this.entity = entity;
        this.xOffset = x;
        this.yOffset = y;
        this.scale = s;
    }

    @Override
    public void drawBackground(GuiBuilder builder, int xPadding, int yPadding)
    {

    }

    @Override
    public void draw(GuiBuilder builder, int xPadding, int yPadding)
    {
        builder.drawEntity(xPadding + xOffset, yPadding + yOffset, scale, entity);
    }

}
