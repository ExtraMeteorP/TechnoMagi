package com.ollieread.technomagi.client.gui;

import java.util.Collection;

import net.minecraft.client.resources.I18n;

import com.ollieread.ennds.EnndsRegistry;
import com.ollieread.ennds.ISpecialisation;
import com.ollieread.ennds.common.PacketHelper;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.client.gui.abstracts.GuiInstructions;
import com.ollieread.technomagi.client.gui.elements.GuiElementButton;
import com.ollieread.technomagi.client.gui.elements.GuiElementSection;
import com.ollieread.technomagi.client.gui.elements.GuiElementSelector;
import com.ollieread.technomagi.client.gui.elements.GuiElementText;
import com.ollieread.technomagi.client.gui.elements.IGuiElement;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.inventory.abstracts.ContainerBuilder;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSpecialisation extends GuiInstructions
{

    public static String choice = "";

    @Override
    public void init(GuiBuilder builder, ContainerBuilder container)
    {

    }

    @Override
    public void build(GuiBuilder builder, ContainerBuilder container)
    {
        builder.setDimensions(200, 227).setHeading(I18n.format("gui.tm.specialise.choice"));

        Collection<ISpecialisation> specialisations = EnndsRegistry.getSpecialisations();

        int p = (builder.width - 6) / 32;
        int x = p;

        for (ISpecialisation spec : specialisations) {
            GuiElementSelector area = new GuiElementSelector(spec.getName(), null, spec.getIcon(), x, 6, true, choice.equals(spec.getName()));
            area.setLink("specSelector:" + spec.getName());
            builder.addElement(area);
            x += 32 + p;
        }

        GuiElementSection section = new GuiElementSection("info", null, 0, 44, 194, 162, true);
        section.setLink("info:specialise");
        boolean enabled = false;

        if (linkName.equals("specSelector")) {
            String selectedSpec = getMetadata(0);

            if (selectedSpec != null && !selectedSpec.isEmpty()) {
                GuiElementText title = new GuiElementText("title", section, 3, 3, EnndsRegistry.getSpecialisation(selectedSpec).getLocalisedName(), 0, 2529246, builder.mc.fontRenderer);
                section.addElement(title);
                section.addElement(new GuiElementText("content", section, 3, 17, 180, Information.getInformationParagraphs("specialisations", selectedSpec), 0, 16777215, builder.mc.fontRenderer));
            }

            enabled = true;
            section.setScrollVertical(true).verticalOffset = this.getScroll("info:specialise");
        }

        builder.addElement(section);
        GuiElementButton button = new GuiElementButton("specialise", null, 7, 210, 180, I18n.format("gui.tm.specialise.complete"));
        button.setLink("specialise");

        if (!enabled) {
            button.setDisabled();
        }

        builder.addElement(button);

    }

    @Override
    public void clicked(GuiBuilder builder, ContainerBuilder container, IGuiElement element)
    {
        IGuiElement prevLinkElement = linkElement;

        super.clicked(builder, container, element);

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
        } else if (linkName.equals("specSelector")) {
            setScroll("info:specialise", 0);
            String selectedSpec = getMetadata(0);
            System.out.println(selectedSpec);

            if (selectedSpec != null && !selectedSpec.isEmpty()) {
                choice = selectedSpec;
            }
        } else if (linkName.equals("specialise")) {
            if (choice != null && !choice.isEmpty()) {
                setScroll("info:specialise", 0);
                ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(builder.mc.thePlayer);
                charon.setSpecialisation(choice);
                PacketHelper.specialisationPacket(choice);
                builder.mc.displayGuiScreen(null);
            }
        }
    }

    public void onGuiClosed()
    {
        this.choice = null;
    }

}