package com.ollieread.technomagi.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPersonalInterface extends GuiScreen
{

    protected GuiBuilder builder = new GuiBuilder();
    protected int xSize = 250;
    protected int ySize = 250;
    protected int xOffset;
    protected int yOffset;
    protected ExtendedPlayerKnowledge charon;
    protected int mouseX;
    protected int mouseY;

    public void initGui()
    {
        buttonList.clear();
        charon = ExtendedPlayerKnowledge.get(mc.thePlayer);
        builder.configure(250, 250, (width - 250) / 2, (height - 250) / 2);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mx, int yx, float par3)
    {
        mouseX = mx - xOffset;
        mouseY = yx - yOffset;

        drawDefaultBackground();

        builder.drawBackground();
        builder.drawHeading(I18n.format("technomagi.personalinterface.gui"));

        builder.drawProgressBar(3, 25, 102, 0, 50);
        builder.drawProgressBar(9, 25, 102, 1, 50);
        builder.drawProgressBar(15, 25, 102, 2, 50);
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

}