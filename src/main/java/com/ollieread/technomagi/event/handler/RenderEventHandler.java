package com.ollieread.technomagi.event.handler;

import java.lang.reflect.Field;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Post;

import org.lwjgl.opengl.GL11;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderEventHandler
{

    public Field manager;

    public RenderEventHandler(Field f)
    {
        manager = f;
    }

    @SubscribeEvent
    public void onPlayerPostRender(Post event)
    {
        EntityPlayer player = event.entityPlayer;
        ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(player);

        if (playerKnowledge != null && !playerKnowledge.canSpecialise()) {
            ItemStack staffStack = new ItemStack(Items.itemTechnomageStaff, 1, 1);

            boolean flag1 = PlayerHelper.hasInventoryItem(player, staffStack) && (player.getHeldItem() == null || !player.getHeldItem().isItemEqual(staffStack));
            boolean flag2 = playerKnowledge.hasStaff();

            if (flag1 || flag2) {
                if (!player.isInvisible()) {
                    IItemRenderer staffRenderer = MinecraftForgeClient.getItemRenderer(staffStack, ItemRenderType.EQUIPPED);

                    // GL11.glColor3f(1F, 1F, 1F);

                    GL11.glPushMatrix();

                    staffRenderer.renderItem(ItemRenderType.EQUIPPED, staffStack, 1);

                    GL11.glPopMatrix();
                }
            }
        }
    }
}
