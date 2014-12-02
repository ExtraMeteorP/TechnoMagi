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

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.IPlayerLocked;
import com.ollieread.technomagi.tileentity.TileEntityStorage;
import com.ollieread.technomagi.util.InventoryHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStorage extends BlockTMContainer implements IDigitalToolable
{

    public BlockStorage(String name)
    {
        super(Material.iron, name);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
        return new TileEntityStorage();
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

        if (te instanceof IPlayerLocked) {
            ((IPlayerLocked) te).setPlayer(((EntityPlayer) entity).getCommandSenderName());
        }

        TileEntityStorage storage = (TileEntityStorage) te;

        if (storage.getBlockMetadata() > 0) {
            storage.setCapacity(4096 * (storage.getBlockMetadata() * 5));
        }

        if (stack.stackTagCompound != null) {
            storage.readFromNBT(stack.stackTagCompound);
            storage.xCoord = x;
            storage.yCoord = y;
            storage.zCoord = z;
        }

        super.onBlockPlacedBy(world, x, y, z, entity, stack);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (player != null) {
            TileEntityStorage storage = (TileEntityStorage) world.getTileEntity(x, y, z);
            ForgeDirection direction = ForgeDirection.getOrientation(side);

            if (storage != null) {
                ItemStack held = player.getHeldItem();
                ItemStack contents = storage.getItem();

                if (held == null) {
                    if (contents != null) {
                        if (storage.isWaiting() && InventoryHelper.countInventoryItem(player.inventory, contents) > 0) {
                            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                                ItemStack slot = player.inventory.getStackInSlot(i);

                                if (slot != null && slot.isItemEqual(contents)) {
                                    int deposited = storage.deposit(direction, slot, true);

                                    if (deposited == slot.stackSize) {
                                        player.inventory.setInventorySlotContents(i, null);
                                    } else {
                                        slot.stackSize -= deposited;
                                    }
                                }
                            }

                            storage.setWaiting(0);
                            storage.sync();

                            return true;
                        } else {
                            ItemStack stack = storage.withdraw(direction, true);
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);

                            storage.setWaiting(0);
                            storage.sync();

                            return true;
                        }
                    }
                } else {
                    if (contents == null) {
                        storage.deposit(direction, held, true);
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

                        storage.setWaiting(1);
                        storage.sync();

                        return true;
                    } else {
                        if (contents.isItemEqual(held)) {
                            int deposited = storage.deposit(direction, held, true);

                            if (deposited == held.stackSize) {
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                            } else {
                                held.stackSize -= deposited;
                            }

                            storage.setWaiting(1);
                            storage.sync();

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

                TileEntityStorage storage = (TileEntityStorage) world.getTileEntity(x, y, z);

                if (storage != null) {
                    storage.writeToNBT(dropStack.stackTagCompound);
                    dropBlockAsItem(world, x, y, z, dropStack);
                    world.setBlockToAir(x, y, z);
                }
            }
        }

        return false;
    }

}
