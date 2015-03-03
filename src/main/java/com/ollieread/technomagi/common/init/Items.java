package com.ollieread.technomagi.common.init;

import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

import com.ollieread.technomagi.common.item.ItemCircuitPatterns;
import com.ollieread.technomagi.common.item.ItemCrystal;
import com.ollieread.technomagi.common.item.ItemCrystalCharged;
import com.ollieread.technomagi.common.item.ItemDigitalTool;
import com.ollieread.technomagi.common.item.ItemFluidCapsule;
import com.ollieread.technomagi.common.item.ItemInterface;
import com.ollieread.technomagi.common.item.ItemProcessorComponent;
import com.ollieread.technomagi.common.item.ItemResources;
import com.ollieread.technomagi.common.item.ItemSubtypes;
import com.ollieread.technomagi.common.item.entity.ItemEntityBrain;
import com.ollieread.technomagi.common.item.entity.ItemEntityCapture;
import com.ollieread.technomagi.common.item.entity.ItemEntityNanites;
import com.ollieread.technomagi.common.item.entity.ItemEntityPreservedBrain;
import com.ollieread.technomagi.common.item.entity.ItemEntitySample;
import com.ollieread.technomagi.common.item.staff.ItemStaffBasic;
import com.ollieread.technomagi.common.item.staff.ItemStaffCleric;
import com.ollieread.technomagi.common.item.staff.ItemStaffEngineer;
import com.ollieread.technomagi.common.item.staff.ItemStaffGuardian;
import com.ollieread.technomagi.common.item.staff.ItemStaffScholar;
import com.ollieread.technomagi.common.item.staff.ItemStaffShadow;
import com.ollieread.technomagi.util.ItemStackHelper;

import cpw.mods.fml.common.registry.GameRegistry;

public class Items
{

    public static ItemSubtypes resource;
    public static ItemSubtypes crystal;
    public static ItemSubtypes crystalCharged;
    public static Item personalInterface;
    public static Item staffBasic;
    public static Item staffCleric;
    public static Item staffEngineer;
    public static Item staffGuardian;
    public static Item staffScholar;
    public static Item staffShadow;
    public static Item digitalTool;
    public static Item entityBrain;
    public static Item entityBrainPreserved;
    public static Item entityCapture;
    public static Item entityNanites;
    public static Item entitySample;
    public static Item fluidCapsule;
    public static ItemSubtypes processorComponent;
    public static ItemSubtypes energyCircuits;

    public static void init()
    {
        resource = new ItemResources("resource");
        resource.setMaxStackSize(64);
        crystal = new ItemCrystal("crystal");
        crystalCharged = new ItemCrystalCharged("crystal_charged");
        personalInterface = new ItemInterface("interface");
        staffBasic = new ItemStaffBasic("staff_basic");
        staffCleric = new ItemStaffCleric("staff_cleric");
        staffEngineer = new ItemStaffEngineer("staff_engineer");
        staffGuardian = new ItemStaffGuardian("staff_guardian");
        staffScholar = new ItemStaffScholar("staff_scholar");
        staffShadow = new ItemStaffShadow("staff_shadow");
        digitalTool = new ItemDigitalTool("digital_tool");
        entityBrain = new ItemEntityBrain("brain");
        entityBrainPreserved = new ItemEntityPreservedBrain("brain_preserved");
        entityCapture = new ItemEntityCapture("capture");
        entityNanites = new ItemEntityNanites("nanites");
        entitySample = new ItemEntitySample("sample");
        fluidCapsule = new ItemFluidCapsule("capsule");
        processorComponent = new ItemProcessorComponent("components");
        energyCircuits = new ItemCircuitPatterns("circuit");

        // Register the items
        GameRegistry.registerItem(resource, "resources");
        GameRegistry.registerItem(crystal, "crystal");
        GameRegistry.registerItem(crystalCharged, "crystal_charged");
        GameRegistry.registerItem(personalInterface, "interface");
        GameRegistry.registerItem(staffBasic, "staff_basic");
        GameRegistry.registerItem(staffCleric, "staff_cleric");
        GameRegistry.registerItem(staffEngineer, "staff_engineer");
        GameRegistry.registerItem(staffGuardian, "staff_guardian");
        GameRegistry.registerItem(staffScholar, "staff_scholar");
        GameRegistry.registerItem(staffShadow, "staff_shadow");
        GameRegistry.registerItem(entityBrain, "brain");
        GameRegistry.registerItem(entityBrainPreserved, "brain_preserved");
        GameRegistry.registerItem(entityCapture, "capture");
        GameRegistry.registerItem(entityNanites, "nanites");
        GameRegistry.registerItem(entitySample, "sample");
        GameRegistry.registerItem(fluidCapsule, "capsule");
        GameRegistry.registerItem(processorComponent, "components");
        GameRegistry.registerItem(digitalTool, "digital_tool");
        GameRegistry.registerItem(energyCircuits, "energy_circuits");

        // Register with the ore dictionary
        OreDictionary.registerOre("dustIron", ItemStackHelper.itemSubtype(resource, "iron_dust", 1));
        OreDictionary.registerOre("dustCopper", ItemStackHelper.itemSubtype(resource, "copper_dust", 1));
        OreDictionary.registerOre("dustAluminium", ItemStackHelper.itemSubtype(resource, "aluminium_dust", 1));
        OreDictionary.registerOre("dustAluminum", ItemStackHelper.itemSubtype(resource, "aluminium_dust", 1));
        OreDictionary.registerOre("dustGold", ItemStackHelper.itemSubtype(resource, "gold_dust", 1));
        OreDictionary.registerOre("dustDiamond", ItemStackHelper.itemSubtype(resource, "diamond_dust", 1));
        OreDictionary.registerOre("ingotCopper", ItemStackHelper.itemSubtype(resource, "copper_ingot", 1));
        OreDictionary.registerOre("ingotAluminium", ItemStackHelper.itemSubtype(resource, "aluminium_ingot", 1));
        OreDictionary.registerOre("ingotAluminum", ItemStackHelper.itemSubtype(resource, "aluminium_ingot", 1));
        OreDictionary.registerOre("rodIron", ItemStackHelper.itemSubtype(resource, "iron_rod", 1));
        OreDictionary.registerOre("rodCopper", ItemStackHelper.itemSubtype(resource, "copper_rod", 1));
        OreDictionary.registerOre("rodAluminium", ItemStackHelper.itemSubtype(resource, "aluminium_rod", 1));
        OreDictionary.registerOre("rodAluminum", ItemStackHelper.itemSubtype(resource, "aluminium_rod", 1));
        OreDictionary.registerOre("rodGold", ItemStackHelper.itemSubtype(resource, "gold_rod", 1));
        OreDictionary.registerOre("rodDiamond", ItemStackHelper.itemSubtype(resource, "diamond_rod", 1));
        OreDictionary.registerOre("sheetIron", ItemStackHelper.itemSubtype(resource, "iron_sheet", 1));
        OreDictionary.registerOre("sheetCopper", ItemStackHelper.itemSubtype(resource, "copper_sheet", 1));
        OreDictionary.registerOre("sheetAluminium", ItemStackHelper.itemSubtype(resource, "aluminium_sheet", 1));
        OreDictionary.registerOre("sheetAluminum", ItemStackHelper.itemSubtype(resource, "aluminium_sheet", 1));
        OreDictionary.registerOre("sheetGold", ItemStackHelper.itemSubtype(resource, "gold_sheet", 1));
        OreDictionary.registerOre("sheetDiamond", ItemStackHelper.itemSubtype(resource, "diamond_sheet", 1));
        OreDictionary.registerOre("plateIron", ItemStackHelper.itemSubtype(resource, "iron_sheet", 1));
        OreDictionary.registerOre("plateCopper", ItemStackHelper.itemSubtype(resource, "copper_sheet", 1));
        OreDictionary.registerOre("plateAluminium", ItemStackHelper.itemSubtype(resource, "aluminium_sheet", 1));
        OreDictionary.registerOre("plateAluminum", ItemStackHelper.itemSubtype(resource, "aluminium_sheet", 1));
        OreDictionary.registerOre("plateGold", ItemStackHelper.itemSubtype(resource, "gold_sheet", 1));
        OreDictionary.registerOre("plateDiamond", ItemStackHelper.itemSubtype(resource, "diamond_sheet", 1));
    }
}
