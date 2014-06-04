package com.ollieread.technomagi.player;

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
import com.ollieread.technomagi.network.message.MessageSyncNanites;

/**
 * This class handles all player specific matters outside of individual blocks
 * and items. It allows for the researching and discovery of knowledge via
 * internal nanites, keeps track of nanites, keeps track of specialisation and
 * allows for the usage of abilities, both passive and active.
 * 
 * @author ollieread
 * 
 */
public class PlayerKnowledge extends ExtendedProperties
{

    public static String PROP_NAME = "TechnoMageKnowledge";
    /**
     * The players specialisation, default is "none" as NBT throws a wobbler if
     * you pass an empty string.
     */
    private String specialisation = "none";
    /**
     * The players nanites, default is 100.
     */
    private int nanites = 100;
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
     * ever piece of research will go in here, only those that cannot be
     * repeated.
     */
    private List<String> researchList = new ArrayList<String>();

    public PlayerAbilities abilities;

    public PlayerKnowledge(EntityPlayer player)
    {
        super(player);
        abilities = PlayerAbilities.get(player);
    }

    public static final void register(EntityPlayer player)
    {
        player.registerExtendedProperties(PROP_NAME, new PlayerKnowledge(player));
    }

    public static final PlayerKnowledge get(EntityPlayer player)
    {
        return (PlayerKnowledge) player.getExtendedProperties(PROP_NAME);
    }

    private void sync()
    {
        PacketHandler.INSTANCE.sendTo(new MessageSyncKnowledge(player), (EntityPlayerMP) player);
    }

    private void syncNanites()
    {
        PacketHandler.INSTANCE.sendTo(new MessageSyncNanites(nanites), (EntityPlayerMP) player);
    }

    public static void sync(EntityPlayer player)
    {
        PacketHandler.INSTANCE.sendTo(new MessageSyncKnowledge(player), (EntityPlayerMP) player);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = new NBTTagCompound();

        properties.setInteger("Specialisation", TMRegistry.getSpecialisationId(specialisation));
        properties.setInteger("Nanites", nanites);

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
        nanites = properties.getInteger("Nanites");

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
        PlayerKnowledge playerData = PlayerKnowledge.get(player);
        NBTTagCompound savedData = new NBTTagCompound();

        playerData.saveNBTData(savedData);

        CommonProxy.storeEntityData(getSaveKey(player), savedData);
    }

    public static void loadProxyData(EntityPlayer player)
    {
        PlayerKnowledge playerData = PlayerKnowledge.get(player);
        NBTTagCompound savedData = CommonProxy.getEntityData(getSaveKey(player));

        if (savedData != null) {
            playerData.loadNBTData(savedData);
        }

        sync(player);
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

    public boolean increaseNanites(int i)
    {
        int maxNanites = getMaxNanites();

        if (nanites < maxNanites) {
            if ((nanites + i) > maxNanites) {
                nanites = maxNanites;
                syncNanites();
                return true;
            } else {
                nanites += i;
                syncNanites();
                return true;
            }
        }

        return false;
    }

    public boolean decreaseNanites(int i)
    {
        if (nanites > 0) {
            if ((nanites - i) >= 0) {
                nanites -= i;
                syncNanites();
                return true;
            }
        }

        return false;
    }

    public void setNanites(int nanites)
    {
        this.nanites = nanites;
    }

    public int getNanites()
    {
        return nanites;
    }

    public int getMaxNanites()
    {
        int maxNanites = 100;

        for (Iterator<Integer> it = researchingKnowledge.values().iterator(); it.hasNext();) {
            maxNanites -= it.next();
        }

        return maxNanites;
    }

    public void researchKnowledge(String research, String knowledge, int amount, boolean flag)
    {
        if (researchList.contains(research))
            return;

        int progress = getKnowledgeProgress(knowledge);

        if (progress == 100)
            return;

        ResearchProgressEvent event = new TMEvent.ResearchProgressEvent(player, TMRegistry.getResearch(research), progress, amount);
        MinecraftForge.EVENT_BUS.post(event);

        amount += event.modifier;
        boolean flag2 = false;

        if ((progress + amount) >= 100) {
            if (decreaseNanites(100 - progress)) {
                amount = 100;
                flag2 = true;
            }
        } else {
            if (decreaseNanites((progress + amount))) {
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

    public boolean hasResearched(String knowledge)
    {
        return researchedKnowledge.contains(knowledge);
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
            abilities = PlayerAbilities.get(player);
        }

        abilities.buildAbilityLists();
    }

}
