package com.ollieread.technomagi.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidTank;

import com.ollieread.technomagi.block.BlockMachine;
import com.ollieread.technomagi.block.BlockRegionController;
import com.ollieread.technomagi.block.BlockResource;
import com.ollieread.technomagi.block.BlockStorage;
import com.ollieread.technomagi.block.BlockTank;
import com.ollieread.technomagi.block.IBlockMulti;
import com.ollieread.technomagi.tileentity.component.ComponentStorage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockMulti extends ItemBlock
{
    private Block field_150950_b;

    public ItemBlockMulti(Block block)
    {
        super(block);
        this.field_150950_b = block;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
        return this.field_150950_b.getIcon(2, par1);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }

    public String getUnlocalizedName(ItemStack stack)
    {
        return getUnlocalizedName() + "." + ((IBlockMulti) Block.getBlockFromItem(stack.getItem())).getName(stack.getItemDamage());
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
    {
        if (stack != null) {
            if (Block.getBlockFromItem(stack.getItem()) instanceof BlockMachine) {
                list.add("Machine");
            } else if (Block.getBlockFromItem(stack.getItem()) instanceof BlockRegionController) {
                list.add("Region Controller");
            } else if (Block.getBlockFromItem(stack.getItem()) instanceof BlockResource) {
                list.add("Resource");
            } else if (Block.getBlockFromItem(stack.getItem()) instanceof BlockTank) {
                if (stack.stackTagCompound != null) {
                    FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 100);
                    tank.readFromNBT(stack.stackTagCompound);

                    if (tank.getFluid() != null) {
                        list.add(tank.getFluid().getLocalizedName());
                    }

                    list.add(tank.getFluidAmount() + "/" + tank.getCapacity());
                }
            } else if (Block.getBlockFromItem(stack.getItem()) instanceof BlockStorage) {
                if (stack.stackTagCompound != null) {
                    ComponentStorage storage = new ComponentStorage(4096);
                    storage.readFromNBT(stack.stackTagCompound);

                    if (storage.getItem() != null) {
                        list.add(storage.getLocalizedName());
                    }

                    list.add(storage.getAmount() + "/" + storage.getCapacity());
                }
            }
        }
    }

}
