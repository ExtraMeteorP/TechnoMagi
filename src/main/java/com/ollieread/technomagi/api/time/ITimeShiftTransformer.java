package com.ollieread.technomagi.api.time;

import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.api.time.TimeHandler.TimeShift;

public interface ITimeShiftTransformer
{

    /**
     * Check whether or not the transformer can transform the given itemstack,
     * for the given time shift type.
     *
     * @param stack
     * @param type
     * @return
     */
    public boolean canTransform(ItemStack stack, TimeShift type);

    /**
     * Transform the given itemstack for the given time shift type.
     *
     * @param stack
     * @param type
     * @return
     */
    public ItemStack transform(ItemStack stack, TimeShift type);

}
