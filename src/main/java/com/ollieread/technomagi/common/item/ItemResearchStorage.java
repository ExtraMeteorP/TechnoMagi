package com.ollieread.technomagi.common.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.util.ItemNBTHelper;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemResearchStorage extends ItemSubtypes
{

    public ItemResearchStorage(String name)
    {
        super(name, new String[] { "basic" });
    }

    public ItemResearchStorage setCapacity(ItemStack stack, int capacity)
    {
        ItemNBTHelper.setInteger(stack, "Capacity", capacity);

        return this;
    }

    public int getCapacity(ItemStack stack)
    {
        return ItemNBTHelper.getInteger(stack, "Capacity");
    }

    public ItemResearchStorage setTotal(ItemStack stack, int total)
    {
        ItemNBTHelper.setInteger(stack, "Total", total);

        return this;
    }

    public int getTotal(ItemStack stack)
    {
        return ItemNBTHelper.getInteger(stack, "Total");
    }

    public ItemResearchStorage setResearch(ItemStack stack, HashMap<String, Integer> research)
    {
        NBTTagList list = new NBTTagList();

        for (Entry<String, Integer> entry : research.entrySet()) {
            NBTTagCompound c = new NBTTagCompound();
            c.setString("Research", entry.getKey());
            c.setInteger("Progress", entry.getValue());

            list.appendTag(c);
        }

        ItemNBTHelper.setTagList(stack, "Research", list);

        return this;
    }

    public HashMap<String, Integer> getResearch(ItemStack stack)
    {
        HashMap<String, Integer> researchStorage = new HashMap<String, Integer>();

        if (ItemNBTHelper.has(stack, "Research")) {
            NBTTagList list = ItemNBTHelper.getTagList(stack, "Research");

            if (list != null) {
                for (int i = 0; i < list.tagCount(); i++) {
                    NBTTagCompound compound = list.getCompoundTagAt(i);
                    researchStorage.put(compound.getString("Knowledge"), compound.getInteger("Progress"));
                }
            }
        }

        return researchStorage;
    }

    public boolean addResearch(ItemStack stack, String knowledge, int progress)
    {
        HashMap<String, Integer> researchStorage = getResearch(stack);
        int total = getTotal(stack);
        int capacity = getCapacity(stack);

        if (total == -1) {
            total = 0;
        }

        if ((total + progress) <= capacity) {
            if (researchStorage.containsKey(knowledge)) {
                progress += researchStorage.get(knowledge);
            }
            total += progress;
            researchStorage.put(knowledge, progress);

            setResearch(stack, researchStorage).setTotal(stack, total);
            return true;
        }

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        int capacity = getCapacity(stack);
        int total = getTotal(stack);
        list.add(total + "/" + capacity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack stack = new ItemStack(item, 1);
        setCapacity(stack, 100);
        list.add(stack);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int hitX, float hitY, float hitZ, float side)
    {
        if (!world.isRemote) {
            PlayerTechnomagi technomage = PlayerHelper.getTechnomagi(player);

            if (technomage != null && technomage.hasSpecialised()) {
                Map<String, Integer> research = technomage.nanites().getKnowledgeProgress();

                if (research != null) {
                    for (Entry<String, Integer> entry : research.entrySet()) {
                        String knowledge = entry.getKey();
                        int progress = entry.getValue();

                        if (progress >= 5) {
                            technomage.nanites().setKnowledgeProgress(knowledge, progress - 5);
                            if (addResearch(stack, knowledge, 5)) {
                                technomage.nanites().decreaseData(5);
                                return true;
                            }
                        } else {
                            technomage.nanites().setKnowledgeProgress(knowledge, progress);
                            if (addResearch(stack, knowledge, progress)) {
                                technomage.nanites().decreaseData(progress);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

}
