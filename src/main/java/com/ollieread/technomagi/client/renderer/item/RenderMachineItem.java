package com.ollieread.technomagi.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBook;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.model.ModelMachineAnalysis;
import com.ollieread.technomagi.client.model.ModelMachineAssembler;
import com.ollieread.technomagi.client.model.ModelMachineConstruct;
import com.ollieread.technomagi.client.model.ModelMachineFurnace;
import com.ollieread.technomagi.client.model.ModelMachineInfuser;
import com.ollieread.technomagi.client.model.ModelMachineProcessor;
import com.ollieread.technomagi.client.model.ModelMachineReplicator;
import com.ollieread.technomagi.common.Reference;

public class RenderMachineItem implements IItemRenderer
{

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
        switch (item.getItemDamage()) {
            case 1:
                renderArchive(type, item, data);
                break;
            case 2:
                renderAssembler(type, item, data);
                break;
            case 3:
                renderReplicator(type, item, data);
                break;
            case 4:
                renderAnalysis(type, item, data);
                break;
            case 5:
                renderProcessor(type, item, data);
                break;
            case 6:
                renderFurnace(type, item, data);
                break;
            case 7:
                renderInfuser(type, item, data);
                break;
        }
    }

    protected void renderAnalysis(ItemRenderType type, ItemStack item, Object... data)
    {
        ModelMachineConstruct construct = new ModelMachineConstruct();
        ModelMachineAnalysis analysis = new ModelMachineAnalysis();

        ResourceLocation textureConstruct = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelConstruct.png"));
        ResourceLocation textureReplicator = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelAnalysis.png"));

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

        Minecraft.getMinecraft().renderEngine.bindTexture(textureConstruct);
        construct.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        Minecraft.getMinecraft().renderEngine.bindTexture(textureReplicator);
        analysis.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

    protected void renderArchive(ItemRenderType type, ItemStack item, Object... data)
    {
        ModelMachineConstruct construct = new ModelMachineConstruct();
        ModelBook book = new ModelBook();

        ResourceLocation textureConstruct = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelConstruct.png"));
        ResourceLocation textureBook = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelArchiveBook.png"));

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

        Minecraft.getMinecraft().renderEngine.bindTexture(textureConstruct);
        construct.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        Minecraft.getMinecraft().renderEngine.bindTexture(textureBook);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);

        book.bookSpine.offsetX = -0.8F;
        book.coverRight.offsetX = -0.8F;
        book.coverLeft.offsetX = -0.8F;
        book.pagesRight.offsetX = -0.8F;
        book.pagesLeft.offsetX = -0.8F;
        book.flippingPageRight.offsetX = -0.8F;
        book.flippingPageLeft.offsetX = -0.8F;
        book.bookSpine.offsetX = -0.8F;

        book.render((Entity) null, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0625F);

        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();
    }

    protected void renderAssembler(ItemRenderType type, ItemStack item, Object... data)
    {
        ModelMachineConstruct construct = new ModelMachineConstruct();
        ModelMachineAssembler crafting = new ModelMachineAssembler();

        ResourceLocation textureConstruct = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelConstruct.png"));
        ResourceLocation textureReplicator = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelCrafting.png"));

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

        Minecraft.getMinecraft().renderEngine.bindTexture(textureConstruct);
        construct.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        Minecraft.getMinecraft().renderEngine.bindTexture(textureReplicator);
        crafting.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

    protected void renderFurnace(ItemRenderType type, ItemStack item, Object... data)
    {
        ModelMachineConstruct construct = new ModelMachineConstruct();
        ModelMachineFurnace furnace = new ModelMachineFurnace();

        ResourceLocation textureConstruct = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelConstruct.png"));
        ResourceLocation textureFurnace = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelFurnace.png"));

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

        Minecraft.getMinecraft().renderEngine.bindTexture(textureConstruct);
        construct.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        if (type.equals(ItemRenderType.INVENTORY) || type.equals(ItemRenderType.ENTITY)) {
            GL11.glRotatef(90F, 0.0F, -1.0F, 0.0F);
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(textureFurnace);
        furnace.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

    protected void renderInfuser(ItemRenderType type, ItemStack item, Object... data)
    {
        ModelMachineConstruct construct = new ModelMachineConstruct();
        ModelMachineInfuser focuser = new ModelMachineInfuser();

        ResourceLocation textureConstruct = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelConstruct.png"));
        ResourceLocation textureFocuser = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelFocuser.png"));

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

        Minecraft.getMinecraft().renderEngine.bindTexture(textureConstruct);
        construct.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        if (type.equals(ItemRenderType.INVENTORY) || type.equals(ItemRenderType.ENTITY)) {
            GL11.glRotatef(90F, 0.0F, -1.0F, 0.0F);
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(textureFocuser);
        focuser.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

    protected void renderProcessor(ItemRenderType type, ItemStack item, Object... data)
    {
        ModelMachineConstruct construct = new ModelMachineConstruct();
        ModelMachineProcessor separator = new ModelMachineProcessor();

        ResourceLocation textureConstruct = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelConstruct.png"));
        ResourceLocation textureSeparator = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelSeparator.png"));

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

        Minecraft.getMinecraft().renderEngine.bindTexture(textureConstruct);
        construct.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        if (type.equals(ItemRenderType.INVENTORY) || type.equals(ItemRenderType.ENTITY)) {
            GL11.glRotatef(90F, 0.0F, -1.0F, 0.0F);
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(textureSeparator);
        separator.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

    protected void renderReplicator(ItemRenderType type, ItemStack item, Object... data)
    {
        ModelMachineConstruct construct = new ModelMachineConstruct();
        ModelMachineReplicator replicator = new ModelMachineReplicator();

        ResourceLocation textureConstruct = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelConstruct.png"));
        ResourceLocation textureReplicator = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelReplicator.png"));

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

        Minecraft.getMinecraft().renderEngine.bindTexture(textureConstruct);
        construct.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        Minecraft.getMinecraft().renderEngine.bindTexture(textureReplicator);
        replicator.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

}
