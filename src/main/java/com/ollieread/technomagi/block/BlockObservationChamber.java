package com.ollieread.technomagi.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.TileEntityObservationChamber;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockObservationChamber extends BlockOwnable
{

    public BlockObservationChamber(String name)
    {
        super(Material.iron, name);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F);
        setBlockTextureName("construct");
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

    @Override
    public TileEntity createNewTileEntity(World world, int var2)
    {
        return new TileEntityObservationChamber();
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

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 2) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3) {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }

        world.setBlock(x, y + 1, z, Blocks.blockChamberFiller);
        world.setBlockMetadataWithNotify(x, y + 1, z, 0, 2);
        world.setBlock(x, y + 2, z, Blocks.blockChamberFiller);
        world.setBlockMetadataWithNotify(x, y + 1, z, 1, 2);

        super.onBlockPlacedBy(world, x, y, z, entity, stack);
    }

    public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int face)
    {
        return true;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (player != null) {
            ItemStack stack = player.getHeldItem();

            if (stack != null && stack.getItem() != null && stack.getItem().equals(Items.spawn_egg)) {
                TileEntityObservationChamber tile = (TileEntityObservationChamber) world.getTileEntity(x, y, z);

                if (tile != null) {
                    tile.setEntity(Integer.valueOf(stack.getItemDamage()));
                    world.markBlockForUpdate(x, y, z);

                    return true;
                }
            }
        }

        return false;
    }

    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta)
    {
        if (world.getBlock(x, y + 1, z).equals(Blocks.blockChamberFiller)) {
            world.setBlockToAir(x, y + 1, z);
        }

        if (world.getBlock(x, y + 2, z).equals(Blocks.blockChamberFiller)) {
            world.setBlockToAir(x, y + 2, z);
        }
    }
}
