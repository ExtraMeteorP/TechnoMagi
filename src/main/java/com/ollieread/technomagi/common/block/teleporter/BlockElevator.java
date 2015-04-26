package com.ollieread.technomagi.common.block.teleporter;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockBaseContainer;
import com.ollieread.technomagi.common.block.teleporter.tile.TileElevator;
import com.ollieread.technomagi.util.ResourceHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockElevator extends BlockBaseContainer
{

    @SideOnly(Side.CLIENT)
    protected IIcon blockIcons[];

    public BlockElevator(String name)
    {
        super(name, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileElevator();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.blockIcons = new IIcon[2];
        this.blockIcons[0] = register.registerIcon(ResourceHelper.icon("machine/electric/generic_side"));
        this.blockIcons[1] = register.registerIcon(ResourceHelper.icon("machine/electric/elevator_top"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 1) {
            return this.blockIcons[1];
        }

        return this.blockIcons[0];
    }

}
