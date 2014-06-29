package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMachineChamber extends ModelBase
{
    // fields
    private ModelRenderer base;
    private ModelRenderer top;
    private ModelRenderer back;
    private ModelRenderer panel;
    private ModelRenderer leftSide;
    private ModelRenderer rightSide;
    private ModelRenderer lside1;
    private ModelRenderer lside2;
    private ModelRenderer lside3;
    private ModelRenderer lside4;
    private ModelRenderer lside5;
    private ModelRenderer lside6;
    private ModelRenderer lside7;
    private ModelRenderer lside8;
    private ModelRenderer lside9;
    private ModelRenderer rside1;
    private ModelRenderer rside2;
    private ModelRenderer rside3;
    private ModelRenderer rside4;
    private ModelRenderer rside5;
    private ModelRenderer rside6;
    private ModelRenderer rside7;
    private ModelRenderer rside8;
    private ModelRenderer rside9;

    public ModelMachineChamber()
    {
        textureWidth = 128;
        textureHeight = 128;

        base = new ModelRenderer(this, 0, 61);
        base.addBox(0F, 0F, 0F, 16, 1, 16);
        base.setRotationPoint(-8F, 23F, -8F);
        base.setTextureSize(128, 128);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        top = new ModelRenderer(this, 0, 61);
        top.addBox(0F, 0F, 0F, 16, 3, 16);
        top.setRotationPoint(-8F, -24F, -8F);
        top.setTextureSize(128, 128);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);
        back = new ModelRenderer(this, 94, 0);
        back.addBox(0F, 0F, 0F, 1, 44, 16);
        back.setRotationPoint(-8F, -21F, -8F);
        back.setTextureSize(128, 128);
        back.mirror = true;
        setRotation(back, 0F, 0F, 0F);

        panel = new ModelRenderer(this, 79, 24);
        panel.addBox(0F, 0F, 0F, 1, 3, 6);
        panel.setRotationPoint(7F, 4F, -3F);
        panel.setTextureSize(128, 128);
        panel.mirror = true;
        setRotation(panel, 0F, 0F, 0F);

        lside1 = new ModelRenderer(this, 69, 0);
        lside1.addBox(0F, 0F, 0F, 11, 10, 1);
        lside1.setRotationPoint(-7F, 13F, -8F);
        lside1.setTextureSize(128, 128);
        lside1.mirror = true;
        setRotation(lside1, 0F, 0F, 0F);
        lside2 = new ModelRenderer(this, 83, 12);
        lside2.addBox(0F, 0F, 0F, 4, 7, 1);
        lside2.setRotationPoint(-7F, 6F, -8F);
        lside2.setTextureSize(128, 128);
        lside2.mirror = true;
        setRotation(lside2, 0F, 0F, 0F);
        lside3 = new ModelRenderer(this, 46, 0);
        lside3.addBox(0F, 0F, 0F, 10, 7, 1);
        lside3.setRotationPoint(-3F, 6F, -8F);
        lside3.setTextureSize(128, 128);
        lside3.mirror = true;
        setRotation(lside3, 0F, 0F, 0.7853982F);
        lside4 = new ModelRenderer(this, 70, 12);
        lside4.addBox(0F, 0F, 0F, 5, 10, 1);
        lside4.setRotationPoint(4F, 13F, -8F);
        lside4.setTextureSize(128, 128);
        lside4.mirror = true;
        setRotation(lside4, 0F, -0.6517617F, 0F);
        lside5 = new ModelRenderer(this, 64, 9);
        lside5.addBox(0F, 0F, 0F, 1, 10, 1);
        lside5.setRotationPoint(7F, 13F, -5F);
        lside5.setTextureSize(128, 128);
        lside5.mirror = true;
        setRotation(lside5, 0F, 0F, 0F);
        lside6 = new ModelRenderer(this, 23, 0);
        lside6.addBox(0F, 0F, 0F, 11, 27, 0);
        lside6.setRotationPoint(-7F, -21F, -7F);
        lside6.setTextureSize(128, 128);
        lside6.mirror = true;
        setRotation(lside6, 0F, 0F, 0F);
        lside7 = new ModelRenderer(this, 31, 28);
        lside7.addBox(0F, 0F, 0F, 7, 7, 0);
        lside7.setRotationPoint(-3F, 6F, -7F);
        lside7.setTextureSize(128, 128);
        lside7.mirror = true;
        setRotation(lside7, 0F, 0F, 0F);
        lside8 = new ModelRenderer(this, 14, 0);
        lside8.addBox(0F, 0F, 0F, 4, 34, 0);
        lside8.setRotationPoint(4F, -21F, -7F);
        lside8.setTextureSize(128, 128);
        lside8.mirror = true;
        setRotation(lside8, 0F, -0.655249F, 0F);
        lside9 = new ModelRenderer(this, 0, 0);
        lside9.addBox(0F, 0F, 0F, 0, 44, 5);
        lside9.setRotationPoint(7F, -21F, -5F);
        lside9.setTextureSize(128, 128);
        lside9.mirror = true;
        setRotation(lside9, 0F, 0F, 0F);
        rside1 = new ModelRenderer(this, 69, 0);
        rside1.addBox(0F, 0F, 0F, 11, 10, 1);
        rside1.setRotationPoint(-7F, 13F, 7F);
        rside1.setTextureSize(128, 128);
        rside1.mirror = true;
        setRotation(rside1, 0F, 0F, 0F);
        rside2 = new ModelRenderer(this, 83, 12);
        rside2.addBox(0F, 0F, 0F, 4, 7, 1);
        rside2.setRotationPoint(-7F, 6F, 7F);
        rside2.setTextureSize(128, 128);
        rside2.mirror = true;
        setRotation(rside2, 0F, 0F, 0F);
        rside3 = new ModelRenderer(this, 46, 0);
        rside3.addBox(0F, 0F, 0F, 10, 7, 1);
        rside3.setRotationPoint(-3F, 6F, 7F);
        rside3.setTextureSize(128, 128);
        rside3.mirror = true;
        setRotation(rside3, 0F, 0F, 0.7853982F);
        rside4 = new ModelRenderer(this, 70, 12);
        rside4.addBox(0F, 0F, 0F, 5, 10, 1);
        rside4.setRotationPoint(3.4F, 13F, 7.2F);
        rside4.setTextureSize(128, 128);
        rside4.mirror = true;
        setRotation(rside4, 0F, 0.6517583F, 0F);
        rside5 = new ModelRenderer(this, 64, 9);
        rside5.addBox(0F, 0F, 0F, 1, 10, 1);
        rside5.setRotationPoint(7F, 13F, 4F);
        rside5.setTextureSize(128, 128);
        rside5.mirror = true;
        setRotation(rside5, 0F, 0F, 0F);
        rside6 = new ModelRenderer(this, 23, 0);
        rside6.addBox(0F, 0F, 0F, 11, 27, 0);
        rside6.setRotationPoint(-7F, -21F, 7F);
        rside6.setTextureSize(128, 128);
        rside6.mirror = true;
        setRotation(rside6, 0F, 0F, 0F);
        rside7 = new ModelRenderer(this, 31, 28);
        rside7.addBox(0F, 0F, 0F, 7, 7, 0);
        rside7.setRotationPoint(-3F, 6F, 7F);
        rside7.setTextureSize(128, 128);
        rside7.mirror = true;
        setRotation(rside7, 0F, 0F, 0F);
        rside8 = new ModelRenderer(this, 14, 0);
        rside8.addBox(0F, 0F, 0F, 4, 34, 0);
        rside8.setRotationPoint(4F, -21F, 7.2F);
        rside8.setTextureSize(128, 128);
        rside8.mirror = true;
        setRotation(rside8, 0F, 0.655249F, 0F);
        rside9 = new ModelRenderer(this, 49, 12);
        rside9.addBox(0F, 0F, 0F, 0, 44, 5);
        rside9.setRotationPoint(7F, -21F, 0F);
        rside9.setTextureSize(128, 128);
        rside9.mirror = true;
        setRotation(rside9, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        base.render(f5);
        top.render(f5);
        back.render(f5);
        panel.render(f5);

        lside1.render(f5);
        lside2.render(f5);
        lside3.render(f5);
        lside4.render(f5);
        lside5.render(f5);
        lside6.render(f5);
        lside7.render(f5);
        lside8.render(f5);
        lside9.render(f5);
        rside1.render(f5);
        rside2.render(f5);
        rside3.render(f5);
        rside4.render(f5);
        rside5.render(f5);
        rside6.render(f5);
        rside7.render(f5);
        rside8.render(f5);
        rside9.render(f5);
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
