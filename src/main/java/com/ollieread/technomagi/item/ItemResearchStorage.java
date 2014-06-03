package com.ollieread.technomagi.item;

import com.ollieread.technomagi.api.item.IResearchStorage;

import net.minecraft.item.Item;

public class ItemResearchStorage extends Item implements IResearchStorage
{

    public String knowledge;

    public int progress;

    @Override
    public void setKnowledge(String name)
    {
        knowledge = name;
    }

    public String getKnowledge()
    {
        return knowledge;
    }

    @Override
    public int setProgress(int amount)
    {
        if (progress < 100) {
            if ((progress + amount) > 100) {
                int diff = 100 - progress;
                progress += amount;

                return progress;
            } else {
                progress += amount;

                return amount;
            }
        }

        return 0;
    }

    public int getProgress()
    {
        return progress;
    }

}
