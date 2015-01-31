package com.ollieread.technomagi.common.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class BlockBaseContainer extends BlockContainer
{

    protected BlockBaseContainer(String name, Material material)
    {
        super(material);

        this.setBlockName(name);
    }

}
