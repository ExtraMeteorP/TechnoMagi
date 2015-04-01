package com.ollieread.technomagi.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.common.tabs.TechnomagiTabs;

public class BlockBase extends Block
{

    protected String name;

    protected BlockBase(String name, Material material)
    {
        super(material);

        this.name = name;
        this.setBlockTextureName(getTexturePath(name));
        this.setCreativeTab(TechnomagiTabs.blocks);
    }

    public String getTexturePath(String name)
    {
        return Technomagi.MODID.toLowerCase() + ":" + name;
    }

    @Override
    public String getUnlocalizedName()
    {
        return "tile.technomagi." + name;
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
    }

}
