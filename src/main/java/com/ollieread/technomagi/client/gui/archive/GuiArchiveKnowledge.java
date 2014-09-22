package com.ollieread.technomagi.client.gui.archive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.client.gui.GuiArchive;
import com.ollieread.technomagi.client.gui.GuiTMButton;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.util.PacketHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiArchiveKnowledge extends GuiArchive
{

    protected static ResourceLocation texture;
    protected List<IKnowledge> knowledgeList;
    protected List knowledgeButtons = new ArrayList();
    protected ExtendedPlayerKnowledge playerKnowledge;
    private GuiButton selectedButton;
    protected static int leftOffset;
    protected static int topOffset;
    protected int knowledge = 0;
    protected int prevPage = 0;

    public GuiArchiveKnowledge(EntityPlayer player, TileEntityArchive archive)
    {
        super(player, archive);

        knowledgeList = ResearchRegistry.getKnowledge();
        playerKnowledge = ExtendedPlayerKnowledge.get(player);
    }

    public void initGui()
    {
        super.initGui();

        leftOffset = guiLeft;
        topOffset = guiTop;

        texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive-info.png");

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
        knowledgeButtons.clear();

        if (subtype == 0) {
            ((GuiButton) buttonList.get(0)).displayString = I18n.format("technomagi.archive.name.main");
            drawMainLayer();
        } else if (subtype == 1) {
            ((GuiButton) buttonList.get(0)).displayString = I18n.format("technomagi.archive.name.knowledge");
            drawKnowledgeLayer();
        }
    }

    protected void drawMainLayer()
    {
        fontRendererObj.drawString(I18n.format("technomagi.archive.name.knowledge"), 9, 50, 5097727);

        if (knowledgeList != null && knowledgeList.size() > 0) {
            int start = 7 * page;
            int end = start + 7;

            List<IKnowledge> sublist = knowledgeList.subList(7 * page, end > knowledgeList.size() ? knowledgeList.size() : end);

            int x = 9;
            int y = 65;
            int id = 4;

            for (Iterator<IKnowledge> i = sublist.iterator(); i.hasNext();) {
                IKnowledge knowledge = i.next();
                int progress = 0;

                if (playerKnowledge.hasKnowledge(knowledge.getName())) {
                    progress = 100;
                } else {
                    progress = playerKnowledge.getKnowledgeProgress(knowledge.getName());
                }

                GuiKnowledgeButton button = new GuiKnowledgeButton(id, x, y, knowledge.getLocalisedName(), knowledge.getIcon(), progress);
                knowledgeButtons.add(button);
                button.drawButton(Minecraft.getMinecraft(), x, y);

                y += 22;
                id++;
            }

            if (end < knowledgeList.size()) {
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

    protected void drawKnowledgeLayer()
    {
        if (knowledgeList != null && knowledgeList.size() > knowledge) {
            IKnowledge k = knowledgeList.get(knowledge);

            if (k != null) {
                mc.getTextureManager().bindTexture(k.getIcon());
                this.func_146110_a(9, 50, 0, 0, 20, 20, 20, 20);
                int progress = 0;

                if (playerKnowledge.hasKnowledge(k.getName())) {
                    progress = 100;
                } else {
                    progress = playerKnowledge.getKnowledgeProgress(k.getName());
                }

                if (progress == 100) {
                    fontRendererObj.drawString(k.getLocalisedName(), 33, 50, 5097727);
                } else {
                    obfFontRendererObj.drawString(k.getLocalisedName(), 33, 50, 5097727);
                }

                mc.getTextureManager().bindTexture(GuiArchiveKnowledge.texture);

                this.drawTexturedModalRect(33, 62, 0, 248, 102, 5);
                this.drawTexturedModalRect(34, 63, 0, 253, progress, 3);

                String content = Information.getInformation("knowledge", k.getName());

                drawStringPage(content, 9, 75, 215, 144, progress < 100);

                if (pages) {
                    ((GuiPaginationButton) buttonList.get(2)).visible = true;
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
        } else if (button.id == 3) {
            page += 1;
            archive.setPage(page);
            PacketHelper.setArchive(archive, type, subtype, page);
        } else if (button.id > 3) {
            int i = button.id - 4;

            if (knowledgeList.size() > i) {
                subtype = 1;
                knowledge = i + (7 * page);
                prevPage = page;
                page = 0;
                archive.setSubType(1);
                archive.setPage(0);
                PacketHelper.setArchive(archive, type, subtype, page);
            }
        }
    }

    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);

        if (p_73864_3_ == 0) {
            for (int l = 0; l < this.knowledgeButtons.size(); ++l) {
                GuiKnowledgeButton guibutton = (GuiKnowledgeButton) this.knowledgeButtons.get(l);

                if (guibutton.mousePressed(this.mc, p_73864_1_, p_73864_2_)) {
                    guibutton.func_146113_a(this.mc.getSoundHandler());
                    actionPerformed(guibutton);
                }
            }
        }
    }

    public static class GuiKnowledgeButton extends GuiButton
    {

        protected ResourceLocation texture;
        /** The x position of this control. */
        public int xPosition;
        /** The y position of this control. */
        public int yPosition;

        public int progress;

        public GuiKnowledgeButton(int id, int x, int y, String name, ResourceLocation icon, int s)
        {
            super(id, GuiArchiveKnowledge.leftOffset + x, GuiArchiveKnowledge.topOffset + y, 200, 20, name);

            xPosition = x;
            yPosition = y;
            texture = icon;
            progress = s;
        }

        @Override
        public void drawButton(Minecraft mc, int x, int y)
        {
            mc.getTextureManager().bindTexture(texture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.func_146110_a(this.xPosition, this.yPosition, 0, 0, 20, 20, 20, 20);

            if (progress == 100) {
                mc.fontRenderer.drawString(displayString, this.xPosition + 24, yPosition + 2, 16777215);
            } else {
                mc.standardGalacticFontRenderer.drawString(displayString, this.xPosition + 24, yPosition + 2, 16777215);
            }

            mc.getTextureManager().bindTexture(GuiArchiveKnowledge.texture);

            this.drawTexturedModalRect(this.xPosition + 24, yPosition + 14, 0, 248, 102, 5);
            this.drawTexturedModalRect(this.xPosition + 25, yPosition + 15, 0, 253, progress, 3);
        }
    }

}
