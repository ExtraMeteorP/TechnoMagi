package com.ollieread.technomagi.item.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.technomagi.util.ItemHelper;

import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeManager
{

    /**
     * Contains all of the recipes we add to vanilla crafting. Also keeps track
     * for future reference.
     */
    public static VanillaRecipes vanilla = new VanillaRecipes();
    /**
     * Contains all of the recipes we add to vanilla smelting, or TM specific
     * smelting. Keeps track of TM only.
     */
    public static FurnaceRecipes furnace = new FurnaceRecipes();
    public static ConstructRecipes construct = new ConstructRecipes();
    /**
     * Contains all of the recipes we add to the assembler, and their
     * associative knowledge.
     */
    public static AssemblerRecipes assembler = new AssemblerRecipes();
    /**
     * Contains all of the recipes we add to the replicator (nanites).
     */
    public static ReplicatorRecipes replicator = new ReplicatorRecipes();
    /**
     * Contains all of the recipes we add as a reactive crafting option.
     */
    public static ReactiveRecipes reactive = new ReactiveRecipes();
    /**
     * Contains all of the recipes we add to the processor.
     */
    public static ProcessorRecipes processor = new ProcessorRecipes();

    public static class VanillaRecipes
    {

        private List<IRecipe> recipes = new ArrayList<IRecipe>();

        public void add(ItemStack result, Object... items)
        {
            recipes.add(CraftingManager.getInstance().addRecipe(result, items));
        }

        public IRecipe find(ItemStack result)
        {
            List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();

            for (IRecipe recipe : recipeList) {
                if (ItemStack.areItemStacksEqual(recipe.getRecipeOutput(), result)) {
                    return recipe;
                }
            }

            return null;
        }

        public List<IRecipe> getRecipes()
        {
            return recipes;
        }
    }

    public static class FurnaceRecipes
    {

        private Map<ItemStack, ItemStack> smeltingList = new HashMap<ItemStack, ItemStack>();
        private Map<ItemStack, Float> experienceList = new HashMap<ItemStack, Float>();

        public void add(ItemStack input, ItemStack output, boolean tm, float exp)
        {
            if (tm) {
                smeltingList.put(input, output);
                experienceList.put(output, Float.valueOf(exp));
            } else {
                GameRegistry.addSmelting(input, output, exp);
            }
        }

        public ItemStack findOutput(ItemStack input)
        {
            if (ItemHelper.containsKey(smeltingList, input, true)) {
                return smeltingList.get(input);
            }

            return net.minecraft.item.crafting.FurnaceRecipes.smelting().getSmeltingResult(input);
        }

        public float exp(ItemStack input)
        {
            float ret = input.getItem().getSmeltingExperience(input);

            if (ret == -1) {
                ret = experienceList.get(input).floatValue();

                if (ret == -1) {
                    ret = net.minecraft.item.crafting.FurnaceRecipes.smelting().func_151398_b(input);
                }
            }

            return ret;
        }

        public boolean isVanilla(ItemStack input)
        {
            return !ItemHelper.containsKey(smeltingList, input, true);
        }

    }

    public static class ConstructRecipes
    {

        private Map<String, List<ConstructRecipe>> knowledgeRecipes = new HashMap<String, List<ConstructRecipe>>();
        private List<ConstructRecipe> recipes = new ArrayList<ConstructRecipe>();

        public void add(Block block, ItemStack[] stacks, String knowledge)
        {
            add(new ConstructRecipe(block, stacks, knowledge));
        }

        public void add(Block block, int metadata, ItemStack[] stacks, String knowledge)
        {
            add(new ConstructRecipe(block, metadata, stacks, knowledge));
        }

        public void add(ConstructRecipe recipe)
        {
            recipes.add(recipe);

            if (!knowledgeRecipes.containsKey(recipe.getKnowledge())) {
                knowledgeRecipes.put(recipe.getKnowledge(), new ArrayList<ConstructRecipe>());
            }

            knowledgeRecipes.get(recipe.getKnowledge()).add(recipe);
        }

        public ConstructRecipe find(ItemStack[] stacks, EntityPlayer player)
        {
            ConstructRecipe recipe = find(stacks);

            if (recipe != null && recipe.canCraft(player)) {
                return recipe;
            }

            return null;
        }

        public ConstructRecipe find(ItemStack[] stacks)
        {
            for (Iterator<ConstructRecipe> i = recipes.iterator(); i.hasNext();) {
                ConstructRecipe recipe = i.next();

                if (recipe.checkMatch(stacks)) {
                    return recipe;
                }
            }

            return null;
        }

        public List<ConstructRecipe> getRecipes()
        {
            return recipes;
        }

        public List<ConstructRecipe> getRecipesForKnowledge(IKnowledge knowledge)
        {
            return knowledgeRecipes.get(knowledge.getName());
        }
    }

    public static class AssemblerRecipes
    {

        private Map<String, List<IRecipeTM>> knowledgeRecipes = new HashMap<String, List<IRecipeTM>>();
        private List<IRecipeTM> recipes = new ArrayList();

        public void addShaped(String knowledge, ItemStack output, Object... items)
        {
            add(RecipeManager.getShapedRecipe(knowledge, output, items), knowledge);
        }

        public void addShapeless(String knowledge, ItemStack output, Object... items)
        {
            add(RecipeManager.getShapelessRecipe(knowledge, output, items), knowledge);
        }

        public void add(IRecipeTM recipe, String knowledge)
        {
            if (recipe != null) {
                if (!knowledgeRecipes.containsKey(knowledge)) {
                    knowledgeRecipes.put(knowledge, new ArrayList<IRecipeTM>());
                }

                recipes.add(recipe);
                knowledgeRecipes.get(knowledge).add(recipe);
            }
        }

        public IRecipeTM find(IInventory inventory, World world)
        {
            for (IRecipeTM recipe : recipes) {
                if (recipe.matches(inventory, world)) {
                    return recipe;
                }
            }

            return null;
        }

        public IRecipeTM find(IInventory inventory, World world, EntityPlayer player)
        {
            IRecipeTM recipe = find(inventory, world);

            if (recipe != null && recipe.canCraft(player)) {
                return recipe;
            }

            return null;
        }

        public List<IRecipeTM> getRecipes()
        {
            return recipes;
        }

        public List<IRecipeTM> getRecipesForKnowledge(IKnowledge knowledge)
        {
            return knowledgeRecipes.get(knowledge.getName());
        }

    }

    public static class ReplicatorRecipes
    {

    }

    public static class ReactiveRecipes
    {

        private Map<ItemStack, ItemStack> recipes = new HashMap<ItemStack, ItemStack>();
        private Map<ItemStack, Integer> recipeTimes = new HashMap<ItemStack, Integer>();

        public void add(Block block, int metadata, ItemStack result, int time)
        {
            ItemStack itemBlock = new ItemStack(Item.getItemFromBlock(block), 1, metadata);

            add(itemBlock, result, time);
        }

        public void add(ItemStack block, ItemStack result, int time)
        {
            if (!ItemHelper.containsKey(recipes, block, true)) {
                recipes.put(block, result);
                recipeTimes.put(block, time);
            }
        }

        public ItemStack result(ItemStack block)
        {
            if (ItemHelper.containsKey(recipes, block, true)) {
                return recipes.get(block);
            }

            return null;
        }

        public int time(ItemStack block)
        {
            if (ItemHelper.containsKey(recipeTimes, block, true)) {
                return recipeTimes.get(block);
            }

            return 0;
        }
    }

    public static class ProcessorRecipes
    {
        private Map<ItemStack, ItemStack> processingList = new HashMap<ItemStack, ItemStack>();
        private Map<ItemStack, Map<Integer, ItemStack>> extraList = new HashMap<ItemStack, Map<Integer, ItemStack>>();
        private Map<ItemStack, Float> experienceList = new HashMap<ItemStack, Float>();

        public void add(ItemStack input, ItemStack output, float exp)
        {
            processingList.put(input, output);
            experienceList.put(output, Float.valueOf(exp));
        }

        public void add(ItemStack input, ItemStack output, float exp, Map<Integer, ItemStack> extra)
        {
            add(input, output, exp);
            extraList.put(input, extra);
        }

        public ItemStack findOutput(ItemStack input)
        {
            if (ItemHelper.containsKey(processingList, input, true)) {
                return processingList.get(input);
            }

            return null;
        }

        public float exp(ItemStack input)
        {
            float ret = input.getItem().getSmeltingExperience(input);

            if (ret == -1) {
                ret = experienceList.get(input).floatValue();
            }

            return ret;
        }

        public Map<Integer, ItemStack> getExtra(ItemStack input)
        {
            return extraList.get(input);
        }

    }

    public static ShapedRecipe getShapedRecipe(String knowledge, ItemStack output, Object... items)
    {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (items[i] instanceof String[]) {
            String[] astring = (String[]) ((String[]) items[i++]);

            for (int l = 0; l < astring.length; ++l) {
                String s1 = astring[l];
                ++k;
                j = s1.length();
                s = s + s1;
            }
        } else {
            while (items[i] instanceof String) {
                String s2 = (String) items[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }

        HashMap hashmap;

        for (hashmap = new HashMap(); i < items.length; i += 2) {
            Character character = (Character) items[i];
            ItemStack itemstack1 = null;

            if (items[i + 1] instanceof Item) {
                itemstack1 = new ItemStack((Item) items[i + 1]);
            } else if (items[i + 1] instanceof Block) {
                itemstack1 = new ItemStack((Block) items[i + 1], 1, 32767);
            } else if (items[i + 1] instanceof ItemStack) {
                itemstack1 = (ItemStack) items[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; ++i1) {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c0))) {
                aitemstack[i1] = ((ItemStack) hashmap.get(Character.valueOf(c0))).copy();
            } else {
                aitemstack[i1] = null;
            }
        }

        return new ShapedRecipe(j, k, aitemstack, output, knowledge);
    }

    public static ShapelessRecipe getShapelessRecipe(String knowledge, ItemStack output, Object... items)
    {
        ArrayList arraylist = new ArrayList();
        Object[] aobject = items;
        int i = items.length;

        for (int j = 0; j < i; ++j) {
            Object object1 = aobject[j];

            if (object1 instanceof ItemStack) {
                arraylist.add(((ItemStack) object1).copy());
            } else if (object1 instanceof Item) {
                arraylist.add(new ItemStack((Item) object1));
            } else {
                if (!(object1 instanceof Block)) {
                    throw new RuntimeException("Invalid shapeless recipe!");
                }

                arraylist.add(new ItemStack((Block) object1));
            }
        }

        return new ShapelessRecipe(output, arraylist, knowledge);
    }

}
