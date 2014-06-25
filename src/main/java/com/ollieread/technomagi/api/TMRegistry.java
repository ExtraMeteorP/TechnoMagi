package com.ollieread.technomagi.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import com.ollieread.technomagi.api.ability.IAbilityActive;
import com.ollieread.technomagi.api.ability.IAbilityPassive;
import com.ollieread.technomagi.api.research.IKnowledge;
import com.ollieread.technomagi.api.research.IResearch;
import com.ollieread.technomagi.api.research.IResearchCrafting;
import com.ollieread.technomagi.api.research.IResearchEvent;
import com.ollieread.technomagi.api.research.IResearchMining;
import com.ollieread.technomagi.api.research.IResearchMonitoring;
import com.ollieread.technomagi.api.research.IResearchObservation;
import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

/**
 * The registry for all things related to the mod. Allows for registration of
 * abilities, knowledge and research.
 * 
 * @author ollieread
 */
public class TMRegistry
{

    protected static List<ISpecialisation> specList = new ArrayList<ISpecialisation>();
    protected static List<IResearch> researchList = new ArrayList<IResearch>();
    protected static List<IAbilityPassive> passiveAbilityList = new ArrayList<IAbilityPassive>();
    protected static List<IAbilityActive> activeAbilityList = new ArrayList<IAbilityActive>();
    protected static List<IKnowledge> knowledgeList = new ArrayList<IKnowledge>();

    protected static Map<String, Integer> specNames = new HashMap<String, Integer>();
    protected static Map<String, Integer> researchNames = new HashMap<String, Integer>();
    protected static Map<String, Integer> passiveNames = new HashMap<String, Integer>();
    protected static Map<String, Integer> activeNames = new HashMap<String, Integer>();
    protected static Map<String, Integer> knowledgeNames = new HashMap<String, Integer>();

    protected static Map<Integer, List<Integer>> researchEvents = new HashMap<Integer, List<Integer>>();
    protected static Map<ItemStack, List<Integer>> researchCrafting = new HashMap<ItemStack, List<Integer>>();
    protected static Map<Integer, List<Integer>> researchMonitoring = new HashMap<Integer, List<Integer>>();
    protected static Map<Integer, List<Integer>> researchObservation = new HashMap<Integer, List<Integer>>();
    protected static Map<ItemStack, List<Integer>> researchMining = new HashMap<ItemStack, List<Integer>>();

    protected static Map<Integer, List<Integer>> passiveAbilityEvents = new HashMap<Integer, List<Integer>>();

    public static void registerSpecialisation(ISpecialisation spec)
    {
        String name = spec.getName();

        if (getSpecialisation(name) == null && !specList.contains(spec)) {
            int i = specList.size();

            specList.add(i, spec);
            specNames.put(name, i);
        }
    }

    public static ISpecialisation getSpecialisation(String name)
    {
        int id = getSpecialisationId(name);

        if (id > -1) {
            return specList.get(id);
        }

        return null;
    }

    public static int getSpecialisationId(String name)
    {
        if (name.equals("none")) {
            return -1;
        } else if (specNames.containsKey(name)) {
            return specNames.get(name);
        }

        return -1;
    }

    public static String getSpecialisationName(int id)
    {
        return id > -1 ? specList.get(id).getName() : "none";
    }

    public static Collection<ISpecialisation> getSpecialisations()
    {
        return specList;
    }

    public static void registerKnowledge(IKnowledge knowledge)
    {
        String name = knowledge.getName();

        if (getKnowledge(name) == null && !knowledgeList.contains(knowledge)) {
            int i = knowledgeList.size();

            knowledgeList.add(i, knowledge);
            knowledgeNames.put(name, i);
        }
    }

    public static IKnowledge getKnowledge(String knowledge)
    {
        int id = getKnowledgeId(knowledge);

        if (id > -1) {
            return knowledgeList.get(id);
        }

        return null;
    }

    public static int getKnowledgeId(String name)
    {
        if (knowledgeNames.containsKey(name)) {
            return knowledgeNames.get(name);
        }

        return -1;
    }

    public static String getKnowledgeName(int id)
    {
        return id > -1 ? knowledgeList.get(id).getName() : null;
    }

    public static void registerResearch(IResearch research)
    {
        String name = research.getName();

        if (getResearch(name) == null && !researchList.contains(research)) {
            int i = researchList.size();
            researchList.add(i, research);
            researchNames.put(name, i);

            if (research instanceof IResearchEvent) {
                registerResearchEvent(i, (IResearchEvent) research);
            } else if (research instanceof IResearchCrafting) {
                registerResearchCrafting(i, (IResearchCrafting) research);
            } else if (research instanceof IResearchMining) {
                registerResearchMining(i, (IResearchMining) research);
            } else if (research instanceof IResearchMonitoring) {
                registerResearchMonitoring(i, (IResearchMonitoring) research);
            } else if (research instanceof IResearchObservation) {
                registerResearchObservation(i, (IResearchObservation) research);
            }
        }
    }

    public static void registerResearchEvent(int i, IResearchEvent research)
    {
        if (researchEvents.containsKey(research.getEvent())) {
            researchEvents.get(research.getEvent()).add(i);
        } else {
            researchEvents.put(research.getEvent(), Arrays.asList(i));
        }
    }

    public static void registerResearchCrafting(int i, IResearchCrafting research)
    {
        if (researchCrafting.containsKey(research.getCrafting())) {
            researchCrafting.get(research.getCrafting()).add(i);
        } else {
            researchCrafting.put(research.getCrafting(), Arrays.asList(i));
        }
    }

    public static void registerResearchMining(int i, IResearchMining research)
    {
        if (researchCrafting.containsKey(research.getMining())) {
            researchCrafting.get(research.getMining()).add(i);
        } else {
            researchCrafting.put(research.getMining(), Arrays.asList(i));
        }
    }

    public static void registerResearchMonitoring(int i, IResearchMonitoring research)
    {
        if (researchMonitoring.containsKey(research.getMonitoringEvent())) {
            researchMonitoring.get(research.getMonitoringEvent()).add(i);
        } else {
            researchMonitoring.put(research.getMonitoringEvent(), Arrays.asList(i));
        }
    }

    public static void registerResearchObservation(int i, IResearchObservation research)
    {
        if (researchObservation.containsKey(research.getObservationEvent())) {
            researchObservation.get(research.getObservationEvent()).add(i);
        } else {
            researchObservation.put(research.getObservationEvent(), Arrays.asList(i));
        }
    }

    public static IResearch getResearch(String name)
    {
        int id = getResearchId(name);

        if (id > -1) {
            return researchList.get(id);
        }

        return null;
    }

    public static int getResearchId(String name)
    {
        if (researchNames.containsKey(name)) {
            return researchNames.get(name);
        }

        return -1;
    }

    public static String getResearchName(int id)
    {
        return id > -1 ? researchList.get(id).getName() : null;
    }

    public static void researchEvent(int id, Event event, ExtendedPlayerKnowledge charon)
    {
        if (!charon.canSpecialise() && researchEvents.containsKey(id)) {
            for (Iterator<Integer> i = researchEvents.get(id).iterator(); i.hasNext();) {
                IResearch research = researchList.get(i.next());

                if (!charon.hasResearched(research.getName()) && research.canPerform(charon)) {
                    charon.researchKnowledge(research.getName(), research.getKnowledge(), research.getProgress(), research.isRepeating());
                }
            }
        }
    }

    public static void researchCrafting(ItemCraftedEvent event, ExtendedPlayerKnowledge charon)
    {
        ItemStack id = event.crafting;
        if (!charon.canSpecialise() && researchCrafting.containsKey(id)) {
            for (Iterator<Integer> i = researchCrafting.get(id).iterator(); i.hasNext();) {
                IResearch research = researchList.get(i.next());

                if (!charon.hasResearched(research.getName()) && research.canPerform(charon)) {
                    charon.researchKnowledge(research.getName(), research.getKnowledge(), research.getProgress(), research.isRepeating());
                }
            }
        }
    }

    public static void researchMining(HarvestDropsEvent event, ExtendedPlayerKnowledge charon)
    {
        if (Item.getItemFromBlock(event.block) != null) {
            ItemStack id = new ItemStack(event.block, 1, event.blockMetadata);
            if (!charon.canSpecialise() && researchMining.containsKey(id)) {
                for (Iterator<Integer> i = researchMining.get(id).iterator(); i.hasNext();) {
                    IResearch research = researchList.get(i.next());

                    if (!charon.hasResearched(research.getName()) && research.canPerform(charon)) {
                        charon.researchKnowledge(research.getName(), research.getKnowledge(), research.getProgress(), research.isRepeating());
                    }
                }
            }
        }
    }

    public static void researchMonitoring(int id, ExtendedPlayerKnowledge charon)
    {
        if (!charon.canSpecialise() && researchMonitoring.containsKey(id)) {
            for (Iterator<Integer> i = researchMonitoring.get(id).iterator(); i.hasNext();) {
                IResearch research = researchList.get(i.next());

                if (!charon.hasResearched(research.getName()) && research.canPerform(charon)) {
                    charon.researchKnowledge(research.getName(), research.getKnowledge(), research.getProgress(), research.isRepeating());
                }
            }
        }
    }

    public static void researchObservation(int id, ExtendedPlayerKnowledge charon)
    {
        if (!charon.canSpecialise() && researchObservation.containsKey(id)) {
            for (Iterator<Integer> i = researchObservation.get(id).iterator(); i.hasNext();) {
                IResearch research = researchList.get(i.next());

                if (!charon.hasResearched(research.getName()) && research.canPerform(charon)) {
                    charon.researchKnowledge(research.getName(), research.getKnowledge(), research.getProgress(), research.isRepeating());
                }
            }
        }
    }

    public static void registerActiveAbility(IAbilityActive ability)
    {
        String name = ability.getName();

        if (!activeAbilityList.contains(ability)) {
            int i = activeAbilityList.size();

            activeAbilityList.add(i, ability);
            activeNames.put(name, i);
        }
    }

    public static void registerPassiveAbility(IAbilityPassive ability)
    {
        String name = ability.getName();
        if (!passiveAbilityList.contains(ability)) {
            int i = passiveAbilityList.size();

            passiveAbilityList.add(i, ability);
            passiveNames.put(name, i);

            if (passiveAbilityEvents.containsKey(ability.getEvent())) {
                passiveAbilityEvents.get(ability.getEvent()).add(i);
            } else {
                passiveAbilityEvents.put(ability.getEvent(), Arrays.asList(i));
            }
        }
    }

    public static int getActiveAbilityId(String name)
    {
        if (activeNames.containsKey(name)) {
            return activeNames.get(name);
        }

        return -1;
    }

    public static int getPassiveAbilityId(String name)
    {
        if (passiveNames.containsKey(name)) {
            return passiveNames.get(name);
        }

        return -1;
    }

    public static String getPassiveAbilityName(IAbilityPassive ability)
    {
        Set<Entry<String, Integer>> set = passiveNames.entrySet();
        int id = passiveAbilityList.indexOf(ability);

        for (Iterator<Entry<String, Integer>> i = set.iterator(); i.hasNext();) {
            Entry<String, Integer> p = i.next();

            if (p.getValue().equals(i)) {
                return p.getKey();
            }
        }

        return null;
    }

    public static IAbilityActive getActiveAbility(String name)
    {
        int id = getActiveAbilityId(name);

        if (id > -1 && id < activeAbilityList.size()) {
            return activeAbilityList.get(id);
        }

        return null;
    }

    public static IAbilityPassive getPassiveAbility(String name)
    {
        int id = getPassiveAbilityId(name);

        if (id > -1 && id < passiveAbilityList.size()) {
            return passiveAbilityList.get(id);
        }

        return null;
    }

    public static Collection<IAbilityActive> getActiveAbilityList()
    {
        return activeAbilityList;
    }

    public static Collection<IAbilityPassive> getPassiveAbilityList()
    {
        return passiveAbilityList;
    }

    public static void passiveAbilityEvent(int id, Event event, ExtendedPlayerKnowledge charon)
    {
        if (!charon.canSpecialise() && passiveAbilityEvents.containsKey(id)) {
            for (Iterator<Integer> i = passiveAbilityEvents.get(id).iterator(); i.hasNext();) {
                IAbilityPassive ability = passiveAbilityList.get(i.next());
                if (charon.abilities.hasPassiveAbility(ability.getName()) && ability.canUse(charon)) {
                    ability.use(event, charon);
                }
            }
        }
    }

}
