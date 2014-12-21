package com.ollieread.technomagi.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.TileEntityBattery;
import com.ollieread.technomagi.util.PlayerHelper;

public class BlockBattery extends BlockTMContainer
{

    public BlockBattery(String name)
    {
        super(Material.iron, name);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        setBlockTextureName("construct");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        if (meta == 0) {
            return new TileEntityBattery(10000, 100, 100);
        }

        return null;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.isRemote) {
            return true;
        } else {
            TileEntityBattery entity = (TileEntityBattery) world.getTileEntity(x, y, z);

            if (entity != null) {
                PlayerHelper.addChatMessage(player, "Energy: " + entity.getEnergyStored(null));
            }

            return true;
        }
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

}
