package com.ollieread.technomagi.client.gui.builder;

import com.ollieread.technomagi.inventory.abstracts.ContainerBuilder;

public interface IGuiInstructions
{

    public void init(GuiBuilder builder, ContainerBuilder container);

    public void build(GuiBuilder builder, ContainerBuilder container);

    public void clicked(GuiBuilder builder, ContainerBuilder container, String link);

}
