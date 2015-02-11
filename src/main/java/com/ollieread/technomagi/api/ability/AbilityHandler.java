package com.ollieread.technomagi.api.ability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ollieread.technomagi.api.entity.PlayerTechnomagi;

/**
 * The ability handler.
 * 
 * This is where abilities are registered and called.
 * 
 * @author ollieread
 *
 */
public class AbilityHandler
{

    /**
     * The type of the ability. Right now, the primary use for this, is for
     * region filters.
     * 
     * @author ollieread
     *
     */
    public static enum AbilityType {
        SELF, PLAYER, ENTITY, ITEM, BLOCK, INTERACTION
    }

    protected static Map<String, IAbilityCast> abilityCastableList = new LinkedHashMap<String, IAbilityCast>();

    /**
     * 
     * 
     * @param ability
     * @return
     */
    public IAbilityCast addCastableAbility(IAbilityCast ability)
    {
        if (!abilityCastableList.containsKey(ability.getName())) {
            abilityCastableList.put(ability.getName(), ability);

            return ability;
        }

        return null;
    }

    /**
     * 
     * 
     * @param name
     * @return
     */
    public IAbilityCast getCastableAbility(String name)
    {
        if (abilityCastableList.containsKey(name)) {
            return abilityCastableList.get(name);
        }

        return null;
    }

    /**
     * 
     * 
     * @param technomage
     * @return
     */
    public Collection<IAbilityCast> getCastableAbilitiesFor(PlayerTechnomagi technomage)
    {
        Collection<IAbilityCast> abilitiesFor = new ArrayList<IAbilityCast>();

        for (IAbilityCast ability : abilityCastableList.values()) {
            if (ability.isAvailable(technomage)) {
                abilitiesFor.add(ability);
            }
        }

        return abilitiesFor;
    }

}
