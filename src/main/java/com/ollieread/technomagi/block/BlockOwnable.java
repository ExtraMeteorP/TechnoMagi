package com.ollieread.technomagi.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.IHasFiller;
import com.ollieread.technomagi.tileentity.TileEntityPlayerLocked;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockOwnable extends BlockContainer
{

    protected BlockOwnable(Material material, String name)
    {
        super(material);

        setBlockName(name);
        setBlockTextureName(name);
        setCreativeTab(TechnoMagi.tabTM);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            TileEntityPlayerLocked tile = (TileEntityPlayerLocked) tileEntity;

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
        if (!player.capabilities.isCreativeMode && !world.isRemote && canHarvestBlock(player, world.getBlockMetadata(x, y, z))) {
            float motion = 0.7F;
            double motionX = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;
            double motionY = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;
            double motionZ = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;

            EntityItem entityItem = new EntityItem(world, x + motionX, y + motionY, z + motionZ, getPickBlock(null, world, x, y, z));

            world.spawnEntityInWorld(entityItem);
        }

        return world.setBlockToAir(x, y, z);
    }

}
