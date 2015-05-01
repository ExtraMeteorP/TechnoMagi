package com.ollieread.technomagi.client.gui.window;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.ollieread.technomagi.client.gui.GuiBuilder.ProgressType;
import com.ollieread.technomagi.client.gui.component.ComponentProgress;
import com.ollieread.technomagi.client.gui.component.ComponentSlot;
import com.ollieread.technomagi.client.gui.window.abstracts.WindowContainer;
import com.ollieread.technomagi.common.block.research.container.ContainerAnalyser;
import com.ollieread.technomagi.common.block.research.tile.TileAnalyser;

public class WindowAnalyser extends WindowContainer
{

    public WindowAnalyser(ContainerAnalyser container)
    {
        super(160, 145);

        this.setContainer(container);
        this.setPadding(5, 5);
        this.setHeading(StatCollector.translateToLocal("gui.technomagi.analyser"));
        this.setHasInventory(true);
        this.setBackground(true);
        this.addComponent("focus_slot", new ComponentSlot()).setOffset(0, 0);
        this.addComponent("output_slot", new ComponentSlot()).setOffset(130, 0);

        this.addComponent("progress", new ComponentProgress(100, ComponentOrientation.HORIZONTAL, ProgressType.NANITES, 0)).setOffset(25, 2);
        this.addComponent("data", new ComponentProgress(100, ComponentOrientation.HORIZONTAL, ProgressType.DATA, 0)).setOffset(25, 12);
    }

    @Override
    public void updateContent()
    {
        TileAnalyser analyser = ((ContainerAnalyser) container).tile;

        ComponentProgress progress = (ComponentProgress) this.getComponent("progress");
        ComponentProgress data = (ComponentProgress) this.getComponent("data");

        progress.setPercentage(analyser.getProgressScaled(100));
        data.setPercentage(analyser.getDataScaled(100));

        List<String> tooltipProgress = new ArrayList<String>();
        tooltipProgress.add(EnumChatFormatting.AQUA + "" + analyser.getProgressPercentage() + "%");
        progress.setTooltip(tooltipProgress);

        List<String> tooltipData = new ArrayList<String>();
        tooltipData.add(EnumChatFormatting.RED + "" + analyser.getData() + "/" + analyser.getMaxData());
        data.setTooltip(tooltipData);
    }
}
