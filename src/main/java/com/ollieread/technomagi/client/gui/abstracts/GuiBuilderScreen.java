package com.ollieread.technomagi.client.gui.abstracts;

import net.minecraft.client.gui.GuiScreen;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.client.gui.IGuiInstructions;
import com.ollieread.technomagi.client.gui.elements.IGuiElement;
import com.ollieread.technomagi.inventory.abstracts.ContainerBuilder;

public class GuiBuilderScreen extends GuiScreen
{

    protected static GuiBuilder builder;
    protected static IGuiInstructions instructions;

    public GuiBuilderScreen(IGuiInstructions instructions)
    {
        this.instructions = instructions;
    }

    public void initGui()
    {
        super.initGui();

        buttonList.clear();
        builder = new GuiBuilder();
        instructions.init(builder, null);
    }

    public void drawScreen(int mouseX, int mouseY, float p_73863_3_)
    {
        builder.setMouse(mouseX, mouseY);
        drawDefaultBackground();

        builder.resetElements();
        instructions.build(builder, (ContainerBuilder) null);
        builder.setOffset(width, height);

        builder.drawBackground();
        builder.drawElementBackgrounds();
        builder.drawElements();

        builder.hover(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button)
    {
        super.mouseClicked(mouseX, mouseY, button);

        IGuiElement link = builder.clicked(mouseX, mouseY);

        if (link != null) {
            instructions.clicked(builder, null, link);
        }
    }

}
