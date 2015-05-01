package com.ollieread.technomagi.common.block.structure;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.ollieread.technomagi.client.renderers.blocks.BlockDisguisedRenderer;
import com.ollieread.technomagi.common.block.BlockBaseContainer;
import com.ollieread.technomagi.common.block.structure.tile.TileShifted;
import com.ollieread.technomagi.util.BlockHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockShifted extends BlockBaseContainer
{

    public BlockShifted(String name)
    {
        super(name, Material.glass);

        setCreativeTab(null);
        setBlockUnbreakable();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileShifted();
    }

    @Override
    public void registerTiles()
    {
        BlockHelper.registerTileEntity(TileShifted.class, "shifted");
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {

    }

    @Override
    public int getRenderType()
    {
        return BlockDisguisedRenderer.id;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public boolean canRenderInPass(int pass)
    {
        return pass == 1;
    }

    @Override
    public boolean canSilkHarvest()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
    }

}
