package com.ollieread.technomagi.block;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.IDisguisableTile;
import com.ollieread.technomagi.tileentity.TileEntityReactiveCrafting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockReactiveCrafting extends BlockTMContainer
{

    public BlockReactiveCrafting(String name)
    {
        super(Material.rock, name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityReactiveCrafting();
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        TileEntityReactiveCrafting reactive = (TileEntityReactiveCrafting) world.getTileEntity(x, y, z);

        if (reactive != null) {
            if (reactive.hasCrafted()) {
                ret.add(reactive.getResult().copy());

                return ret;
            }
        }

        return super.getDrops(world, x, y, z, metadata, fortune);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof IDisguisableTile) {
            IDisguisableTile disguise = (IDisguisableTile) tile;

            if (disguise.isDisguised()) {
                ItemStack stack = disguise.getDisguise();

                if (stack != null && stack.getItem() != null) {
                    Block block = Block.getBlockFromItem(stack.getItem());

                    if (block != null) {
                        return block.getIcon(side, stack.getItemDamage());
                    }
                }
            }
        }

        return this.getIcon(side, world.getBlockMetadata(x, y, z));
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof IDisguisableTile) {
            IDisguisableTile disguise = (IDisguisableTile) tile;

            if (disguise.isDisguised()) {
                ItemStack stack = disguise.getDisguise();

                if (stack != null && stack.getItem() != null) {
                    Block block = Block.getBlockFromItem(stack.getItem());

                    if (block != null) {
                        return block.colorMultiplier(world, x, y, z);
                    }
                }
            }
        }

        return super.colorMultiplier(world, x, y, z);
    }

}
