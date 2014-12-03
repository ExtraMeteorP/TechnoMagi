package com.ollieread.technomagi.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.IHasFiller;
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
        super.onBlockPlacedBy(world, x, y, z, entity, stack);

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

        TileEntity tileEntity = world.getTileEntity(x, y, z);

        ((IHasFiller) tileEntity).create();
    }

    @Override
    public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int face)
    {
        return true;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        float f = 0.0625F;
        return AxisAlignedBB.getBoundingBox((double) ((float) x + f), (double) y, (double) ((float) z + f), (double) ((float) (x + 1) - f), (double) ((float) (y + 1) - f), (double) ((float) (z + 1) - f));
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (player != null) {
            ItemStack stack = player.getHeldItem();

            if (!world.isRemote) {
                TileEntityObservationChamber tile = (TileEntityObservationChamber) world.getTileEntity(x, y, z);

                if (tile != null) {
                    if (tile.isPlayer(player)) {
                        player.openGui(TechnoMagi.instance, CommonProxy.GUI_OBSERVATION, world, x, y, z);
                    }
                }
            }
        }

        return true;
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer) && !world.isRemote) {
            if (ResearchRegistry.getObservableEntities().contains(entity.getClass())) {
                TileEntityObservationChamber tile = (TileEntityObservationChamber) world.getTileEntity(x, y, z);

                if (tile != null && tile.getEntity() == null) {
                    tile.setEntity((EntityLivingBase) entity);
                    entity.setDead();
                    world.markBlockForUpdate(x, y, z);
                }
            }
        }
    }

    public void onEntityWalking(World world, int x, int y, int z, Entity entity)
    {
        if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer) && !world.isRemote) {
            if (ResearchRegistry.getObservableEntities().contains(entity.getClass())) {
                TileEntityObservationChamber tile = (TileEntityObservationChamber) world.getTileEntity(x, y, z);

                if (tile != null && tile.getEntity() == null) {
                    tile.setEntity((EntityLivingBase) entity);
                    entity.setDead();
                    world.markBlockForUpdate(x, y, z);
                }
            }
        }
    }
}
