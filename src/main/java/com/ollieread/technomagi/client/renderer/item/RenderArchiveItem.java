package com.ollieread.technomagi.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.model.ModelMachineArchive;
import com.ollieread.technomagi.common.Reference;

public class RenderArchiveItem implements IItemRenderer
{

    protected ModelMachineArchive model;

    public RenderArchiveItem()
    {
        model = new ModelMachineArchive();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {

        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelArchive.png"));

        float scale = 0;

        if (type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON)) {
            scale = 1.2F;

            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90F, 0.0F, -1.0F, 0.0F);
        } else if (type.equals(ItemRenderType.INVENTORY)) {
            scale = 1.0F;

            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(270F, 0.0F, -1.0F, 0.0F);
        }
        GL11.glTranslatef(0.0F, -1.0F, 0.0F);

        model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

}