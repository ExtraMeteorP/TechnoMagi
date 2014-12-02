package com.ollieread.technomagi.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.IHasFiller;
import com.ollieread.technomagi.tileentity.IPlayerLocked;

public abstract class BlockOwnable extends BlockTMContainer
{

    protected BlockOwnable(Material material, String name)
    {
        super(material, name);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            IPlayerLocked tile = (IPlayerLocked) tileEntity;

            tile.setPlayer(player.getCommandSenderName());
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof IHasFiller) {
            ((IHasFiller) tileEntity).destroy();
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
    {
        if (!world.isRemote) {
            float motion = 0.7F;
            double motionX = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;
            double motionY = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;
            double motionZ = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;

            TileEntity tile = world.getTileEntity(x, y, z);

            if (tile instanceof IInventory) {
                IInventory inventory = (IInventory) tile;

                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack slot = inventory.getStackInSlot(i);

                    if (slot != null) {
                        EntityItem entity = new EntityItem(world, x + motionX, y + motionY, z + motionZ, slot);
                        world.spawnEntityInWorld(entity);
                    }
                }
            }

            if (!player.capabilities.isCreativeMode && canHarvestBlock(player, world.getBlockMetadata(x, y, z))) {
                EntityItem entityItem = new EntityItem(world, x + motionX, y + motionY, z + motionZ, getPickBlock(null, world, x, y, z));
                world.spawnEntityInWorld(entityItem);
            }
        }

        return world.setBlockToAir(x, y, z);
    }
}
