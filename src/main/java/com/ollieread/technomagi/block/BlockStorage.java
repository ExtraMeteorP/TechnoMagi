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

import com.ollieread.technomagi.block.abstracts.BlockBasicContainer;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.ITileEntityToolable;
import com.ollieread.technomagi.tileentity.TileEntityStorageItems;
import com.ollieread.technomagi.tileentity.component.IHasOwner;
import com.ollieread.technomagi.util.InventoryHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStorage extends BlockBasicContainer implements ITileEntityToolable
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
        return new TileEntityStorageItems();
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

        if (te instanceof IHasOwner) {
            ((IHasOwner) te).setOwner(((EntityPlayer) entity).getCommandSenderName());
        }

        TileEntityStorageItems storage = (TileEntityStorageItems) te;

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
            TileEntityStorageItems storage = (TileEntityStorageItems) world.getTileEntity(x, y, z);

            if (storage != null) {
                ItemStack held = player.getHeldItem();
                ItemStack contents = storage.getItem();

                if (held == null) {
                    if (contents != null) {
                        if (storage.isWaiting() && InventoryHelper.countInventoryItem(player.inventory, contents) > 0) {
                            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                                ItemStack slot = player.inventory.getStackInSlot(i);

                                if (slot != null && slot.isItemEqual(contents)) {
                                    int deposited = storage.deposit(slot, true);

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
                            ItemStack stack = null;

                            if (player.isSneaking()) {
                                stack = storage.withdraw(true, 1);
                            } else {
                                stack = storage.withdraw(true);
                            }

                            player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);

                            storage.setWaiting(0);
                            storage.sync();

                            return true;
                        }
                    }
                } else {
                    if (contents == null) {
                        storage.deposit(held, true);
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

                        storage.setWaiting(1);
                        storage.sync();

                        return true;
                    } else {
                        if (contents.isItemEqual(held)) {
                            int deposited = storage.deposit(held, true);

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

                TileEntityStorageItems storage = (TileEntityStorageItems) world.getTileEntity(x, y, z);

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
