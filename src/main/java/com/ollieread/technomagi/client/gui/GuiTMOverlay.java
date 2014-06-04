package com.ollieread.technomagi.client.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.ability.IAbilityActive;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.player.PlayerKnowledge;

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

    public GuiTMOverlay(Minecraft mc)
    {
        super();
        this.mc = mc;
    }

    @SubscribeEvent
    public void onRenderExperienceBar(RenderGameOverlayEvent event)
    {
        if (event.isCancelable() || event.type != ElementType.EXPERIENCE) {
            return;
        }

        PlayerKnowledge charon = PlayerKnowledge.get(this.mc.thePlayer);

        if (charon == null || charon.canSpecialise()) {
            return;
        }

        ScaledResolution scaled = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        this.width = scaled.getScaledWidth();
        this.height = scaled.getScaledHeight();
        this.xOffset = 2;
        this.yOffset = (this.height - this.ySize) / 2;

        int nanites = charon.getNanites();
        int maxNanites = charon.getMaxNanites();
        int researchNanites = 100 - maxNanites;
        float nanite = 102 / 100;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);

        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.xOffset, this.yOffset, 0, 0, this.xSize, this.ySize);

        this.drawTexturedModalRect(this.xOffset + 25, this.yOffset, 22, 0, 5, this.ySize);

        float ro = Math.round(nanite * researchNanites);
        float yo = ((100 - nanites) * nanite);

        if (maxNanites > 0) {
            this.drawTexturedModalRect(this.xOffset + 25, (int) (this.yOffset + yo), 27, (int) yo, 5, (int) (this.ySize - yo));
        }

        if (researchNanites > 0) {
            this.drawTexturedModalRect(this.xOffset + 25, (int) (this.yOffset + (yo - ro)), 32, (int) (yo - ro), 5, (int) ro);
        }

        List<String> abilities = charon.abilities.getActiveAbilities();

        if (abilities.size() > 0) {
            int currentAbility = charon.abilities.getCurrentAbility();
            int end = aOffset + 4;
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

            if (s > -1) {
                this.drawTexturedModalRect(this.xOffset - 1, (this.yOffset - 1) + s, 37, 7, 24, 24);
            }

            int x = aOffset;
            for (int i = 0; i < 5; i++) {
                x += i;

                if (x == end)
                    break;

                IAbilityActive ability = TMRegistry.getActiveAbility(abilities.get(aOffset + i));

                this.mc.getTextureManager().bindTexture(ability.getIcon());
                this.func_146110_a(5, yOffset + (3 + (20 * i)), 0, 0, 16, 16, 16, 16);
            }
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
    }

}
