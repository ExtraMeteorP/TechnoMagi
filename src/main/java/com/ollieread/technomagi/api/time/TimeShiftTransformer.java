package com.ollieread.technomagi.api.time;

import java.util.EnumMap;

import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.api.time.TimeHandler.TimeShift;
import com.ollieread.technomagi.util.ItemStackRepresentation;

/**
 * This is just an example time shift transformer, unless you need to do
 * anything complicated, this is the ideal way to define one.
 *
 * @author ollieread
 *
 */
public class TimeShiftTransformer implements ITimeShiftTransformer
{

    protected ItemStackRepresentation representation;
    protected EnumMap<TimeShift, ItemStack> mapping = new EnumMap<TimeShift, ItemStack>(TimeShift.class);

    public TimeShiftTransformer(ItemStackRepresentation representation)
    {
        this.representation = representation;
    }

    @Override
    public boolean canTransform(ItemStack stack, TimeShift type)
    {
        return representation.equals(stack) && mapping.containsKey(type) && mapping.get(type) != null;
    }

    @Override
    public ItemStack transform(ItemStack stack, TimeShift type)
    {
        ItemStack returnStack = mapping.get(type).copy();
        return returnStack;
    }

    public ItemStackRepresentation getRepresentation()
    {
        return this.representation;
    }

    /**
     * Add a mapping for the item.
     *
     * @param stack
     * @param type
     */
    public void add(ItemStack stack, TimeShift type)
    {
        mapping.put(type, stack);
    }

}
