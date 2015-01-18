package com.ollieread.technomagi.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.ollieread.technomagi.block.abstracts.BlockBasicContainer;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.ITileEntityHasOwner;
import com.ollieread.technomagi.tileentity.ITileEntityToolable;
import com.ollieread.technomagi.tileentity.TileEntityStorageFluid;
import com.ollieread.technomagi.util.InventoryHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTank extends BlockBasicContainer implements ITileEntityToolable, IBlockMulti
{

    public static String[] blockNames = new String[] { "normal", "advanced", "elite" };

    public BlockTank(String name)
    {
        super(Material.iron, name);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        setBlockTextureName("construct");
    }

    @Override
    public String getName(int metadata)
    {
        return blockNames[metadata];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

    @Override
    public TileEntity createNewTileEntity(World world, int var2)
    {
        return new TileEntityStorageFluid();
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
        TileEntity te = world.getTileEntity(x, y, z);

        if (te instanceof ITileEntityHasOwner) {
            ((ITileEntityHasOwner) te).setOwner(((EntityPlayer) entity).getCommandSenderName());
        }

        TileEntityStorageFluid tank = (TileEntityStorageFluid) te;

        if (tank.getBlockMetadata() > 0) {
            tank.setCapacity(FluidContainerRegistry.BUCKET_VOLUME * (100 * (tank.getBlockMetadata() * 5)));
        }

        if (stack.stackTagCompound != null) {
            tank.readFromNBT(stack.stackTagCompound);
            tank.xCoord = x;
            tank.yCoord = y;
            tank.zCoord = z;
        }

        super.onBlockPlacedBy(world, x, y, z, entity, stack);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (player != null) {
            TileEntityStorageFluid tank = (TileEntityStorageFluid) world.getTileEntity(x, y, z);

            if (tank != null) {
                if (tank.isOwner(player.getCommandSenderName())) {
                    ItemStack stack = player.getHeldItem();
                    ForgeDirection direction = ForgeDirection.getOrientation(side);

                    FluidStack containerFluid;

                    containerFluid = FluidContainerRegistry.getFluidForFilledItem(stack);

                    if (containerFluid != null) {
                        int qty = tank.fill(direction, containerFluid, true);

                        if (qty != 0 && !player.capabilities.isCreativeMode) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, InventoryHelper.consumeInventoryItem(stack));
                        }

                        tank.sync();

                        return true;
                    } else {
                        FluidStack fluid = tank.getFluid();

                        if (!world.isRemote) {
                            if (fluid == null || fluid.amount <= 0) {
                                return false;
                            }

                            ItemStack container = FluidContainerRegistry.fillFluidContainer(fluid, stack);
                            containerFluid = FluidContainerRegistry.getFluidForFilledItem(container);

                            if (containerFluid != null) {
                                if (!player.capabilities.isCreativeMode) {
                                    if (stack.stackSize > 1) {
                                        if (!player.inventory.addItemStackToInventory(container)) {
                                            return false;
                                        }

                                        player.inventory.setInventorySlotContents(player.inventory.currentItem, InventoryHelper.consumeInventoryItem(stack));
                                    } else {
                                        player.inventory.setInventorySlotContents(player.inventory.currentItem, container);
                                    }
                                }

                                tank.drain(direction, containerFluid.amount, true);
                                tank.sync();

                                return true;
                            }
                        } else {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        super.getSubBlocks(item, tab, list);

        list.add(new ItemStack(this, 1, 1));
        list.add(new ItemStack(this, 1, 2));
    }

    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

    @Override
    public boolean onTooled(EntityPlayer player, World world, int x, int y, int z, ItemStack tool)
    {
        if (!world.isRemote) {
            Item dropItem = Item.getItemFromBlock(this);

            if (dropItem != null) {
                ItemStack dropStack = new ItemStack(dropItem, 1, world.getBlockMetadata(x, y, z));
                dropStack.stackTagCompound = new NBTTagCompound();

                TileEntityStorageFluid tank = (TileEntityStorageFluid) world.getTileEntity(x, y, z);

                if (tank != null) {
                    tank.writeToNBT(dropStack.stackTagCompound);
                    dropBlockAsItem(world, x, y, z, dropStack);
                    world.setBlockToAir(x, y, z);
                }
            }
        }

        return false;
    }

}
