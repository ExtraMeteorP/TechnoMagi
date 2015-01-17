package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMachineProcessor extends ModelBase
{
    // fields
    ModelRenderer side1;
    ModelRenderer side2;
    ModelRenderer side3;
    ModelRenderer side4;

    public ModelMachineProcessor()
    {
        textureWidth = 128;
        textureHeight = 64;

        side1 = new ModelRenderer(this, 0, 0);
        side1.addBox(0F, 0F, 0F, 4, 4, 1);
        side1.setRotationPoint(0F, 9F, 3F);
        side1.setTextureSize(128, 64);
        side1.mirror = true;
        setRotation(side1, 0F, 0F, 0.7853982F);
        side2 = new ModelRenderer(this, 0, 5);
        side2.addBox(0F, 0F, 0F, 2, 2, 1);
        side2.setRotationPoint(-1F, 10.8F, 4F);
        side2.setTextureSize(128, 64);
        side2.mirror = true;
        setRotation(side2, 0F, 0F, 0F);
        side3 = new ModelRenderer(this, 0, 0);
        side3.addBox(0F, 0F, 0F, 4, 4, 1);
        side3.setRotationPoint(0F, 9F, -4F);
        side3.setTextureSize(128, 64);
        side3.mirror = true;
        setRotation(side3, 0F, 0F, 0.7853982F);
        side4 = new ModelRenderer(this, 0, 5);
        side4.addBox(0F, 0F, 0F, 2, 2, 1);
        side4.setRotationPoint(-1F, 10.8F, -5F);
        side4.setTextureSize(128, 64);
        side4.mirror = true;
        setRotation(side4, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        side1.render(f5);
        side2.render(f5);
        side3.render(f5);
        side4.render(f5);
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
