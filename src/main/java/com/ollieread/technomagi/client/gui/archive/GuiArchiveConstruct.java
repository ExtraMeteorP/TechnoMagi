package com.ollieread.technomagi.client.gui.archive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.client.gui.GuiArchive;
import com.ollieread.technomagi.client.gui.GuiTMButton;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.item.crafting.ConstructManager;
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.util.PacketHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiArchiveConstruct extends GuiArchive
{

    protected static ResourceLocation texture;
    protected List<Block> blockList;
    protected Map<Block, List<ItemStack>> recipeList;
    protected List recipeButtons = new ArrayList();
    protected ExtendedPlayerKnowledge playerKnowledge;
    private GuiButton selectedButton;
    protected static int leftOffset;
    protected static int topOffset;
    protected int recipe = 0;
    protected int prevPage = 0;

    public GuiArchiveConstruct(EntityPlayer player, TileEntityArchive archive)
    {
        super(player, archive);

        recipeList = ConstructManager.getInstance().getRecipeList();
        blockList = new ArrayList<Block>();
        blockList.addAll(recipeList.keySet());
    }

    public void initGui()
    {
        super.initGui();

        leftOffset = guiLeft;
        topOffset = guiTop;

        if (subtype == 0) {
            texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive-info.png");
        } else if (subtype == 1) {
            texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive-construct.png");
        }

        buttonList.add(new GuiTMButton(1, guiLeft + 4, guiTop + 25, 100, 14, I18n.format("technomagi.archive.name.main")));
        buttonList.add(new GuiPaginationButton(2, guiLeft + 9, guiTop + 227, 1));
        buttonList.add(new GuiPaginationButton(3, guiLeft + 216, guiTop + 227, 0));
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
            ((GuiButton) buttonList.get(0)).displayString = I18n.format("technomagi.archive.name.construct");
            drawRecipeLayer();
        }
    }

    protected void drawMainLayer()
    {
        fontRendererObj.drawString(I18n.format("technomagi.archive.name.recipes"), 9, 50, 5097727);

        if (blockList != null && blockList.size() > 0) {
            int start = 7 * page;
            int end = start + 7;

            List<Block> sublist = blockList.subList(7 * page, end > recipeList.size() ? recipeList.size() : end);

            int x = 9;
            int y = 65;
            int id = 5;

            for (Iterator<Block> i = sublist.iterator(); i.hasNext();) {
                Block block = i.next();

                GuiRecipeButton button = new GuiRecipeButton(id, x, y, block);
                recipeButtons.add(button);
                button.drawButton(Minecraft.getMinecraft(), x, y);

                y += 22;
                id++;
            }

            if (end < recipeList.size()) {
                ((GuiPaginationButton) buttonList.get(2)).visible = true;
            } else {
                ((GuiPaginationButton) buttonList.get(2)).visible = false;
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
            Block b = blockList.get(this.recipe);
            boolean can = true;

            if (b != null) {
                List<ItemStack> items = recipeList.get(b);

                drawItemStack(new ItemStack(b), 9, 68, "");
                int progress = 0;

                if (can) {
                    fontRendererObj.drawString(b.getLocalizedName(), 28, 72, 5097727);
                } else {
                    obfFontRendererObj.drawString(b.getLocalizedName(), 28, 72, 5097727);
                }

                String content = Information.getInformation("recipes", b.getUnlocalizedName());

                drawStringPage(content, 9, 89, 158, 130, !can);

                for (int i = 0; i < items.size(); i++) {
                    ItemStack stack = items.get(i);
                    drawItemStack(stack, 63 + (i * 30), 43, "" + (stack.stackSize > 1 ? stack.stackSize : ""));
                }

                if (pages) {
                    ((GuiPaginationButton) buttonList.get(2)).visible = false;
                } else {
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
                texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive-construct.png");
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

        protected Block block;
        /** The x position of this control. */
        public int xPosition;
        /** The y position of this control. */
        public int yPosition;

        public boolean can = true;

        public GuiRecipeButton(int id, int x, int y, Block b)
        {
            super(id, GuiArchiveConstruct.leftOffset + x, GuiArchiveConstruct.topOffset + y, 200, 20, b.getLocalizedName());

            xPosition = x;
            yPosition = y;
            block = b;
        }

        @Override
        public void drawButton(Minecraft mc, int x, int y)
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawItemStack(new ItemStack(block), this.xPosition, this.yPosition, "");

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
