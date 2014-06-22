package com.ollieread.technomagi.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;

public abstract class GuiScrollable extends GuiSlot
{

    public GuiScrollable(Minecraft mc, int width, int height, int top, int bottom, int slotHeight)
    {
        super(mc, width, height, top, bottom, slotHeight);
    }

    @Override
    protected int getSize()
    {
        return 0;
    }

    @Override
    protected void elementClicked(int var1, boolean var2, int var3, int var4)
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected boolean isSelected(int var1)
    {
        return false;
    }

    @Override
    protected void drawBackground()
    {

    }

    @Override
    protected void drawContainerBackground(Tessellator tessellator)
    {

    }

    public void drawDefaultBackground()
    {

    }

}
