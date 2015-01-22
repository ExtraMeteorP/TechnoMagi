package com.ollieread.technomagi.client.gui.abstracts;

import net.minecraft.client.gui.inventory.GuiContainer;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.client.gui.IGuiInstructions;
import com.ollieread.technomagi.client.gui.elements.IGuiElement;
import com.ollieread.technomagi.inventory.abstracts.ContainerBuilder;

public class GuiBuilderContainer extends GuiContainer
{

    protected static GuiBuilder builder;
    protected static IGuiInstructions instructions;

    public GuiBuilderContainer(ContainerBuilder container, IGuiInstructions instructions)
    {
        super(container);

        this.instructions = instructions;
    }

    public void initGui()
    {
        super.initGui();

        buttonList.clear();
        builder = new GuiBuilder();
        instructions.init(builder, (ContainerBuilder) inventorySlots);
    }

    public void drawScreen(int mouseX, int mouseY, float p_73863_3_)
    {
        builder.setMouse(mouseX, mouseY);

        super.drawScreen(mouseX, mouseY, p_73863_3_);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mouseX, int mouseY)
    {
        builder.resetElements();
        instructions.build(builder, (ContainerBuilder) inventorySlots);
        builder.setOffset(width, height);

        builder.drawBackground();
        builder.drawElementBackgrounds();
        builder.drawElements();
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        builder.hover(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button)
    {
        super.mouseClicked(mouseX, mouseY, button);

        IGuiElement link = builder.clicked(mouseX, mouseY);

        if (link != null) {
            instructions.clicked(builder, (ContainerBuilder) inventorySlots, link);
        }
    }

}
