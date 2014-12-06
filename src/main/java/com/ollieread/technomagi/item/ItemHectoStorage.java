package com.ollieread.technomagi.item;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.init.Config;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

            if (getTotal(stack) < 100) {
                Map<String, Integer> researching = charon.nanites.getResearchingKnowledge();

                for (Iterator<Entry<String, Integer>> i = researching.entrySet().iterator(); i.hasNext();) {
                    Entry<String, Integer> k = i.next();

                    if (this.addResearch(stack, k.getKey(), 5)) {
                        charon.nanites.decreaseData(5, k.getKey());

                        break;
                    }
                }
            }
        }

        return stack;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        super.getSubItems(item, tab, list);

        HashMap<String, Integer> researchingAll = new HashMap<String, Integer>();

        if (Config.creativeKnowledge) {
            List<IKnowledge> knowledge = ResearchRegistry.getKnowledge();

            for (Iterator<IKnowledge> i = knowledge.iterator(); i.hasNext();) {
                IKnowledge k = i.next();

                if (k != null) {
                    ItemStack stack = new ItemStack(this);
                    HashMap<String, Integer> researching = new HashMap<String, Integer>();
                    researching.put(k.getName(), 100);
                    researchingAll.put(k.getName(), 100);
                    setCapacity(stack, 100);
                    setTotal(stack, 100);
                    setResearch(stack, researching);
                    stack.setStackDisplayName(stack.getDisplayName() + ": " + k.getLocalisedName());
                    list.add(stack);
                }
            }

            if (Config.creativeKnowledgeAll) {
                ItemStack stack = new ItemStack(this);
                stack.setStackDisplayName(EnumChatFormatting.GOLD + "All Knowledge");
                setCapacity(stack, 100 * knowledge.size());
                setTotal(stack, 100);
                setResearch(stack, researchingAll);
                list.add(stack);
            }
        }
    }
}
