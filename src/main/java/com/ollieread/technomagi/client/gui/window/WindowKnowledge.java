package com.ollieread.technomagi.client.gui.window;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.KnowledgeCategory;
import com.ollieread.technomagi.client.gui.GuiBuilder.TabLocation;
import com.ollieread.technomagi.client.gui.component.Component;
import com.ollieread.technomagi.client.gui.component.ComponentKnowledge;
import com.ollieread.technomagi.client.gui.component.ComponentKnowledgeGrid;
import com.ollieread.technomagi.client.gui.component.ComponentTab;
import com.ollieread.technomagi.client.gui.component.IClickHandler;
import com.ollieread.technomagi.client.gui.window.abstracts.WindowTabbed;
import com.ollieread.technomagi.util.ResourceHelper;

public class WindowKnowledge extends WindowTabbed implements IClickHandler
{

    protected String activeTab = null;
    protected PlayerTechnomagi technomage;

    public WindowKnowledge(PlayerTechnomagi technomage, String activeTab)
    {
        super(253, 253, TabLocation.LEFT);

        this.activeTab = activeTab;
        this.technomage = technomage;
        this.setHeading(StatCollector.translateToLocal("gui.technomagi.knowledge"));
        this.setBackground(true);
        this.setPadding(5, 5);

        Collection<KnowledgeCategory> categories = TechnomagiApi.getKnowledgeCategories();

        for (KnowledgeCategory category : categories) {
            ComponentTab tab = this.addTab(category.getName(), category.getIcon());
            tab.setClickHandler(this);
            List<String> tooltip = new ArrayList<String>();
            tooltip.add(StatCollector.translateToLocal(category.getUnlocalisedName()));
            tooltip.add(EnumChatFormatting.GRAY + "Knowledge: " + TechnomagiApi.knowledge().getKnowledgeForCategory(category.getName()).size());
            tab.setTooltip(tooltip);

            if (this.activeTab == null || this.activeTab.isEmpty()) {
                this.activeTab = tab.getName();
                tab.setActive(true);
            } else if (this.activeTab.equals(tab.getName())) {
                tab.setActive(true);
            }
        }

        ComponentKnowledgeGrid grid = new ComponentKnowledgeGrid(243, 208);
        grid.setBackground(ResourceHelper.texture("gui/knowledge.png")).setPadding(6, 6);
        this.addComponent("hex_grid", grid);
    }

    @Override
    public void updateContent()
    {
        if (activeTab != null) {
            List<Knowledge> knowledgeList = TechnomagiApi.knowledge().getKnowledgeForCategory(activeTab);
            Component component = this.getComponent("hex_grid");

            if (component != null && component instanceof ComponentKnowledgeGrid) {
                ComponentKnowledgeGrid grid = (ComponentKnowledgeGrid) component;
                grid.clearComponents();

                for (Knowledge knowledge : knowledgeList) {
                    ComponentKnowledge hex = new ComponentKnowledge();
                    hex.setImage(knowledge.getIcon()).setOffset(knowledge.x, knowledge.y);
                    int progress = this.technomage.knowledge().getKnowledgeProgress(knowledge.getName());

                    if (progress == 100) {
                        hex.setActive(true);
                        hex.setClickHandler(this);
                    }

                    List<String> tooltip = new ArrayList<String>();
                    tooltip.add((progress < 100 ? EnumChatFormatting.OBFUSCATED : "") + StatCollector.translateToLocal(knowledge.getUnlocalisedName()));
                    tooltip.add(EnumChatFormatting.RED + "" + progress + "%");
                    hex.setTooltip(tooltip);

                    grid.addComponent(knowledge.getName(), hex);
                }
            }
        }
    }

    public void highlightPath(List<String> names)
    {
        Component grid = this.getComponent("hex_grid");

        if (grid != null && grid instanceof ComponentKnowledgeGrid) {
            ComponentKnowledgeGrid hexGrid = (ComponentKnowledgeGrid) grid;

            for (String name : names) {
                Component component = hexGrid.getComponent(name);

                if (component != null && component instanceof ComponentKnowledge) {
                    ((ComponentKnowledge) component).setHighlight(true);
                }
            }
        }
    }

    @Override
    public void onClick(Component component)
    {
        if (component instanceof ComponentTab) {
            if (activeTab != null && activeTab.equals(component.getName())) {
                return;
            }

            this.setActiveTab(component.getName());
            this.activeTab = component.getName();
            this.updateContent();
        } else if (component instanceof ComponentKnowledge) {
            if (((ComponentKnowledge) component).isActive()) {
                String name = component.getName();
                builder.instance.currentWindow = new WindowKnowledgeInfo(this.technomage, this.activeTab, name);
                builder.instance.currentWindow.updateContent();
            }
        }
    }
}
