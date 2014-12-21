package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGeneratorSolar extends ModelBase
{
    // fields
    ModelRenderer platform1;
    ModelRenderer platform2;
    ModelRenderer platform3;
    ModelRenderer platform4;
    ModelRenderer petal1;
    ModelRenderer petal2;
    ModelRenderer petal3;
    ModelRenderer petal4;
    ModelRenderer petal5;
    ModelRenderer petal6;
    ModelRenderer petal7;
    ModelRenderer petal8;
    ModelRenderer stem;

    public ModelGeneratorSolar()
    {
        textureWidth = 64;
        textureHeight = 64;

        stem = new ModelRenderer(this, 0, 12);
        stem.setRotationPoint(-1.5F, 18.0F, 0.0F);
        stem.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2);
        setRotation(stem, 0.0F, 0.7853981852531433F, 0.0F);
        petal4 = new ModelRenderer(this, 17, 0);
        petal4.setRotationPoint(0.0F, 18.0F, 0.0F);
        petal4.addBox(-5.0F, 0.0F, -5.0F, 5, 1, 5);
        setRotation(petal4, -0.24434609527920614F, 0.0F, 0.24434609527920614F);
        petal5 = new ModelRenderer(this, 9, 12);
        petal5.setRotationPoint(0.0F, 15.5F, 0.0F);
        petal5.addBox(0.0F, 0.0F, -4.0F, 4, 1, 4);
        setRotation(petal5, -0.3839724354387525F, 0.7740535232594852F, -0.24434609527920614F);
        platform2 = new ModelRenderer(this, 0, 5);
        platform2.setRotationPoint(-1.5F, 23.0F, 1.0F);
        platform2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 5);
        petal3 = new ModelRenderer(this, 17, 0);
        petal3.setRotationPoint(0.0F, 18.0F, 0.0F);
        petal3.addBox(0.0F, 0.0F, -5.0F, 5, 1, 5);
        setRotation(petal3, -0.24434609527920614F, -3.141069054814195F, 0.24434609527920614F);
        petal2 = new ModelRenderer(this, 17, 0);
        petal2.setRotationPoint(0.0F, 18.0F, 0.0F);
        petal2.addBox(0.0F, 0.0F, -5.0F, 5, 1, 5);
        setRotation(petal2, -0.24434609527920614F, 0.0F, -0.24434609527920614F);
        platform3 = new ModelRenderer(this, 0, 0);
        platform3.setRotationPoint(1.0F, 23.0F, -1.5F);
        platform3.addBox(0.0F, 0.0F, 0.0F, 5, 1, 3);
        platform1 = new ModelRenderer(this, 0, 0);
        platform1.setRotationPoint(-6.0F, 23.0F, -1.5F);
        platform1.addBox(0.0F, 0.0F, 0.0F, 5, 1, 3);
        petal8 = new ModelRenderer(this, 9, 12);
        petal8.setRotationPoint(0.0F, 15.5F, 0.0F);
        petal8.addBox(0.0F, 0.0F, -4.0F, 4, 1, 4);
        setRotation(petal8, 0.0F, 2.367539130330308F, 0.31869712141416456F);
        petal6 = new ModelRenderer(this, 9, 12);
        petal6.setRotationPoint(0.0F, 15.5F, 0.0F);
        petal6.addBox(-4.0F, 0.0F, -4.0F, 4, 1, 4);
        setRotation(petal6, -0.3839724354387525F, 2.356194490192345F, -0.24434609527920614F);
        petal1 = new ModelRenderer(this, 17, 0);
        petal1.setRotationPoint(0.0F, 18.0F, 0.0F);
        petal1.addBox(0.0F, 0.0F, 0.0F, 5, 1, 5);
        setRotation(petal1, 0.24434609527920614F, 0.0F, -0.24434609527920614F);
        platform4 = new ModelRenderer(this, 0, 5);
        platform4.setRotationPoint(-1.5F, 23.0F, -6.0F);
        platform4.addBox(0.0F, 0.0F, 0.0F, 3, 1, 5);
        petal7 = new ModelRenderer(this, 9, 12);
        petal7.setRotationPoint(0.0F, 15.5F, 0.0F);
        petal7.addBox(0.0F, 0.0F, -4.0F, 4, 1, 4);
        setRotation(petal7, 0.0F, -0.8196066167365371F, -0.31869712141416456F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        platform1.render(f5);
        platform2.render(f5);
        platform3.render(f5);
        platform4.render(f5);
        stem.render(f5);
    }

    public void renderPetalLayer1(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        petal1.render(f5);
        petal2.render(f5);
        petal3.render(f5);
        petal4.render(f5);
    }

    public void renderPetalLayer2(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        petal5.render(f5);
        petal6.render(f5);
        petal7.render(f5);
        petal8.render(f5);
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
