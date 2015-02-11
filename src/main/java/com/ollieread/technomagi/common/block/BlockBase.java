package com.ollieread.technomagi.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.ollieread.technomagi.Technomagi;

public class BlockBase extends Block
{

    protected BlockBase(String name, Material material)
    {
        super(material);

        this.setBlockName(name);
    }

    public String getTexturePath(String name)
    {
        return Technomagi.MODID.toLowerCase() + ":" + name;
    }

}
