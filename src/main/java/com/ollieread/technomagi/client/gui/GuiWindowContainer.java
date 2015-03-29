package com.ollieread.technomagi.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import org.lwjgl.input.Mouse;

import com.ollieread.technomagi.client.gui.window.abstracts.Window;

public class GuiWindowContainer extends GuiContainer
{

    public GuiWindowContainer(Window window, Container container)
    {
        super(container);

        GuiBuilder.instance.setGui(this);
        GuiBuilder.instance.setWindow(window);

        this.xSize = window.getWidth();
        this.ySize = window.getHeight();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks)
    {
        super.drawScreen(mouseX, mouseY, renderPartialTicks);

        GuiBuilder.instance.currentWindow.mouseHovered(this.guiLeft, this.guiTop, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicks, int mouseX, int mouseY)
    {
        Window window = GuiBuilder.instance.currentWindow;
        window.updateContent();

        int x = this.guiLeft;
        int y = this.guiTop;

        GuiBuilder.instance.mouseX = mouseX;
        GuiBuilder.instance.mouseY = mouseY;
        GuiBuilder.instance.partialTicks = renderPartialTicks;

        window.draw(x, y);

        int dwheel = Mouse.getDWheel();

        if (dwheel != 0) {
            window.mouseScrolled(x, y, mouseX, mouseY, dwheel);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int clickedButton)
    {
        Window window = GuiBuilder.instance.currentWindow;

        int x = this.guiLeft;
        int y = this.guiTop;

        window.mouseClicked(x, y, mouseX, mouseY, clickedButton);

        super.mouseClicked(mouseX, mouseY, clickedButton);
    }

}
