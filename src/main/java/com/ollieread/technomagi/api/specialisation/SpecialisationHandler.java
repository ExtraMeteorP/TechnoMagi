package com.ollieread.technomagi.api.specialisation;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpecialisationHandler
{

    protected Map<String, Specialisation> specialisationList = new LinkedHashMap<String, Specialisation>();

    /**
     * Register a specialisation.
     *
     * @param specialisation
     * @return
     */
    public Specialisation addSpecialisation(Specialisation specialisation)
    {
        if (!specialisationList.containsKey(specialisation.getName()) && !specialisationList.containsValue(specialisation)) {
            specialisationList.put(specialisation.getName(), specialisation);
            return specialisation;
        }

        return null;
    }

    public Specialisation getSpecialisation(String name)
    {
        if (specialisationList.containsKey(name)) {
            return specialisationList.get(name);
        }

        return null;
    }

    public Collection<Specialisation> getSpecialisations()
    {
        return specialisationList.values();
    }

}
