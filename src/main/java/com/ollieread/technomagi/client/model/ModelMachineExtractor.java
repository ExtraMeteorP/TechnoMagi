package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMachineExtractor extends ModelBase
{
    // fields
    ModelRenderer slab;
    ModelRenderer side1;
    ModelRenderer side2;
    ModelRenderer side3;
    ModelRenderer side4;
    ModelRenderer side5;
    ModelRenderer side6;
    ModelRenderer block1;
    ModelRenderer block2;

    public ModelMachineExtractor()
    {
        textureWidth = 128;
        textureHeight = 64;

        slab = new ModelRenderer(this, 0, 0);
        slab.addBox(0F, 0F, 0F, 4, 1, 4);
        slab.setRotationPoint(-2F, 12F, -2F);
        slab.setTextureSize(128, 64);
        slab.mirror = true;
        setRotation(slab, 0F, 0F, 0F);
        side1 = new ModelRenderer(this, 0, 5);
        side1.addBox(0F, 0F, 0F, 4, 1, 1);
        side1.setRotationPoint(-4.5F, 8F, 0F);
        side1.setTextureSize(128, 64);
        side1.mirror = true;
        setRotation(side1, 0F, 0.7853982F, 0F);
        side2 = new ModelRenderer(this, 0, 5);
        side2.addBox(0F, 0F, 0F, 4, 1, 1);
        side2.setRotationPoint(1.9F, 8F, -2.8F);
        side2.setTextureSize(128, 64);
        side2.mirror = true;
        setRotation(side2, 0F, -0.7853982F, 0F);
        side3 = new ModelRenderer(this, 0, 7);
        side3.addBox(0F, 0F, 0F, 1, 1, 4);
        side3.setRotationPoint(-4.5F, 8F, 0F);
        side3.setTextureSize(128, 64);
        side3.mirror = true;
        setRotation(side3, 0F, 0.7853982F, 0F);
        side4 = new ModelRenderer(this, 0, 7);
        side4.addBox(0F, 0F, 0F, 1, 1, 4);
        side4.setRotationPoint(4F, 8F, -0.6F);
        side4.setTextureSize(128, 64);
        side4.mirror = true;
        setRotation(side4, 0F, -0.7853982F, 0F);
        side5 = new ModelRenderer(this, 0, 0);
        side5.addBox(0F, 0F, 0F, 4, 1, 1);
        side5.setRotationPoint(-1.8F, 8F, -2.5F);
        side5.setTextureSize(128, 64);
        side5.mirror = true;
        setRotation(side5, 0F, 0F, 0F);
        side6 = new ModelRenderer(this, 0, 0);
        side6.addBox(0F, 0F, 0F, 4, 1, 1);
        side6.setRotationPoint(-1.8F, 8F, 1.5F);
        side6.setTextureSize(128, 64);
        side6.mirror = true;
        setRotation(side6, 0F, 0F, 0F);
        block1 = new ModelRenderer(this, 0, 0);
        block1.addBox(0F, 0F, 0F, 2, 2, 6);
        block1.setRotationPoint(4F, 11F, -3F);
        block1.setTextureSize(128, 64);
        block1.mirror = true;
        setRotation(block1, 0F, 0F, 0F);
        block2 = new ModelRenderer(this, 0, 0);
        block2.addBox(0F, 0F, 0F, 2, 2, 6);
        block2.setRotationPoint(-6F, 11F, -3F);
        block2.setTextureSize(128, 64);
        block2.mirror = true;
        setRotation(block2, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        slab.render(f5);
        side1.render(f5);
        side2.render(f5);
        side3.render(f5);
        side4.render(f5);
        side5.render(f5);
        side6.render(f5);
        block1.render(f5);
        block2.render(f5);
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
