package com.ollieread.technomagi.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.ollieread.ennds.ability.AbilityRegistry;
import com.ollieread.ennds.ability.IAbilityActive;
import com.ollieread.ennds.ability.IAbilityActiveHasModes;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiTMOverlay extends Gui
{

    private Minecraft mc;
    private int width;
    private int height;
    private int xSize = 22;
    private int ySize = 102;
    private int xOffset = 0;
    private int yOffset = 0;
    private int aOffset = 0;
    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/overlay.png");
    private static final ResourceLocation textureAchievement = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/achievement.png");
    public static int highlightTicks;
    public static boolean shouldDisplay = false;
    private FontRenderer fontrenderer;
    private static List<IKnowledge> knowledgeList = new ArrayList<IKnowledge>();
    private static IKnowledge knowledge;
    private static int display = 0;

    public GuiTMOverlay(Minecraft mc)
    {
        super();
        this.mc = mc;
    }

    public void addKnowledge(IKnowledge k)
    {
        if (knowledge == null) {
            knowledge = k;
        } else {
            knowledgeList.add(k);
        }
    }

    @SubscribeEvent
    public void onRenderExperienceBar(RenderGameOverlayEvent event)
    {
        if (event.isCancelable() || event.type != ElementType.EXPERIENCE) {
            return;
        }

        renderKnowledge(event);
        renderControls(event);
    }

    private void renderKnowledge(RenderGameOverlayEvent event)
    {
        if (knowledge != null) {
            if (display <= 600) {
                if (mc.thePlayer != null) {
                    double d0 = (double) 600 / 3000.0D;

                    if (d0 > 0.5D) {
                        d0 = 0.5D;
                    }

                    GL11.glPushMatrix();
                    GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
                    GL11.glMatrixMode(GL11.GL_PROJECTION);
                    GL11.glLoadIdentity();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glLoadIdentity();
                    this.width = mc.displayWidth;
                    this.height = mc.displayHeight;
                    ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
                    this.width = scaledresolution.getScaledWidth();
                    this.height = scaledresolution.getScaledHeight();
                    GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
                    GL11.glMatrixMode(GL11.GL_PROJECTION);
                    GL11.glLoadIdentity();
                    GL11.glOrtho(0.0D, (double) this.width, (double) this.height, 0.0D, 1000.0D, 3000.0D);
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glLoadIdentity();
                    GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    GL11.glDepthMask(false);
                    double d1 = d0 * 2.0D;

                    if (d1 > 1.0D) {
                        d1 = 2.0D - d1;
                    }

                    d1 *= 4.0D;
                    d1 = 1.0D - d1;

                    if (d1 < 0.0D) {
                        d1 = 0.0D;
                    }

                    d1 *= d1;
                    d1 *= d1;
                    int i = this.width - 160;
                    int j = 0 - (int) (d1 * 36.0D);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    mc.getTextureManager().bindTexture(textureAchievement);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    this.drawTexturedModalRect(i, j, 96, 202, 160, 32);

                    mc.fontRenderer.drawString("Knowledge Unlocked", i + 30, j + 7, -256);
                    mc.fontRenderer.drawString(knowledge.getLocalisedName(), i + 30, j + 18, -1);

                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                    GL11.glEnable(GL11.GL_COLOR_MATERIAL);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    mc.getTextureManager().bindTexture(knowledge.getIcon());
                    this.func_146110_a(i + 6, j + 6, 0, 0, 20, 20, 20, 20);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glDepthMask(true);
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glPopMatrix();
                }

                display++;
            } else {
                knowledge = null;
            }
        } else {
            display = 0;

            if (knowledgeList.size() > 0) {
                Iterator<IKnowledge> i = knowledgeList.iterator();

                if (i.hasNext()) {
                    knowledge = i.next();
                    knowledgeList.remove(knowledge);
                }
            }
        }
    }

    private void renderControls(RenderGameOverlayEvent event)
    {
        ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(this.mc.thePlayer);

        if (charon == null || charon.canSpecialise()) {
            return;
        }

        this.fontrenderer = Minecraft.getMinecraft().fontRenderer;

        ScaledResolution scaled = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        this.width = scaled.getScaledWidth();
        this.height = scaled.getScaledHeight();
        this.xOffset = 2;
        this.yOffset = (this.height - this.ySize) / 2;

        int nanites = charon.nanites.getNanites();
        int maxNanites = charon.nanites.getMaxNanites();
        int researchNanites = charon.nanites.getData();
        float nanite = 102 / 100;
        int staffMode = charon.abilities.getMode();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);

        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.xOffset, this.yOffset, 0, 0, this.xSize, this.ySize);

        this.drawTexturedModalRect(this.xOffset + 22, this.yOffset, 22, 0, 5, this.ySize);

        this.drawTexturedModalRect(this.xOffset + 28, this.yOffset, 22, 0, 5, this.ySize);

        float ro = ((100 - researchNanites) * nanite);
        float yo = ((100 - nanites) * nanite);

        if (maxNanites > 0) {
            this.drawTexturedModalRect(this.xOffset + 22, (int) (this.yOffset + yo), 27, (int) yo, 5, (int) (this.ySize - yo));
        }

        if (researchNanites > 0) {
            this.drawTexturedModalRect(this.xOffset + 28, (int) (this.yOffset + ro), 32, (int) ro, 5, (int) (this.ySize - ro));
        }

        List<String> abilities = charon.abilities.getActiveAbilities();

        if (abilities.size() > 0) {
            int currentAbility = charon.abilities.getCurrentAbility();
            int end = (aOffset + 4) >= abilities.size() ? abilities.size() : aOffset + 4;
            int s = -1;

            if (currentAbility >= 0) {
                if (currentAbility < aOffset) {
                    aOffset = currentAbility;
                } else if (currentAbility > end) {
                    aOffset = currentAbility - 4;
                }

                if (end >= abilities.size()) {
                    end = abilities.size();
                }

                s = (currentAbility - aOffset) * 20;
            }

            for (int i = 0; i < 5; i++) {

                if (i > end || (aOffset + i) >= abilities.size())
                    break;

                IAbilityActive ability = AbilityRegistry.getActiveAbility(abilities.get(aOffset + i));
                int mode = charon.abilities.getAbilityMode();

                if (!ability.hasModes() || mode == -1) {
                    this.mc.getTextureManager().bindTexture(ability.getIcon(staffMode));
                    this.func_146110_a(5, yOffset + (3 + (20 * i)), 0, 0, 16, 16, 16, 16);
                } else {
                    this.mc.getTextureManager().bindTexture(((IAbilityActiveHasModes) ability).getModeIcon(mode, staffMode));
                    this.func_146110_a(5, yOffset + (3 + (20 * i)), 0, 0, 16, 16, 16, 16);
                }
            }

            this.mc.getTextureManager().bindTexture(texture);

            if (s > -1) {
                this.drawTexturedModalRect(this.xOffset - 1, (this.yOffset - 1) + s, 37, 0, 24, 24);
            }

            if (aOffset > 0) {
                this.drawTexturedModalRect(this.xOffset + 5, this.yOffset - 8, 37, 36, 11, 7);
            }

            if (abilities.size() > (end + 1)) {
                this.drawTexturedModalRect(this.xOffset + 5, this.yOffset + 103, 37, 24, 11, 7);
            }

            if (currentAbility > -1 && shouldDisplay && highlightTicks > 0) {
                IAbilityActive ability = charon.abilities.getAbility();
                int mode = charon.abilities.getAbilityMode();
                String display = null;

                if (!ability.hasModes() || mode == -1) {
                    display = ability.getLocalisedName(staffMode);
                } else {
                    display = ((IAbilityActiveHasModes) ability).getModeLocalisedName(mode, staffMode);
                }

                int k1 = (width - fontrenderer.getStringWidth(display)) / 2;
                int l1 = height - 72;

                if (!this.mc.playerController.shouldDrawHUD()) {
                    l1 += 14;
                }

                int i2 = (int) ((float) highlightTicks * 256.0F / 10.0F);

                if (i2 > 255) {
                    i2 = 255;
                }

                if (i2 > 0) {
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    fontrenderer.drawStringWithShadow(display, k1, l1, 10339063 + (i2 << 24));
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                    --highlightTicks;
                }
            } else {
                shouldDisplay = false;
                highlightTicks = 0;
            }
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
    }

}
