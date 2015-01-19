package com.ollieread.technomagi.client.gui.builder;

import net.minecraft.client.gui.GuiScreen;

public class GuiBuilderScreen extends GuiScreen
{

    protected GuiBuilder builder;

    public void initGui()
    {
        super.initGui();

        buttonList.clear();
        builder = GuiBuilder.instance;
    }

    public void drawScreen(int mx, int yx, float par3)
    {
        drawDefaultBackground();

        builder.drawBackground();
        builder.drawElements();
    }

}
