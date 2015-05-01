package com.ollieread.technomagi.client.gui.window;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.ollieread.technomagi.client.gui.GuiBuilder.ProgressType;
import com.ollieread.technomagi.client.gui.component.ComponentProgress;
import com.ollieread.technomagi.client.gui.component.ComponentSlot;
import com.ollieread.technomagi.client.gui.window.abstracts.WindowContainer;
import com.ollieread.technomagi.common.block.machine.container.ContainerProcessorElectric;
import com.ollieread.technomagi.common.block.machine.tile.TileResourceProcessorElectric;

public class WindowProcessorElectric extends WindowContainer
{

    public WindowProcessorElectric(ContainerProcessorElectric container)
    {
        super(176, 170);

        this.setContainer(container);
        this.setPadding(5, 5);
        this.setHeading(StatCollector.translateToLocal("gui.technomagi.processor.electric"));
        this.setHasInventory(true);
        this.setBackground(true);

        this.addComponent("subject_slot", new ComponentSlot());
        this.addComponent("progress", new ComponentProgress(113, ComponentOrientation.HORIZONTAL, ProgressType.NANITES, 0)).setOffset(25, 2);
        this.addComponent("energy", new ComponentProgress(113, ComponentOrientation.HORIZONTAL, ProgressType.ENERGY, 0)).setOffset(25, 14);
        this.addComponent("result_slot", new ComponentSlot()).setOffset(143, 0);
        this.addComponent("byproduct_slot", new ComponentSlot()).setOffset(143, 25);
        this.addComponent("component_slot", new ComponentSlot()).setOffset(0, 25);
    }

    @Override
    public void updateContent()
    {
        ComponentProgress component = (ComponentProgress) this.getComponent("progress");
        component.setPercentage(((ContainerProcessorElectric) container).tile.getProgressScaled(113));
        List<String> tooltip = new ArrayList<String>();
        tooltip.add(EnumChatFormatting.AQUA + "" + ((ContainerProcessorElectric) container).tile.getProgressPercentage() + "%");
        component.setTooltip(tooltip);

        component = (ComponentProgress) this.getComponent("energy");
        component.setPercentage(((TileResourceProcessorElectric) ((ContainerProcessorElectric) container).tile).getEnergyStoredScaled(113));
        tooltip = new ArrayList<String>();
        tooltip.add(EnumChatFormatting.GREEN + "" + ((TileResourceProcessorElectric) ((ContainerProcessorElectric) container).tile).getEnergyStored(null) + "/" + ((TileResourceProcessorElectric) ((ContainerProcessorElectric) container).tile).getMaxEnergyStored(null));
        component.setTooltip(tooltip);
    }

}
