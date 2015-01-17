package com.ollieread.technomagi.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.model.ModelTank;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.component.Storage;

public class RenderStorageItem implements IItemRenderer
{

    private final ModelTank model;

    public RenderStorageItem()
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
        // GL11.glRotatef(270F, 0.0F, -1.0F, 0.0F);
        GL11.glTranslatef(0.0F, -1.0F, 0.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        if (item.stackTagCompound != null) {
            Storage storage = new Storage(4096);
            storage.readFromNBT(item.stackTagCompound);
            ItemStack itemStack = storage.getItem();

            if (itemStack != null) {

                int amount = storage.getAmount();
                int capacity = storage.getCapacity();

                if (amount > 0) {
                    GL11.glDisable(GL11.GL_LIGHTING);

                    GL11.glPushMatrix();
                    GL11.glTranslatef(0, 1.0F, 0);

                    double offset = -0.1D;
                    float scaleF = 2.0F;

                    if (!(itemStack.getItem() instanceof ItemBlock)) {
                        scaleF = 1.5F;
                        offset = -0.23D;
                    }

                    GL11.glScalef(scaleF, scaleF, scaleF);
                    GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

                    ItemStack stack2 = itemStack.copy();

                    RenderBlocks renderBlocks = new RenderBlocks();
                    RenderItem renderItem = ((RenderItem) RenderManager.instance.getEntityClassRenderObject(EntityItem.class));

                    EntityItem entityItem = new EntityItem(null, 0.0D, 0.0D, 0.0D, stack2);
                    entityItem.getEntityItem().stackSize = 1;
                    entityItem.hoverStart = 0.0F;
                    entityItem.rotationPitch = 0.0F;
                    entityItem.rotationYaw = 0.0F;
                    RenderManager.instance.renderEntityWithPosYaw(entityItem, 0D, offset, 0D, 0.0F, 0.0F);

                    GL11.glPopMatrix();

                    GL11.glEnable(GL11.GL_LIGHTING);
                }
            }
        }

        GL11.glPopMatrix();
    }

}
