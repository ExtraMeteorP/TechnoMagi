package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMachineConstruct extends ModelBase
{
    // fields
    ModelRenderer base;
    ModelRenderer stand;
    ModelRenderer top;

    public ModelMachineConstruct()
    {
        textureWidth = 128;
        textureHeight = 64;

        base = new ModelRenderer(this, 0, 0);
        base.addBox(0F, 0F, 0F, 16, 1, 16);
        base.setRotationPoint(-8F, 23F, -8F);
        base.setTextureSize(128, 64);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        stand = new ModelRenderer(this, 0, 17);
        stand.addBox(0F, 0F, 0F, 14, 4, 14);
        stand.setRotationPoint(-7F, 19F, -7F);
        stand.setTextureSize(128, 64);
        stand.mirror = true;
        setRotation(stand, 0F, 0F, 0F);
        top = new ModelRenderer(this, 0, 39);
        top.addBox(0F, 0F, 0F, 16, 6, 16);
        top.setRotationPoint(-8F, 13F, -8F);
        top.setTextureSize(128, 64);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        base.render(f5);
        stand.render(f5);
        top.render(f5);
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
