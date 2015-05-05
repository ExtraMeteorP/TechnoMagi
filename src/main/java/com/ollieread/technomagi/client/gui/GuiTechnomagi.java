package com.ollieread.technomagi.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.ability.IAbilityCast;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.client.gui.window.WindowOverlay;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiTechnomagi extends Gui
{

    public static WindowOverlay overlay;
    public static int highlightTicks;
    private GuiBuilder builder;

    public static void updateContent()
    {
        if (overlay != null) {
            overlay.updateContent();
        }
    }

    public GuiTechnomagi()
    {
        this.builder = GuiBuilder.instance;
    }

    private void init()
    {
        if (overlay == null && PlayerHelper.hasSpecialised(Technomagi.proxy.getClientPlayer())) {
            GuiTechnomagi.overlay = new WindowOverlay();
        }
    }

    @SubscribeEvent
    public void onRenderExperienceBar(RenderGameOverlayEvent event)
    {
        if (event.isCancelable() || event.type != ElementType.EXPERIENCE) {
            return;
        }

        init();

        if (PlayerHelper.hasSpecialised(Technomagi.proxy.getClientPlayer())) {
            PlayerTechnomagi technomage = PlayerHelper.getTechnomagi(builder.mc.thePlayer);
            renderOverlay(event, technomage);
            renderKnowledge(event, technomage);
            renderAbility(event, technomage);
        }
    }

    private void renderOverlay(RenderGameOverlayEvent event, PlayerTechnomagi technomage)
    {
        if (GuiTechnomagi.overlay != null) {
            ScaledResolution scaled = new ScaledResolution(builder.mc, builder.mc.displayWidth, builder.mc.displayHeight);
            int width = scaled.getScaledWidth();
            int height = scaled.getScaledHeight();
            int x = 0;
            int y = (height - GuiTechnomagi.overlay.getHeight()) / 2;

            GuiTechnomagi.overlay.draw(x, y);
        }
    }

    private void renderKnowledge(RenderGameOverlayEvent event, PlayerTechnomagi technomage)
    {
    }

    private void renderAbility(RenderGameOverlayEvent event, PlayerTechnomagi technomage)
    {
        IAbilityCast ability = technomage.abilities().getCurrentAbility();

        if (highlightTicks > 0 && ability != null) {
            ScaledResolution scaledresolution = new ScaledResolution(builder.mc, builder.mc.displayWidth, builder.mc.displayHeight);
            int width = scaledresolution.getScaledWidth();
            int height = scaledresolution.getScaledHeight();
            String name = StatCollector.translateToLocal(ability.getUnlocalisedName(technomage.abilities().getCastableAbilityMode(ability.getName())));
            int x = (width - builder.mc.fontRenderer.getStringWidth(name)) / 2;
            int y = height - 71;

            if (!builder.mc.playerController.shouldDrawHUD()) {
                y += 14;
            }

            int i2 = (int) (highlightTicks * 256.0F / 10.0F);

            if (i2 > 255) {
                i2 = 255;
            }

            if (i2 > 0) {
                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                builder.mc.fontRenderer.drawStringWithShadow(name, x, y, 2529246 + (i2 << 24));
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
                --highlightTicks;
            }
        } else {
            highlightTicks = 0;
        }
    }

}
