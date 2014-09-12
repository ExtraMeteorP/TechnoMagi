package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ModelTechnomageStaff extends ModelBase
{
    // fields
    ModelRenderer staff1;
    ModelRenderer ring1;
    ModelRenderer ring2;
    ModelRenderer ring3;
    ModelRenderer ring4;
    ModelRenderer focus1;
    ModelRenderer focus2;
    ModelRenderer support1;
    ModelRenderer support2;
    ModelRenderer support3;
    ModelRenderer support4;
    ModelRenderer support5;
    ModelRenderer shield1;
    ModelRenderer shield2;
    ModelRenderer support6;
    ModelRenderer support7;
    ModelRenderer support8;
    ModelRenderer support9;
    ModelRenderer support10;
    ModelRenderer shield3;
    ModelRenderer shield4;
    ModelRenderer staff2;

    public ModelTechnomageStaff()
    {
        textureWidth = 128;
        textureHeight = 128;

        staff1 = new ModelRenderer(this, 0, 0);
        staff1.addBox(0F, -40F, 0F, 4, 14, 4);
        staff1.setRotationPoint(0F, 0F, 0F);
        staff1.setTextureSize(128, 128);
        staff1.mirror = true;
        setRotation(staff1, 0F, 0F, 0F);
        staff2 = new ModelRenderer(this, 0, 18);
        staff2.addBox(0.5F, 0F, 0.5F, 3, 50, 3);
        staff2.setRotationPoint(0F, -26F, 0F);
        staff2.setTextureSize(128, 128);
        staff2.mirror = true;
        setRotation(staff2, 0F, 0F, 0F);
        ring1 = new ModelRenderer(this, 16, 0);
        ring1.addBox(0F, 0F, 0F, 5, 1, 5);
        ring1.setRotationPoint(-0.5F, -39F, -0.5F);
        ring1.setTextureSize(128, 128);
        ring1.mirror = true;
        setRotation(ring1, 0F, 0F, 0F);
        ring2 = new ModelRenderer(this, 16, 0);
        ring2.addBox(-0.5F, 0F, -0.5F, 5, 1, 5);
        ring2.setRotationPoint(0F, -37F, 0F);
        ring2.setTextureSize(128, 128);
        ring2.mirror = true;
        setRotation(ring2, 0F, 0F, 0F);
        ring3 = new ModelRenderer(this, 16, 0);
        ring3.addBox(-0.5F, 0F, -0.5F, 5, 1, 5);
        ring3.setRotationPoint(0F, -35F, 0F);
        ring3.setTextureSize(128, 128);
        ring3.mirror = true;
        setRotation(ring3, 0F, 0F, 0F);
        ring4 = new ModelRenderer(this, 16, 0);
        ring4.addBox(-0.5F, 0F, -0.5F, 5, 1, 5);
        ring4.setRotationPoint(0F, -33F, 0F);
        ring4.setTextureSize(128, 128);
        ring4.mirror = true;
        setRotation(ring4, 0F, 0F, 0F);
        focus1 = new ModelRenderer(this, 16, 6);
        focus1.addBox(0F, 0F, 0F, 4, 8, 4);
        focus1.setRotationPoint(0F, -52F, 0F);
        focus1.setTextureSize(128, 128);
        focus1.mirror = true;
        setRotation(focus1, 0F, 0F, 0F);
        focus2 = new ModelRenderer(this, 16, 18);
        focus2.addBox(0F, 0F, 0F, 4, 10, 4);
        focus2.setRotationPoint(-1F, -53F, 2F);
        focus2.setTextureSize(128, 128);
        focus2.mirror = true;
        setRotation(focus2, 0F, 0.7853982F, 0F);
        support1 = new ModelRenderer(this, 36, 0);
        support1.addBox(0F, 0F, 0.5F, 1, 10, 1);
        support1.setRotationPoint(-4F, -47F, 1F);
        support1.setTextureSize(128, 128);
        support1.mirror = true;
        setRotation(support1, 0F, 0F, -0.1570796F);
        support2 = new ModelRenderer(this, 40, 0);
        support2.addBox(0F, 0F, 0.5F, 1, 5, 1);
        support2.setRotationPoint(-4F, -52F, 1F);
        support2.setTextureSize(128, 128);
        support2.mirror = true;
        setRotation(support2, 0F, 0F, 0F);
        support3 = new ModelRenderer(this, 44, 0);
        support3.addBox(0F, 0F, 0.5F, 10, 1, 1);
        support3.setRotationPoint(-3F, -38F, 1F);
        support3.setTextureSize(128, 128);
        support3.mirror = true;
        setRotation(support3, 0F, 0F, 0F);
        support4 = new ModelRenderer(this, 36, 0);
        support4.addBox(0F, 0.8F, 0.5F, 1, 10, 1);
        support4.setRotationPoint(7F, -48F, 1F);
        support4.setTextureSize(128, 128);
        support4.mirror = true;
        setRotation(support4, 0F, 0F, 0.1570796F);
        support5 = new ModelRenderer(this, 40, 0);
        support5.addBox(-0.1F, 0F, 0.5F, 1, 5, 1);
        support5.setRotationPoint(7F, -52F, 1F);
        support5.setTextureSize(128, 128);
        support5.mirror = true;
        setRotation(support5, 0F, 0F, 0F);
        support6 = new ModelRenderer(this, 44, 0);
        support6.addBox(-0.5F, 0F, 0F, 1, 1, 10);
        support6.setRotationPoint(2F, -38F, -3F);
        support6.setTextureSize(128, 128);
        support6.mirror = true;
        setRotation(support6, 0F, 0F, 0F);
        support7 = new ModelRenderer(this, 36, 0);
        support7.addBox(-0.5F, 0.8F, 0F, 1, 10, 1);
        support7.setRotationPoint(2F, -48F, 7F);
        support7.setTextureSize(128, 128);
        support7.mirror = true;
        setRotation(support7, -0.1570796F, 0F, 0F);
        support8 = new ModelRenderer(this, 36, 0);
        support8.addBox(-0.5F, 0F, 0F, 1, 10, 1);
        support8.setRotationPoint(2F, -47F, -4F);
        support8.setTextureSize(128, 128);
        support8.mirror = true;
        setRotation(support8, 0.1570796F, 0F, 0F);
        support9 = new ModelRenderer(this, 40, 0);
        support9.addBox(0.5F, 0F, 0F, 1, 5, 1);
        support9.setRotationPoint(1F, -52F, -4F);
        support9.setTextureSize(128, 128);
        support9.mirror = true;
        setRotation(support9, 0F, 0F, 0F);
        support10 = new ModelRenderer(this, 40, 0);
        support10.addBox(-0.5F, 0F, -0.1F, 1, 5, 1);
        support10.setRotationPoint(2F, -52F, 7F);
        support10.setTextureSize(128, 128);
        support10.mirror = true;
        setRotation(support10, 0F, 0F, 0F);
        shield1 = new ModelRenderer(this, 32, 6);
        shield1.addBox(0F, 0F, 0F, 2, 10, 0);
        shield1.setRotationPoint(-7F, -57F, 2F);
        shield1.setTextureSize(128, 128);
        shield1.mirror = true;
        setRotation(shield1, 0F, 0F, 0F);
        shield2 = new ModelRenderer(this, 32, 6);
        shield2.addBox(0F, 0F, 0F, 2, 10, 0);
        shield2.setRotationPoint(9F, -57F, 2F);
        shield2.setTextureSize(128, 128);
        shield2.mirror = true;
        setRotation(shield2, 0F, 0F, 0F);
        shield3 = new ModelRenderer(this, 32, 16);
        shield3.addBox(0F, 0F, 0F, 0, 10, 2);
        shield3.setRotationPoint(2F, -57F, 9F);
        shield3.setTextureSize(128, 128);
        shield3.mirror = true;
        setRotation(shield3, 0F, 0F, 0F);
        shield4 = new ModelRenderer(this, 32, 16);
        shield4.addBox(0F, 0F, 0F, 0, 10, 2);
        shield4.setRotationPoint(2F, -57F, -7F);
        shield4.setTextureSize(128, 128);
        shield4.mirror = true;
        setRotation(shield4, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, ItemStack staff)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        if (staff.getItemDamage() == 1) {
            staff1.render(f5);
            ring1.render(f5);
            ring2.render(f5);
            ring3.render(f5);
            ring4.render(f5);
            focus1.render(f5);
            focus2.render(f5);
            support1.render(f5);
            support2.render(f5);
            support3.render(f5);
            support4.render(f5);
            support5.render(f5);
            support6.render(f5);
            support7.render(f5);
            support8.render(f5);
            support9.render(f5);
            support10.render(f5);
            shield1.render(f5);
            shield2.render(f5);
            shield3.render(f5);
            shield4.render(f5);
        }

        staff2.render(f5);
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
