package com.ollieread.technomagi.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.StringUtils;

import scala.actors.threadpool.Arrays;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.client.gui.builder.GuiBuilder;
import com.ollieread.technomagi.client.gui.builder.GuiElementButton;
import com.ollieread.technomagi.client.gui.builder.GuiElementButtonText;
import com.ollieread.technomagi.client.gui.builder.GuiElementEntity;
import com.ollieread.technomagi.client.gui.builder.GuiElementImage;
import com.ollieread.technomagi.client.gui.builder.GuiElementProgressBar;
import com.ollieread.technomagi.client.gui.builder.GuiElementSection;
import com.ollieread.technomagi.client.gui.builder.GuiElementSectionScrollable;
import com.ollieread.technomagi.client.gui.builder.GuiElementTab;
import com.ollieread.technomagi.client.gui.builder.GuiElementText;
import com.ollieread.technomagi.client.gui.builder.GuiElementTextMulti;
import com.ollieread.technomagi.client.gui.builder.IGuiInstructions;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.ContainerPersonalInterface;
import com.ollieread.technomagi.inventory.abstracts.ContainerBuilder;
import com.ollieread.technomagi.item.ItemPersonalInterface;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPersonalInterface implements IGuiInstructions
{

    protected ExtendedPlayerKnowledge charon;
    protected List<String> linkParts;

    public void init(GuiBuilder builder, ContainerBuilder container)
    {
        charon = ExtendedPlayerKnowledge.get(builder.mc.thePlayer);
        builder.init(container.width, container.height);
    }

    public void build(GuiBuilder builder, ContainerBuilder container)
    {
        String link = container.getLink();
        String tab = "diagnosisTab";

        if (link != null && !link.isEmpty()) {
            linkParts = Arrays.asList(StringUtils.split(link, '/'));
            tab = linkParts.get(0);
        }

        // Add tabs
        GuiElementTab diagnosisTab = new GuiElementTab("diagnosisTab", null, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/diagnosis.png"), 0, 25, 3, tab.equals("diagnosisTab") || tab.equals("diagnosisSection"));
        GuiElementTab informationTab = new GuiElementTab("informationTab", null, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/information.png"), 0, 54, 3, tab.equals("informationTab") || tab.equals("informationLinks") || tab.equals("informationContent"));
        GuiElementTab intrigueTab = new GuiElementTab("intrigueTab", null, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/intrigue.png"), 0, 83, 3, tab.equals("intrigueTab") || tab.equals("intrigueSection"));
        GuiElementTab knowledgeTab = new GuiElementTab("knowledgeTab", null, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/knowledge.png"), 0, 112, 3, tab.equals("knowledgeTab") || tab.equals("knowledgeSection"));
        GuiElementTab abilitiesTab = new GuiElementTab("abilitiesTab", null, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/abilities.png"), 0, 141, 3, tab.equals("abilitiesTab") || tab.equals("abilitiesSection"));
        // Set tab hover text
        diagnosisTab.setHover(I18n.format("technomagi.tab.diagnosis.gui"));
        informationTab.setHover(I18n.format("technomagi.tab.information.gui"));
        intrigueTab.setHover(I18n.format("technomagi.tab.intrigue.gui"));
        knowledgeTab.setHover(I18n.format("technomagi.tab.knowledge.gui"));
        abilitiesTab.setHover(I18n.format("technomagi.tab.abilities.gui"));
        // Add to builder
        builder.addElement(diagnosisTab);
        builder.addElement(informationTab);
        builder.addElement(intrigueTab);
        builder.addElement(knowledgeTab);
        builder.addElement(abilitiesTab);

        if (tab.equals("diagnosisTab") || tab.equals("diagnosisSection")) {
            buildDiagnosis(builder, container);
        } else if (tab.equals("informationTab") || tab.equals("informationLinks") || tab.equals("informationContent")) {
            buildInformation(builder);
        } else if (tab.equals("intrigueTab") || tab.equals("intrigueSection")) {
            buildIntrigue(builder);
        } else if (tab.equals("knowledgeTab") || tab.equals("knowledgeSection")) {
            buildKnowledge(builder);
        } else if (tab.equals("abilitiesTab") || tab.equals("abilitiesSection")) {
            buildAbilities(builder);
        }
    }

    protected void buildDiagnosis(GuiBuilder builder, ContainerBuilder container)
    {
        builder.setHeading(I18n.format("technomagi.personalinterface.diagnosis.gui"));
        builder.setDimensions(196, 250);
        // Add information section
        GuiElementSection diagnosisSection = new GuiElementSection("diagnosisSection", null, 0, 0, 190, 226);
        GuiElementSection selfModel = new GuiElementSection("selfModel", diagnosisSection, 0, 0, 76, 120, true);
        GuiElementSection selfData = new GuiElementSection("selfData", diagnosisSection, 79, 0, 110, 49, true);
        // Add player details
        selfModel.addElement(new GuiElementImage("specialisation", selfModel, charon.getSpecialisation().getIcon(), 19, 3, 32, 32));
        selfModel.addElement(new GuiElementEntity("player", selfModel, builder.mc.thePlayer, 35, 107, 35));
        selfData.addElement(new GuiElementText("naniteName", selfData, 1, 4, I18n.format("technomagi.nanites"), 0, 16777215));
        selfData.addElement(new GuiElementText("nanitePercentage", selfData, 0, 4, charon.nanites.getNanites() + "%", 2, 4118771));
        selfData.addElement(new GuiElementProgressBar("naniteBar", selfData, 0, 15, 102, 0, charon.nanites.getNanites(), false));
        selfData.addElement(new GuiElementText("dataName", selfData, 1, 25, I18n.format("technomagi.data"), 0, 16777215));
        selfData.addElement(new GuiElementText("dataPercentage", selfData, 0, 25, charon.nanites.getData() + "%", 2, 15944766));
        selfData.addElement(new GuiElementProgressBar("dataBar", selfData, 0, 35, 102, 1, charon.nanites.getData(), false));
        // Add to information section
        diagnosisSection.addElement(selfModel);
        diagnosisSection.addElement(selfData);
        boolean syncing = ItemPersonalInterface.getSyncing(((ContainerPersonalInterface) container).personalInterface);
        diagnosisSection.addElement(new GuiElementButton("syncButton", null, 79, 52, 110, (syncing ? I18n.format("technomagi.stopsync") : I18n.format("technomagi.sync"))));
        // Add to builder
        builder.addElement(diagnosisSection);
    }

    protected void buildInformation(GuiBuilder builder)
    {
        builder.setHeading(I18n.format("technomagi.personalinterface.information.gui"));
        builder.setDimensions(250, 250);

        Map<String, List<String>> information = Information.getInformation("info");
        GuiElementSectionScrollable informationSection = null;

        if (linkParts.size() == 1) {
            informationSection = new GuiElementSectionScrollable("informationLinks", null, 0, 0, 244, 223);
            informationSection.scrollOffset = builder.getScroll("informationLinks");
            builder.setScroll("informationContent", 0);
            int y = 6;

            for (String entry : information.keySet()) {
                GuiElementButtonText infoButton = new GuiElementButtonText("information" + StringUtils.capitalize(entry), informationSection, 6, y, I18n.format("info." + entry));
                y += infoButton.getHeight(false) + 6;

                informationSection.addElement(infoButton);
            }
            builder.addElement(informationSection);
        } else {
            informationSection = new GuiElementSectionScrollable("informationContent", null, 0, 0, 244, 223);
            informationSection.scrollOffset = builder.getScroll("informationContent");
            String info = linkParts.get(1).substring(11, linkParts.get(1).length()).toLowerCase();
            List<String> content = information.get(info);
            String text = "";

            if (content != null) {

                for (String line : content) {
                    text += line + "\n\n";
                }
            }

            builder.setHeading(I18n.format("technomagi.personalinterface.information.gui") + " - " + I18n.format("info." + info));

            GuiElementTextMulti informationText = new GuiElementTextMulti("information" + StringUtils.capitalize(info), informationSection, 6, 6, 216, text, 16777215);

            informationSection.addElement(informationText);
        }

        builder.addElement(informationSection);
    }

    protected void buildIntrigue(GuiBuilder builder)
    {
        builder.setHeading(I18n.format("technomagi.personalinterface.intrigue.gui"));
        builder.setDimensions(250, 250);
        GuiElementSection intrigueSection = new GuiElementSectionScrollable("intrigueSection", null, 0, 0, 244, 223);

        Map<String, String[]> intrigue = charon.getIntrigue();
        int y = 0;

        for (Entry<String, String[]> entry : intrigue.entrySet()) {
            IKnowledge knowledge = ResearchRegistry.getKnowledge(entry.getKey());
            String[] textList = entry.getValue();
            String text = "";

            for (int i = 0; i < textList.length; i++) {
                text += textList[i] + "\n\n";
            }

            GuiElementSection knowledgeSection = new GuiElementSection("intrigueSection" + StringUtils.capitalize(entry.getKey()), intrigueSection, 0, y, 124, 0);
            GuiElementTextMulti knowledgeIntrigue = new GuiElementTextMulti("intrigue" + StringUtils.capitalize(entry.getKey()), knowledgeSection, 25, 3, 193, text, 16777215);
            GuiElementImage knowledgeIcon = new GuiElementImage("knowledgeIcon" + StringUtils.capitalize(entry.getKey()), knowledgeSection, knowledge.getIcon(), 3, 3, 16, 16);
            knowledgeSection.width = knowledgeIntrigue.getHeight(false);
            y += knowledgeSection.width + 12;

            knowledgeSection.addElement(knowledgeIntrigue);
            knowledgeSection.addElement(knowledgeIcon);
            intrigueSection.addElement(knowledgeSection);
        }

        builder.addElement(intrigueSection);
    }

    protected void buildKnowledge(GuiBuilder builder)
    {
        builder.setHeading(I18n.format("technomagi.personalinterface.knowledge.gui"));
        builder.setDimensions(250, 250);
        GuiElementSectionScrollable knowledgeSection = new GuiElementSectionScrollable("knowledgeSection", null, 0, 0, 244, 223);
        knowledgeSection.scrollOffset = builder.getScroll("knowledgeSection");

        List<String> knowledgeList = new ArrayList<String>();
        knowledgeList.addAll(charon.getResearchedKnowledge());
        knowledgeList.addAll(charon.getResearchingKnowledge());
        int y = 6;

        for (String knowledge : knowledgeList) {
            IKnowledge iknowledge = ResearchRegistry.getKnowledge(knowledge);

            GuiElementSection knowledgeItem = new GuiElementSection("knowledge" + StringUtils.capitalize(knowledge), knowledgeSection, 6, y, 130, 16);
            knowledgeItem.addElement(new GuiElementImage("knowledgeImage" + StringUtils.capitalize(knowledge), knowledgeItem, iknowledge.getIcon(), 0, 0, 16, 16));
            GuiElementText name = new GuiElementText("knowledgeText" + StringUtils.capitalize(knowledge), knowledgeItem, 22, 2, iknowledge.getLocalisedName(), 0, 16777215);

            if (!charon.hasKnowledge(knowledge)) {
                name.setFontRenderer(builder.mc.standardGalacticFontRenderer);
            }

            knowledgeItem.addElement(name);
            knowledgeItem.addElement(new GuiElementProgressBar("knowledgeProgress" + StringUtils.capitalize(knowledge), knowledgeItem, 22, 11, 102, 0, charon.getKnowledgeProgress(knowledge), false));
            knowledgeSection.addElement(knowledgeItem);
            y += 22;
        }

        builder.addElement(knowledgeSection);
    }

    protected void buildAbilities(GuiBuilder builder)
    {
        builder.setHeading(I18n.format("technomagi.personalinterface.abilities.gui"));
        builder.setDimensions(250, 250);
    }

    public void clicked(GuiBuilder builder, ContainerBuilder container, String link)
    {
        container.setLink(link);
    }

}