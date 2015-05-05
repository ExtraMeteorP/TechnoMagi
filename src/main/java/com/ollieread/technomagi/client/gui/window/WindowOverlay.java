package com.ollieread.technomagi.client.gui.window;

import java.util.List;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.ability.IAbilityCast;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.client.gui.GuiBuilder.ProgressType;
import com.ollieread.technomagi.client.gui.component.Component;
import com.ollieread.technomagi.client.gui.component.ComponentProgress;
import com.ollieread.technomagi.client.gui.component.ComponentSelector;
import com.ollieread.technomagi.client.gui.component.ComponentVerticalList;
import com.ollieread.technomagi.client.gui.window.abstracts.Window;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.util.PlayerHelper;

public class WindowOverlay extends Window
{

    protected PlayerTechnomagi technomage;

    public WindowOverlay()
    {
        super(47, 102);

        this.technomage = PlayerHelper.getTechnomagi(Technomagi.proxy.getClientPlayer());
        ComponentVerticalList abilityList = new ComponentVerticalList(28, 120);
        abilityList.setPadding(5, 3);
        this.addComponent("ability_list", abilityList);

        this.addComponent("data", new ComponentProgress(100, ComponentOrientation.VERTICAL, ProgressType.DATA, 0)).setOffset(28, 3);
        this.addComponent("nanites", new ComponentProgress(100, ComponentOrientation.VERTICAL, ProgressType.NANITES, 0)).setOffset(36, 3);

        this.updateContent();
    }

    @Override
    public void updateContent()
    {
        Component component = this.getComponent("ability_list");

        if (component != null) {
            ComponentVerticalList abilityList = (ComponentVerticalList) component;
            abilityList.clearComponents();
            List<IAbilityCast> abilities = this.technomage.abilities().getAbilitiesForDisplay(Config.abilityCount);
            IAbilityCast current = this.technomage.abilities().getCurrentAbility();

            for (IAbilityCast ability : abilities) {
                int mode = this.technomage.abilities().getCastableAbilityMode(ability.getName());
                ComponentSelector selector = new ComponentSelector(ComponentSize.SMALL);
                selector.setImage(ability.getIcon(mode));

                if (ability.equals(current)) {
                    selector.setHover(true);
                }

                abilityList.addComponent(ability.getName(), selector);
            }
        }

        component = this.getComponent("data");

        if (component != null) {
            ComponentProgress data = (ComponentProgress) component;
            int count = (100 / this.technomage.nanites().getMaxData()) * this.technomage.nanites().getData();
            data.setPercentage(count);
        }

        component = this.getComponent("nanites");

        if (component != null) {
            ComponentProgress nanites = (ComponentProgress) component;
            int count = (100 / this.technomage.nanites().getMaxNanites()) * this.technomage.nanites().getNanites();
            nanites.setPercentage(count);
        }
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int clickedButton)
    {
        return false;
    }

    @Override
    public void mouseScrolled(int x, int y, int mouseX, int mouseY, int dwheel)
    {
    }

    @Override
    public void mouseHovered(int x, int y, int mouseX, int mouseY)
    {
    }

}
