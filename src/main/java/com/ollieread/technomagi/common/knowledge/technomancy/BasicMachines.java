package com.ollieread.technomagi.common.knowledge.technomancy;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.Research;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.ItemStackHelper;
import com.ollieread.technomagi.util.ResourceHelper;

public class BasicMachines extends Knowledge
{

    public static Research craftWorkbench;
    public static Research craftFurnace;
    public static Research craftDispenser;
    public static Research craftPiston;
    public static Research craftPressurePlate;
    public static Research craftChest;
    public static Research craftButton;
    public static Research craftLever;
    public static Research craftRedstoneDevice;

    public BasicMachines()
    {
        super("basic_machines", ResourceHelper.texture("knowledge/basic_machines.png"), Technomancy.category.getName());

        TechnomagiApi.addKnowledge(this);

        craftWorkbench = TechnomagiApi.addResearch("craft_workbench", this.getName()).setProgress(5).setChance(3).setRepetition(2); // 10
        craftFurnace = TechnomagiApi.addResearch("craft_furnace", this.getName()).setProgress(5).setChance(6).setRepetition(2); // 10
        craftDispenser = TechnomagiApi.addResearch("craft_dispenser", this.getName()).setProgress(5).setChance(4).setRepetition(2); // 10
        craftPiston = TechnomagiApi.addResearch("craft_piston", this.getName()).setProgress(5).setChance(4).setRepetition(2); // 10
        craftPressurePlate = TechnomagiApi.addResearch("craft_pressure_plate", this.getName()).setProgress(5).setChance(4).setRepetition(2); // 10
        craftChest = TechnomagiApi.addResearch("craft_chest", this.getName()).setProgress(5).setChance(6).setRepetition(4); // 20
        craftButton = TechnomagiApi.addResearch("craft_button", this.getName()).setProgress(5).setChance(4).setRepetition(4); // 20
        craftLever = TechnomagiApi.addResearch("craft_lever", this.getName()).setProgress(5).setChance(4).setRepetition(4); // 20
        // This should be called whenever anything is crafted that uses redstone
        craftRedstoneDevice = TechnomagiApi.addResearch("craft_redstone_device", this.getName()).setProgress(4).setChance(4).setRepetition(6); // 24
    }

    public void mappings()
    {
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("crafting_table"), craftWorkbench);
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("furnace"), craftFurnace);
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("dispenser"), craftDispenser);
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("piston"), craftPiston);
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("sticky_piston"), craftPiston);
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("wooden_pressure_plate"), craftPressurePlate);
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("stone_pressure_plate"), craftPressurePlate);
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("chest"), craftChest);
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("trapped_chest"), craftChest);
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("wooden_button"), craftButton);
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("stone_button"), craftButton);
        TechnomagiApi.knowledge().mapCraftingResearch(ItemStackHelper.block("wooden_button"), craftLever);
    }

}
