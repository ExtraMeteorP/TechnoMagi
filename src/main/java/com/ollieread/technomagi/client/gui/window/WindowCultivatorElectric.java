package com.ollieread.technomagi.client.gui.window;

import java.util.ArrayList;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.ollieread.technomagi.client.gui.GuiBuilder.ProgressType;
import com.ollieread.technomagi.client.gui.component.ComponentProgress;
import com.ollieread.technomagi.common.block.machine.container.ContainerCultivatorElectric;
import com.ollieread.technomagi.common.block.machine.tile.TileCultivatorElectric;

public class WindowCultivatorElectric extends WindowCultivator
{

    public WindowCultivatorElectric(ContainerCultivatorElectric container)
    {
        super(container);

        this.setWidth(170);
        this.setHeading(StatCollector.translateToLocal("gui.technomagi.cultivator.electric"));

        this.addComponent("energy", new ComponentProgress(68, ComponentOrientation.VERTICAL, ProgressType.ENERGY, 0)).setOffset(153, 0);
    }

    @Override
    public void updateContent()
    {
        super.updateContent();

        ComponentProgress component = (ComponentProgress) this.getComponent("energy");
        component.setPercentage(((TileCultivatorElectric) ((ContainerCultivatorElectric) container).tile).getEnergyStoredScaled(68));
        tooltip = new ArrayList<String>();
        tooltip.add(EnumChatFormatting.GREEN + "" + ((TileCultivatorElectric) ((ContainerCultivatorElectric) container).tile).getEnergyStored(null) + "/" + ((TileCultivatorElectric) ((ContainerCultivatorElectric) container).tile).getMaxEnergyStored(null));
        component.setTooltip(tooltip);
    }
}
