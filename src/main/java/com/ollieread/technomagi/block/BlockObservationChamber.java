package com.ollieread.technomagi.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
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
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.item.ItemCapture;
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

    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.isAirBlock(x, y + 1, z) && world.isAirBlock(x, y + 2, z);
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        TileEntityObservationChamber chamber = (TileEntityObservationChamber) world.getTileEntity(x, y, z);

        if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
            if (chamber.getEntity() != null) {

                if (!world.isRemote) {
                    chamber.flush();

                    for (int i = 0; i < 32; i++) {
                        double d6 = (double) i / ((double) 128 - 1.0D);
                        float f = (world.rand.nextFloat() - 0.5F) * 0.2F;
                        float f1 = (world.rand.nextFloat() - 0.5F) * 0.2F;
                        float f2 = (world.rand.nextFloat() - 0.5F) * 0.2F;
                        double d7 = (z + 0.5D) * d6 + (world.rand.nextDouble() - 0.5D) * (double) 1D;
                        double d8 = y * d6 + world.rand.nextDouble() * (double) 3D;
                        double d9 = (z + 0.5D) * d6 + (world.rand.nextDouble() - 0.5D) * (double) 1.0D;
                        world.spawnParticle("reddust", d7, d8, d9, (double) f, (double) f1, (double) f2);
                    }

                    world.markBlockForUpdate(x, y, z);
                }
            }
        }
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
                        if (stack != null && stack.isItemEqual(new ItemStack(Items.itemCapture, 1, 1))) {
                            ItemCapture capture = (ItemCapture) stack.getItem();

                            if (capture.hasEntity(stack)) {
                                String name = capture.getName(stack);
                                String entityName = capture.getEntityName(stack);

                                if (entityName != null) {
                                    EntityLiving entityLiving = (EntityLiving) EntityList.createEntityByName(entityName, world);

                                    if (entityLiving != null) {
                                        entityLiving.readEntityFromNBT(ItemCapture.getEntityNBT(stack));

                                        if (name != null) {
                                            entityLiving.setCustomNameTag(name);
                                        }

                                        capture.setEntity(null, stack);
                                        if (ResearchRegistry.getObservableEntities().contains(entityLiving.getClass())) {
                                            tile.setEntity(entityLiving);
                                            stack.stackSize--;

                                            return true;
                                        }
                                    }
                                }
                            }
                        }

                        player.openGui(TechnoMagi.instance, CommonProxy.GUI_OBSERVATION, world, x, y, z);
                    }
                }
            }
        }

        return true;
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        /*
         * if (entity instanceof EntityLivingBase && !(entity instanceof
         * EntityPlayer)) { if
         * (ResearchRegistry.getObservableEntities().contains
         * (entity.getClass())) { TileEntityObservationChamber tile =
         * (TileEntityObservationChamber) world.getTileEntity(x, y, z);
         * 
         * if (!world.isRemote) { if (tile != null && tile.getEntity() == null)
         * { tile.setEntity((EntityLivingBase) entity); entity.setDead();
         * 
         * world.markBlockForUpdate(x, y, z); } } } }
         */
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
