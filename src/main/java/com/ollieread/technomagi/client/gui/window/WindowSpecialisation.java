package com.ollieread.technomagi.client.gui.window;

import java.util.Collection;
import java.util.List;

import net.minecraft.util.StatCollector;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.specialisation.Specialisation;
import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.client.gui.component.Component;
import com.ollieread.technomagi.client.gui.component.ComponentButton;
import com.ollieread.technomagi.client.gui.component.ComponentButton.ButtonType;
import com.ollieread.technomagi.client.gui.component.ComponentHorizontalList;
import com.ollieread.technomagi.client.gui.component.ComponentSelector;
import com.ollieread.technomagi.client.gui.component.ComponentVerticalList;
import com.ollieread.technomagi.client.gui.component.IClickHandler;
import com.ollieread.technomagi.client.gui.content.Content;
import com.ollieread.technomagi.client.gui.content.ContentLoader;
import com.ollieread.technomagi.client.gui.content.Section;
import com.ollieread.technomagi.client.gui.content.element.Element;
import com.ollieread.technomagi.client.gui.window.abstracts.Window;
import com.ollieread.technomagi.util.PlayerHelper;

public class WindowSpecialisation extends Window implements IClickHandler
{

    protected String currentSpec = "";
    protected Content content;

    public WindowSpecialisation()
    {
        super(220, 250);
        this.setPadding(3, 3);
        this.setBackground(true);

        this.content = ContentLoader.get("specialisations", "/assets/" + Technomagi.MODID.toLowerCase() + "/content/specialisations.xml");
        this.setHeading(StatCollector.translateToLocal("gui.technomagi.specialisation"));
        Collection<Specialisation> specialisations = TechnomagiApi.specialisation().getSpecialisations();
        ComponentHorizontalList buttonList = new ComponentHorizontalList(214, 44);
        int p = 8;
        buttonList.setPadding(p, 0);

        for (Specialisation spec : specialisations) {
            ComponentSelector button = (ComponentSelector) buttonList.addComponent(spec.getName(), new ComponentSelector(ComponentSize.LARGE));
            button.setImage(spec.getIcon()).setClickHandler(this);
        }

        this.addComponent("specialisations", buttonList);

        ComponentVerticalList list = new ComponentVerticalList(214, 154, true);
        list.setBackground(true).setPadding(6, 6).setOffset(0, 40);
        this.addComponent("content", list);
        list.setClickHandler(this);

        ComponentButton specialiseButton = new ComponentButton(214, ButtonType.DEFAULT);
        specialiseButton.setText(StatCollector.translateToLocal("gui.technomagi.specialise.choose"));
        this.addComponent("specialise_button", specialiseButton).setOffset(0, 198).setClickHandler(this);
    }

    @Override
    public void updateContent()
    {
        ComponentVerticalList list = (ComponentVerticalList) this.getComponent("content");
        list.clearComponents();
        list.setScroll(0);

        if (!currentSpec.isEmpty()) {
            Section section = content.getSection(currentSpec);

            if (section != null) {
                List<Element> elements = section.getElements(0);
                list.addElements(elements);
            }
        }
        ((ComponentButton) this.getComponent("specialise_button")).setActive(true);
        ComponentHorizontalList specialisations = (ComponentHorizontalList) this.getComponent("specialisations");

        if (specialisations != null) {
            for (Component component : specialisations.getComponents()) {
                if (component instanceof ComponentSelector) {
                    if (component.getName().equals(currentSpec)) {
                        ((ComponentSelector) component).setActive(true);
                    } else {
                        ((ComponentSelector) component).setActive(false);
                    }
                }
            }

            Component specButton = specialisations.getComponent(currentSpec);

            if (specButton != null) {
                ((ComponentSelector) specButton).setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(Component component)
    {
        if (component instanceof ComponentSelector) {
            String spec = component.getName();

            if (!currentSpec.equals(spec)) {
                currentSpec = spec;
                updateContent();
            }
        } else if (component instanceof ComponentButton) {
            ButtonType type = ((ComponentButton) component).getType();

            if (type.equals(ButtonType.SCROLL_DOWN)) {
                ((ComponentVerticalList) component.getParent()).scrollDown();
            } else if (type.equals(ButtonType.SCROLL_UP)) {
                ((ComponentVerticalList) component.getParent()).scrollUp();
            } else if (component.getName() != null && component.getName().equals("specialise_button")) {
                if (!currentSpec.isEmpty()) {
                    PlayerHelper.specialise(Technomagi.proxy.getClientPlayer(), currentSpec);
                    GuiBuilder.instance.mc.displayGuiScreen(null);
                }
            }
        }
    }
}
