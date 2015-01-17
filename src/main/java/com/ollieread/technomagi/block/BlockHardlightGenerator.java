package com.ollieread.technomagi.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.block.abstracts.BlockBasicContainer;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.item.ItemDigitalTool;
import com.ollieread.technomagi.tileentity.ITileEntityDisguisable;
import com.ollieread.technomagi.tileentity.ITileEntityToolable;
import com.ollieread.technomagi.tileentity.TileEntityHardlightGenerator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHardlightGenerator extends BlockBasicContainer implements ITileEntityToolable
{

    @SideOnly(Side.CLIENT)
    public IIcon sideIcon;

    public BlockHardlightGenerator(String name)
    {
        super(Material.iron, name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int var2)
    {
        return new TileEntityHardlightGenerator();
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
        sideIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":genericSide");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if (meta == 0 && side == 1) {
            return blockIcon;
        } else if (side == meta) {
            return blockIcon;
        }

        return sideIcon;
    }

    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side)
    {
        return !ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z)).equals(ForgeDirection.getOrientation(side));
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        TileEntityHardlightGenerator generator = (TileEntityHardlightGenerator) world.getTileEntity(x, y, z);

        if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
            if (!generator.isOn()) {
                generator.toggleStatus();
            }
        } else {
            if (generator.isOn()) {
                generator.toggleStatus();
            }
        }
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

        super.onBlockPlacedBy(world, x, y, z, entity, stack);
    }

    public void onBlockPreDestroy(World world, int x, int y, int z, int meta)
    {
        TileEntityHardlightGenerator generator = (TileEntityHardlightGenerator) world.getTileEntity(x, y, z);

        generator.cleanUp();
    }

    @Override
    public boolean onTooled(EntityPlayer player, World world, int x, int y, int z, ItemStack tool)
    {
        if (!world.isRemote) {
            ItemDigitalTool digitalTool = (ItemDigitalTool) tool.getItem();

            if (tool != null) {
                TileEntityHardlightGenerator generator = (TileEntityHardlightGenerator) world.getTileEntity(x, y, z);

                if (generator != null) {
                    if (player.isSneaking()) {
                        if (generator.isDisguised()) {
                            generator.setDisguise(null);
                            world.markBlockForUpdate(x, y, z);

                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (player != null) {
            ItemStack stack = player.getHeldItem();

            if (!world.isRemote) {
                TileEntity tile = world.getTileEntity(x, y, z);

                if (tile != null && tile instanceof ITileEntityDisguisable) {
                    ITileEntityDisguisable disguise = (ITileEntityDisguisable) tile;

                    if (!disguise.isDisguised()) {

                        if (disguise.setDisguise(stack)) {
                            world.markBlockForUpdate(x, y, z);

                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof ITileEntityDisguisable) {
            ITileEntityDisguisable disguise = (ITileEntityDisguisable) tile;

            if (disguise.isDisguised()) {
                ItemStack stack = disguise.getDisguise();

                if (stack != null && stack.getItem() != null) {
                    Block block = Block.getBlockFromItem(stack.getItem());

                    if (block != null) {
                        return block.getLightValue();
                    }
                }
            }
        }

        return super.getLightValue(world, x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof ITileEntityDisguisable) {
            ITileEntityDisguisable disguise = (ITileEntityDisguisable) tile;

            if (disguise.isDisguised()) {
                ItemStack stack = disguise.getDisguise();

                if (stack != null && stack.getItem() != null) {
                    Block block = Block.getBlockFromItem(stack.getItem());

                    if (block != null) {
                        return block.getIcon(side, stack.getItemDamage());
                    }
                }
            }
        }

        return super.getIcon(world, x, y, z, side);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof ITileEntityDisguisable) {
            ITileEntityDisguisable disguise = (ITileEntityDisguisable) tile;

            if (disguise.isDisguised()) {
                ItemStack stack = disguise.getDisguise();

                if (stack != null && stack.getItem() != null) {
                    Block block = Block.getBlockFromItem(stack.getItem());

                    if (block != null) {
                        return block.colorMultiplier(world, x, y, z);
                    }
                }
            }
        }

        return 0xffffff;
    }

}
