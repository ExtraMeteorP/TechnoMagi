package com.ollieread.technomagi.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
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

    public ItemResearchStorage(String name, int capacity)
    {
        super(name);

        setMaxDamage(capacity);
        setMaxStackSize(1);
    }

    @Override
    public boolean addResearch(ItemStack stack, String name, int amount)
    {
        int capacity = getCapacity(stack);
        int total = getTotal(stack);
        Map<String, Integer> researchingKnowledge = getResearch(stack);

        if ((total + amount) <= capacity) {
            total += amount;
            setTotal(stack, total);

            if (researchingKnowledge.containsKey(name)) {
                if ((researchingKnowledge.get(name) + amount) > 100) {
                    return false;
                }
            }

            researchingKnowledge.put(name, total);
            setResearch(stack, researchingKnowledge);
            return true;
        }

        return false;
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
        int capacity = getCapacity(stack);
        int total = getTotal(stack);

        stack.setItemDamage(capacity - total);
    }

    public int getCapacity(ItemStack stack)
    {
        NBTTagCompound compound = stack.stackTagCompound;

        if (compound.hasKey("Capacity")) {
            return compound.getInteger("Capacity");
        }

        return 0;
    }

    public int getTotal(ItemStack stack)
    {
        NBTTagCompound compound = stack.stackTagCompound;

        if (compound.hasKey("Total")) {
            return compound.getInteger("Total");
        }

        return 0;
    }

    public Map<String, Integer> getResearch(ItemStack stack)
    {
        NBTTagCompound compound = stack.stackTagCompound;

        if (compound.hasKey("ResearchProgress")) {
            NBTTagList researchProgressList = compound.getTagList("ResearchProgress", compound.getId());
            Map<String, Integer> researchingKnowledge = new HashMap<String, Integer>();

            for (int i = 0; i < researchProgressList.tagCount(); i++) {
                NBTTagCompound research = researchProgressList.getCompoundTagAt(i);
                researchingKnowledge.put(ResearchRegistry.getKnowledgeName(research.getInteger("Research")), research.getInteger("Progress"));
            }

            return researchingKnowledge;
        }

        return null;
    }

    public void setCapacity(ItemStack stack, int capacity)
    {
        NBTTagCompound compound = stack.stackTagCompound;
        compound.setInteger("Capacity", capacity);
    }

    public void setTotal(ItemStack stack, int total)
    {
        NBTTagCompound compound = stack.stackTagCompound;
        compound.setInteger("Total", total);
    }

    public void setResearch(ItemStack stack, Map<String, Integer> researchingKnowledge)
    {
        NBTTagCompound compound = stack.stackTagCompound;
        NBTTagList researchProgressList = new NBTTagList();

        for (String k : researchingKnowledge.keySet()) {
            NBTTagCompound research = new NBTTagCompound();
            research.setInteger("Research", ResearchRegistry.getKnowledgeId(k));
            research.setInteger("Progress", researchingKnowledge.get(k));
            researchProgressList.appendTag(research);
        }

        compound.setTag("ResearchProgress", researchProgressList);
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        stack.stackTagCompound = new NBTTagCompound();

        setCapacity(stack, 0);
        setTotal(stack, 0);
        setResearch(stack, new HashMap<String, Integer>());
    }

    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if (stack.stackTagCompound != null) {
            list.add(EnumChatFormatting.AQUA + "Stored: " + getTotal(stack));
            list.add(EnumChatFormatting.RED + "Capacity: " + getCapacity(stack));
            list.add(EnumChatFormatting.LIGHT_PURPLE + "Knowledge: " + getResearch(stack).size());
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack stack = new ItemStack(item, 1, 100);
        stack.stackTagCompound = new NBTTagCompound();

        setCapacity(stack, 100);
        setTotal(stack, 0);
        setResearch(stack, new HashMap<String, Integer>());

        list.add(stack);
    }

}
