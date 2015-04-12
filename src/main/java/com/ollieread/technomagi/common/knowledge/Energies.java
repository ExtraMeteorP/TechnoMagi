package com.ollieread.technomagi.common.knowledge;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.KnowledgeCategory;
import com.ollieread.technomagi.common.knowledge.energies.BasicEnergy;
import com.ollieread.technomagi.common.knowledge.energies.ElectromagneticPockets;
import com.ollieread.technomagi.common.knowledge.energies.EnergyHeat;
import com.ollieread.technomagi.common.knowledge.energies.EnergyLife;
import com.ollieread.technomagi.common.knowledge.energies.EnergyLight;
import com.ollieread.technomagi.common.knowledge.energies.EnergyPolarHeat;
import com.ollieread.technomagi.common.knowledge.energies.EnergyPolarLife;
import com.ollieread.technomagi.common.knowledge.energies.EnergyPolarLight;
import com.ollieread.technomagi.common.knowledge.energies.EnergyPolarVoid;
import com.ollieread.technomagi.common.knowledge.energies.EnergyVoid;
import com.ollieread.technomagi.common.knowledge.energies.ForceFields;
import com.ollieread.technomagi.common.knowledge.energies.Imbuement;
import com.ollieread.technomagi.util.ResourceHelper;

public class Energies
{

    public static KnowledgeCategory category;
    public static BasicEnergy basicEnergy;
    public static ElectromagneticPockets electromagneticPockets;
    public static EnergyHeat energyHeat;
    public static EnergyLife energyLife;
    public static EnergyLight energyLight;
    public static EnergyVoid energyVoid;
    public static EnergyPolarHeat energyPolarHeat;
    public static EnergyPolarLife energyPolarLife;
    public static EnergyPolarLight energyPolarLight;
    public static EnergyPolarVoid energyPolarVoid;
    public static ForceFields forcefields;
    public static Imbuement imbuement;

    public static void knowledge()
    {
        category = TechnomagiApi.addKnowledgeCategory("energies", ResourceHelper.texture("knowledge/energies.png"));
        category.setLocation(27, -15);

        basicEnergy = new BasicEnergy();
        basicEnergy.setLocation(0, 0);

        electromagneticPockets = new ElectromagneticPockets();
        electromagneticPockets.setLocation(0, -30);

        energyHeat = new EnergyHeat();
        energyHeat.setLocation(27, -15);

        energyLife = new EnergyLife();
        energyLife.setLocation(27, 15);

        energyLight = new EnergyLight();
        energyLight.setLocation(0, 30);

        energyVoid = new EnergyVoid();
        energyVoid.setLocation(-27, 15);

        energyPolarHeat = new EnergyPolarHeat();
        energyPolarHeat.setLocation(54, -30);

        energyPolarLife = new EnergyPolarLife();
        energyPolarLife.setLocation(54, 30);

        energyPolarLight = new EnergyPolarLight();
        energyPolarLight.setLocation(0, 60);

        energyPolarVoid = new EnergyPolarVoid();
        energyPolarVoid.setLocation(-54, 30);

        forcefields = new ForceFields();
        forcefields.setLocation(0, -60);

        imbuement = new Imbuement();
        imbuement.setLocation(-27, -15);

        electromagneticPockets.addPrerequisite(basicEnergy.getName());
        energyHeat.addPrerequisite(basicEnergy.getName());
        energyLife.addPrerequisite(basicEnergy.getName());
        energyLight.addPrerequisite(basicEnergy.getName());
        energyVoid.addPrerequisite(basicEnergy.getName());
        imbuement.addPrerequisite(basicEnergy.getName());
        energyPolarHeat.addPrerequisite(energyHeat.getName());
        energyPolarLife.addPrerequisite(energyLife.getName());
        energyPolarLight.addPrerequisite(energyLight.getName());
        energyPolarVoid.addPrerequisite(energyVoid.getName());
        forcefields.addPrerequisite(electromagneticPockets.getName());
    }

    public static void mappings()
    {
        basicEnergy.mappings();
        electromagneticPockets.mappings();
        energyHeat.mappings();
        energyLife.mappings();
        energyLight.mappings();
        energyVoid.mappings();
        energyPolarHeat.mappings();
        energyPolarLife.mappings();
        energyPolarLight.mappings();
        energyPolarVoid.mappings();
        forcefields.mappings();
        imbuement.mappings();
    }

    public static void recipes()
    {

    }

}
