package com.ollieread.technomagi.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.ollieread.ennds.item.IResearchStorage;
import com.ollieread.ennds.research.ResearchRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemResearchStorage extends ItemTM implements IResearchStorage
{

    protected int capacity;
    protected int total;
    protected Map<String, Integer> researchingKnowledge = new HashMap<String, Integer>();

    public ItemResearchStorage(String name, int capacity)
    {
        super(name);

        this.capacity = capacity;

        setMaxDamage(capacity);
        setMaxStackSize(1);
    }

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

    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        addStackInformation(stack);
    }

    protected void addStackInformation(ItemStack stack)
    {
        stack.stackTagCompound = new NBTTagCompound();

        stack.stackTagCompound.setInteger("Capacity", capacity);
        stack.stackTagCompound.setInteger("Total", total);

        NBTTagList researchProgressList = new NBTTagList();

        for (String k : researchingKnowledge.keySet()) {
            NBTTagCompound research = new NBTTagCompound();
            research.setInteger("Research", ResearchRegistry.getResearchId(k));
            research.setInteger("Progress", researchingKnowledge.get(k));
            researchProgressList.appendTag(research);
        }

        stack.stackTagCompound.setTag("ResearchProgress", researchProgressList);
    }

    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if (stack.stackTagCompound != null) {
            list.add(EnumChatFormatting.AQUA + "Stored: " + total);
            list.add(EnumChatFormatting.RED + "Capacity: " + capacity);
            list.add(EnumChatFormatting.LIGHT_PURPLE + "Knowledge: " + researchingKnowledge.size());
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack stack = new ItemStack(item, 1, 100);

        addStackInformation(stack);

        list.add(stack);
    }

}
