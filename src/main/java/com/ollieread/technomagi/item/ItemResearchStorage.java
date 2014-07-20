package com.ollieread.technomagi.item;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;

import com.ollieread.ennds.item.IResearchStorage;

public class ItemResearchStorage extends Item implements IResearchStorage
{

    protected int capacity;
    protected int total;
    protected Map<String, Integer> researchingKnowledge = new HashMap<String, Integer>();

    @Override
    public boolean addResearch(String name, int amount)
    {
        if ((total + amount) > capacity) {
            total += amount;
            if (researchingKnowledge.containsKey(name)) {
                researchingKnowledge.put(name, researchingKnowledge.get(name) + amount);
                return true;
            } else {
                researchingKnowledge.put(name, amount);
                return true;
            }
        }

        return false;
    }

}
