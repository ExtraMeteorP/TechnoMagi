package com.ollieread.technomagi.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.block.abstracts.BlockBasicContainer;
import com.ollieread.technomagi.tileentity.TileEntityPocketEnergy;
import com.ollieread.technomagi.tileentity.TileEntityPocketGrowth;
import com.ollieread.technomagi.tileentity.TileEntityPocketNanite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPocket extends BlockBasicContainer implements IBlockMulti
{

    public static String[] blockNames = new String[] { "energy", "void", "nanite", "growth", "life", "light" };
    protected Random rand = new Random();

    public BlockPocket(String name)
    {
        super(Material.glass, name);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return net.minecraft.init.Blocks.air.getIcon(p_149691_1_, p_149691_2_);
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        int x = 0;

        for (int i = 0; i < blockNames.length; i++) {
            list.add(new ItemStack(item, 1, x));
            x++;
            list.add(new ItemStack(item, 1, x));
            x++;
        }
    }

    @Override
    public String getName(int metadata)
    {
        return blockNames[metadata / 2];
    }

    public int getLightOpacity(IBlockAccess world, int x, int y, int z)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        return metadata == 10 ? 0 : (metadata == 11 ? 255 : getLightOpacity());
    }

    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        return metadata == 10 ? 15 : (metadata == 11 ? 0 : getLightValue());
    }

    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }

    public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        boolean negative = metadata % 2 != 0;
        int size = 16 + rand.nextInt(33);

        switch (metadata / 2) {
            case 0:
                return new TileEntityPocketEnergy(negative, size);
            case 1:
                return null;
            case 2:
                return new TileEntityPocketNanite(negative, size);
            case 3:
                return new TileEntityPocketGrowth(negative, size);
            case 4:
                return null;
            case 5:
                return null;
        }

        return null;
    }

    public int getRenderType()
    {
        return -1;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
    {
        return false;
    }

    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
    {
    }

    public boolean canSilkHarvest()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

}
