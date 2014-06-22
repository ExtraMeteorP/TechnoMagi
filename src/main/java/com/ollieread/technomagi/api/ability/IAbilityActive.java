package com.ollieread.technomagi.api.ability;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.player.PlayerKnowledge;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IAbilityActive
{

    public String getName();

    @SideOnly(Side.CLIENT)
    public ResourceLocation getIcon();

    public boolean canUse(PlayerKnowledge charon, Event event);

    public boolean isAvailable(PlayerKnowledge charon);

    public boolean use(PlayerKnowledge charon, Event event);

    public String getLocalisedName();

}
