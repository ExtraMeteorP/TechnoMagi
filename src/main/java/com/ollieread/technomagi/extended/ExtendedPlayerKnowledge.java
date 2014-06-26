package com.ollieread.technomagi.extended;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;

import com.ollieread.technomagi.api.ISpecialisation;
import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.event.TMEvent;
import com.ollieread.technomagi.api.event.TMEvent.ResearchProgressEvent;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageSyncKnowledge;
import com.ollieread.technomagi.network.message.MessageSyncKnowledgeOther;

/**
 * This class handles all player specific matters outside of individual blocks
 * and items. It allows for the researching and discovery of knowledge via
 * internal nanites, keeps track of nanites, keeps track of specialisation and
 * allows for the usage of abilities, both passive and active.
 * 
 * @author ollieread
 * 
 */
public class ExtendedPlayerKnowledge extends ExtendedProperties
{

    public static String PROP_NAME = "TechnoMageKnowledge";
    /**
     * The players specialisation, default is "none" as NBT throws a wobbler if
     * you pass an empty string.
     */
    private String specialisation = "none";
    /**
     * Knowledge being researched by the player. Contains a list of all
     * knowledge with a progress greater than 0 and less than 100.
     */
    private HashMap<String, Integer> researchingKnowledge = new HashMap<String, Integer>();
    /**
     * A list of all completed knowledge. Stored separately as we don't need to
     * know progress, just that it's complete.
     */
    private List<String> researchedKnowledge = new ArrayList<String>();
    /**
     * A list of research activities that have already been carried out. Not
     * every piece of research will go in here, only those that cannot be
     * repeated.
     */
    private List<String> researchList = new ArrayList<String>();

    public ExtendedPlayerAbilities abilities;

    public ExtendedNanites nanites;

    public ExtendedPlayerKnowledge(EntityPlayer player)
    {
        super(player);
        abilities = ExtendedPlayerAbilities.get(player);
        nanites = ExtendedNanites.get(player);
    }

    public static final void register(EntityPlayer player)
    {
        player.registerExtendedProperties(PROP_NAME, new ExtendedPlayerKnowledge(player));
    }

    public static final ExtendedPlayerKnowledge get(EntityPlayer player)
    {
        return (ExtendedPlayerKnowledge) player.getExtendedProperties(PROP_NAME);
    }

    private void sync()
    {
        PacketHandler.INSTANCE.sendTo(new MessageSyncKnowledge(player), (EntityPlayerMP) player);
    }

    public void syncTo(EntityPlayer entityPlayer)
    {
        if (!player.worldObj.isRemote) {
            PacketHandler.INSTANCE.sendTo(new MessageSyncKnowledgeOther(player), (EntityPlayerMP) entityPlayer);
        }
    }

    public static void sync(EntityPlayer player)
    {
        if (!player.worldObj.isRemote) {
            PacketHandler.INSTANCE.sendTo(new MessageSyncKnowledge(player), (EntityPlayerMP) player);
        }
    }

    public String toString()
    {
        if (player != null) {
            return "PlayerKnowledge:" + player.getCommandSenderName() + ":" + canSpecialise();
        }

        return super.toString();
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = new NBTTagCompound();

        properties.setInteger("Specialisation", TMRegistry.getSpecialisationId(specialisation));

        NBTTagList researchCompleteList = new NBTTagList();

        for (Iterator<String> i = researchedKnowledge.iterator(); i.hasNext();) {
            NBTTagCompound research = new NBTTagCompound();
            research.setInteger("Research", TMRegistry.getResearchId(i.next()));
            researchCompleteList.appendTag(research);
        }

        properties.setTag("ResearchComplete", researchCompleteList);

        NBTTagList researchProgressList = new NBTTagList();

        for (String k : researchingKnowledge.keySet()) {
            NBTTagCompound research = new NBTTagCompound();
            research.setInteger("Research", TMRegistry.getResearchId(k));
            research.setInteger("Progress", researchingKnowledge.get(k));
            researchProgressList.appendTag(research);
        }

        properties.setTag("ResearchProgress", researchProgressList);

        NBTTagList researchedList = new NBTTagList();

        for (Iterator<String> i = researchList.iterator(); i.hasNext();) {
            NBTTagCompound research = new NBTTagCompound();
            research.setInteger("Research", TMRegistry.getResearchId(i.next()));
            researchProgressList.appendTag(research);
        }

        properties.setTag("ResearchList", researchedList);

        compound.setTag(PROP_NAME, properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag(PROP_NAME);

        specialisation = TMRegistry.getSpecialisationName(properties.getInteger("Specialisation"));

        NBTTagList researchCompleteList = properties.getTagList("ResearchComplete", properties.getId());
        researchedKnowledge = new ArrayList<String>();

        for (int i = 0; i < researchCompleteList.tagCount(); i++) {
            NBTTagCompound research = researchCompleteList.getCompoundTagAt(i);
            researchedKnowledge.add(TMRegistry.getResearchName(research.getInteger("Research")));
        }

        NBTTagList researchProgressList = properties.getTagList("ResearchProgress", properties.getId());
        researchingKnowledge = new HashMap<String, Integer>();

        for (int i = 0; i < researchProgressList.tagCount(); i++) {
            NBTTagCompound research = researchProgressList.getCompoundTagAt(i);
            researchingKnowledge.put(TMRegistry.getResearchName(research.getInteger("Research")), research.getInteger("Progress"));
        }

        NBTTagList researchedList = properties.getTagList("ResearchList", properties.getId());
        researchList = new ArrayList<String>();

        for (int i = 0; i < researchedList.tagCount(); i++) {
            NBTTagCompound research = researchedList.getCompoundTagAt(i);
            researchList.add(TMRegistry.getResearchName(research.getInteger("Research")));
        }

        updateAbilities();
    }

    public static void saveProxyData(EntityPlayer player)
    {
        ExtendedPlayerKnowledge playerData = ExtendedPlayerKnowledge.get(player);
        NBTTagCompound savedData = new NBTTagCompound();

        playerData.saveNBTData(savedData);

        CommonProxy.storeEntityData(getSaveKey(player), savedData);
    }

    public static void loadProxyData(EntityPlayer player)
    {
        ExtendedPlayerKnowledge playerData = ExtendedPlayerKnowledge.get(player);
        NBTTagCompound savedData = CommonProxy.getEntityData(getSaveKey(player));

        if (savedData != null) {
            playerData.loadNBTData(savedData);
        }

        sync(player);
    }

    protected static String getSaveKey(EntityPlayer player)
    {
        return player.getCommandSenderName() + ":" + PROP_NAME;
    }

    public boolean canSpecialise()
    {
        return specialisation.equals("none");
    }

    public void setSpecialisation(String specialisation)
    {
        this.specialisation = specialisation;

        MinecraftForge.EVENT_BUS.post(new TMEvent.SpecialisationChosenEvent(player, TMRegistry.getSpecialisation(specialisation)));

        updateAbilities();
    }

    public ISpecialisation getSpecialisation()
    {
        return TMRegistry.getSpecialisation(this.specialisation);
    }

    public boolean isSpecialisation(String name)
    {
        return specialisation.equals(name);
    }

    public void researchKnowledge(String research, String knowledge, int amount, boolean flag)
    {
        if (researchList.contains(research))
            return;

        int progress = getKnowledgeProgress(knowledge);
        int data = nanites.getData();

        if (progress == 100)
            return;

        ResearchProgressEvent event = new TMEvent.ResearchProgressEvent(player, TMRegistry.getResearch(research), progress, amount);
        MinecraftForge.EVENT_BUS.post(event);

        amount += event.modifier;
        boolean flag2 = false;

        if ((data + amount) > nanites.getMaxData())
            return;

        if ((progress + amount) >= 100) {
            if (nanites.increaseData(100 - progress)) {
                amount = 100;
                flag2 = true;
            }
        } else {
            if (nanites.increaseData((progress + amount))) {
                amount += progress;
                flag2 = true;
            }
        }

        if (flag2) {
            researchingKnowledge.put(knowledge, amount);

            if (!flag) {
                researchList.add(research);
            }

            sync();
        }
    }

    public int getKnowledgeProgress(String knowledge)
    {
        Integer progress = researchingKnowledge.get(knowledge);

        return progress == null ? 0 : progress;
    }

    public boolean hasKnowledge(String knowledge)
    {
        return researchedKnowledge.contains(knowledge);
    }

    public boolean hasResearched(String research)
    {
        return researchList.contains(research);
    }

    public Set<String> getResearchingKnowledge()
    {
        return researchingKnowledge.keySet();
    }

    public int transferKnowledgeProgress(String knowledge, int progress)
    {
        int current = researchingKnowledge.get(knowledge);

        if (current > 0) {
            if (progress > current) {
                researchingKnowledge.remove(knowledge);

                return current;
            } else {
                researchingKnowledge.put(knowledge, (current - progress));

                return progress;
            }
        }

        return 0;
    }

    public void updateAbilities()
    {
        if (abilities == null) {
            abilities = ExtendedPlayerAbilities.get(player);
        }

        if (nanites == null) {
            nanites = ExtendedNanites.get(player);
        }

        abilities.buildAbilityLists();
    }

}
