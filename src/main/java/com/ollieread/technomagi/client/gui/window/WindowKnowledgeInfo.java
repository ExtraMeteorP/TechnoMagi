package com.ollieread.technomagi.client.gui.window;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.KnowledgeCategory;
import com.ollieread.technomagi.client.gui.GuiBuilder.TabLocation;
import com.ollieread.technomagi.client.gui.component.Component;
import com.ollieread.technomagi.client.gui.component.ComponentButton;
import com.ollieread.technomagi.client.gui.component.ComponentButton.ButtonType;
import com.ollieread.technomagi.client.gui.component.ComponentTab;
import com.ollieread.technomagi.client.gui.component.ComponentVerticalList;
import com.ollieread.technomagi.client.gui.component.IClickHandler;
import com.ollieread.technomagi.client.gui.content.Content;
import com.ollieread.technomagi.client.gui.content.ContentLoader;
import com.ollieread.technomagi.client.gui.content.Section;
import com.ollieread.technomagi.client.gui.window.abstracts.WindowTabbed;

public class WindowKnowledgeInfo extends WindowTabbed implements IClickHandler
{

    protected String activeTab = null;
    protected PlayerTechnomagi technomage;
    protected Knowledge knowledge = null;
    protected int page = 0;
    protected String sectionName = null;

    public WindowKnowledgeInfo(PlayerTechnomagi technomage, String activeTab, String knowledge)
    {
        super(358, 253, TabLocation.LEFT);
        this.technomage = technomage;
        this.activeTab = activeTab;
        this.knowledge = TechnomagiApi.knowledge().getKnowledge(knowledge);
        this.setHeading(StatCollector.translateToLocal(this.knowledge.getUnlocalisedName()));
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

            if (this.activeTab.equals(tab.getName())) {
                tab.setActive(true);
            }
        }

        ComponentVerticalList info = new ComponentVerticalList(243, 208, true);
        info.setOffset(105, 0);
        info.setBackground(true).setPadding(6, 6);
        info.setClickHandler(this);
        this.addComponent("knowledge_info", info);

        ComponentVerticalList list = new ComponentVerticalList(100, 208);
        list.setBackground(true).setPadding(6, 9);
        list.setClickHandler(this);
        this.addComponent("link_list", list);
    }

    @Override
    public void updateContent()
    {
        if (knowledge != null) {
            Component component1 = this.getComponent("knowledge_info");
            Component component2 = this.getComponent("link_list");

            if (component1 != null && component1 instanceof ComponentVerticalList && component2 != null && component2 instanceof ComponentVerticalList) {
                ComponentVerticalList list = (ComponentVerticalList) component1;
                ComponentVerticalList links = (ComponentVerticalList) component2;
                list.clearComponents();
                links.clearComponents();
                list.setScroll(0);

                Content content = ContentLoader.get(knowledge.getName(), "/assets/" + Technomagi.MODID.toLowerCase() + "/content/" + activeTab + "/" + knowledge.getName() + ".xml");

                if (content != null) {
                    Section section = null;

                    for (Section i : content.getSections()) {
                        if (sectionName == null) {
                            sectionName = i.getName();
                            section = i;
                        }

                        ComponentButton button = new ComponentButton(links.getWidth() - (links.paddingX * 2), ButtonType.TEXT);
                        button.setText(StatCollector.translateToLocal("gui.technomagi.link." + i.getName())).setEnabled(true).setClickHandler(this);

                        if (sectionName.equals(i.getName())) {
                            button.setActive(true);
                            section = i;
                        }
                        links.addComponent(i.getName(), button);
                    }

                    if (section != null) {
                        list.addElements(section.getElements(page));
                    }
                }
            }
        }
    }

    @Override
    public void onClick(Component component)
    {
        if (component instanceof ComponentTab) {
            builder.instance.currentWindow = new WindowKnowledge(this.technomage, component.getName());
            builder.instance.currentWindow.updateContent();
        } else if (component instanceof ComponentButton) {
            ButtonType type = ((ComponentButton) component).getType();

            if (type.equals(ButtonType.SCROLL_DOWN)) {
                ((ComponentVerticalList) component.getParent()).scrollDown();
            } else if (type.equals(ButtonType.SCROLL_UP)) {
                ((ComponentVerticalList) component.getParent()).scrollUp();
            } else if (type.equals(ButtonType.TEXT)) {
                sectionName = component.getName();
                page = 0;
                updateContent();
            }
        }
    }

}
