package com.ollieread.technomagi.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.client.gui.abstracts.GuiInstructions;
import com.ollieread.technomagi.client.gui.elements.GuiElementButton;
import com.ollieread.technomagi.client.gui.elements.GuiElementPlayerInventory;
import com.ollieread.technomagi.client.gui.elements.GuiElementSlot;
import com.ollieread.technomagi.client.gui.elements.IGuiElement;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.inventory.ContainerStaff;
import com.ollieread.technomagi.inventory.abstracts.ContainerBuilder;
import com.ollieread.technomagi.item.ItemStaff;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiStaff extends GuiInstructions
{

    protected ItemStack staff;
    protected EntityPlayer player;

    @Override
    public void init(GuiBuilder builder, ContainerBuilder container)
    {
        staff = ((ContainerStaff) container).staff;
        player = ((ContainerStaff) container).player;
    }

    @Override
    public void build(GuiBuilder builder, ContainerBuilder container)
    {
        builder.setDimensions(container.width, container.height).setHeading(I18n.format("gui.tm.staff"));

        builder.addElement(new GuiElementSlot("slot1", null, 20, 0));
        builder.addElement(new GuiElementSlot("slot2", null, 60, 0));
        builder.addElement(new GuiElementSlot("slot3", null, 100, 0));
        builder.addElement(new GuiElementSlot("slot4", null, 140, 0));

        GuiElementButton button = new GuiElementButton("activate", null, 39, 25, 100, I18n.format("gui.tm.activate"));
        button.setLink("active");

        if (!ItemStaff.isComplete(staff)) {
            button.setDisabled();
        }

        builder.addElement(button);
        builder.addElement(new GuiElementPlayerInventory("inventory", null, 2, 48));
    }

    @Override
    public void clicked(GuiBuilder builder, ContainerBuilder container, IGuiElement link)
    {
        super.clicked(builder, container, link);

        if (linkName.equals("active")) {
            player.openGui(TechnoMagi.instance, CommonProxy.GUI_SPECIALISATION, player.worldObj, 0, 0, 0);
        }
    }
}