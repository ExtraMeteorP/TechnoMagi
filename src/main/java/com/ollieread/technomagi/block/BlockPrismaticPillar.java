package com.ollieread.technomagi.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.item.ItemDigitalTool;
import com.ollieread.technomagi.tileentity.IHasFiller;
import com.ollieread.technomagi.tileentity.TileEntityPrismaticPillar;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPrismaticPillar extends BlockOwnable implements IDigitalToolable
{

    public BlockPrismaticPillar(String name)
    {
        super(Material.iron, name);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
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
        return new TileEntityPrismaticPillar();
    }

    @Override
    public boolean onTooled(EntityPlayer player, World world, int x, int y, int z, ItemStack tool)
    {
        if (!world.isRemote) {
            TileEntityPrismaticPillar thisPillar = (TileEntityPrismaticPillar) world.getTileEntity(x, y, z);

            if (ItemDigitalTool.hasFocusLocation(tool)) {
                int[] location = ItemDigitalTool.getFocusLocation(tool);
                int x1 = location[0];
                int y1 = location[1];
                int z1 = location[2];
                Block block = world.getBlock(x1, y1, z1);

                if (block != null && block instanceof BlockPrismaticPillar) {
                    TileEntityPrismaticPillar otherPillar = (TileEntityPrismaticPillar) world.getTileEntity(x1, y1, z1);

                    if (thisPillar.canLink(x1, y1, z1) && otherPillar.canLink(x, y, z)) {
                        thisPillar.link(otherPillar);
                        otherPillar.link(thisPillar);
                        ItemDigitalTool.resetFocusLocation(tool);
                        PlayerHelper.addChatMessage(player, "Pillars Linked: " + x + " " + y + " " + z);
                    }

                    return true;
                }
            } else if (thisPillar.canLink()) {
                ItemDigitalTool.setFocusLocation(tool, x, y, z);
                PlayerHelper.addChatMessage(player, "Pillar Focused: " + x + " " + y + " " + z);

                return true;
            } else {
                boolean flag = false;

                if (thisPillar.order == 0) {
                    if (thisPillar.isLinked()) {

                    }
                }
            }
        }

        return false;
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
        return world.isAirBlock(x, y + 1, z);
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
}
