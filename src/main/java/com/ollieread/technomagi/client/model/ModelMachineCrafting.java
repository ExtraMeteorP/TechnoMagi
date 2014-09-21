package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMachineCrafting extends ModelBase
{

    ModelRenderer crafting1;
    ModelRenderer crafting2;
    ModelRenderer crafting3;
    ModelRenderer crafting4;
    ModelRenderer crafting5;
    ModelRenderer crafting6;
    ModelRenderer crafting7;
    ModelRenderer crafting8;
    ModelRenderer crafting9;

    public ModelMachineCrafting()
    {
        textureWidth = 128;
        textureHeight = 64;

        crafting1 = new ModelRenderer(this, 77, 0);
        crafting1.addBox(0F, 0F, 0F, 2, 1, 2);
        crafting1.setRotationPoint(-5F, 11F, -5F);
        crafting1.setTextureSize(128, 64);
        crafting1.mirror = true;
        setRotation(crafting1, 0F, 0F, 0F);
        crafting2 = new ModelRenderer(this, 77, 0);
        crafting2.addBox(0F, 0F, 0F, 2, 1, 2);
        crafting2.setRotationPoint(-1F, 11F, -5F);
        crafting2.setTextureSize(128, 64);
        crafting2.mirror = true;
        setRotation(crafting2, 0F, 0F, 0F);
        crafting3 = new ModelRenderer(this, 77, 0);
        crafting3.addBox(0F, 0F, 0F, 2, 1, 2);
        crafting3.setRotationPoint(3F, 11F, -5F);
        crafting3.setTextureSize(128, 64);
        crafting3.mirror = true;
        setRotation(crafting3, 0F, 0F, 0F);
        crafting4 = new ModelRenderer(this, 77, 0);
        crafting4.addBox(0F, 0F, 0F, 2, 1, 2);
        crafting4.setRotationPoint(-5F, 11F, -1F);
        crafting4.setTextureSize(128, 64);
        crafting4.mirror = true;
        setRotation(crafting4, 0F, 0F, 0F);
        crafting5 = new ModelRenderer(this, 77, 0);
        crafting5.addBox(0F, 0F, -1F, 2, 1, 2);
        crafting5.setRotationPoint(-1F, 11F, 0F);
        crafting5.setTextureSize(128, 64);
        crafting5.mirror = true;
        setRotation(crafting5, 0F, 0F, 0F);
        crafting6 = new ModelRenderer(this, 77, 0);
        crafting6.addBox(0F, 0F, 0F, 2, 1, 2);
        crafting6.setRotationPoint(3F, 11F, -1F);
        crafting6.setTextureSize(128, 64);
        crafting6.mirror = true;
        setRotation(crafting6, 0F, 0F, 0F);
        crafting7 = new ModelRenderer(this, 77, 0);
        crafting7.addBox(0F, 0F, 0F, 2, 1, 2);
        crafting7.setRotationPoint(-5F, 11F, 3F);
        crafting7.setTextureSize(128, 64);
        crafting7.mirror = true;
        setRotation(crafting7, 0F, 0F, 0F);
        crafting8 = new ModelRenderer(this, 77, 0);
        crafting8.addBox(0F, 0F, 0F, 2, 1, 2);
        crafting8.setRotationPoint(-1F, 11F, 3F);
        crafting8.setTextureSize(128, 64);
        crafting8.mirror = true;
        setRotation(crafting8, 0F, 0F, 0F);
        crafting9 = new ModelRenderer(this, 77, 0);
        crafting9.addBox(0F, 0F, 3F, 2, 1, 2);
        crafting9.setRotationPoint(3F, 11F, 0F);
        crafting9.setTextureSize(128, 64);
        crafting9.mirror = true;
        setRotation(crafting9, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        crafting1.render(f5);
        crafting2.render(f5);
        crafting3.render(f5);
        crafting4.render(f5);
        crafting5.render(f5);
        crafting6.render(f5);
        crafting7.render(f5);
        crafting8.render(f5);
        crafting9.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
