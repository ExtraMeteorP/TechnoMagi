package com.ollieread.technomagi.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDigitalVisor extends ItemArmor
{

    public ItemDigitalVisor(String name, int i, int j)
    {
        super(ArmorMaterial.IRON, i, j);

        setUnlocalizedName(name);
        setTextureName(name);
        setCreativeTab(TechnoMagi.tabTM);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return Reference.MODID.toLowerCase() + ":textures/armour/digital_layer_1.png";
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString());
    }
}
