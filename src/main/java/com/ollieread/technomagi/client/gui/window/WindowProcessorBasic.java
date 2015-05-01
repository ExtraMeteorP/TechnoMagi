package com.ollieread.technomagi.client.gui.window;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.ollieread.technomagi.client.gui.GuiBuilder.ProgressType;
import com.ollieread.technomagi.client.gui.component.ComponentProgress;
import com.ollieread.technomagi.client.gui.component.ComponentSlot;
import com.ollieread.technomagi.client.gui.window.abstracts.WindowContainer;
import com.ollieread.technomagi.common.block.machine.container.ContainerProcessorBasic;
import com.ollieread.technomagi.common.block.machine.tile.TileResourceProcessorBasic;

public class WindowProcessorBasic extends WindowContainer
{

    public WindowProcessorBasic(ContainerProcessorBasic container)
    {
        super(176, 170);

        this.setContainer(container);
        this.setPadding(5, 5);
        this.setHeading(StatCollector.translateToLocal("gui.technomagi.processor.basic"));
        this.setHasInventory(true);
        this.setBackground(true);

        this.addComponent("subject_slot", new ComponentSlot());
        this.addComponent("progress", new ComponentProgress(113, ComponentOrientation.HORIZONTAL, ProgressType.NANITES, 0)).setOffset(25, 2);
        this.addComponent("fuel", new ComponentProgress(113, ComponentOrientation.HORIZONTAL, ProgressType.DATA, 0)).setOffset(25, 14);
        this.addComponent("result_slot", new ComponentSlot()).setOffset(143, 0);
        this.addComponent("byproduct_slot", new ComponentSlot()).setOffset(143, 25);
        this.addComponent("fuel_slot", new ComponentSlot()).setOffset(0, 25);
        this.addComponent("component_slot", new ComponentSlot()).setOffset(68, 25);
    }

    @Override
    public void updateContent()
    {
        ComponentProgress component = (ComponentProgress) this.getComponent("progress");
        component.setPercentage(((ContainerProcessorBasic) container).tile.getProgressScaled(113));
        List<String> tooltip = new ArrayList<String>();
        tooltip.add(EnumChatFormatting.AQUA + "" + ((ContainerProcessorBasic) container).tile.getProgressPercentage() + "%");
        component.setTooltip(tooltip);

        component = (ComponentProgress) this.getComponent("fuel");
        component.setPercentage(((TileResourceProcessorBasic) ((ContainerProcessorBasic) container).tile).getFuelTimeScaled(113));
        tooltip = new ArrayList<String>();
        tooltip.add(EnumChatFormatting.RED + "" + ((TileResourceProcessorBasic) ((ContainerProcessorBasic) container).tile).getFuelTime() + "/" + ((TileResourceProcessorBasic) ((ContainerProcessorBasic) container).tile).getMaxFuelTime());
        component.setTooltip(tooltip);
    }

}
