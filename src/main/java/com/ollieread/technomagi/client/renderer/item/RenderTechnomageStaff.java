package com.ollieread.technomagi.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.client.model.ModelTechnomageStaff;
import com.ollieread.technomagi.common.Reference;

public class RenderTechnomageStaff implements IItemRenderer
{

    private final ModelTechnomageStaff staff;

    public RenderTechnomageStaff()
    {
        staff = new ModelTechnomageStaff();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {

        ResourceLocation texture = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/items/technomageStaff.png"));

        GL11.glPushMatrix();

        float scale = 0;

        if (type.equals(ItemRenderType.EQUIPPED) && (data != null && data[0] instanceof Integer)) {
            int i = (Integer) data[0];

            if (i == 1) {
                scale = 0.4F;
                GL11.glTranslatef(0.0F, 0.6F, 0.1F);
                GL11.glRotatef(40F, 0.0F, 0.0F, -1.0F);
                GL11.glScalef(scale, scale, scale);

                Minecraft.getMinecraft().renderEngine.bindTexture(texture);
                staff.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, item);

                GL11.glPopMatrix();

                return;
            }
        }
        if (type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON)) {
            scale = 1F;
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);

            EntityPlayer player = TechnoMagi.proxy.getClientPlayer();

            if (player != null) {
                if (player.isInvisible()) {
                    return;
                }
                if (player.isUsingItem()) {
                    GL11.glRotatef(90F, -1.0F, 0.0F, 0.0F);
                }
            }

        } else if (type.equals(ItemRenderType.EQUIPPED)) {
            scale = 1F;
            GL11.glTranslatef(1.5F, -0.2F, 1.5F);
            GL11.glRotatef(90F, -1.0F, 1.0F, 0.0F);

            if (data != null && data.length >= 2 && data[1] instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) data[1];

                if (player != null) {
                    if (player.isInvisible()) {
                        return;
                    }
                    if (player.isUsingItem()) {
                        GL11.glTranslatef(0.0F, 0.6F, 0.0F);
                        GL11.glRotatef(90F, -1.0F, 0.0F, 0.0F);
                    }
                }
            }
        } else if (type.equals(ItemRenderType.INVENTORY)) {
            if (item.getItemDamage() == 0) {
                scale = 0.45F;
                GL11.glTranslatef(0.1F, -0.25F, 0.0F);
            } else {
                scale = 0.3F;
                GL11.glTranslatef(0.1F, -0.5F, 0.0F);
            }
        } else if (type.equals(ItemRenderType.ENTITY)) {
            scale = 1F;
        }

        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(0.0F, -0.6F, 0.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        staff.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, item);

        GL11.glPopMatrix();
    }

}
