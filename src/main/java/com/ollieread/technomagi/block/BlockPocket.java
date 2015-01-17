package com.ollieread.technomagi.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.block.abstracts.BlockBasicContainer;
import com.ollieread.technomagi.tileentity.TileEntityPocketEnergy;
import com.ollieread.technomagi.tileentity.TileEntityPocketGrowth;
import com.ollieread.technomagi.tileentity.TileEntityPocketNanite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPocket extends BlockBasicContainer
{

    public static String[] blockNames = new String[] { "energy", "void", "nanite", "growth" };
    protected Random rand = new Random();

    public BlockPocket(String name)
    {
        super(Material.air, name);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return net.minecraft.init.Blocks.air.getIcon(p_149691_1_, p_149691_2_);
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
        }

        return null;
    }

}
