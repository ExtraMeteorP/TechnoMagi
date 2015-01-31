package com.ollieread.technomagi.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block
{

    protected BlockBase(String name, Material material)
    {
        super(material);

        this.setBlockName(name);
    }

}
