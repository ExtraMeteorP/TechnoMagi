package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBattery extends ModelBase
{
    // fields
    ModelRenderer main1;
    ModelRenderer main2;
    ModelRenderer main3;
    ModelRenderer main4;
    ModelRenderer side1;
    ModelRenderer side2;
    ModelRenderer side3;
    ModelRenderer side4;
    ModelRenderer side5;
    ModelRenderer side6;
    ModelRenderer side7;
    ModelRenderer side8;
    ModelRenderer conduit1;
    ModelRenderer conduit3;
    ModelRenderer conduit4;
    ModelRenderer conduit5;
    ModelRenderer conduit6;
    ModelRenderer conduit7;
    ModelRenderer conduit8;
    ModelRenderer conduit9;
    ModelRenderer conduit10;
    ModelRenderer conduit11;
    ModelRenderer conduit12;
    ModelRenderer conduit13;

    public ModelBattery()
    {
        textureWidth = 64;
        textureHeight = 64;

        main1 = new ModelRenderer(this, 0, 0);
        main1.addBox(0F, 0F, 0F, 6, 14, 6);
        main1.setRotationPoint(-7F, 9F, -3F);
        main1.setTextureSize(64, 64);
        main1.mirror = true;
        setRotation(main1, 0F, 0F, 0F);
        main2 = new ModelRenderer(this, 0, 0);
        main2.addBox(0F, 0F, 0F, 6, 14, 6);
        main2.setRotationPoint(1F, 9F, -3F);
        main2.setTextureSize(64, 64);
        main2.mirror = true;
        setRotation(main2, 0F, 0F, 0F);
        main3 = new ModelRenderer(this, 0, 0);
        main3.addBox(0F, 0F, 0F, 6, 14, 6);
        main3.setRotationPoint(-3F, 9F, 1F);
        main3.setTextureSize(64, 64);
        main3.mirror = true;
        setRotation(main3, 0F, 0F, 0F);
        main4 = new ModelRenderer(this, 0, 0);
        main4.addBox(0F, 0F, 0F, 6, 14, 6);
        main4.setRotationPoint(-3F, 9F, -7F);
        main4.setTextureSize(64, 64);
        main4.mirror = true;
        setRotation(main4, 0F, 0F, 0F);
        side1 = new ModelRenderer(this, 0, 21);
        side1.addBox(0F, 0F, 0F, 7, 1, 15);
        side1.setRotationPoint(-3.5F, 8F, -7.5F);
        side1.setTextureSize(64, 64);
        side1.mirror = true;
        setRotation(side1, 0F, 0F, 0F);
        side2 = new ModelRenderer(this, 0, 38);
        side2.addBox(0F, 0F, 0F, 15, 1, 7);
        side2.setRotationPoint(-7.5F, 8F, -3.5F);
        side2.setTextureSize(64, 64);
        side2.mirror = true;
        setRotation(side2, 0F, 0F, 0F);
        side3 = new ModelRenderer(this, 0, 21);
        side3.addBox(0F, 0F, 0F, 7, 1, 15);
        side3.setRotationPoint(-3.5F, 23F, -7.5F);
        side3.setTextureSize(64, 64);
        side3.mirror = true;
        setRotation(side3, 0F, 0F, 0F);
        side4 = new ModelRenderer(this, 0, 38);
        side4.addBox(0F, 0F, 0F, 15, 1, 7);
        side4.setRotationPoint(-7.5F, 23F, -3.5F);
        side4.setTextureSize(64, 64);
        side4.mirror = true;
        setRotation(side4, 0F, 0F, 0F);
        side5 = new ModelRenderer(this, 48, 0);
        side5.addBox(0F, 0F, 0F, 1, 16, 7);
        side5.setRotationPoint(7F, 8F, -3.5F);
        side5.setTextureSize(64, 64);
        side5.mirror = true;
        setRotation(side5, 0F, 0F, 0F);
        side6 = new ModelRenderer(this, 31, 0);
        side6.addBox(0F, 0F, 0F, 7, 16, 1);
        side6.setRotationPoint(-3.5F, 8F, 7F);
        side6.setTextureSize(64, 64);
        side6.mirror = true;
        setRotation(side6, 0F, 0F, 0F);
        side7 = new ModelRenderer(this, 48, 0);
        side7.addBox(0F, 0F, 0F, 1, 16, 7);
        side7.setRotationPoint(-8F, 8F, -3.5F);
        side7.setTextureSize(64, 64);
        side7.mirror = true;
        setRotation(side7, 0F, 0F, 0F);
        side8 = new ModelRenderer(this, 31, 0);
        side8.addBox(0F, 0F, 0F, 7, 16, 1);
        side8.setRotationPoint(-3.5F, 8F, -8F);
        side8.setTextureSize(64, 64);
        side8.mirror = true;
        setRotation(side8, 0F, 0F, 0F);
        conduit1 = new ModelRenderer(this, 45, 24);
        conduit1.addBox(0F, 0F, 0F, 6, 2, 1);
        conduit1.setRotationPoint(-6F, 11F, 2F);
        conduit1.setTextureSize(64, 64);
        conduit1.mirror = true;
        setRotation(conduit1, 0F, -0.7853982F, 0F);
        conduit3 = new ModelRenderer(this, 45, 24);
        conduit3.addBox(0F, 0F, 0F, 6, 2, 1);
        conduit3.setRotationPoint(-6F, 19F, 2F);
        conduit3.setTextureSize(64, 64);
        conduit3.mirror = true;
        setRotation(conduit3, 0F, -0.7853982F, 0F);
        conduit4 = new ModelRenderer(this, 45, 24);
        conduit4.addBox(0F, 0F, 0F, 6, 2, 1);
        conduit4.setRotationPoint(-6F, 15F, 2F);
        conduit4.setTextureSize(64, 64);
        conduit4.mirror = true;
        setRotation(conduit4, 0F, -0.7853982F, 0F);
        conduit5 = new ModelRenderer(this, 45, 28);
        conduit5.addBox(0F, 0F, 0F, 1, 2, 6);
        conduit5.setRotationPoint(-2.8F, 11F, -6.8F);
        conduit5.setTextureSize(64, 64);
        conduit5.mirror = true;
        setRotation(conduit5, 0F, -0.7853982F, 0F);
        conduit6 = new ModelRenderer(this, 45, 28);
        conduit6.addBox(0F, 0F, 0F, 1, 2, 6);
        conduit6.setRotationPoint(-2.8F, 15F, -6.8F);
        conduit6.setTextureSize(64, 64);
        conduit6.mirror = true;
        setRotation(conduit6, 0F, -0.7853982F, 0F);
        conduit7 = new ModelRenderer(this, 45, 28);
        conduit7.addBox(0F, 0F, 0F, 1, 2, 6);
        conduit7.setRotationPoint(-2.8F, 19F, -6.8F);
        conduit7.setTextureSize(64, 64);
        conduit7.mirror = true;
        setRotation(conduit7, 0F, -0.7853982F, 0F);
        conduit8 = new ModelRenderer(this, 45, 24);
        conduit8.addBox(0F, 0F, 0F, 6, 2, 1);
        conduit8.setRotationPoint(2.7F, 11F, -6.7F);
        conduit8.setTextureSize(64, 64);
        conduit8.mirror = true;
        setRotation(conduit8, 0F, -0.7853982F, 0F);
        conduit9 = new ModelRenderer(this, 45, 24);
        conduit9.addBox(0F, 0F, 0F, 6, 2, 1);
        conduit9.setRotationPoint(2.7F, 15F, -6.7F);
        conduit9.setTextureSize(64, 64);
        conduit9.mirror = true;
        setRotation(conduit9, 0F, -0.7853982F, 0F);
        conduit10 = new ModelRenderer(this, 45, 24);
        conduit10.addBox(0F, 0F, 0F, 6, 2, 1);
        conduit10.setRotationPoint(2.7F, 19F, -6.7F);
        conduit10.setTextureSize(64, 64);
        conduit10.mirror = true;
        setRotation(conduit10, 0F, -0.7853982F, 0F);
        conduit11 = new ModelRenderer(this, 45, 28);
        conduit11.addBox(0F, 0F, 0F, 1, 2, 6);
        conduit11.setRotationPoint(6F, 11F, 2F);
        conduit11.setTextureSize(64, 64);
        conduit11.mirror = true;
        setRotation(conduit11, 0F, -0.7853982F, 0F);
        conduit12 = new ModelRenderer(this, 45, 28);
        conduit12.addBox(0F, 0F, 0F, 1, 2, 6);
        conduit12.setRotationPoint(6F, 15F, 2F);
        conduit12.setTextureSize(64, 64);
        conduit12.mirror = true;
        setRotation(conduit12, 0F, -0.7853982F, 0F);
        conduit13 = new ModelRenderer(this, 45, 28);
        conduit13.addBox(0F, 0F, 0F, 1, 2, 6);
        conduit13.setRotationPoint(6F, 19F, 2F);
        conduit13.setTextureSize(64, 64);
        conduit13.mirror = true;
        setRotation(conduit13, 0F, -0.7853982F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        main1.render(f5);
        main2.render(f5);
        main3.render(f5);
        main4.render(f5);
        side1.render(f5);
        side2.render(f5);
        side3.render(f5);
        side4.render(f5);
        side5.render(f5);
        side6.render(f5);
        side7.render(f5);
        side8.render(f5);
        conduit1.render(f5);
        conduit3.render(f5);
        conduit4.render(f5);
        conduit5.render(f5);
        conduit6.render(f5);
        conduit7.render(f5);
        conduit8.render(f5);
        conduit9.render(f5);
        conduit10.render(f5);
        conduit11.render(f5);
        conduit12.render(f5);
        conduit13.render(f5);
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
