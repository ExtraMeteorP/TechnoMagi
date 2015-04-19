package com.ollieread.technomagi.common.block.structure;

import net.minecraft.block.BlockStairs;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.tabs.TechnomagiTabs;

public class BlockHardlightStairs extends BlockStairs
{

    protected String name;

    public BlockHardlightStairs(String name)
    {
        super(Blocks.hardlight, 0);

        this.name = name;
        this.setCreativeTab(TechnomagiTabs.blocks);
    }

    @Override
    public String getUnlocalizedName()
    {
        return "tile.technomagi." + name;
    }

}
