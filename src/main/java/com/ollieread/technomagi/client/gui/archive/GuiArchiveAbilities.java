package com.ollieread.technomagi.client.gui.archive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.ennds.ability.AbilityRegistry;
import com.ollieread.ennds.ability.IAbility;
import com.ollieread.ennds.ability.IAbilityActive;
import com.ollieread.ennds.ability.IAbilityPassive;
import com.ollieread.ennds.extended.ExtendedPlayerAbilities;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.client.gui.GuiArchive;
import com.ollieread.technomagi.client.gui.GuiTMButton;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.util.PacketHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiArchiveAbilities extends GuiArchive
{

    protected static ResourceLocation texture;
    protected List<String> abilityList;
    protected List abilityButtons = new ArrayList();
    protected ExtendedPlayerAbilities playerAbilities;
    private GuiButton selectedButton;
    protected static int leftOffset;
    protected static int topOffset;
    protected int ability = 0;
    protected int prevPage = 0;

    public GuiArchiveAbilities(EntityPlayer player, TileEntityArchive archive)
    {
        super(player, archive);

        playerAbilities = ExtendedPlayerKnowledge.get(player).abilities;
        abilityList = playerAbilities.getAbilities();
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
        abilityButtons.clear();

        if (subtype == 0) {
            ((GuiButton) buttonList.get(0)).displayString = I18n.format("technomagi.archive.name.main");
            drawMainLayer();
        } else if (subtype == 1) {
            ((GuiButton) buttonList.get(0)).displayString = I18n.format("technomagi.archive.name.abilities");
            drawAbilityLayer();
        }
    }

    protected void drawMainLayer()
    {
        fontRendererObj.drawString(I18n.format("technomagi.archive.name.abilities"), 9, 50, 5097727);

        if (abilityList != null && abilityList.size() > 0) {
            int start = 7 * page;
            int end = start + 7;

            List<String> sublist = abilityList.subList(7 * page, end > abilityList.size() ? abilityList.size() : end);

            int x = 9;
            int y = 65;
            int id = 4;

            for (Iterator<String> i = sublist.iterator(); i.hasNext();) {
                String name = i.next();

                GuiAbilityButton button = null;
                IAbility ability = AbilityRegistry.getAbility(name);

                button = new GuiAbilityButton(id, x, y, ability);
                abilityButtons.add(button);
                button.drawButton(Minecraft.getMinecraft(), x, y);

                y += 22;
                id++;
            }

            if (end < abilityList.size()) {
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

    protected void drawAbilityLayer()
    {
        if (abilityList != null && abilityList.size() > ability) {
            String a = abilityList.get(ability);
            IAbility ability = AbilityRegistry.getAbility(a);

            if (ability != null) {
                mc.getTextureManager().bindTexture(ability.getIcon());
                this.func_146110_a(9, 50, 0, 0, 20, 20, 20, 20);
                fontRendererObj.drawString(ability.getLocalisedName(), 33, 50, 5097727);
                String name = "";

                String content = Information.getInformation("abilities", name);
                String type = (ability instanceof IAbilityActive ? "Active" : "Passive");
                String[] knowledge = ability.getKnowledge();
                Map<String, Integer> enhancements = null;

                fontRendererObj.drawString(type, 33, 60, 16777215);

                if (ability instanceof IAbilityActive) {
                    name = ((IAbilityActive) ability).getLocalisedName();
                    enhancements = ((IAbilityActive) ability).getEnhancements();
                } else if (ability instanceof IAbilityPassive) {
                    name = ((IAbilityPassive) ability).getLocalisedName();
                }

                int y = 75;

                fontRendererObj.drawString("Knowledge", 9, y, 5097727);
                y += 10;

                if (knowledge != null) {
                    for (int i = 0; i < knowledge.length; i++) {
                        y += (10 * i);
                        fontRendererObj.drawString(I18n.format("knowledge." + knowledge[i]), 9, y, 16777215);
                    }
                    y += 15;
                } else {
                    fontRendererObj.drawString("None", 9, y, 16777215);
                    y += 15;
                }

                fontRendererObj.drawString("Requirements", 9, y, 5097727);
                y += 10;

                if (enhancements != null) {
                    int x = 0;
                    for (Iterator<String> i = enhancements.keySet().iterator(); i.hasNext();) {
                        String key = i.next();

                        y += (10 * x);
                        fontRendererObj.drawString(I18n.format("enhancement." + key) + " " + enhancements.get(key), 9, y, 16777215);
                        x++;
                    }
                } else {
                    fontRendererObj.drawString("None", 9, y, 16777215);
                }

                GL11.glPopMatrix();

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

            if (abilityList.size() > i) {
                subtype = 1;
                ability = i + (7 * page);
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
            for (int l = 0; l < this.abilityButtons.size(); ++l) {
                GuiAbilityButton guibutton = (GuiAbilityButton) this.abilityButtons.get(l);

                if (guibutton.mousePressed(this.mc, p_73864_1_, p_73864_2_)) {
                    guibutton.func_146113_a(this.mc.getSoundHandler());
                    actionPerformed(guibutton);
                }
            }
        }
    }

    public static class GuiAbilityButton extends GuiButton
    {

        protected ResourceLocation texture;
        /** The x position of this control. */
        public int xPosition;
        /** The y position of this control. */
        public int yPosition;

        public int progress;

        public IAbility ability;

        public GuiAbilityButton(int id, int x, int y, IAbility a)
        {
            super(id, GuiArchiveAbilities.leftOffset + x, GuiArchiveAbilities.topOffset + y, 200, 20, a.getName());

            xPosition = x;
            yPosition = y;
            ability = a;
        }

        @Override
        public void drawButton(Minecraft mc, int x, int y)
        {
            mc.getTextureManager().bindTexture(ability.getIcon());
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.func_146110_a(this.xPosition, this.yPosition, 0, 0, 20, 20, 20, 20);
            String name = "";

            if (ability instanceof IAbilityActive) {
                name = ((IAbilityActive) ability).getLocalisedName();
            } else if (ability instanceof IAbilityPassive) {
                name = ((IAbilityPassive) ability).getLocalisedName();
            }
            mc.fontRenderer.drawString(name, this.xPosition + 24, yPosition + 5, 16777215);
        }
    }

}
