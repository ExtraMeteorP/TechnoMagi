package com.ollieread.technomagi.block;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.TileEntityArchive;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockArchive extends BlockContainer {

	public BlockArchive(String name)
	{
		super(Material.iron);

		setBlockName(name);
		setBlockTextureName(name);
	}
	
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

	@Override
	public TileEntity createNewTileEntity(World world, int var2)
	{
		return new TileEntityArchive();
	}

}
