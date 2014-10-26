package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTank extends ModelBase
{
    // fields
    ModelRenderer plateBottom;
    ModelRenderer plateTop;
    ModelRenderer corner1;
    ModelRenderer corner2;
    ModelRenderer corner3;
    ModelRenderer corner4;
    ModelRenderer bottomSup1;
    ModelRenderer bottomSup2;
    ModelRenderer bottomSup3;
    ModelRenderer bottomSup4;
    ModelRenderer topSup1;
    ModelRenderer topSup2;
    ModelRenderer topSup3;
    ModelRenderer topSup4;

    public ModelTank()
    {
        textureWidth = 64;
        textureHeight = 32;

        plateBottom = new ModelRenderer(this, 9, 0);
        plateBottom.addBox(0F, 0F, 0F, 6, 1, 6);
        plateBottom.setRotationPoint(-3F, 23F, -3F);
        plateBottom.setTextureSize(64, 32);
        plateBottom.mirror = true;
        setRotation(plateBottom, 0F, 0F, 0F);
        plateTop = new ModelRenderer(this, 9, 0);
        plateTop.addBox(0F, 0F, 0F, 6, 1, 6);
        plateTop.setRotationPoint(-3F, 8F, -3F);
        plateTop.setTextureSize(64, 32);
        plateTop.mirror = true;
        setRotation(plateTop, 0F, 0F, 0F);
        corner1 = new ModelRenderer(this, 0, 0);
        corner1.addBox(0F, 0F, 0F, 2, 14, 2);
        corner1.setRotationPoint(-8F, 9F, -8F);
        corner1.setTextureSize(64, 32);
        corner1.mirror = true;
        setRotation(corner1, 0F, 0F, 0F);
        corner2 = new ModelRenderer(this, 0, 0);
        corner2.addBox(0F, 0F, 0F, 2, 14, 2);
        corner2.setRotationPoint(-8F, 9F, 6F);
        corner2.setTextureSize(64, 32);
        corner2.mirror = true;
        setRotation(corner2, 0F, 0F, 0F);
        corner3 = new ModelRenderer(this, 0, 0);
        corner3.addBox(0F, 0F, 0F, 2, 14, 2);
        corner3.setRotationPoint(6F, 9F, -8F);
        corner3.setTextureSize(64, 32);
        corner3.mirror = true;
        setRotation(corner3, 0F, 0F, 0F);
        corner4 = new ModelRenderer(this, 0, 0);
        corner4.addBox(0F, 0F, 0F, 2, 14, 2);
        corner4.setRotationPoint(6F, 9F, 6F);
        corner4.setTextureSize(64, 32);
        corner4.mirror = true;
        setRotation(corner4, 0F, 0F, 0F);
        bottomSup1 = new ModelRenderer(this, 9, 8);
        bottomSup1.addBox(0F, 0F, 0.2F, 6, 1, 1);
        bottomSup1.setRotationPoint(3F, 23F, 2F);
        bottomSup1.setTextureSize(64, 32);
        bottomSup1.mirror = true;
        setRotation(bottomSup1, 0F, -0.7853982F, -0.1570796F);
        bottomSup2 = new ModelRenderer(this, 9, 10);
        bottomSup2.addBox(0.2F, 0F, 0F, 1, 1, 6);
        bottomSup2.setRotationPoint(-3F, 23F, 2F);
        bottomSup2.setTextureSize(64, 32);
        bottomSup2.mirror = true;
        setRotation(bottomSup2, 0.1570796F, -0.7853982F, 0F);
        bottomSup3 = new ModelRenderer(this, 9, 8);
        bottomSup3.addBox(0F, 0F, 0.2F, 6, 1, 1);
        bottomSup3.setRotationPoint(2F, 23F, -3F);
        bottomSup3.setTextureSize(64, 32);
        bottomSup3.mirror = true;
        setRotation(bottomSup3, 0F, 0.7853982F, -0.1570796F);
        bottomSup4 = new ModelRenderer(this, 9, 8);
        bottomSup4.addBox(0F, 0F, 0.2F, 6, 1, 1);
        bottomSup4.setRotationPoint(-3F, 23F, -2F);
        bottomSup4.setTextureSize(64, 32);
        bottomSup4.mirror = true;
        setRotation(bottomSup4, 0F, 2.356194F, 0.1570796F);
        topSup1 = new ModelRenderer(this, 9, 10);
        topSup1.addBox(0.2F, 0F, 0F, 1, 1, 6);
        topSup1.setRotationPoint(2F, 8F, 3F);
        topSup1.setTextureSize(64, 32);
        topSup1.mirror = true;
        setRotation(topSup1, -0.1570796F, 0.7853982F, 0F);
        topSup2 = new ModelRenderer(this, 9, 8);
        topSup2.addBox(0F, 0F, 0.2F, 6, 1, 1);
        topSup2.setRotationPoint(2F, 8F, -3F);
        topSup2.setTextureSize(64, 32);
        topSup2.mirror = true;
        setRotation(topSup2, 0F, 0.7853982F, 0.1570796F);
        topSup3 = new ModelRenderer(this, 9, 10);
        topSup3.addBox(0.2F, 0F, 0F, 1, 1, 6);
        topSup3.setRotationPoint(-3F, 8F, 2F);
        topSup3.setTextureSize(64, 32);
        topSup3.mirror = true;
        setRotation(topSup3, -0.1570796F, -0.7853982F, 0F);
        topSup4 = new ModelRenderer(this, 9, 8);
        topSup4.addBox(0F, 0F, 0.2F, 6, 1, 1);
        topSup4.setRotationPoint(-3F, 8F, -2F);
        topSup4.setTextureSize(64, 32);
        topSup4.mirror = true;
        setRotation(topSup4, 0F, 2.356194F, -0.1670796F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        plateBottom.render(f5);
        plateTop.render(f5);
        corner1.render(f5);
        corner2.render(f5);
        corner3.render(f5);
        corner4.render(f5);
        bottomSup1.render(f5);
        bottomSup2.render(f5);
        bottomSup3.render(f5);
        bottomSup4.render(f5);
        topSup1.render(f5);
        topSup2.render(f5);
        topSup3.render(f5);
        topSup4.render(f5);
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
