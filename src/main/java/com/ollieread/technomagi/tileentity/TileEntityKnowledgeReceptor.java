package com.ollieread.technomagi.tileentity;

import java.util.HashMap;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.block.TileEntityPlayerLocked;
import com.ollieread.technomagi.api.event.TMEvent;
import com.ollieread.technomagi.api.event.TMEvent.ResearchProgressEvent;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

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
