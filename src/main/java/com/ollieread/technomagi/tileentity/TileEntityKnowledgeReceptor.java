package com.ollieread.technomagi.tileentity;

import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityKnowledgeReceptor extends TileEntityPlayerLocked
{

    private HashMap<String, Integer> knowledge = new HashMap<String, Integer>();
    public String syncingKnowledge;
    public int syncingTime = 0;
    public boolean isUsing = false;

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        NBTTagList knowledgeList = new NBTTagList();

        for (String k : knowledge.keySet()) {
            NBTTagCompound research = new NBTTagCompound();
            research.setString("Research", k);
            research.setInteger("Progress", knowledge.get(k));
            knowledgeList.appendTag(research);
        }

        compound.setTag("Knowledge", knowledgeList);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagList knowledgeList = compound.getTagList("ResearchProgress", compound.getId());
        knowledge = new HashMap<String, Integer>();

        for (int i = 0; i < knowledgeList.tagCount(); i++) {
            NBTTagCompound research = knowledgeList.getCompoundTagAt(i);
            knowledge.put(research.getString("Research"), research.getInteger("Progress"));
        }
    }

    @Override
    public void updateEntity()
    {
        if (isUsing) {

        }
    }

}
