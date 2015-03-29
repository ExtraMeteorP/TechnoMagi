package com.ollieread.technomagi.client.gui.window;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.ollieread.technomagi.client.gui.GuiBuilder.ProgressType;
import com.ollieread.technomagi.client.gui.component.ComponentProgress;
import com.ollieread.technomagi.client.gui.component.ComponentSlot;
import com.ollieread.technomagi.client.gui.window.abstracts.WindowContainer;
import com.ollieread.technomagi.common.block.energy.container.ContainerBasicGenerator;

public class WindowBasicGenerator extends WindowContainer
{

    public WindowBasicGenerator(ContainerBasicGenerator container)
    {
        super(176, 145);

        this.setContainer(container);
        this.setPadding(5, 5);
        this.setHeading(StatCollector.translateToLocal("gui.technomagi.basic_generator"));
        this.setHasInventory(true);
        this.setBackground(true);

        this.addComponent("fuel_slot", new ComponentSlot());
        this.addComponent("progress", new ComponentProgress(113, ComponentOrientation.HORIZONTAL, ProgressType.DATA, 0)).setOffset(25, 2);
        this.addComponent("energy", new ComponentProgress(113, ComponentOrientation.HORIZONTAL, ProgressType.ENERGY, 0)).setOffset(25, 14);
        this.addComponent("charge_slot", new ComponentSlot()).setOffset(143, 0);
    }

    @Override
    public void updateContent()
    {
        ComponentProgress component = (ComponentProgress) this.getComponent("progress");
        component.setPercentage(((ContainerBasicGenerator) container).tile.getProgressScaled(113));
        List<String> tooltip = new ArrayList<String>();
        tooltip.add(EnumChatFormatting.RED + "" + ((ContainerBasicGenerator) container).tile.getProgress() + "/" + ((ContainerBasicGenerator) container).tile.getMaxProgress());
        component.setTooltip(tooltip);

        component = (ComponentProgress) this.getComponent("energy");
        component.setPercentage(((ContainerBasicGenerator) container).tile.getEnergyStoredScaled(113));
        tooltip = new ArrayList<String>();
        tooltip.add(EnumChatFormatting.GREEN + "" + ((ContainerBasicGenerator) container).tile.getEnergyStored(null) + "/" + ((ContainerBasicGenerator) container).tile.getMaxEnergyStored(null));
        component.setTooltip(tooltip);
    }

}
