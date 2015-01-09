package com.ollieread.technomagi.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemSword;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEnergySword extends ItemSword
{

    public ItemEnergySword(String name)
    {
        super(ToolMaterial.EMERALD);

        setMaxStackSize(1);
        setUnlocalizedName(name);
        setTextureName(name);
        setCreativeTab(TechnoMagi.tabTM);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString());
    }

}
