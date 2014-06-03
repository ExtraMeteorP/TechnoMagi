package com.ollieread.technomagi.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.ability.IAbilityActive;
import com.ollieread.technomagi.api.ability.IAbilityPassive;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageSyncAbilities;

import cpw.mods.fml.common.eventhandler.Event;

/**
 * This class handles all player specific matters outside of individual blocks
 * and items. It allows for the researching and discovery of knowledge via
 * internal nanites, keeps track of nanites, keeps track of specialisation and
 * allows for the usage of abilities, both passive and active.
 * 
 * @author ollieread
 * 
 */
public class PlayerAbilities extends ExtendedProperties
{

    public static String PROP_NAME = "TechnoMageAbilities";
    /**
     * A list of passive abilities currently available to the player. This is
     * not stored in NBT but is generated when a change or update to researched
     * knowledge takes place.
     */
    private List<String> passiveAbilityList = new ArrayList<String>();
    /**
     * A list of active abilities currently available to the player. This is not
     * stored in NBT but is generated when a change or update to researched
     * knowledge takes place.
     */
    private List<String> activeAbilityList = new ArrayList<String>();

    private int currentAbility = -1;

    public PlayerAbilities(EntityPlayer player)
    {
        super(player);
    }

    public static final void register(EntityPlayer player)
    {
        player.registerExtendedProperties(PROP_NAME, new PlayerAbilities(player));
    }

    public static final PlayerAbilities get(EntityPlayer player)
    {
        return (PlayerAbilities) player.getExtendedProperties(PROP_NAME);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = new NBTTagCompound();

        properties.setInteger("Current", currentAbility);

        compound.setTag(PROP_NAME, properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag(PROP_NAME);

        currentAbility = properties.getInteger("Current");
    }

    public static void saveProxyData(EntityPlayer player)
    {
        PlayerAbilities playerData = PlayerAbilities.get(player);
        NBTTagCompound savedData = new NBTTagCompound();

        playerData.saveNBTData(savedData);

        CommonProxy.storeEntityData(getSaveKey(player), savedData);
    }

    public static void loadProxyData(EntityPlayer player)
    {
        PlayerAbilities playerData = PlayerAbilities.get(player);
        NBTTagCompound savedData = CommonProxy.getEntityData(getSaveKey(player));

        if (savedData != null) {
            playerData.loadNBTData(savedData);
        }

        PacketHandler.INSTANCE.sendTo(new MessageSyncAbilities(player), (EntityPlayerMP) player);
    }

    public void buildAbilityLists()
    {
        passiveAbilityList = new ArrayList<String>();
        activeAbilityList = new ArrayList<String>();

        Collection<IAbilityPassive> passiveAbilities = TMRegistry.getPassiveAbilityList();
        Collection<IAbilityActive> activeAbilities = TMRegistry.getActiveAbilityList();

        for (Iterator<IAbilityPassive> i = passiveAbilities.iterator(); i.hasNext();) {
            IAbilityPassive ability = i.next();
            if (ability.isAvailable(PlayerKnowledge.get(player))) {
                passiveAbilityList.add(TMRegistry.getPassiveAbilityName(ability));
            }
        }

        for (Iterator<IAbilityActive> i = activeAbilities.iterator(); i.hasNext();) {
            IAbilityActive ability = i.next();
            if (ability.isAvailable(PlayerKnowledge.get(player))) {
                activeAbilityList.add(ability.getName());
            }
        }
    }

    public boolean hasPassiveAbility(String name)
    {
        return passiveAbilityList.contains(name);
    }

    public boolean hasActiveAbility(String name)
    {
        return activeAbilityList.contains(name);
    }

    public List<String> getActiveAbilities()
    {
        return activeAbilityList;
    }

    public void setNextAbility()
    {
        int next = currentAbility + 1;

        if (next >= 0) {
            if (next >= activeAbilityList.size()) {
                currentAbility = -1;
            } else {
                currentAbility = next;
            }
        } else {
            currentAbility = 0;
        }
    }

    public void setPreviousAbility()
    {
        int prev = currentAbility - 1;

        if (prev >= 0) {
            if (prev >= activeAbilityList.size()) {
                currentAbility = -1;
            } else {
                currentAbility = prev;
            }
        } else {
            if (prev == -1) {
                currentAbility = prev;
            } else {
                currentAbility = activeAbilityList.size() - 1;
            }
        }
    }

    public void useAbility(Event event)
    {
        if (currentAbility >= 0) {
            String name = activeAbilityList.get(currentAbility);
            PlayerKnowledge charon = PlayerKnowledge.get(player);
            IAbilityActive ability = TMRegistry.getActiveAbility(name);

            if (ability.canUse(charon, event)) {
                ability.use(charon, event);
            }
        }
    }

    public int getCurrentAbility()
    {
        return currentAbility;
    }

}
