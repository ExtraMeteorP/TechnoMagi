package com.ollieread.technomagi.api.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.util.ItemStackRepresentation;

/**
 *
 * @author ollieread
 *
 */
public class TimeHandler
{

    /**
     * The types for time shifts, the name should be self explanatory.
     *
     * @author ollieread
     *
     */
    public static enum TimeShift {
        BACKWARD, FORWARD, FIXED;
    }

    protected List<ITimeShiftTransformer> timeShifts = new ArrayList<ITimeShiftTransformer>();

    /**
     * Add a time shift transformer mapping for an item/block.
     *
     * @param transformer
     */
    public void addTimeShift(ITimeShiftTransformer transformer)
    {
        if (timeShifts.contains(transformer)) {
            timeShifts.add(transformer);
        }
    }

    /**
     * Get all the time shift transformers.
     *
     * @return
     */
    public List<ITimeShiftTransformer> getTimeShifts()
    {
        return timeShifts;
    }

    /**
     * Get a random transformed result for the given item/block.
     *
     * @param representation
     * @param type
     * @return
     */
    public ItemStack getShiftedItem(ItemStackRepresentation representation, TimeShift type)
    {
        ItemStack returnStack = null;
        List<ITimeShiftTransformer> list = getTimeShifts();
        Random rand = new Random();

        if (list != null && !list.isEmpty()) {
            for (ITimeShiftTransformer transformer : list) {
                if (transformer.canTransform(representation.getItemStack(), type)) {
                    returnStack = transformer.transform(representation.getItemStack(), type);

                    if (returnStack != null) {
                        break;
                    }
                }
            }
        }

        return returnStack;
    }
}
