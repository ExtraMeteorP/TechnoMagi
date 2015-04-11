package com.ollieread.technomagi.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import com.ollieread.technomagi.util.ItemNBTHelper;

public class ItemBlockTank extends ItemBlockBase
{

    public ItemBlockTank(Block block)
    {
        super(block);
    }

    public ItemBlockTank setFluid(ItemStack stack, FluidStack fluid)
    {
        NBTTagCompound compound = new NBTTagCompound();
        fluid.writeToNBT(compound);
        ItemNBTHelper.setCompound(stack, "Fluid", compound);

        return this;
    }

    public FluidStack getFluid(ItemStack stack)
    {
        NBTTagCompound compound = ItemNBTHelper.getCompound(stack, "Fluid");

        if (compound != null) {
            return FluidStack.loadFluidStackFromNBT(compound);
        }

        return null;
    }

    public ItemBlockTank setCapacity(ItemStack stack, int capacity)
    {
        ItemNBTHelper.setInteger(stack, "Capacity", capacity);
        return this;
    }

    public int getCapacity(ItemStack stack)
    {
        return ItemNBTHelper.getInteger(stack, "Capacity");
    }

}
