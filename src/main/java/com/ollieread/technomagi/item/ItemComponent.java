package com.ollieread.technomagi.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemComponent extends ItemTM
{

    protected IIcon[] itemIcons = new IIcon[23];

    public ItemComponent(String name)
    {
        super(name);

        setHasSubtypes(true);
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + "." + stack.getItemDamage() + ".name")).trim();
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString());

        itemIcons[0] = register.registerIcon(Reference.MODID.toLowerCase() + ":ironDust");
        itemIcons[1] = register.registerIcon(Reference.MODID.toLowerCase() + ":ironSheet");
        itemIcons[2] = register.registerIcon(Reference.MODID.toLowerCase() + ":ironRod");
        itemIcons[3] = register.registerIcon(Reference.MODID.toLowerCase() + ":goldDust");
        itemIcons[4] = register.registerIcon(Reference.MODID.toLowerCase() + ":goldSheet");
        itemIcons[5] = register.registerIcon(Reference.MODID.toLowerCase() + ":goldRod");
        itemIcons[6] = register.registerIcon(Reference.MODID.toLowerCase() + ":diamondDust");
        itemIcons[7] = register.registerIcon(Reference.MODID.toLowerCase() + ":diamondSheet");
        itemIcons[8] = register.registerIcon(Reference.MODID.toLowerCase() + ":diamondRod");
        itemIcons[9] = register.registerIcon(Reference.MODID.toLowerCase() + ":basicPowerWater");
        itemIcons[10] = register.registerIcon(Reference.MODID.toLowerCase() + ":analysisUnit");
        itemIcons[11] = register.registerIcon(Reference.MODID.toLowerCase() + ":lifeUnit");
        itemIcons[12] = register.registerIcon(Reference.MODID.toLowerCase() + ":naniteUnit");
        itemIcons[13] = register.registerIcon(Reference.MODID.toLowerCase() + ":powerUnit");
        itemIcons[14] = register.registerIcon(Reference.MODID.toLowerCase() + ":teleportUnit");
        itemIcons[15] = register.registerIcon(Reference.MODID.toLowerCase() + ":lifeUnitAdvanced");
        itemIcons[16] = register.registerIcon(Reference.MODID.toLowerCase() + ":naniteUnitAdvanced");
        itemIcons[17] = register.registerIcon(Reference.MODID.toLowerCase() + ":powerUnitAdvanced");
        itemIcons[18] = register.registerIcon(Reference.MODID.toLowerCase() + ":teleportUnitAdvanced");
        itemIcons[19] = register.registerIcon(Reference.MODID.toLowerCase() + ":lifeUnitElite");
        itemIcons[20] = register.registerIcon(Reference.MODID.toLowerCase() + ":naniteUnitElite");
        itemIcons[21] = register.registerIcon(Reference.MODID.toLowerCase() + ":powerUnitElite");
        itemIcons[22] = register.registerIcon(Reference.MODID.toLowerCase() + ":teleportUnitElite");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
        return itemIcons[damage];
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < itemIcons.length; i++) {
            list.add(new ItemStack(this, 1, i));
        }

    }

}
