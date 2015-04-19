package com.ollieread.technomagi.client.gui.component;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.crafting.CraftingHandler;
import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.util.ResourceHelper;

public class ComponentRecipe extends Component
{

    public static enum RecipeType {
        VANILLA(140, 84, 0, 0), FURNACE(140, 84, 0, 84);

        public final int width;
        public final int height;
        public final int u;
        public final int v;

        RecipeType(int width, int height, int u, int v)
        {
            this.width = width;
            this.height = height;
            this.u = u;
            this.v = v;
        }
    }

    protected RecipeType type;
    public ItemStack output;
    protected Map<Integer[], ItemStack> tooltips = new HashMap<Integer[], ItemStack>();

    public ComponentRecipe(RecipeType type)
    {
        super(0, 0);

        this.type = type;
        this.width = type.width;
        this.height = type.height;
    }

    public ComponentRecipe setOutput(ItemStack output)
    {
        this.output = output;
        return this;
    }

    @Override
    public void draw(int x, int y)
    {
        tooltips.clear();
        if (this.type != null && this.output != null) {
            x += getOffsetX() + paddingX;
            y += getOffsetY() + paddingY;

            if (this.alignment.equals(ComponentAlignment.CENTER)) {
                x += (((this.parent.getWidth() - this.getWidth()) - (this.parent.paddingX * 2)) / 2) - getOffsetX();
            }

            if (this.type.equals(RecipeType.VANILLA)) {
                IRecipe recipe = CraftingHandler.vanilla.find(this.output);

                if (recipe != null) {
                    builder.drawTextureArea(x, y, type.u, type.v, type.width, type.height, ResourceHelper.texture("gui/recipes.png"));
                    ItemStack[] items = null;
                    int w = 0;
                    int h = 0;

                    if (recipe instanceof ShapedRecipes) {
                        items = ((ShapedRecipes) recipe).recipeItems;
                    }

                    w = ((ShapedRecipes) recipe).recipeWidth;
                    h = ((ShapedRecipes) recipe).recipeHeight;

                    ItemStack output = recipe.getRecipeOutput();

                    if (output == null) {
                        Technomagi.debug("Output: " + output);
                    } else {
                        tooltips.put(new Integer[] { 106, 35 }, output);
                        builder.drawItemStack(output, x + 106, y + 35, "" + output.stackSize);

                        for (int r = 0; r < w; r++) {
                            for (int c = 0; c < h; c++) {
                                int i = r + c * w;

                                if (items.length > i) {
                                    ItemStack item = items[i];

                                    if (item != null) {
                                        int x2 = 12 + (r * 18);
                                        int y2 = 17 + (c * 18);
                                        tooltips.put(new Integer[] { x2, y2 }, item);
                                        builder.drawItemStack(item, x + x2, y + y2, "" + (item.stackSize > 1 ? item.stackSize : ""));
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (this.type.equals(RecipeType.FURNACE)) {
                ItemStack input = CraftingHandler.furnace.find(output);

                if (input != null) {
                    builder.drawTextureArea(x, y, type.u, type.v, type.width, type.height, ResourceHelper.texture("gui/recipes.png"));
                    tooltips.put(new Integer[] { 88, 33 }, input);
                    builder.drawItemStack(input, x + 88, y + 33, "" + output.stackSize);
                    tooltips.put(new Integer[] { 28, 15 }, output);
                    builder.drawItemStack(output, x + 28, y + 15, "" + input.stackSize);
                }
            }
        }
    }

    @Override
    public void mouseHovered(int x, int y, int mouseX, int mouseY)
    {
        x += offsetX + paddingX;
        y += offsetY + paddingY;

        if (this.alignment.equals(ComponentAlignment.CENTER)) {
            x += (((this.parent.getWidth() - this.getWidth()) - (this.parent.paddingX * 2)) / 2) - getOffsetX();
        }

        if (isHovered(x, y, mouseX, mouseY)) {
            for (Integer[] key : tooltips.keySet()) {
                if (this.isInsideRegion(mouseX, mouseY, x + key[0], y + key[1], x + key[0] + 16, y + key[1] + 16)) {
                    GuiBuilder.instance.drawHoveringText(tooltips.get(key).getTooltip(builder.mc.thePlayer, builder.mc.gameSettings.advancedItemTooltips), mouseX, mouseY);
                    return;
                }
            }
            if (tooltip != null) {
                GuiBuilder.instance.drawHoveringText(tooltip, mouseX, mouseY);
            }
        }
    }

}
