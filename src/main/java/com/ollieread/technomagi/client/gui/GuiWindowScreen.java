package com.ollieread.technomagi.client.gui;

import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Mouse;

import com.ollieread.technomagi.client.gui.window.Window;

public class GuiWindowScreen extends GuiScreen
{

    public GuiWindowScreen(Window window)
    {
        GuiBuilder.instance.setGui(this);
        GuiBuilder.instance.setWindow(window);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks)
    {
        Window window = GuiBuilder.instance.currentWindow;

        int x = (width - window.getWidth()) / 2;
        int y = (height - window.getHeight()) / 2;

        GuiBuilder.instance.mouseX = mouseX;
        GuiBuilder.instance.mouseY = mouseY;
        GuiBuilder.instance.partialTicks = renderPartialTicks;

        this.drawDefaultBackground();
        window.draw(x, y);
        window.mouseHovered(x, y, mouseX, mouseY);

        int dwheel = Mouse.getDWheel();

        if (dwheel != 0) {
            window.mouseScrolled(x, y, mouseX, mouseY, dwheel);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int clickedButton)
    {
        Window window = GuiBuilder.instance.currentWindow;

        int x = (width - window.getWidth()) / 2;
        int y = (height - window.getHeight()) / 2;

        window.mouseClicked(x, y, mouseX, mouseY, clickedButton);
    }

}
