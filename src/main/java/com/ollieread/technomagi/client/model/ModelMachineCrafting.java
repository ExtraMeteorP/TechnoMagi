package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMachineCrafting extends ModelBase
{
    ModelRenderer topLayer;

    public ModelMachineCrafting()
    {
        textureWidth = 128;
        textureHeight = 64;

        topLayer = new ModelRenderer(this, 0, 0);
        topLayer.addBox(0F, 0F, 0F, 16, 1, 16);
        topLayer.setRotationPoint(-8F, 12F, -8F);
        topLayer.setTextureSize(128, 64);
        topLayer.mirror = true;
        setRotation(topLayer, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        topLayer.render(f5);
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
