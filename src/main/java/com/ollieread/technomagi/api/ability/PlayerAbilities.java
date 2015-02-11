package com.ollieread.technomagi.api.ability;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.technomagi.api.ability.AbilityPayload.AbilityResult;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.event.AbilityCastEvent.Cast;
import com.ollieread.technomagi.api.event.AbilityCastEvent.Start;
import com.ollieread.technomagi.api.event.TechnomagiHooks;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;

/**
 * 
 * @author ollieread
 *
 */
public class PlayerAbilities
{
    protected PlayerTechnomagi technomagi;

    protected List<IAbilityCast> abilityCastableList = new ArrayList<IAbilityCast>();

    protected int currentAbilityIndex = -1;

    protected Map<String, Integer> abilityCastableCooldowns = new ConcurrentHashMap<String, Integer>();
    protected Map<String, Integer> abilityCastableModes = new ConcurrentHashMap<String, Integer>();

    protected Map<String, IAbilityData> abilityData = new ConcurrentHashMap<String, IAbilityData>();

    protected AbilityPayload abilityCastingPayload = null;
    protected IAbilityCast currentCastingAbility = null;

    public PlayerAbilities(PlayerTechnomagi technomagi)
    {
        this.technomagi = technomagi;
    }

    /**
     * Replace the current castable abilities. Typically fired when knowledge is
     * unlocked.
     * 
     * @param castableAbilities
     */
    public void setCastableAbilities(List<IAbilityCast> castableAbilities)
    {
        abilityCastableList = castableAbilities;

        if (abilityCastableList.size() > currentAbilityIndex) {
            currentAbilityIndex = -1;
        }
    }

    /**
     * Decrement the current ability index.
     */
    public void setPreviousAbility()
    {
        if (currentAbilityIndex == 0) {
            currentAbilityIndex = -1;
        } else {
            currentAbilityIndex -= 1;
        }
    }

    /**
     * Increment the current ability index.
     */
    public void setNextAbility()
    {
        if (currentAbilityIndex == abilityCastableList.size()) {
            currentAbilityIndex = -1;
        } else {
            currentAbilityIndex += 1;
        }
    }

    /**
     * Get the current ability index.
     * 
     * @return
     */
    public int getCurrentAbilityIndex()
    {
        return currentAbilityIndex;
    }

    /**
     * Get the current ability.
     * 
     * @return
     */
    public IAbilityCast getCurrentAbility()
    {
        if (currentAbilityIndex != -1) {
            return abilityCastableList.get(currentAbilityIndex);
        }

        return null;
    }

    /**
     * Get an ability at the assigned index from the players currently available
     * abilities.
     * 
     * @param index
     * @return
     */
    public IAbilityCast getAbility(int index)
    {
        if (index > -1 && abilityCastableList.size() >= index) {
            return abilityCastableList.get(index);
        }

        return null;
    }

    public int getCastableAbilityMode(String name)
    {
        if (abilityCastableModes.containsKey(name)) {
            return abilityCastableModes.get(name);
        }

        return 0;
    }

    public IAbilityData addAbilityData(String name, IAbilityData data)
    {
        if (!abilityData.containsKey(name)) {
            abilityData.put(name, data);
            return data;
        }

        return null;
    }

    public IAbilityData getAbilityData(String name)
    {
        if (abilityData.containsKey(name)) {
            return abilityData.get(name);
        }

        return null;
    }

    public void removeAbilityData(String name)
    {
        if (abilityData.containsKey(name)) {
            abilityData.remove(name);
        }
    }

    public void resetAbilityData()
    {
        abilityData.clear();
    }

    public IAbilityCast startCasting(PlayerTechnomagi technomage, ItemStack stack, AbilityPayload payload)
    {
        if (stack != null && stack.getItem() instanceof IAbilityItem) {
            IAbilityItem abilityItem = (IAbilityItem) stack.getItem();
            IAbilityCast ability = null;

            if (abilityItem.isAbilityLocked(stack)) {
                ability = abilityItem.getLockedAbility(stack);
            } else {
                ability = getCurrentAbility();
            }

            abilityCastingPayload = payload;
            payload.setAbilityItem(stack);
            currentCastingAbility = ability;

            if (ability != null && ability.canUse(technomage, abilityCastingPayload, getCastableAbilityMode(currentCastingAbility.getName()))) {
                Start start = TechnomagiHooks.startCasting(technomage.getPlayer(), currentCastingAbility, abilityCastingPayload);

                if (!start.isCanceled()) {
                    technomage.getPlayer().setItemInUse(stack, currentCastingAbility.getMaxFocus(getCastableAbilityMode(currentCastingAbility.getName())));
                    if (casting(technomage)) {
                        return ability;
                    } else {
                        return null;
                    }
                }

                stopCasting(technomage, false);
            }
        }

        return null;
    }

    public boolean casting(PlayerTechnomagi technomage)
    {
        if (abilityCastingPayload != null && currentCastingAbility != null) {
            abilityCastingPayload.duration += 1;
            Cast cast = TechnomagiHooks.continueCasting(technomage.getPlayer(), currentCastingAbility, abilityCastingPayload);
            boolean flag = false;

            if (!cast.isCanceled()) {
                Result result = cast.getResult();

                if (result.equals(Result.ALLOW) || result.equals(Result.DEFAULT)) {
                    currentCastingAbility.use(technomage, abilityCastingPayload, getCastableAbilityMode(currentCastingAbility.getName()));
                    AbilityResult castingResult = abilityCastingPayload.result;

                    if (!castingResult.equals(AbilityResult.CONTINUE)) {
                        if (castingResult.equals(AbilityResult.COMPLETE)) {
                            flag = true;
                        }
                    } else {
                        return true;
                    }
                }
            }

            stopCasting(technomage, flag);
        }

        return false;
    }

    public void stopCasting(PlayerTechnomagi technomage, boolean complete)
    {
        if (abilityCastingPayload != null && currentCastingAbility != null) {
            currentCastingAbility.stoppedUsing(technomage, abilityCastingPayload, getCastableAbilityMode(currentCastingAbility.getName()));
            TechnomagiHooks.stopCasting(technomage.getPlayer(), currentCastingAbility, abilityCastingPayload, complete);
            currentCastingAbility = null;
            abilityCastingPayload = null;
        }
    }

    public void update(PlayerTechnomagi technomage, Side side)
    {
        if (side.equals(Side.SERVER)) {
            /**
             * Update the data, if it requires updating.
             */
            for (IAbilityData data : abilityData.values()) {
                if (data.requiresUpdate()) {
                    data.update();
                }
            }

            /**
             * Reduce the cooldowns.
             */
            for (Entry<String, Integer> entry : abilityCastableCooldowns.entrySet()) {
                int cooldown = entry.getValue();
                cooldown--;

                if (cooldown == 0) {
                    abilityCastableCooldowns.remove(entry.getKey());
                } else {
                    abilityCastableCooldowns.put(entry.getKey(), cooldown);
                }
            }
        }

        casting(technomage);
    }

    public void saveNBTData(NBTTagCompound compound)
    {
        /**
         * Ability index.
         */
        compound.setInteger("Index", currentAbilityIndex);

        /**
         * Ability castable cooldown.
         */
        NBTTagList cooldownCompound = new NBTTagList();

        for (Entry<String, Integer> entry : abilityCastableCooldowns.entrySet()) {
            NBTTagCompound ability = new NBTTagCompound();
            ability.setString("Ability", entry.getKey());
            ability.setInteger("Cooldown", entry.getValue());
            cooldownCompound.appendTag(ability);
        }

        compound.setTag("Cooldowns", cooldownCompound);

        /**
         * Ability castable modes.
         */
        NBTTagList modeCompound = new NBTTagList();

        for (Entry<String, Integer> entry : abilityCastableModes.entrySet()) {
            NBTTagCompound ability = new NBTTagCompound();
            ability.setString("Ability", entry.getKey());
            ability.setInteger("Mode", entry.getValue());
            cooldownCompound.appendTag(modeCompound);
        }

        compound.setTag("Modes", modeCompound);

        /**
         * Ability Data
         */
        NBTTagList dataCompound = new NBTTagList();

        for (Entry<String, IAbilityData> entry : abilityData.entrySet()) {
            NBTTagCompound ability = new NBTTagCompound();
            NBTTagCompound data = new NBTTagCompound();
            entry.getValue().saveNBTData(data);
            ability.setString("Name", entry.getKey());
            ability.setString("Class", entry.getValue().getClass().getName());
            ability.setTag("Data", data);
            dataCompound.appendTag(dataCompound);
        }

        compound.setTag("Data", dataCompound);

        /**
         * Ability Payload
         */
        if (abilityCastingPayload != null) {
            NBTTagCompound payloadCompound = new NBTTagCompound();
            abilityCastingPayload.writeToNBT(payloadCompound);
            compound.setTag("Payload", payloadCompound);
        } else {
            compound.setBoolean("NoPayload", false);
        }
    }

    public void loadNBTData(NBTTagCompound compound)
    {
        /**
         * Ability index.
         */
        currentAbilityIndex = compound.getInteger("Index");

        /**
         * Ability castable cooldown.
         */
        NBTTagList cooldownCompound = compound.getTagList("Cooldown", compound.getId());
        abilityCastableCooldowns = new ConcurrentHashMap<String, Integer>();

        for (int i = 0; i < cooldownCompound.tagCount(); i++) {
            NBTTagCompound nbt = cooldownCompound.getCompoundTagAt(i);
            abilityCastableCooldowns.put(nbt.getString("Ability"), nbt.getInteger("Cooldown"));
        }

        /**
         * Ability castable modes.
         */
        NBTTagList modeCompound = compound.getTagList("Modes", compound.getId());
        abilityCastableModes = new ConcurrentHashMap<String, Integer>();

        for (int i = 0; i < modeCompound.tagCount(); i++) {
            NBTTagCompound nbt = modeCompound.getCompoundTagAt(i);
            abilityCastableModes.put(nbt.getString("Ability"), nbt.getInteger("Mode"));
        }

        /**
         * Ability Data
         */
        NBTTagList dataCompound = compound.getTagList("Data", compound.getId());
        abilityData = new ConcurrentHashMap<String, IAbilityData>();

        for (int i = 0; i < dataCompound.tagCount(); i++) {
            NBTTagCompound nbt = dataCompound.getCompoundTagAt(i);
            try {
                Class dataClass = Class.forName(nbt.getString("Class"));
                IAbilityData dataInstance = (IAbilityData) dataClass.newInstance();
                dataInstance.loadNBTData(nbt.getCompoundTag("Data"));
                abilityData.put(nbt.getString("Name"), dataInstance);
            } catch (ClassNotFoundException e) {
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            }
        }

        /**
         * Ability Payload
         */
        if (!compound.hasKey("NoPayload")) {
            abilityCastingPayload = new AbilityPayload();
            abilityCastingPayload.readFromNBT(compound.getCompoundTag("Payload"));
        } else {
            abilityCastingPayload = null;
        }
    }
}
