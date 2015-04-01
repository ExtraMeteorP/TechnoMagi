package com.ollieread.technomagi.common.block.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.fluids.BlockFluidFinite;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.common.init.Fluids;
import com.ollieread.technomagi.common.tabs.TechnomagiTabs;

public class BlockEnrichedFluid extends BlockFluidFinite
{

    protected String name;

    public BlockEnrichedFluid(String name)
    {
        super(Fluids.enriched, Material.water);

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
    public void registerBlockIcons(IIconRegister register)
    {
        super.registerBlockIcons(register);

        Fluids.enriched.setIcons(this.blockIcon);
    }

}
