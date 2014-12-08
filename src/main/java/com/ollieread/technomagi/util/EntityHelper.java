package com.ollieread.technomagi.util;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class EntityHelper
{

    public static boolean isStoodOn(EntityLivingBase player, Block block)
    {
        if (getBlockStoodOn(player).equals(block)) {
            return true;
        }

        return false;
    }

    public static Block getBlockStoodOn(EntityLivingBase player)
    {
        int x = MathHelper.floor_double(player.posX);
        int y = MathHelper.floor_double(player.posY) - 1;
        int z = MathHelper.floor_double(player.posZ);

        return player.worldObj.getBlock(x, y, z);
    }

    public static boolean isStoodOnMeta(EntityLivingBase player, Block block, int meta)
    {
        if (getBlockStoodOnMeta(player).isItemEqual(new ItemStack(block, 1, meta))) {
            return true;
        }

        return false;
    }

    public static ItemStack getBlockStoodOnMeta(EntityLivingBase player)
    {
        int x = MathHelper.floor_double(player.posX);
        int y = MathHelper.floor_double(player.posY) - 1;
        int z = MathHelper.floor_double(player.posZ);

        return new ItemStack(player.worldObj.getBlock(x, y, z), 1, player.worldObj.getBlockMetadata(x, y, z));
    }

    public static TileEntity getTileEntityStoodOn(EntityLivingBase player)
    {
        int x = MathHelper.floor_double(player.posX);
        int y = MathHelper.floor_double(player.posY) - 1;
        int z = MathHelper.floor_double(player.posZ);

        return player.worldObj.getTileEntity(x, y, z);
    }

    public static Vec3 getLookVector(EntityLivingBase player)
    {
        Vec3 look = player.getLookVec();

        return look;
    }

    public static Vec3 getEyeVector(EntityLivingBase player)
    {
        Vec3 vec = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);

        if (!player.worldObj.isRemote) {
            vec.yCoord += player.getEyeHeight();

            if (player instanceof EntityLivingBase && player.isSneaking()) {
                vec.yCoord -= 0.08;
            }
        }

        return vec;
    }

}
