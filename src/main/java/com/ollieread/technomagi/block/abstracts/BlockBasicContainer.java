package com.ollieread.technomagi.block.abstracts;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.ITileEntityGui;
import com.ollieread.technomagi.tileentity.ITileEntityMachine;
import com.ollieread.technomagi.tileentity.TileEntityMachineAreaLight;
import com.ollieread.technomagi.tileentity.component.IDisguisable;
import com.ollieread.technomagi.tileentity.component.IHasFiller;
import com.ollieread.technomagi.tileentity.component.IHasOwner;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockBasicContainer extends BlockContainer
{

    public BlockBasicContainer(Material material, String name)
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

    public int quantityDropped(Random random)
    {
        return 1;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        TileEntity machine = world.getTileEntity(x, y, z);

        if (machine instanceof IHasOwner) {
            if (entity instanceof EntityPlayer) {
                ((IHasOwner) machine).setOwner(entity.getCommandSenderName());
            }
        }

        if (machine instanceof ITileEntityMachine) {
            int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

            if (l == 0) {
                ((ITileEntityMachine) machine).setFacing(2);
            }

            if (l == 1) {
                ((ITileEntityMachine) machine).setFacing(5);
            }

            if (l == 2) {
                ((ITileEntityMachine) machine).setFacing(3);
            }

            if (l == 3) {
                ((ITileEntityMachine) machine).setFacing(4);
            }

            ((ITileEntityMachine) machine).placed(entity, stack);
        }

        if (machine instanceof IHasFiller) {
            ((IHasFiller) machine).create();
        }

        super.onBlockPlacedBy(world, x, y, z, entity, stack);
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

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (player != null) {
            ItemStack stack = player.getHeldItem();

            if (!world.isRemote) {
                TileEntity tile = world.getTileEntity(x, y, z);

                if (tile instanceof IHasOwner) {
                    if (!((IHasOwner) tile).isOwner(player.getCommandSenderName())) {
                        return false;
                    }
                }

                if (tile != null && tile instanceof IDisguisable) {
                    IDisguisable disguise = (IDisguisable) tile;

                    if (!disguise.isDisguised()) {

                        if (disguise.setDisguise(stack)) {
                            world.markBlockForUpdate(x, y, z);

                            return true;
                        }
                    }
                }

                if (tile instanceof ITileEntityMachine) {
                    ITileEntityMachine machine = (ITileEntityMachine) tile;

                    if (machine.activated(player, side, hitX, hitY, hitZ)) {
                        return true;
                    }
                }

                if (tile instanceof ITileEntityGui) {
                    int gui = ((ITileEntityGui) tile).getGui(world, x, y, z, player);

                    if (gui > -1) {
                        player.openGui(TechnoMagi.instance, gui, world, x, y, z);
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

        if (tile != null && tile instanceof IDisguisable) {
            IDisguisable disguise = (IDisguisable) tile;

            if (disguise.isDisguised() && ((TileEntityMachineAreaLight) tile).isOn()) {
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

        if (tile != null && tile instanceof IDisguisable) {
            IDisguisable disguise = (IDisguisable) tile;

            if (disguise.isDisguised() && ((TileEntityMachineAreaLight) tile).isOn()) {
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
