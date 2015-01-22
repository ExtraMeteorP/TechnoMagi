package com.ollieread.technomagi.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.client.gui.abstracts.GuiInstructions;
import com.ollieread.technomagi.client.gui.elements.GuiElementButton;
import com.ollieread.technomagi.client.gui.elements.GuiElementButtonText;
import com.ollieread.technomagi.client.gui.elements.GuiElementEntity;
import com.ollieread.technomagi.client.gui.elements.GuiElementImage;
import com.ollieread.technomagi.client.gui.elements.GuiElementProgressBar;
import com.ollieread.technomagi.client.gui.elements.GuiElementSection;
import com.ollieread.technomagi.client.gui.elements.GuiElementTab;
import com.ollieread.technomagi.client.gui.elements.GuiElementText;
import com.ollieread.technomagi.client.gui.elements.IGuiElement;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.ContainerPersonalInterface;
import com.ollieread.technomagi.inventory.abstracts.ContainerBuilder;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPersonalInterface extends GuiInstructions
{

    protected ExtendedPlayerKnowledge charon;

    public void init(GuiBuilder builder, ContainerBuilder container)
    {
        charon = ExtendedPlayerKnowledge.get(builder.mc.thePlayer);
    }

    public void build(GuiBuilder builder, ContainerBuilder container)
    {
        // Add tabs
        GuiElementTab diagnosisTab = new GuiElementTab("diagnosisTab", null, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/diagnosis.png"), 0, 25, 3, linkName.isEmpty() || linkName.equals("diagnosis"));
        GuiElementTab informationTab = new GuiElementTab("informationTab", null, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/information.png"), 0, 54, 3, linkName.equals("information"));
        GuiElementTab intrigueTab = new GuiElementTab("intrigueTab", null, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/intrigue.png"), 0, 83, 3, linkName.equals("intrigue"));
        GuiElementTab knowledgeTab = new GuiElementTab("knowledgeTab", null, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/knowledge.png"), 0, 112, 3, linkName.equals("knowledge"));
        GuiElementTab abilitiesTab = new GuiElementTab("abilitiesTab", null, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/abilities.png"), 0, 141, 3, linkName.equals("abilities"));
        // Set tab hover text
        diagnosisTab.setHover(I18n.format("technomagi.tab.diagnosis.gui")).setLink("diagnosis");
        informationTab.setHover(I18n.format("technomagi.tab.information.gui")).setLink("information");
        intrigueTab.setHover(I18n.format("technomagi.tab.intrigue.gui")).setLink("intrigue");
        knowledgeTab.setHover(I18n.format("technomagi.tab.knowledge.gui")).setLink("knowledge");
        abilitiesTab.setHover(I18n.format("technomagi.tab.abilities.gui")).setLink("abilities");
        // Add to builder
        builder.addElement(diagnosisTab);
        builder.addElement(informationTab);
        builder.addElement(intrigueTab);
        builder.addElement(knowledgeTab);
        builder.addElement(abilitiesTab);

        if (linkName.isEmpty() || linkName.equals("diagnosis")) {
            buildDiagnosis(builder, container);
        } else if (linkName.equals("information")) {
            buildInformation(builder);
        } else if (linkName.equals("intrigue")) {
            buildIntrigue(builder);
        } else if (linkName.equals("knowledge")) {
            buildKnowledge(builder);
        } else if (linkName.equals("abilities")) {
            buildAbilities(builder);
        }
    }

    protected void buildDiagnosis(GuiBuilder builder, ContainerBuilder container)
    {
        builder.setHeading(I18n.format("technomagi.personalinterface.diagnosis.gui")).setDimensions(196, 250);
        // Add information section
        GuiElementSection section = new GuiElementSection("diagnosisSection", null, 0, 0, 190, 226);
        GuiElementSection model = new GuiElementSection("selfModel", section, 0, 0, 76, 120, true);
        GuiElementSection data = new GuiElementSection("selfData", section, 79, 0, 110, 49, true);
        // Add player details
        model.addElement(new GuiElementImage("specialisation", model, charon.getSpecialisation().getIcon(), 19, 3, 32, 32));
        model.addElement(new GuiElementEntity("player", model, builder.mc.thePlayer, 35, 107, 35));
        data.addElement(new GuiElementText("naniteName", data, 1, 4, data.getWidth(), I18n.format("technomagi.nanites"), 0, 16777215, builder.mc.fontRenderer));
        data.addElement(new GuiElementText("nanitePercentage", data, 0, 4, data.getWidth() - 6, charon.nanites.getNanites() + "%", 2, 4118771, builder.mc.fontRenderer));
        data.addElement(new GuiElementProgressBar("naniteBar", data, 0, 15, 102, 0, charon.nanites.getNanites(), false));
        data.addElement(new GuiElementText("dataName", data, 1, 25, data.getWidth(), I18n.format("technomagi.data"), 0, 16777215, builder.mc.fontRenderer));
        data.addElement(new GuiElementText("dataPercentage", data, 0, 25, data.getWidth() - 6, charon.nanites.getData() + "%", 2, 15944766, builder.mc.fontRenderer));
        data.addElement(new GuiElementProgressBar("dataBar", data, 0, 35, 102, 1, charon.nanites.getData(), false));
        // Add to information section
        section.addElement(model);
        section.addElement(data);
        boolean syncing = ((ContainerPersonalInterface) container).isSyncing();
        GuiElementButton syncButton = new GuiElementButton("syncButton", null, 79, 52, 110, (syncing ? I18n.format("technomagi.stopsync") : I18n.format("technomagi.sync")));
        syncButton.setLink("diagnosis:sync");
        section.addElement(syncButton);
        // Add to builder
        builder.addElement(section);
    }

    protected void buildInformation(GuiBuilder builder)
    {
        builder.setHeading(I18n.format("technomagi.personalinterface.information.gui")).setDimensions(250, 250);

        Map<String, List<String>> information = Information.getInformation("info");
        GuiElementSection section = null;
        String subsection = getMetadata(0);

        if (subsection == null || subsection.isEmpty()) {
            section = new GuiElementSection("links", null, 0, 0, 244, 223, true);
            section.setLink("information");
            int y = 6;

            for (String entry : information.keySet()) {
                String name = entry;
                GuiElementButtonText link = new GuiElementButtonText(name, section, 6, y, I18n.format("info." + entry), builder.mc.fontRenderer);
                link.setLink("information:" + name);
                section.addElement(link);
                y += link.getHeight() + 6;
            }

            section.setScrollVertical(true).verticalOffset = getScroll("information");
        } else if (!subsection.isEmpty()) {
            section = new GuiElementSection("content", null, 0, 0, 244, 223, true);
            section.setLink("information:content");
            List<String> content = information.get(subsection);

            if (content != null) {
                String text = "";

                if (content != null) {
                    for (String line : content) {
                        text += line + "\n\n";
                    }

                    builder.setHeading(I18n.format("technomagi.personalinterface.information.gui") + " - " + I18n.format("info." + subsection));
                    GuiElementText info = new GuiElementText(subsection, section, 6, 6, 232, text, 0, 16777215, builder.mc.fontRenderer);
                    section.addElement(info).setScrollVertical(true).verticalOffset = this.getScroll("information:content");
                }
            } else {
                linkMetadata = new ArrayList<String>();
            }
        }

        builder.addElement(section);
    }

    protected void buildIntrigue(GuiBuilder builder)
    {
        builder.setHeading(I18n.format("technomagi.personalinterface.intrigue.gui"));
        builder.setDimensions(250, 250);
        GuiElementSection section = new GuiElementSection("intrigueSection", null, 0, 0, 244, 223);
        section.setLink("intrigue");

        Map<String, String[]> intrigue = charon.getIntrigue();
        int y = 0;

        for (Entry<String, String[]> entry : intrigue.entrySet()) {
            IKnowledge knowledge = ResearchRegistry.getKnowledge(entry.getKey());
            String[] textList = entry.getValue();
            String text = "";

            for (int i = 0; i < textList.length; i++) {
                text += textList[i] + "\n\n";
            }

            String name = entry.getKey();

            GuiElementSection subsection = new GuiElementSection(name, section, 0, y, 244, 0, true);
            GuiElementText content = new GuiElementText("content", subsection, 25, 3, 193, text, 0, 16777215, builder.mc.fontRenderer);
            GuiElementImage icon = new GuiElementImage("icon", subsection, knowledge.getIcon(), 3, 3, 16, 16);
            subsection.setHeight(content.getHeight());
            y += subsection.getHeight() + 6;

            subsection.addElement(content);
            subsection.addElement(icon);
            section.addElement(subsection);
        }

        section.setScrollVertical(true).verticalOffset = getScroll("intrigue");

        builder.addElement(section);
    }

    protected void buildKnowledge(GuiBuilder builder)
    {
        builder.setHeading(I18n.format("technomagi.personalinterface.knowledge.gui")).setDimensions(250, 250);
        GuiElementSection section = new GuiElementSection("knowledgeSection", null, 0, 0, 244, 223);
        section.setLink("knowledge");

        List<String> knowledgeList = new ArrayList<String>();
        knowledgeList.addAll(charon.getResearchedKnowledge());
        knowledgeList.addAll(charon.getResearchingKnowledge());
        int y = 0;

        for (String knowledge : knowledgeList) {
            IKnowledge iknowledge = ResearchRegistry.getKnowledge(knowledge);
            FontRenderer f = builder.mc.standardGalacticFontRenderer;
            int p = charon.getKnowledgeProgress(knowledge);

            if (charon.hasKnowledge(knowledge)) {
                p = 100;
                f = builder.mc.fontRenderer;
            }

            GuiElementSection subsection = new GuiElementSection(knowledge, section, 0, y, 244, 28, true);
            subsection.addElement(new GuiElementImage("image", subsection, iknowledge.getIcon(), 3, 3, 16, 16));
            subsection.addElement(new GuiElementText("text", subsection, 25, 3, iknowledge.getLocalisedName(), 0, 16777215, f));
            subsection.addElement((new GuiElementProgressBar("progress", subsection, 25, 14, 102, 0, p, false)).setHover(p + "%"));
            section.addElement(subsection);
            y += 34;
        }

        builder.addElement(section);
    }

    protected void buildAbilities(GuiBuilder builder)
    {
        builder.setHeading(I18n.format("technomagi.personalinterface.abilities.gui"));
        builder.setDimensions(250, 250);
    }

    @Override
    public void clicked(GuiBuilder builder, ContainerBuilder container, IGuiElement link)
    {
        IGuiElement prevLinkElement = linkElement;
        String prevLinkName = linkName;
        List<String> prevLinkMetadata = linkMetadata;

        super.clicked(builder, container, link);

        if (linkName.equals("up")) {
            GuiElementSection section = (GuiElementSection) linkElement.getParent();
            int scrollOffset = section.verticalOffset - 15;

            if (scrollOffset < 0) {
                scrollOffset = 0;
            }

            setScroll(section.getLink(), scrollOffset);
            super.clicked(builder, container, prevLinkElement);
        } else if (linkName.equals("down")) {
            GuiElementSection section = (GuiElementSection) linkElement.getParent();
            int scrollOffset = section.verticalOffset + 15;

            if (scrollOffset > (section.getInnerHeight() - (section.getHeight() - (section.background ? 6 : 0)))) {
                scrollOffset = (section.getInnerHeight() - (section.getHeight() - (section.background ? 6 : 0)));
            }

            setScroll(section.getLink(), scrollOffset);
            super.clicked(builder, container, prevLinkElement);
        } else if (linkName.equals("diagnosis")) {
            String subsection = getMetadata(0);

            if (subsection != null && subsection.equals("sync")) {
                ((ContainerPersonalInterface) container).toggleSyncing();
            }

        } else if (linkName.equals("information")) {
            String subsection = getMetadata(0);

            if (subsection == null || subsection.isEmpty()) {
                setScroll("information", 0);
            }

            for (Entry<String, Integer> entry : scroll.entrySet()) {
                if (entry.getKey().startsWith("information:")) {
                    scroll.remove(entry.getKey());
                }
            }
        }
    }
}