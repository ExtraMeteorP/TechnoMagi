package com.ollieread.technomagi.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

import com.ollieread.technomagi.Technomagi;

public abstract class BlockBaseContainer extends BlockContainer
{

    protected BlockBaseContainer(String name, Material material)
    {
        super(material);

        this.setBlockName(name);
    }

    public String getTexturePath(String name)
    {
        return Technomagi.MODID.toLowerCase() + ":" + name;
    }

}
