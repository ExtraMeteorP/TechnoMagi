package com.ollieread.technomagi.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.IPlayerLocked;
import com.ollieread.technomagi.tileentity.TileEntityPerceptionFilter;

public class BlockPerceptionFilter extends BlockTMContainer
{

    public BlockPerceptionFilter(String name)
    {
        super(Material.iron, name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityPerceptionFilter();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        IPlayerLocked te = (IPlayerLocked) world.getTileEntity(x, y, z);
        ((IPlayerLocked) te).setPlayer(((EntityPlayer) entity).getCommandSenderName());

        super.onBlockPlacedBy(world, x, y, z, entity, stack);
    }

}
