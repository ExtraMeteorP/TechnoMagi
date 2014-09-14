package com.ollieread.technomagi.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.item.ItemDigitalTool;
import com.ollieread.technomagi.tileentity.IDisguisableTile;
import com.ollieread.technomagi.tileentity.TileEntityAreaLight;
import com.ollieread.technomagi.util.EntityHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAreaLight extends BlockTMContainer implements IDigitalToolable
{

    public BlockAreaLight(String name)
    {
        super(Material.iron, name);

        setLightLevel(0F);
        setLightOpacity(0);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileEntityAreaLight();
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return !world.canBlockSeeTheSky(x, y, z);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        if (!world.isRemote) {

        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

    @Override
    public boolean onTooled(EntityPlayer player, World world, int x, int y, int z, ItemStack tool)
    {
        if (!world.isRemote) {
            ItemDigitalTool digitalTool = (ItemDigitalTool) tool.getItem();

            if (tool != null) {
                TileEntityAreaLight light = (TileEntityAreaLight) world.getTileEntity(x, y, z);

                if (light != null) {

                    if (player.isSneaking()) {
                        if (light.isDisguised()) {
                            light.setDisguise(null);
                            world.markBlockForUpdate(x, y, z);

                            return true;
                        }
                    } else {
                        light.toggleStatus();
                        world.markBlockForUpdate(x, y, z);
                        EntityHelper.addChatMessage(player, "Area light " + (light.isOn() ? "enabled" : "disabled"));

                        return true;
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

                if (tile != null && tile instanceof IDisguisableTile) {
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

        return false;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof IDisguisableTile) {
            IDisguisableTile disguise = (IDisguisableTile) tile;

            if (disguise.isDisguised() && ((TileEntityAreaLight) tile).isOn()) {
                ItemStack stack = disguise.getDisguise();

                if (stack != null && stack.getItem() != null) {
                    Block block = Block.getBlockFromItem(stack.getItem());

                    if (block != null) {
                        return block.getIcon(side, stack.getItemDamage());
                    }
                }
            }
        }

        return this.getIcon(side, world.getBlockMetadata(x, y, z));
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof IDisguisableTile) {
            IDisguisableTile disguise = (IDisguisableTile) tile;

            if (disguise.isDisguised() && ((TileEntityAreaLight) tile).isOn()) {
                ItemStack stack = disguise.getDisguise();

                if (stack != null && stack.getItem() != null) {
                    Block block = Block.getBlockFromItem(stack.getItem());

                    if (block != null) {
                        return block.colorMultiplier(world, x, y, z);
                    }
                }
            }
        }

        return super.colorMultiplier(world, x, y, z);
    }
}
