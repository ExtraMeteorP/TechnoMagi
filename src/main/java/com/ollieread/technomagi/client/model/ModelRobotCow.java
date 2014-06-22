package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import com.ollieread.technomagi.entity.passive.EntityRobotCow;

public class ModelRobotCow extends ModelQuadruped
{
    // fields
    ModelRenderer face;
    ModelRenderer udders;
    ModelRenderer edge11;
    ModelRenderer edge21;
    ModelRenderer edge31;
    ModelRenderer edge41;
    ModelRenderer edge12;
    ModelRenderer edge22;
    ModelRenderer edge32;
    ModelRenderer edge42;
    ModelRenderer edge13;
    ModelRenderer edge23;
    ModelRenderer edge33;
    ModelRenderer edge43;
    ModelRenderer top;
    ModelRenderer side1;
    ModelRenderer side2;

    public ModelRobotCow()
    {
        super(12, 0.0F);

        textureWidth = 128;
        textureHeight = 64;

        this.setTextureOffset("head.head", 0, 0);
        this.setTextureOffset("head.face", 85, 36);

        head = new ModelRenderer(this, "head");
        head.addBox("head", -4F, -4F, -6F, 6, 6, 5);
        head.addBox("face", -5F, -4F, -7F, 8, 8, 1);
        head.setRotationPoint(1F, 4F, -7F);
        head.mirror = true;
        setRotation(head, 0F, 0F, 0F);
        body = new ModelRenderer(this, 19, 5);
        body.addBox(-6F, -10F, -7F, 10, 18, 9);
        body.setRotationPoint(1F, 5F, 2F);
        body.setTextureSize(128, 64);
        body.mirror = true;
        setRotation(body, 1.570796F, 0F, 0F);
        leg1 = new ModelRenderer(this, 0, 16);
        leg1.addBox(-3F, 0F, -2F, 4, 12, 4);
        leg1.setRotationPoint(-3F, 12F, 7F);
        leg1.setTextureSize(128, 64);
        leg1.mirror = true;
        setRotation(leg1, 0F, 0F, 0F);
        leg2 = new ModelRenderer(this, 0, 16);
        leg2.addBox(-1F, 0F, -2F, 4, 12, 4);
        leg2.setRotationPoint(3F, 12F, 7F);
        leg2.setTextureSize(128, 64);
        leg2.mirror = true;
        setRotation(leg2, 0F, 0F, 0F);
        leg3 = new ModelRenderer(this, 0, 16);
        leg3.addBox(-3F, 0F, -3F, 4, 12, 4);
        leg3.setRotationPoint(-3F, 12F, -5F);
        leg3.setTextureSize(128, 64);
        leg3.mirror = true;
        setRotation(leg3, 0F, 0F, 0F);
        leg4 = new ModelRenderer(this, 0, 16);
        leg4.addBox(-1F, 0F, -3F, 4, 12, 4);
        leg4.setRotationPoint(3F, 12F, -4F);
        leg4.setTextureSize(128, 64);
        leg4.mirror = true;
        setRotation(leg4, 0F, 0F, 0F);
        udders = new ModelRenderer(this, 52, 0);
        udders.addBox(-2F, -3F, 0F, 4, 6, 2);
        udders.setRotationPoint(0F, 14F, 6F);
        udders.setTextureSize(128, 64);
        udders.mirror = true;
        setRotation(udders, 1.570796F, 0F, 0F);
        edge11 = new ModelRenderer(this, 66, 1);
        edge11.addBox(0F, 0F, 0F, 1, 1, 18);
        edge11.setRotationPoint(4F, 2F, -8F);
        edge11.setTextureSize(128, 64);
        edge11.mirror = true;
        setRotation(edge11, 0F, 0F, 0F);
        edge21 = new ModelRenderer(this, 66, 1);
        edge21.addBox(0F, 0F, 0F, 1, 1, 18);
        edge21.setRotationPoint(-5F, 2F, -8F);
        edge21.setTextureSize(128, 64);
        edge21.mirror = true;
        setRotation(edge21, 0F, 0F, 0F);
        edge31 = new ModelRenderer(this, 29, 0);
        edge31.addBox(0F, 0F, 0F, 8, 1, 1);
        edge31.setRotationPoint(-4F, 2F, -8F);
        edge31.setTextureSize(128, 64);
        edge31.mirror = true;
        setRotation(edge31, 0F, 0F, 0F);
        edge41 = new ModelRenderer(this, 29, 0);
        edge41.addBox(0F, 0F, 0F, 8, 1, 1);
        edge41.setRotationPoint(-4F, 2F, 9F);
        edge41.setTextureSize(128, 64);
        edge41.mirror = true;
        setRotation(edge41, 0F, 0F, 0F);
        edge12 = new ModelRenderer(this, 58, 10);
        edge12.addBox(0F, 0F, 0F, 1, 7, 1);
        edge12.setRotationPoint(-6F, 4F, -8F);
        edge12.setTextureSize(128, 64);
        edge12.mirror = true;
        setRotation(edge12, 0F, 0F, 0F);
        edge22 = new ModelRenderer(this, 58, 10);
        edge22.addBox(0F, 0F, 0F, 1, 7, 1);
        edge22.setRotationPoint(-6F, 4F, 9F);
        edge22.setTextureSize(128, 64);
        edge22.mirror = true;
        setRotation(edge22, 0F, 0F, 0F);
        edge32 = new ModelRenderer(this, 66, 1);
        edge32.addBox(0F, 0F, 0F, 1, 1, 18);
        edge32.setRotationPoint(-6F, 3F, -8F);
        edge32.setTextureSize(128, 64);
        edge32.mirror = true;
        setRotation(edge32, 0F, 0F, 0F);
        edge42 = new ModelRenderer(this, 66, 1);
        edge42.addBox(0F, 0F, 0F, 1, 1, 18);
        edge42.setRotationPoint(-6F, 11F, -8F);
        edge42.setTextureSize(128, 64);
        edge42.mirror = true;
        setRotation(edge42, 0F, 0F, 0F);
        edge13 = new ModelRenderer(this, 58, 10);
        edge13.addBox(0F, 0F, 0F, 1, 7, 1);
        edge13.setRotationPoint(5F, 4F, -8F);
        edge13.setTextureSize(128, 64);
        edge13.mirror = true;
        setRotation(edge13, 0F, 0F, 0F);
        edge23 = new ModelRenderer(this, 58, 10);
        edge23.addBox(0F, 0F, 0F, 1, 7, 1);
        edge23.setRotationPoint(5F, 4F, 9F);
        edge23.setTextureSize(128, 64);
        edge23.mirror = true;
        setRotation(edge23, 0F, 0F, 0F);
        edge33 = new ModelRenderer(this, 66, 1);
        edge33.addBox(0F, 0F, 0F, 1, 1, 18);
        edge33.setRotationPoint(5F, 3F, -8F);
        edge33.setTextureSize(128, 64);
        edge33.mirror = true;
        setRotation(edge33, 0F, 0F, 0F);
        edge43 = new ModelRenderer(this, 66, 1);
        edge43.addBox(0F, 0F, 0F, 1, 1, 18);
        edge43.setRotationPoint(5F, 11F, -8F);
        edge43.setTextureSize(128, 64);
        edge43.mirror = true;
        setRotation(edge43, 0F, 0F, 0F);
        top = new ModelRenderer(this, 36, 34);
        top.addBox(0F, 0F, 0F, 8, 1, 16);
        top.setRotationPoint(-4F, 2F, -7F);
        top.setTextureSize(128, 64);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);
        side1 = new ModelRenderer(this, 0, 34);
        side1.addBox(0F, 0F, 0F, 1, 7, 16);
        side1.setRotationPoint(-6F, 4F, -7F);
        side1.setTextureSize(128, 64);
        side1.mirror = true;
        setRotation(side1, 0F, 0F, 0F);
        side2 = new ModelRenderer(this, 0, 34);
        side2.addBox(0F, 0F, 0F, 1, 7, 16);
        side2.setRotationPoint(5F, 4F, -7F);
        side2.setTextureSize(128, 64);
        side2.mirror = true;
        setRotation(side2, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        EntityRobotCow cow = (EntityRobotCow) entity;

        int milk = cow.getMilkStatus();
        int leather = cow.getLeatherStatus();
        int steak = cow.getSteakStatus();

        edge11.render(f5);
        edge21.render(f5);
        edge31.render(f5);
        edge41.render(f5);
        edge12.render(f5);
        edge22.render(f5);
        edge32.render(f5);
        edge42.render(f5);
        edge13.render(f5);
        edge23.render(f5);
        edge33.render(f5);
        edge43.render(f5);

        udders.render(f5);
        top.render(f5);
        side1.render(f5);
        side2.render(f5);
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
