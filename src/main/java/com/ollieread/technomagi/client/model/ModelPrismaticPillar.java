package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPrismaticPillar extends ModelBase
{
    // fields
    ModelRenderer pillar1;
    ModelRenderer pillar2;
    ModelRenderer pillar3;
    ModelRenderer pillar4;
    ModelRenderer pillarSide1;
    ModelRenderer pillarSide2;
    ModelRenderer pillarSide3;
    ModelRenderer pillarSide4;
    ModelRenderer pillarSide5;
    ModelRenderer pillarSide6;
    ModelRenderer pillarSide7;
    ModelRenderer pillarSide8;
    ModelRenderer base1Bottom;
    ModelRenderer centreBottom;
    ModelRenderer base2Bottom;
    ModelRenderer side1;
    ModelRenderer side2;
    ModelRenderer side3;
    ModelRenderer side4;
    ModelRenderer crystal1;
    ModelRenderer crystal2;
    ModelRenderer base1Top;
    ModelRenderer centreTop;
    ModelRenderer base2Top;

    public ModelPrismaticPillar()
    {
        textureWidth = 128;
        textureHeight = 128;

        pillar1 = new ModelRenderer(this, -1, 0);
        pillar1.addBox(0F, 0F, 0F, 4, 8, 4);
        pillar1.setRotationPoint(-2F, 16F, -6F);
        pillar1.setTextureSize(128, 128);
        pillar1.mirror = true;
        setRotation(pillar1, 0F, 0F, 0F);
        pillar2 = new ModelRenderer(this, 0, 0);
        pillar2.addBox(0F, 0F, 0F, 4, 8, 4);
        pillar2.setRotationPoint(-2F, 16F, 2F);
        pillar2.setTextureSize(128, 128);
        pillar2.mirror = true;
        setRotation(pillar2, 0F, 0F, 0F);
        pillar3 = new ModelRenderer(this, 38, 0);
        pillar3.addBox(0F, 0F, 0F, 4, 8, 4);
        pillar3.setRotationPoint(2F, 16F, -2F);
        pillar3.setTextureSize(128, 128);
        pillar3.mirror = true;
        setRotation(pillar3, 0F, 0F, 0F);
        pillar4 = new ModelRenderer(this, 38, 0);
        pillar4.addBox(0F, 0F, 0F, 4, 8, 4);
        pillar4.setRotationPoint(-6F, 16F, -2F);
        pillar4.setTextureSize(128, 128);
        pillar4.mirror = true;
        setRotation(pillar4, 0F, 0F, 0F);
        pillarSide1 = new ModelRenderer(this, 17, 0);
        pillarSide1.addBox(0F, 0F, 0F, 1, 6, 2);
        pillarSide1.setRotationPoint(-3F, 16F, 3F);
        pillarSide1.setTextureSize(128, 128);
        pillarSide1.mirror = true;
        setRotation(pillarSide1, 0F, 0F, 0F);
        pillarSide2 = new ModelRenderer(this, 17, 9);
        pillarSide2.addBox(0F, 0F, 0F, 2, 6, 1);
        pillarSide2.setRotationPoint(-5F, 16F, 2F);
        pillarSide2.setTextureSize(128, 128);
        pillarSide2.mirror = true;
        setRotation(pillarSide2, 0F, 0F, 0F);
        pillarSide3 = new ModelRenderer(this, 17, 0);
        pillarSide3.addBox(0F, 0F, 0F, 1, 6, 2);
        pillarSide3.setRotationPoint(-3F, 16F, -5F);
        pillarSide3.setTextureSize(128, 128);
        pillarSide3.mirror = true;
        setRotation(pillarSide3, 0F, 0F, 0F);
        pillarSide4 = new ModelRenderer(this, 17, 0);
        pillarSide4.addBox(0F, 0F, 0F, 1, 6, 2);
        pillarSide4.setRotationPoint(2F, 16F, 3F);
        pillarSide4.setTextureSize(128, 128);
        pillarSide4.mirror = true;
        setRotation(pillarSide4, 0F, 0F, 0F);
        pillarSide5 = new ModelRenderer(this, 17, 0);
        pillarSide5.addBox(0F, 0F, 0F, 1, 6, 2);
        pillarSide5.setRotationPoint(2F, 16F, -5F);
        pillarSide5.setTextureSize(128, 128);
        pillarSide5.mirror = true;
        setRotation(pillarSide5, 0F, 0F, 0F);
        pillarSide6 = new ModelRenderer(this, 17, 9);
        pillarSide6.addBox(0F, 0F, 0F, 2, 6, 1);
        pillarSide6.setRotationPoint(-5F, 16F, -3F);
        pillarSide6.setTextureSize(128, 128);
        pillarSide6.mirror = true;
        setRotation(pillarSide6, 0F, 0F, 0F);
        pillarSide7 = new ModelRenderer(this, 17, 9);
        pillarSide7.addBox(0F, 0F, 0F, 2, 6, 1);
        pillarSide7.setRotationPoint(3F, 16F, 2F);
        pillarSide7.setTextureSize(128, 128);
        pillarSide7.mirror = true;
        setRotation(pillarSide7, 0F, 0F, 0F);
        pillarSide8 = new ModelRenderer(this, 17, 9);
        pillarSide8.addBox(0F, 0F, 0F, 2, 6, 1);
        pillarSide8.setRotationPoint(3F, 16F, -3F);
        pillarSide8.setTextureSize(128, 128);
        pillarSide8.mirror = true;
        setRotation(pillarSide8, 0F, 0F, 0F);
        base1Bottom = new ModelRenderer(this, 0, 19);
        base1Bottom.addBox(0F, 0F, 0F, 10, 1, 10);
        base1Bottom.setRotationPoint(-5F, 15F, -5F);
        base1Bottom.setTextureSize(128, 128);
        base1Bottom.mirror = true;
        setRotation(base1Bottom, 0F, 0F, 0F);
        centreBottom = new ModelRenderer(this, 0, 31);
        centreBottom.addBox(0F, 0F, 0F, 8, 2, 8);
        centreBottom.setRotationPoint(-4F, 13F, -4F);
        centreBottom.setTextureSize(128, 128);
        centreBottom.mirror = true;
        setRotation(centreBottom, 0F, 0F, 0F);
        base2Bottom = new ModelRenderer(this, 0, 19);
        base2Bottom.addBox(0F, 0F, 0F, 10, 1, 10);
        base2Bottom.setRotationPoint(-5F, 12F, -5F);
        base2Bottom.setTextureSize(128, 128);
        base2Bottom.mirror = true;
        setRotation(base2Bottom, 0F, 0F, 0F);
        side1 = new ModelRenderer(this, 31, 0);
        side1.addBox(0F, 0F, 0F, 2, 16, 1);
        side1.setRotationPoint(3F, -4F, 4.2F);
        side1.setTextureSize(128, 128);
        side1.mirror = true;
        setRotation(side1, 0F, 0.7853982F, 0F);
        side2 = new ModelRenderer(this, 24, 0);
        side2.addBox(0F, 0F, 0F, 1, 16, 2);
        side2.setRotationPoint(3F, -4F, -4.2F);
        side2.setTextureSize(128, 128);
        side2.mirror = true;
        setRotation(side2, 0F, 0.7853982F, 0F);
        side3 = new ModelRenderer(this, 31, 0);
        side3.addBox(0F, 0F, 0F, 2, 16, 1);
        side3.setRotationPoint(-5F, -4F, -3.8F);
        side3.setTextureSize(128, 128);
        side3.mirror = true;
        setRotation(side3, 0F, 0.7853982F, 0F);
        side4 = new ModelRenderer(this, 24, 0);
        side4.addBox(0F, 0F, 0F, 1, 16, 2);
        side4.setRotationPoint(-5F, -4F, 3.8F);
        side4.setTextureSize(128, 128);
        side4.mirror = true;
        setRotation(side4, 0F, 0.7853982F, 0F);
        crystal1 = new ModelRenderer(this, 55, 0);
        crystal1.addBox(-2F, -2F, -2F, 4, 4, 4);
        crystal1.setRotationPoint(0F, 4F, 0F);
        crystal1.setTextureSize(128, 128);
        crystal1.mirror = true;
        setRotation(crystal1, 0F, 0.7853982F, 0.7853982F);
        crystal2 = new ModelRenderer(this, 55, 0);
        crystal2.addBox(-2F, -2F, -2F, 4, 4, 4);
        crystal2.setRotationPoint(0F, 4F, 0F);
        crystal2.setTextureSize(128, 128);
        crystal2.mirror = true;
        setRotation(crystal2, 0.7853982F, 0.7853982F, 0F);
        base1Top = new ModelRenderer(this, 0, 19);
        base1Top.addBox(0F, 0F, 0F, 10, 1, 10);
        base1Top.setRotationPoint(-5F, -5F, -5F);
        base1Top.setTextureSize(128, 128);
        base1Top.mirror = true;
        setRotation(base1Top, 0F, 0F, 0F);
        centreTop = new ModelRenderer(this, 0, 31);
        centreTop.addBox(0F, 0F, 0F, 8, 2, 8);
        centreTop.setRotationPoint(-4F, -7F, -4F);
        centreTop.setTextureSize(128, 128);
        centreTop.mirror = true;
        setRotation(centreTop, 0F, 0F, 0F);
        base2Top = new ModelRenderer(this, 0, 19);
        base2Top.addBox(0F, 0F, 0F, 10, 1, 10);
        base2Top.setRotationPoint(-5F, -8F, -5F);
        base2Top.setTextureSize(128, 128);
        base2Top.mirror = true;
        setRotation(base2Top, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        pillar1.render(f5);
        pillar2.render(f5);
        pillar3.render(f5);
        pillar4.render(f5);
        pillarSide1.render(f5);
        pillarSide2.render(f5);
        pillarSide3.render(f5);
        pillarSide4.render(f5);
        pillarSide5.render(f5);
        pillarSide6.render(f5);
        pillarSide7.render(f5);
        pillarSide8.render(f5);
        base1Bottom.render(f5);
        centreBottom.render(f5);
        base2Bottom.render(f5);
        side1.render(f5);
        side2.render(f5);
        side3.render(f5);
        side4.render(f5);
        base1Top.render(f5);
        centreTop.render(f5);
        base2Top.render(f5);
    }

    public void renderPrism(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        crystal1.render(f5);
        crystal2.render(f5);
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
