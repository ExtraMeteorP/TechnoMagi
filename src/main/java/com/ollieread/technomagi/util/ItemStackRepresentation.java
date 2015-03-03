package com.ollieread.technomagi.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

/**
 * This class represents items as keys for maps, gets around the annoying
 * ItemStack issue where it doesn't implement equals{}. This would represent a
 * block placed normally in the world, or an ItemStack representing a block.
 *
 * @author ollieread
 *
 */
public class ItemStackRepresentation
{

    protected ItemStack stack;
    protected boolean oreDict = true;
    protected boolean strict = false;

    public ItemStackRepresentation(ItemStack stack)
    {
        this.stack = stack;
    }

    public void setStrict(boolean strict)
    {
        this.strict = strict;
    }

    public void setOreDict(boolean oreDict)
    {
        this.oreDict = oreDict;
    }

    public ItemStack getItemStack()
    {
        return this.stack;
    }

    @Override
    public int hashCode()
    {
        return 10;
    }

    @Override
    public boolean equals(Object o)
    {
        boolean flag = false;

        if (o != null) {
            ItemStack thatStack = null;

            if (o instanceof ItemStackRepresentation) {
                ItemStackRepresentation item = (ItemStackRepresentation) o;

                thatStack = item.stack;
            } else if (o instanceof ItemStack) {
                thatStack = (ItemStack) o;
            }

            if (stack != null && stack.getItem() != null && thatStack != null && thatStack.getItem() != null) {
                flag = stack.getItem() == thatStack.getItem() && (stack.getItemDamage() == -1 || stack.getItemDamage() == thatStack.getItemDamage());

                if (!flag && oreDict) {
                    flag = ItemStackHelper.matchesOreDict(stack, thatStack);
                }

                if (flag && strict) {
                    if (!ItemStack.areItemStackTagsEqual(stack, thatStack)) {
                        flag = false;
                    }
                }
            }
        }

        return flag;
    }

    public static class PlantRepresentation extends ItemStackRepresentation
    {

        protected EnumPlantType type;

        public PlantRepresentation(EnumPlantType type)
        {
            super(null);

            this.type = type;
        }

        @Override
        public boolean equals(Object o)
        {
            if (o != null) {
                ItemStack thatStack = null;

                if (o instanceof ItemStackRepresentation) {
                    ItemStackRepresentation item = (ItemStackRepresentation) o;

                    thatStack = item.stack;
                } else if (o instanceof ItemStack) {
                    thatStack = (ItemStack) o;
                }

                if (thatStack != null && thatStack.getItem() != null && thatStack.getItem() instanceof IPlantable) {
                    return ((IPlantable) thatStack.getItem()).getPlantType(null, 0, 0, 0).equals(this.type);
                }
            }

            return false;
        }
    }

}
