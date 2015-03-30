package com.ollieread.technomagi.common.block.structure;

import java.util.Random;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.client.renderers.blocks.BlockHardlightPaneRenderer;
import com.ollieread.technomagi.common.tabs.TechnomagiTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHardlightPane extends BlockPane
{

    protected String name;

    public BlockHardlightPane(String name)
    {
        super(name, Technomagi.MODID.toLowerCase() + ":hardlight", Material.glass, false);

        this.name = name;
        this.setCreativeTab(TechnomagiTabs.blocks);
        this.setBlockUnbreakable();
        this.setLightOpacity(0);
        this.setBlockTextureName("hardlight");
    }

    @Override
    public String getUnlocalizedName()
    {
        return "tile.technomagi." + name;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean canSilkHarvest()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public int getLightValue()
    {
        return 15;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public int getRenderType()
    {
        return BlockHardlightPaneRenderer.id;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Technomagi.MODID.toLowerCase() + ":" + getTextureName());
    }

}
