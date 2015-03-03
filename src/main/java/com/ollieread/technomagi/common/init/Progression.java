package com.ollieread.technomagi.common.init;

import com.ollieread.technomagi.common.knowledge.Arcane;
import com.ollieread.technomagi.common.knowledge.Energies;
import com.ollieread.technomagi.common.knowledge.Existence;
import com.ollieread.technomagi.common.knowledge.Nanites;
import com.ollieread.technomagi.common.knowledge.Organics;
import com.ollieread.technomagi.common.knowledge.Resources;
import com.ollieread.technomagi.common.knowledge.Technomancy;

public class Progression
{

    public static void init()
    {
        Technomancy.knowledge();
        Nanites.knowledge();
        Energies.knowledge();
        Existence.knowledge();
        Organics.knowledge();
        Resources.knowledge();
        Arcane.knowledge();

        Technomancy.mappings();
        Resources.mappings();
        Energies.mappings();
        Nanites.mappings();
        Existence.mappings();
        Organics.mappings();
        Arcane.mappings();
    }
}
