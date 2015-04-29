package com.ollieread.technomagi.client.gui.window;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.ollieread.technomagi.client.gui.GuiBuilder.ProgressType;
import com.ollieread.technomagi.client.gui.component.ComponentProgress;
import com.ollieread.technomagi.client.gui.component.ComponentSlot;
import com.ollieread.technomagi.client.gui.window.abstracts.WindowContainer;
import com.ollieread.technomagi.common.block.machine.container.ContainerCultivator;
import com.ollieread.technomagi.common.block.machine.tile.TileCultivator;

public class WindowCultivator extends WindowContainer
{

    public WindowCultivator(ContainerCultivator container)
    {
        super(160, 195);

        this.setContainer(container);
        this.setPadding(5, 5);
        this.setHeading(StatCollector.translateToLocal("gui.technomagi.cultivator.basic"));
        this.setHasInventory(true);
        this.setBackground(true);

        this.addComponent("nanite_slot", new ComponentSlot());
        this.addComponent("sample_slot", new ComponentSlot()).setOffset(0, 25);
        this.addComponent("input_slot", new ComponentSlot()).setOffset(0, 50);
        this.addComponent("output_slot", new ComponentSlot()).setOffset(130, 50);

        this.addComponent("nanites", new ComponentProgress(100, ComponentOrientation.HORIZONTAL, ProgressType.NANITES, 0)).setOffset(25, 9);
        this.addComponent("sample", new ComponentProgress(100, ComponentOrientation.HORIZONTAL, ProgressType.DATA, 0)).setOffset(25, 33);
        this.addComponent("cultivated", new ComponentProgress(100, ComponentOrientation.HORIZONTAL, ProgressType.NANITES, 0)).setOffset(25, 57);
    }

    @Override
    public void updateContent()
    {
        TileCultivator cultivator = ((ContainerCultivator) container).tile;

        ComponentProgress nanites = (ComponentProgress) this.getComponent("nanites");
        ComponentProgress sample = (ComponentProgress) this.getComponent("sample");
        ComponentProgress cultivated = (ComponentProgress) this.getComponent("cultivated");

        nanites.setPercentage(cultivator.getNanitesScaled(100));
        sample.setPercentage(cultivator.getSampleScaled(100));
        cultivated.setPercentage(cultivator.getCultivatedScaled(100));

        List<String> tooltipNanite = new ArrayList<String>();
        tooltipNanite.add(EnumChatFormatting.AQUA + "Current Nanites");

        if (cultivator.getNaniteEntity(false) == null) {
            tooltipNanite.add(EnumChatFormatting.RED + "None");
        } else {
            tooltipNanite.add(cultivator.getNaniteEntity(true));
        }

        tooltipNanite.add(cultivator.getNanites() + "/" + cultivator.getMaxNanites());
        nanites.setTooltip(tooltipNanite);

        List<String> tooltipSample = new ArrayList<String>();
        tooltipSample.add(EnumChatFormatting.RED + "Current Sample");

        if (cultivator.getSampleEntity(false) == null) {
            tooltipSample.add(EnumChatFormatting.RED + "None");
        } else {
            tooltipSample.add(cultivator.getSampleEntity(true));
        }

        tooltipSample.add(cultivator.getSample() + "/" + cultivator.getMaxSample());
        sample.setTooltip(tooltipSample);

        List<String> tooltipCultivated = new ArrayList<String>();
        tooltipCultivated.add(EnumChatFormatting.AQUA + "Cultivated Nanites");
        tooltipCultivated.add(cultivator.getCultivated() + "/" + cultivator.getMaxCultivated());
        cultivated.setTooltip(tooltipCultivated);
    }
}
