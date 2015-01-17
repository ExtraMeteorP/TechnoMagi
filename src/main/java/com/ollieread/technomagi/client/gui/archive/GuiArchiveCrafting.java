package com.ollieread.technomagi.client.gui.archive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.client.gui.GuiArchive;
import com.ollieread.technomagi.client.gui.GuiTMButton;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.item.crafting.IRecipeTM;
import com.ollieread.technomagi.item.crafting.RecipeManager;
import com.ollieread.technomagi.item.crafting.ShapedRecipe;
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.util.PacketHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiArchiveCrafting extends GuiArchive
{

    protected static ResourceLocation texture;
    protected List recipeList;
    protected List recipeButtons = new ArrayList();
    protected ExtendedPlayerKnowledge playerKnowledge;
    private GuiButton selectedButton;
    protected static int leftOffset;
    protected static int topOffset;
    protected int recipe = 0;
    protected int prevPage = 0;

    public GuiArchiveCrafting(EntityPlayer player, TileEntityArchive archive)
    {
        super(player, archive);

        recipeList = RecipeManager.assembler.getRecipes();
        playerKnowledge = ExtendedPlayerKnowledge.get(player);
    }

    public void initGui()
    {
        super.initGui();

        leftOffset = guiLeft;
        topOffset = guiTop;

        if (subtype == 0) {
            texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive-info.png");
        } else if (subtype == 1) {
            texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive-crafting.png");
        }

        buttonList.add(new GuiTMButton(1, guiLeft + 4, guiTop + 25, 100, 14, I18n.format("technomagi.archive.name.main")));
        buttonList.add(new GuiPaginationButton(2, guiLeft + 9, guiTop + 227, 1));
        buttonList.add(new GuiPaginationButton(3, guiLeft + 216, guiTop + 227, 0));
        buttonList.add(new GuiPaginationButton(4, guiLeft + 160, guiTop + 227, 0));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    protected void drawGuiContainerForegroundLayer(int var1, int var2)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        fontRendererObj.drawString(I18n.format("technomagi.archive.name.main"), 9, 9, 16777215);
        recipeButtons.clear();

        if (subtype == 0) {
            ((GuiButton) buttonList.get(0)).displayString = I18n.format("technomagi.archive.name.main");
            drawMainLayer();
        } else if (subtype == 1) {
            ((GuiButton) buttonList.get(0)).displayString = I18n.format("technomagi.archive.name.recipes");
            drawRecipeLayer();
        }
    }

    protected void drawMainLayer()
    {
        fontRendererObj.drawString(I18n.format("technomagi.archive.name.recipes"), 9, 50, 5097727);

        if (recipeList != null && recipeList.size() > 0) {
            int start = 7 * page;
            int end = start + 7;

            List<IRecipeTM> sublist = recipeList.subList(7 * page, end > recipeList.size() ? recipeList.size() : end);

            int x = 9;
            int y = 65;
            int id = 5;

            for (Iterator<IRecipeTM> i = sublist.iterator(); i.hasNext();) {
                Object o = i.next();
                boolean can = true;
                ItemStack output = null;

                if (o instanceof IRecipe) {
                    IRecipe recipe = (IRecipe) o;

                    output = recipe.getRecipeOutput();
                } else if (o instanceof IRecipeTM) {
                    IRecipeTM recipe = (IRecipeTM) o;
                    String knowledge = ((IRecipeTM) recipe).getKnowledge();

                    if (knowledge != null && !knowledge.equals("")) {
                        can = playerKnowledge.hasKnowledge(knowledge);
                    }

                    output = recipe.getRecipeOutput();
                }

                if (can) {
                    GuiRecipeButton button = new GuiRecipeButton(id, x, y, output.getDisplayName(), output, can);
                    recipeButtons.add(button);
                    button.drawButton(Minecraft.getMinecraft(), x, y);

                    y += 22;
                    id++;
                }
            }

            if (end < recipeList.size()) {
                ((GuiPaginationButton) buttonList.get(2)).visible = true;
                ((GuiPaginationButton) buttonList.get(3)).visible = false;
            } else {
                ((GuiPaginationButton) buttonList.get(2)).visible = false;
                ((GuiPaginationButton) buttonList.get(3)).visible = false;
            }

            if (start > 0) {
                ((GuiPaginationButton) buttonList.get(1)).visible = true;
            } else {
                ((GuiPaginationButton) buttonList.get(1)).visible = false;
            }
        }
    }

    protected void drawRecipeLayer()
    {
        if (recipeList != null && recipeList.size() > recipe) {
            Object o = recipeList.get(this.recipe);

            if (o != null) {
                ItemStack output = null;
                ItemStack[] items = null;
                boolean can = true;
                int w = 0;
                int h = 0;
                String type = "";
                String knowledge = "";

                if (o instanceof IRecipe) {
                    IRecipe recipe = (IRecipe) o;
                    output = recipe.getRecipeOutput();

                    if (recipe instanceof ShapedRecipes) {
                        items = ((ShapedRecipes) recipe).recipeItems;
                    }

                    w = ((ShapedRecipes) recipe).recipeWidth;
                    h = ((ShapedRecipes) recipe).recipeHeight;
                    type = "Vanilla";
                } else if (o instanceof IRecipeTM) {
                    IRecipeTM recipe = (IRecipeTM) o;
                    output = recipe.getRecipeOutput();
                    knowledge = recipe.getKnowledge();

                    if (knowledge != null && !knowledge.equals("")) {
                        can = playerKnowledge.hasKnowledge(knowledge);
                    }

                    if (recipe instanceof ShapedRecipe) {
                        items = ((ShapedRecipe) recipe).items;
                    }

                    w = ((ShapedRecipe) recipe).width;
                    h = ((ShapedRecipe) recipe).height;
                    type = "TechnoMagi";
                }

                this.drawItemStack(output, 9, 50, "");
                String name = output.getUnlocalizedName().replaceAll("item.", "").replaceAll(".name", "").replaceAll("tile.", "");
                int progress = 0;
                String content = Information.getInformationParagraphs("recipes", name);

                drawStringPage(content, 9, 70, 158, 144, !can);

                if (can) {
                    fontRendererObj.drawString(output.getDisplayName(), 28, 55, 5097727);
                } else {
                    obfFontRendererObj.drawString(output.getDisplayName(), 28, 55, 5097727);
                }

                GL11.glPushMatrix();
                GL11.glScalef(0.8F, 0.8F, 0.8F);
                GL11.glTranslatef(45F, 26F, 0.0F);

                fontRendererObj.drawString("Type", 178, 105, 5097727);
                fontRendererObj.drawString(type, 178, 115, 16777215);

                fontRendererObj.drawString("Knowledge", 178, 130, 5097727);

                if (can) {
                    fontRendererObj.drawSplitString((knowledge == null || knowledge.isEmpty()) ? "None" : I18n.format("knowledge." + name), 178, 140, 51, 16777215);
                } else {
                    obfFontRendererObj.drawSplitString((knowledge == null || knowledge.isEmpty()) ? "None" : I18n.format("knowledge." + name), 178, 140, 51, 16777215);
                }

                GL11.glPopMatrix();

                for (int r = 0; r < w; r++) {
                    for (int c = 0; c < h; c++) {
                        int i = r + c * w;

                        if (items.length > i) {
                            ItemStack item = items[i];

                            if (item != null) {
                                int x = 177 + (r * 17);
                                int y = 43 + (c * 17);
                                this.drawItemStack(item, x, y, "" + (item.stackSize > 1 ? item.stackSize : ""));
                            }
                        }
                    }
                }

                if (pages) {
                    ((GuiPaginationButton) buttonList.get(3)).visible = true;
                    ((GuiPaginationButton) buttonList.get(2)).visible = false;
                } else {
                    ((GuiPaginationButton) buttonList.get(3)).visible = false;
                    ((GuiPaginationButton) buttonList.get(2)).visible = false;
                }

                if (page > 0) {
                    ((GuiPaginationButton) buttonList.get(1)).visible = true;
                } else {
                    ((GuiPaginationButton) buttonList.get(1)).visible = false;
                }
            }
        }
    }

    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1) {
            if (subtype == 1) {
                subtype = 0;
                page = prevPage;
                archive.setSubType(0);
                archive.setPage(prevPage);
                texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive-info.png");
                PacketHelper.setArchive(archive, type, subtype, page);
            } else {
                archive.setType(0);
                archive.setSubType(0);
                archive.setPage(0);
                PacketHelper.setArchive(archive, 0, 0, 0);
            }
        } else if (button.id == 2) {
            page -= 1;
            archive.setPage(page);
            PacketHelper.setArchive(archive, type, subtype, page);
        } else if (button.id == 3 || button.id == 4) {
            page += 1;
            archive.setPage(page);
            PacketHelper.setArchive(archive, type, subtype, page);
        } else if (button.id > 4) {
            int i = button.id - 5;

            if (recipeList.size() > i) {
                subtype = 1;
                recipe = i + (7 * page);
                prevPage = page;
                page = 0;
                archive.setSubType(1);
                archive.setPage(0);
                texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive-crafting.png");
                PacketHelper.setArchive(archive, type, subtype, page);
            }
        }
    }

    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);

        if (p_73864_3_ == 0) {
            for (int l = 0; l < this.recipeButtons.size(); ++l) {
                GuiRecipeButton guibutton = (GuiRecipeButton) this.recipeButtons.get(l);

                if (guibutton.mousePressed(this.mc, p_73864_1_, p_73864_2_)) {
                    guibutton.func_146113_a(this.mc.getSoundHandler());
                    actionPerformed(guibutton);
                }
            }
        }
    }

    public static class GuiRecipeButton extends GuiButton
    {

        protected ItemStack item;
        /** The x position of this control. */
        public int xPosition;
        /** The y position of this control. */
        public int yPosition;

        public boolean can;

        public GuiRecipeButton(int id, int x, int y, String name, ItemStack icon, boolean s)
        {
            super(id, GuiArchiveCrafting.leftOffset + x, GuiArchiveCrafting.topOffset + y, 200, 20, name);

            xPosition = x;
            yPosition = y;
            item = icon;
            can = s;
        }

        @Override
        public void drawButton(Minecraft mc, int x, int y)
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawItemStack(item, this.xPosition, this.yPosition, "");

            if (can) {
                mc.fontRenderer.drawString(displayString, this.xPosition + 19, yPosition + 5, 16777215);
            } else {
                mc.standardGalacticFontRenderer.drawString(displayString, this.xPosition + 19, yPosition + 5, 16777215);
            }
        }

        protected void drawItemStack(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_)
        {
            Minecraft mc = Minecraft.getMinecraft();
            GL11.glTranslatef(0.0F, 0.0F, 32.0F);
            this.zLevel = 200.0F;
            itemRender.zLevel = 200.0F;
            FontRenderer font = null;

            if (can) {
                font = mc.fontRenderer;
            } else {
                font = mc.standardGalacticFontRenderer;
            }

            itemRender.renderItemAndEffectIntoGUI(font, mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_);
            itemRender.renderItemOverlayIntoGUI(font, mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_, p_146982_4_);
            this.zLevel = 0.0F;
            itemRender.zLevel = 0.0F;
        }
    }

}
