package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMachineFocusCharger extends ModelBase
{
    // fields
    ModelRenderer slab;
    ModelRenderer side1;
    ModelRenderer side2;
    ModelRenderer side3;
    ModelRenderer side4;
    ModelRenderer side5;
    ModelRenderer side6;
    ModelRenderer side7;
    ModelRenderer side8;
    ModelRenderer side9;
    ModelRenderer side10;
    ModelRenderer side11;
    ModelRenderer side12;
    ModelRenderer stand1;
    ModelRenderer stand2;

    public ModelMachineFocusCharger()
    {
        textureWidth = 128;
        textureHeight = 64;

        slab = new ModelRenderer(this, 10, 0);
        slab.addBox(0F, 0F, 0F, 6, 1, 6);
        slab.setRotationPoint(-3F, 23F, -3F);
        slab.setTextureSize(128, 64);
        slab.mirror = true;
        setRotation(slab, 0F, 0F, 0F);
        side1 = new ModelRenderer(this, 0, 5);
        side1.addBox(0F, 0F, 0F, 4, 1, 1);
        side1.setRotationPoint(-4.5F, 11F, 0F);
        side1.setTextureSize(128, 64);
        side1.mirror = true;
        setRotation(side1, 0F, 0.7853982F, 0F);
        side2 = new ModelRenderer(this, 0, 7);
        side2.addBox(0F, 0F, 0F, 1, 1, 4);
        side2.setRotationPoint(-4.5F, 11F, 0F);
        side2.setTextureSize(128, 64);
        side2.mirror = true;
        setRotation(side2, 0F, 0.7853982F, 0F);
        side3 = new ModelRenderer(this, 0, 5);
        side3.addBox(0F, 0F, 0F, 4, 1, 1);
        side3.setRotationPoint(1.9F, 11F, -2.8F);
        side3.setTextureSize(128, 64);
        side3.mirror = true;
        setRotation(side3, 0F, -0.7853982F, 0F);
        side4 = new ModelRenderer(this, 0, 7);
        side4.addBox(0F, 0F, 0F, 1, 1, 4);
        side4.setRotationPoint(4F, 11F, -0.6F);
        side4.setTextureSize(128, 64);
        side4.mirror = true;
        setRotation(side4, 0F, -0.7853982F, 0F);
        side5 = new ModelRenderer(this, 0, 5);
        side5.addBox(0F, 0F, 0F, 4, 1, 1);
        side5.setRotationPoint(-3.5F, 14F, 0F);
        side5.setTextureSize(128, 64);
        side5.mirror = true;
        setRotation(side5, 0F, 0.7853982F, 0F);
        side6 = new ModelRenderer(this, 0, 5);
        side6.addBox(0F, 0F, 0F, 4, 1, 1);
        side6.setRotationPoint(0.9F, 14F, -2.8F);
        side6.setTextureSize(128, 64);
        side6.mirror = true;
        setRotation(side6, 0F, -0.7853982F, 0F);
        side7 = new ModelRenderer(this, 0, 7);
        side7.addBox(0F, 0F, 0F, 1, 1, 4);
        side7.setRotationPoint(-3.5F, 14F, 0F);
        side7.setTextureSize(128, 64);
        side7.mirror = true;
        setRotation(side7, 0F, 0.7853982F, 0F);
        side8 = new ModelRenderer(this, 0, 7);
        side8.addBox(0F, 0F, 0F, 1, 1, 4);
        side8.setRotationPoint(3F, 14F, -0.6F);
        side8.setTextureSize(128, 64);
        side8.mirror = true;
        setRotation(side8, 0F, -0.7853982F, 0F);
        side9 = new ModelRenderer(this, 0, 5);
        side9.addBox(0F, 0F, 0F, 4, 1, 1);
        side9.setRotationPoint(-3.5F, 17F, 0F);
        side9.setTextureSize(128, 64);
        side9.mirror = true;
        setRotation(side9, 0F, 0.7853982F, 0F);
        side10 = new ModelRenderer(this, 0, 5);
        side10.addBox(0F, 0F, 0F, 4, 1, 1);
        side10.setRotationPoint(0.9F, 17F, -2.8F);
        side10.setTextureSize(128, 64);
        side10.mirror = true;
        setRotation(side10, 0F, -0.7853982F, 0F);
        side11 = new ModelRenderer(this, 0, 7);
        side11.addBox(0F, 0F, 0F, 1, 1, 4);
        side11.setRotationPoint(-3.5F, 17F, 0F);
        side11.setTextureSize(128, 64);
        side11.mirror = true;
        setRotation(side11, 0F, 0.7853982F, 0F);
        side12 = new ModelRenderer(this, 0, 7);
        side12.addBox(0F, 0F, 0F, 1, 1, 4);
        side12.setRotationPoint(3F, 17F, -0.6F);
        side12.setTextureSize(128, 64);
        side12.mirror = true;
        setRotation(side12, 0F, -0.7853982F, 0F);
        stand1 = new ModelRenderer(this, 0, 12);
        stand1.addBox(0F, 0F, 0F, 1, 14, 1);
        stand1.setRotationPoint(-0.5F, 9F, -0.5F);
        stand1.setTextureSize(128, 64);
        stand1.mirror = true;
        setRotation(stand1, 0F, 0F, 0F);
        stand2 = new ModelRenderer(this, 4, 18);
        stand2.addBox(0F, 0F, 0F, 2, 2, 2);
        stand2.setRotationPoint(-1F, 8F, -1F);
        stand2.setTextureSize(128, 64);
        stand2.mirror = true;
        setRotation(stand2, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        slab.render(f5);
        stand1.render(f5);
        stand2.render(f5);
    }

    public void renderSecondary(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        side1.render(f5);
        side2.render(f5);
        side3.render(f5);
        side4.render(f5);
        side5.render(f5);
        side6.render(f5);
        side7.render(f5);
        side8.render(f5);
        side9.render(f5);
        side10.render(f5);
        side11.render(f5);
        side12.render(f5);
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
