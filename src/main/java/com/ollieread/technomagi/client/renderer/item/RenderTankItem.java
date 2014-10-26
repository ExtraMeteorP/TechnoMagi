package com.ollieread.technomagi.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.model.ModelTank;
import com.ollieread.technomagi.common.Reference;

public class RenderTankItem implements IItemRenderer
{

    private final ModelTank model;

    public RenderTankItem()
    {
        model = new ModelTank();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED || type == ItemRenderType.ENTITY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        ResourceLocation texture;

        switch (item.getItemDamage()) {
            case 1:
                texture = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelTankAdvanced.png"));
                break;
            case 2:
                texture = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelTankElite.png"));
                break;
            default:
                texture = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelTank.png"));
                break;
        }

        GL11.glPushMatrix();

        float scale = 0;

        if (type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON) || type.equals(ItemRenderType.EQUIPPED)) {
            scale = 1.2F;
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        } else if (type.equals(ItemRenderType.INVENTORY) || type.equals(ItemRenderType.ENTITY)) {
            scale = 1.0F;
        }

        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(270F, 0.0F, -1.0F, 0.0F);
        GL11.glTranslatef(0.0F, -1.0F, 0.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

}
