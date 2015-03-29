package com.ollieread.technomagi.client.gui.window;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.ollieread.technomagi.client.gui.GuiBuilder.ProgressType;
import com.ollieread.technomagi.client.gui.component.ComponentProgress;
import com.ollieread.technomagi.client.gui.component.ComponentSlot;
import com.ollieread.technomagi.client.gui.window.abstracts.WindowContainer;
import com.ollieread.technomagi.common.block.energy.container.ContainerBattery;

public class WindowBattery extends WindowContainer
{

    public WindowBattery(ContainerBattery container)
    {
        super(176, 145);

        this.setContainer(container);
        this.setPadding(5, 5);
        this.setHeading(StatCollector.translateToLocal("gui.technomagi.battery"));
        this.setHasInventory(true);
        this.setBackground(true);

        this.addComponent("charge_slot", new ComponentSlot());
        this.addComponent("energy", new ComponentProgress(132, ComponentOrientation.HORIZONTAL, ProgressType.ENERGY, 0)).setOffset(25, 7);
    }

    @Override
    public void updateContent()
    {
        ComponentProgress component = (ComponentProgress) this.getComponent("energy");
        component.setPercentage(((ContainerBattery) container).tile.getEnergyStoredScaled(132));
        List<String> tooltip = new ArrayList<String>();
        tooltip.add(EnumChatFormatting.GREEN + "" + ((ContainerBattery) container).tile.getEnergyStored(null) + "/" + ((ContainerBattery) container).tile.getMaxEnergyStored(null));
        component.setTooltip(tooltip);
    }

}
