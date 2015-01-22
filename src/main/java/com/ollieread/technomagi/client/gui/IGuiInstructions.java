package com.ollieread.technomagi.client.gui;

import com.ollieread.technomagi.client.gui.elements.IGuiElement;
import com.ollieread.technomagi.inventory.abstracts.ContainerBuilder;

public interface IGuiInstructions
{

    /**
     * Initialisation of the GUI. Typically this will load in anything from the
     * container that is required.
     * 
     * @param builder The instance of GuiBuilder for this GUI.
     * @param container The instance of the ContainerBuilder for this GUI.
     */
    public void init(GuiBuilder builder, ContainerBuilder container);

    /**
     * Build the actual GUI. This typically just creates new elements and adds
     * them.
     * 
     * @param builder The instance of GuiBuilder for this GUI.
     * @param container The instance of the ContainerBuilder for this GUI.
     */
    public void build(GuiBuilder builder, ContainerBuilder container);

    /**
     * When elements are clicked, this will handle that event.
     * 
     * @param builder The instance of GuiBuilder for this GUI.
     * @param container The instance of the ContainerBuilder for this GUI.
     * @param element The element that was clicked
     */
    public void clicked(GuiBuilder builder, ContainerBuilder container, IGuiElement element);

}
