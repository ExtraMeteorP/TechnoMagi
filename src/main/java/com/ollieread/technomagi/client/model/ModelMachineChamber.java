package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelMachineChamber extends ModelBase
{
    // fields
    ModelRenderer bottom;
    ModelRenderer top;
    ModelRenderer side1;
    ModelRenderer side2;
    ModelRenderer back;
    ModelRenderer front;
    ModelRenderer details;

    public ModelMachineChamber()
    {
        textureWidth = 128;
        textureHeight = 64;

        bottom = new ModelRenderer(this, 0, 0);
        bottom.addBox(0F, 0F, 0F, 18, 1, 18);
        bottom.setRotationPoint(-9F, 23F, -9F);
        bottom.setTextureSize(128, 64);
        bottom.mirror = true;
        setRotation(bottom, 0F, 0F, 0F);
        top = new ModelRenderer(this, 0, 0);
        top.addBox(0F, 0F, 0F, 18, 3, 18);
        top.setRotationPoint(-9F, -13F, -9F);
        top.setTextureSize(128, 64);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);
        side1 = new ModelRenderer(this, 75, 0);
        side1.addBox(0F, 0F, 0F, 0, 33, 17);
        side1.setRotationPoint(-9F, -10F, -8F);
        side1.setTextureSize(128, 64);
        side1.mirror = true;
        setRotation(side1, 0F, 0F, 0F);
        side2 = new ModelRenderer(this, 75, 0);
        side2.addBox(0F, 0F, 0F, 0, 33, 17);
        side2.setRotationPoint(9F, -10F, -8F);
        side2.setTextureSize(128, 64);
        side2.mirror = true;
        setRotation(side2, 0F, 0F, 0F);
        back = new ModelRenderer(this, 36, 25);
        back.addBox(0F, 0F, 0F, 18, 34, 1);
        back.setRotationPoint(-9F, -10F, -9F);
        back.setTextureSize(128, 64);
        back.mirror = true;
        setRotation(back, 0F, 0F, 0F);
        front = new ModelRenderer(this, 0, 25);
        front.addBox(0F, 0F, 0F, 18, 33, 0);
        front.setRotationPoint(-9F, -10F, 9F);
        front.setTextureSize(128, 64);
        front.mirror = true;
        setRotation(front, 0F, 0F, 0F);
        details = new ModelRenderer(this, 81, 51);
        details.addBox(0F, 0F, 0F, 8, 4, 1);
        details.setRotationPoint(-4F, 4F, 9F);
        details.setTextureSize(128, 64);
        details.mirror = true;
        setRotation(details, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        bottom.render(f5);
        top.render(f5);
        back.render(f5);
        details.render(f5);

        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);

        side1.render(f5);
        side2.render(f5);
        front.render(f5);

        GL11.glDisable(GL11.GL_DITHER);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
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
