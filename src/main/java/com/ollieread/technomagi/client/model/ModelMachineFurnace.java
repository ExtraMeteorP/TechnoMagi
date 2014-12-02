package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMachineFurnace extends ModelBase
{
    // fields
    ModelRenderer back;
    ModelRenderer side1;
    ModelRenderer side2;
    ModelRenderer plate1;
    ModelRenderer plate2;

    public ModelMachineFurnace()
    {
        textureWidth = 128;
        textureHeight = 64;

        back = new ModelRenderer(this, 0, 0);
        back.addBox(0F, 0F, 0F, 4, 4, 14);
        back.setRotationPoint(4F, 10F, -7F);
        back.setTextureSize(128, 64);
        back.mirror = true;
        setRotation(back, 0F, 0F, 0F);
        side1 = new ModelRenderer(this, 0, 18);
        side1.addBox(0F, 0F, 0F, 6, 3, 3);
        side1.setRotationPoint(0F, 14F, -6F);
        side1.setTextureSize(128, 64);
        side1.mirror = true;
        setRotation(side1, 0F, 0F, -0.7330383F);
        side2 = new ModelRenderer(this, 0, 18);
        side2.addBox(0F, 0F, 0F, 6, 3, 3);
        side2.setRotationPoint(0F, 14F, 3F);
        side2.setTextureSize(128, 64);
        side2.mirror = true;
        setRotation(side2, 0F, 0F, -0.7330383F);
        plate1 = new ModelRenderer(this, 0, 24);
        plate1.addBox(0F, 0F, 0F, 4, 2, 0);
        plate1.setRotationPoint(-6F, 12F, 3F);
        plate1.setTextureSize(128, 64);
        plate1.mirror = true;
        setRotation(plate1, 0F, -0.7853982F, 0F);
        plate2 = new ModelRenderer(this, 0, 24);
        plate2.addBox(0F, 0F, 0F, 4, 2, 0);
        plate2.setRotationPoint(-6F, 12F, -3F);
        plate2.setTextureSize(128, 64);
        plate2.mirror = true;
        setRotation(plate2, 0F, 0.7853982F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        back.render(f5);
        side1.render(f5);
        side2.render(f5);
        plate1.render(f5);
        plate2.render(f5);
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
