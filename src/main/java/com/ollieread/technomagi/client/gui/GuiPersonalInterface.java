package com.ollieread.technomagi.client.gui;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.StringUtils;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.client.gui.builder.GuiBuilder;
import com.ollieread.technomagi.client.gui.builder.GuiElementButton;
import com.ollieread.technomagi.client.gui.builder.GuiElementEntity;
import com.ollieread.technomagi.client.gui.builder.GuiElementImage;
import com.ollieread.technomagi.client.gui.builder.GuiElementProgressBar;
import com.ollieread.technomagi.client.gui.builder.GuiElementSection;
import com.ollieread.technomagi.client.gui.builder.GuiElementSectionScrollable;
import com.ollieread.technomagi.client.gui.builder.GuiElementTab;
import com.ollieread.technomagi.client.gui.builder.GuiElementText;
import com.ollieread.technomagi.client.gui.builder.GuiElementTextMulti;
import com.ollieread.technomagi.client.gui.builder.IGuiInstructions;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.abstracts.ContainerBuilder;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPersonalInterface implements IGuiInstructions
{

    protected ExtendedPlayerKnowledge charon;
    protected String[] linkParts;

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
            linkParts = StringUtils.split(link, '/');
            tab = linkParts[0];
        }

        // Add tabs
        GuiElementTab diagnosisTab = new GuiElementTab("diagnosisTab", null, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/diagnosis.png"), 0, 25, 3, tab.equals("diagnosisTab"));
        GuiElementTab intrigueTab = new GuiElementTab("intrigueTab", null, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/intrigue.png"), 0, 54, 3, tab.equals("intrigueTab"));
        GuiElementTab knowledgeTab = new GuiElementTab("knowledgeTab", null, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/knowledge.png"), 0, 83, 3, tab.equals("knowledgeTab"));
        GuiElementTab abilitiesTab = new GuiElementTab("abilitiesTab", null, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/abilities.png"), 0, 112, 3, tab.equals("abilitiesTab"));
        // Set tab hover text
        diagnosisTab.setHover(I18n.format("technomagi.tab.diagnosis.gui"));
        intrigueTab.setHover(I18n.format("technomagi.tab.intrigue.gui"));
        knowledgeTab.setHover(I18n.format("technomagi.tab.knowledge.gui"));
        abilitiesTab.setHover(I18n.format("technomagi.tab.abilities.gui"));
        // Add to builder
        builder.addElement(diagnosisTab);
        builder.addElement(intrigueTab);
        builder.addElement(knowledgeTab);
        builder.addElement(abilitiesTab);

        if (tab.equals("diagnosisTab")) {
            buildDiagnosis(builder);
        } else if (tab.equals("intrigueTab")) {
            buildIntrigue(builder);
        } else if (tab.equals("knowledgeTab")) {
            buildKnowledge(builder);
        } else if (tab.equals("abilitiesTab")) {
            buildAbilities(builder);
        }
    }

    protected void buildDiagnosis(GuiBuilder builder)
    {
        builder.setHeading(I18n.format("technomagi.personalinterface.diagnosis.gui"));
        // Add information section
        GuiElementSection selfInformation = new GuiElementSection("selfInformation", null, 0, 0, 190, 226);
        GuiElementSection selfModel = new GuiElementSection("selfModel", selfInformation, 0, 0, 76, 120, true);
        GuiElementSection selfData = new GuiElementSection("selfData", selfInformation, 79, 0, 110, 49, true);
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
        selfInformation.addElement(selfModel);
        selfInformation.addElement(selfData);
        selfInformation.addElement(new GuiElementButton("syncButton", null, 79, 52, 110, I18n.format("technomagi.sync")));
        // Add to builder
        builder.addElement(selfInformation);
    }

    protected void buildIntrigue(GuiBuilder builder)
    {
        builder.setHeading(I18n.format("technomagi.personalinterface.intrigue.gui"));
        GuiElementSection intrigueSection = new GuiElementSectionScrollable("intrigueSection", null, 0, 0, 190, 223);

        Map<String, String[]> intrigue = charon.getIntrigue();
        int y = 0;

        for (Entry<String, String[]> entry : intrigue.entrySet()) {
            IKnowledge knowledge = ResearchRegistry.getKnowledge(entry.getKey());
            String[] textList = entry.getValue();
            String text = "";

            for (int i = 0; i < textList.length; i++) {
                text += textList[i] + "\n\n";
            }

            GuiElementSection knowledgeSection = new GuiElementSection("intrigueSection" + StringUtils.capitalize(entry.getKey()), intrigueSection, 0, y, 170, 0);
            GuiElementTextMulti knowledgeIntrigue = new GuiElementTextMulti("intrigue" + StringUtils.capitalize(entry.getKey()), knowledgeSection, 25, 3, 139, text, 16777215);
            GuiElementImage knowledgeIcon = new GuiElementImage("knowledgeIcon" + StringUtils.capitalize(entry.getKey()), knowledgeSection, knowledge.getIcon(), 3, 3, 16, 16);
            knowledgeSection.width = knowledgeIntrigue.getHeight(false);
            y += knowledgeSection.width + 6;

            knowledgeSection.addElement(knowledgeIntrigue);
            knowledgeSection.addElement(knowledgeIcon);
            intrigueSection.addElement(knowledgeSection);
        }

        builder.addElement(intrigueSection);
    }

    protected void buildKnowledge(GuiBuilder builder)
    {
    }

    protected void buildAbilities(GuiBuilder builder)
    {
    }

    public void clicked(GuiBuilder builder, ContainerBuilder container, String link)
    {
        container.setLink(link);
    }

}