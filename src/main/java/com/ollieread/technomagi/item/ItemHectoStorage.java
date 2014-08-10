package com.ollieread.technomagi.item;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;

public class ItemHectoStorage extends ItemResearchStorage
{

    public ItemHectoStorage(String name)
    {
        super(name, 100);
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote) {
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

            Map<String, Integer> researching = charon.nanites.getResearchingKnowledge();

            for (Iterator<Entry<String, Integer>> i = researching.entrySet().iterator(); i.hasNext();) {
                Entry<String, Integer> k = i.next();

                if (this.addResearch(k.getKey(), 5)) {
                    charon.nanites.decreaseData(5, k.getKey());

                    break;
                }
            }

            addStackInformation(stack);
        }

        return stack;
    }
}
