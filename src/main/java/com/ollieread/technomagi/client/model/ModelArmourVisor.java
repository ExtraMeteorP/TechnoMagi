package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelArmourVisor extends ModelBiped
{
    // fields
    ModelRenderer back;
    ModelRenderer right;
    ModelRenderer left;
    ModelRenderer right2;
    ModelRenderer left2;
    ModelRenderer visor;

    public ModelArmourVisor(float f)
    {
        super(f, 0, 64, 64);

        textureWidth = 32;
        textureHeight = 32;

        back = new ModelRenderer(this, 10, 0);
        back.addBox(0F, 0F, 0F, 10, 2, 1);
        back.setRotationPoint(-5F, -7F, 4F);
        back.setTextureSize(32, 32);
        back.mirror = true;
        setRotation(back, 0F, 0F, 0F);
        right = new ModelRenderer(this, 0, 0);
        right.addBox(0F, 0F, 0F, 1, 2, 3);
        right.setRotationPoint(-5F, -7F, 1F);
        right.setTextureSize(32, 32);
        right.mirror = true;
        setRotation(right, 0F, 0F, 0F);
        left = new ModelRenderer(this, 0, 0);
        left.addBox(0F, 0F, 0F, 1, 2, 3);
        left.setRotationPoint(4F, -7F, 1F);
        left.setTextureSize(32, 32);
        left.mirror = false;
        setRotation(left, 0F, 0F, 0F);
        right2 = new ModelRenderer(this, 0, 6);
        right2.addBox(0F, 0F, 0F, 1, 2, 2);
        right2.setRotationPoint(-5F, -5F, 1F);
        right2.setTextureSize(32, 32);
        right2.mirror = true;
        setRotation(right2, 0.7853982F, 0F, 0F);
        left2 = new ModelRenderer(this, 0, 6);
        left2.addBox(0F, 0F, 0F, 1, 2, 2);
        left2.setRotationPoint(4F, -5F, 1F);
        left2.setTextureSize(32, 32);
        left2.mirror = false;
        setRotation(left2, 0.7853982F, 0F, 0F);
        visor = new ModelRenderer(this, 0, 11);
        visor.addBox(0F, 0F, 0F, 6, 2, 0);
        visor.setRotationPoint(-3F, -6F, -5F);
        visor.setTextureSize(32, 32);
        visor.mirror = true;
        setRotation(visor, 0F, 0F, 0F);

        bipedHead.addChild(back);
        bipedHead.addChild(right);
        bipedHead.addChild(right2);
        bipedHead.addChild(left);
        bipedHead.addChild(left2);
        bipedHead.addChild(visor);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
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
