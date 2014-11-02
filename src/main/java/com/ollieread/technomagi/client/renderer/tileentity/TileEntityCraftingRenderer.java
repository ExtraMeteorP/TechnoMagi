package com.ollieread.technomagi.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.model.ModelMachineConstruct;
import com.ollieread.technomagi.client.model.ModelMachineCrafting;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.TileEntityCrafting;

public class TileEntityCraftingRenderer extends TileEntitySpecialRenderer
{

    private final ModelMachineConstruct construct;
    private final ModelMachineCrafting crafting;

    public TileEntityCraftingRenderer()
    {
        construct = new ModelMachineConstruct();
        crafting = new ModelMachineCrafting();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        TileEntityCrafting machine = (TileEntityCrafting) te;

        int side = machine.getBlockMetadata();
        Tessellator tessellator = Tessellator.instance;
        // This will make your block brightness dependent from surroundings
        // lighting.
        float f = Blocks.blockArchive.getLightValue(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
        int l = te.getWorldObj().getLightBrightnessForSkyBlocks(te.xCoord, te.yCoord, te.zCoord, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        tessellator.setColorOpaque_F(f, f, f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) l1, (float) l2);

        ResourceLocation textureConstruct = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelConstruct.png"));
        ResourceLocation textureReplicator = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelCrafting.png"));

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        switch (side) {
            case 2:
                GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
                break;
            case 3:
                GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
                break;
            case 4:
                GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
                break;
            case 5:
                GL11.glRotatef(270, 0.0F, 1.0F, 0.0F);
                break;
        }

        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(textureConstruct);
        construct.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();

        GL11.glTranslatef(0.0F, (float) -1.5F, 0.0F);
        float f1 = (float) machine.field_145926_a + scale;
        GL11.glTranslatef(0.0F, 0.1F + MathHelper.sin(f1 * 0.1F) * 0.01F, 0.0F);
        float f2;

        for (f2 = machine.field_145928_o - machine.field_145925_p; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F)) {
            ;
        }

        while (f2 < -(float) Math.PI) {
            f2 += ((float) Math.PI * 2F);
        }

        switch (side) {
            case 2:
                GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
                break;
            case 3:
                GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
                break;
            case 4:
                GL11.glRotatef(270, 0.0F, 1.0F, 0.0F);
                break;
            case 5:
                GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
                break;
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(textureReplicator);

        float f4 = machine.field_145931_j + (machine.field_145933_i - machine.field_145931_j) * scale + 0.25F;
        float f5 = machine.field_145931_j + (machine.field_145933_i - machine.field_145931_j) * scale + 0.75F;
        f4 = (f4 - (float) MathHelper.truncateDoubleToInt((double) f4)) * 1.6F - 0.3F;
        f5 = (f5 - (float) MathHelper.truncateDoubleToInt((double) f5)) * 1.6F - 0.3F;

        if (f4 < 0.0F) {
            f4 = 0.0F;
        }

        if (f5 < 0.0F) {
            f5 = 0.0F;
        }

        if (f4 > 1.0F) {
            f4 = 1.0F;
        }

        if (f5 > 1.0F) {
            f5 = 1.0F;
        }

        float f6 = machine.field_145927_n + (machine.field_145930_m - machine.field_145927_n) * scale;
        GL11.glEnable(GL11.GL_CULL_FACE);

        crafting.render((Entity) null, f1, f4, f5, f6, 0.0F, 0.0625F);

        ItemStack[] items = machine.getInventory();

        if (items != null) {
            double dx = -0.5D;
            double dz = -0.5D;

            for (int i = 0; i < items.length; i++) {
                ItemStack stack = items[i];

                if (stack != null) {
                    ItemStack stack2 = stack.copy();
                    EntityItem entityItem = new EntityItem(machine.getWorldObj(), 0.0D, 0.0D, 0.0D, stack2);
                    entityItem.getEntityItem().stackSize = 1;
                    entityItem.hoverStart = 0.0F;
                    entityItem.setLocationAndAngles(x, y, z, 0.0F, 0.0F);

                    if (i < 3) {
                        dx = -0.5D + (i * 0.5D);
                    } else if (i < 6) {
                        dx = -0.5D + ((i - 3) * 0.5D);
                        dz = 0.0D;
                    } else if (i < 9) {
                        dx = -0.5D + ((i - 6) * 0.5D);
                        dz = 0.5D;
                    } else {
                        break;
                    }

                    GL11.glPushMatrix();
                    GL11.glScalef(0.5F, 0.5F, 0.5F);
                    RenderManager.instance.renderEntityWithPosYaw(entityItem, dx, 1.5D, dz, 0.0F, 0F);
                    GL11.glPopMatrix();
                }
            }
        }

        GL11.glPopMatrix();
    }
}
