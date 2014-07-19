package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMachineAnalysis extends ModelBase
{
    // fields
    ModelRenderer bar1;
    ModelRenderer bar2;
    ModelRenderer panel1;
    ModelRenderer panel2;

    public ModelMachineAnalysis()
    {
        textureWidth = 128;
        textureHeight = 64;

        bar1 = new ModelRenderer(this, 0, 0);
        bar1.addBox(0F, 0F, 0F, 6, 1, 1);
        bar1.setRotationPoint(-3F, 11F, -4F);
        bar1.setTextureSize(128, 64);
        bar1.mirror = true;
        setRotation(bar1, 0F, 0F, 0F);
        bar2 = new ModelRenderer(this, 0, 0);
        bar2.addBox(0F, 0F, 0F, 6, 1, 1);
        bar2.setRotationPoint(-3F, 11F, 3F);
        bar2.setTextureSize(128, 64);
        bar2.mirror = true;
        setRotation(bar2, 0F, 0F, 0F);
        panel1 = new ModelRenderer(this, 0, 4);
        panel1.addBox(0F, 0F, 0F, 3, 1, 8);
        panel1.setRotationPoint(-6F, 11F, -4F);
        panel1.setTextureSize(128, 64);
        panel1.mirror = true;
        setRotation(panel1, 0F, 0F, 0F);
        panel2 = new ModelRenderer(this, 0, 4);
        panel2.addBox(0F, 0F, 0F, 3, 1, 8);
        panel2.setRotationPoint(3F, 11F, -4F);
        panel2.setTextureSize(128, 64);
        panel2.mirror = true;
        setRotation(panel2, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        bar1.render(f5);
        bar2.render(f5);
        panel1.render(f5);
        panel2.render(f5);
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
