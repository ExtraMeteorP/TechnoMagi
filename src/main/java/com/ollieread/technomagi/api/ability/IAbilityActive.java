package com.ollieread.technomagi.api.ability;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IAbilityActive
{

    public String getName();

    @SideOnly(Side.CLIENT)
    public ResourceLocation getIcon();

    public boolean canUse(ExtendedPlayerKnowledge charon, Event event);

    public boolean isAvailable(ExtendedPlayerKnowledge charon);

    public boolean use(ExtendedPlayerKnowledge charon, Event event);

    public String getLocalisedName();

}
