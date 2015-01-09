package com.ollieread.technomagi.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.client.ClientProxy;
import com.ollieread.technomagi.client.renderer.block.BlockSmartmetalRenderer;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.item.ItemDigitalTool;
import com.ollieread.technomagi.tileentity.IDisguisableTile;
import com.ollieread.technomagi.tileentity.IPlayerLocked;
import com.ollieread.technomagi.tileentity.TileEntitySmartmetal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSmartmetal extends BlockTMContainer implements IDigitalToolable
{

    @SideOnly(Side.CLIENT)
    public static IIcon overlayIcon;
    @SideOnly(Side.CLIENT)
    public static IIcon glassIcon;

    public BlockSmartmetal(String name)
    {
        super(Material.glass, name);

        setBlockUnbreakable();
        setStepSound(soundTypeMetal);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int var2)
    {
        return new TileEntitySmartmetal();
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        super.registerBlockIcons(register);

        overlayIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName() + "Overlay");
        glassIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":invisible");
    }

    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public boolean canRenderInPass(int pass)
    {
        ClientProxy.renderPass = pass;

        return true;
    }

    public int getRenderType()
    {
        return BlockSmartmetalRenderer.id;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean canSilkHarvest()
    {
        return false;
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisaligned, List pool, Entity entity)
    {
        if (entity != null) {
            switch (world.getBlockMetadata(x, y, z)) {
                case 1:
                    if (entity instanceof EntityPlayer) {
                        return;
                    }
                    break;
                case 2:
                    if (entity instanceof EntityPlayer) {
                        if (((IPlayerLocked) world.getTileEntity(x, y, z)).isPlayer(((EntityPlayer) entity).getCommandSenderName())) {
                            return;
                        }
                    }
                    break;
                case 4:
                    if (entity instanceof EntityPlayer) {
                        if (!((IPlayerLocked) world.getTileEntity(x, y, z)).isPlayer(((EntityPlayer) entity).getCommandSenderName())) {
                            return;
                        }
                    } else {
                        return;
                    }
                    break;
            }
        }

        super.addCollisionBoxesToList(world, x, y, z, axisaligned, pool, entity);
    }

    @Override
    public boolean onTooled(EntityPlayer player, World world, int x, int y, int z, ItemStack tool)
    {
        if (!world.isRemote) {
            ItemDigitalTool digitalTool = (ItemDigitalTool) tool.getItem();

            if (tool != null) {
                TileEntitySmartmetal smartmetal = (TileEntitySmartmetal) world.getTileEntity(x, y, z);

                if (smartmetal != null) {
                    if (player.isSneaking()) {
                        if (smartmetal.isDisguised()) {
                            smartmetal.setDisguise(null);
                            world.markBlockForUpdate(x, y, z);

                            return true;
                        }
                    } else {
                        Item dropItem = Item.getItemFromBlock(this);

                        if (dropItem != null) {
                            ItemStack dropStack = new ItemStack(dropItem, 1, world.getBlockMetadata(x, y, z));
                            dropBlockAsItem(world, x, y, z, dropStack);
                            world.setBlockToAir(x, y, z);
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        IPlayerLocked te = (IPlayerLocked) world.getTileEntity(x, y, z);
        ((IPlayerLocked) te).setPlayer(((EntityPlayer) entity).getCommandSenderName());

        super.onBlockPlacedBy(world, x, y, z, entity, stack);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.getBlockMetadata(x, y, z) != 3) {
            if (player != null) {
                ItemStack stack = player.getHeldItem();

                if (!world.isRemote) {
                    TileEntitySmartmetal tile = (TileEntitySmartmetal) world.getTileEntity(x, y, z);

                    if (tile.setMode(stack)) {
                        return true;
                    }

                    if (tile != null) {
                        IDisguisableTile disguise = (IDisguisableTile) tile;

                        if (!disguise.isDisguised()) {

                            if (disguise.setDisguise(stack)) {
                                world.markBlockForUpdate(x, y, z);

                                return true;
                            }
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

        if (tile != null && tile instanceof IDisguisableTile) {
            IDisguisableTile disguise = (IDisguisableTile) tile;

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

        if (tile != null) {
            if (tile.getBlockMetadata() != 3) {
                if (tile instanceof IDisguisableTile) {
                    IDisguisableTile disguise = (IDisguisableTile) tile;

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
            } else {
                return glassIcon;
            }
        }

        return super.getIcon(world, x, y, z, side);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null) {
            if (tile.getBlockMetadata() != 3) {
                if (tile instanceof IDisguisableTile) {
                    IDisguisableTile disguise = (IDisguisableTile) tile;

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
            } else {
                return tile.getWorldObj().isDaytime() ? 12 : 0;
            }
        }

        return 0xffffff;
    }

}
