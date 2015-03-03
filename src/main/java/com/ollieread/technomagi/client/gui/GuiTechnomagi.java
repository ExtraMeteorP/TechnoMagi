package com.ollieread.technomagi.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.client.gui.window.WindowOverlay;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiTechnomagi extends Gui
{

    public static WindowOverlay overlay;

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
            this.overlay = new WindowOverlay();
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
            renderOverlay(event);
            renderKnowledge(event);
        }
    }

    private void renderOverlay(RenderGameOverlayEvent event)
    {
        if (this.overlay != null) {
            ScaledResolution scaled = new ScaledResolution(builder.mc, builder.mc.displayWidth, builder.mc.displayHeight);
            int width = scaled.getScaledWidth();
            int height = scaled.getScaledHeight();
            int x = 0;
            int y = (height - this.overlay.getHeight()) / 2;

            this.overlay.draw(x, y);
        }
    }

    private void renderKnowledge(RenderGameOverlayEvent event)
    {
        PlayerTechnomagi technomage = PlayerHelper.getTechnomagi(builder.mc.thePlayer);
    }

}
