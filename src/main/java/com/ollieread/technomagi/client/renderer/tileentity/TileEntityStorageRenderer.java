package com.ollieread.technomagi.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.model.ModelTank;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.TileEntityStorage;

public class TileEntityStorageRenderer extends TileEntitySpecialRenderer
{

    private final ModelTank model;

    public TileEntityStorageRenderer()
    {
        model = new ModelTank();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        TileEntityStorage storage = (TileEntityStorage) te;
        Tessellator tessellator = Tessellator.instance;
        // This will make your block brightness dependent from surroundings
        // lighting.
        float f = Blocks.blockTank.getLightValue(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
        int l = te.getWorldObj().getLightBrightnessForSkyBlocks(te.xCoord, te.yCoord, te.zCoord, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        tessellator.setColorOpaque_F(f, f, f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) l1, (float) l2);
        ResourceLocation texture;

        switch (storage.getBlockMetadata()) {
            case 1:
                texture = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelTankAdvanced.png"));
                break;
            case 2:
                texture = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelTankElite.png"));
                break;
            default:
                texture = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelTank.png"));
                break;
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(0.95F, 1F, 0.95F);

        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        ItemStack itemStack = storage.getItem();

        if (itemStack != null) {

            int amount = storage.getAmount();
            int capacity = storage.getCapacity();

            if (amount > 0) {
                GL11.glDisable(GL11.GL_LIGHTING);

                GL11.glPushMatrix();
                GL11.glTranslatef(0, 1.0F, 0);

                double offset = -0.1D;
                float scaleF = 2.0F;

                if (!(itemStack.getItem() instanceof ItemBlock)) {
                    scaleF = 1.5F;
                    offset = -0.23D;
                }

                GL11.glScalef(scaleF, scaleF, scaleF);
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

                ItemStack stack2 = itemStack.copy();
                EntityItem entityItem = new EntityItem(storage.getWorldObj(), 0.0D, 0.0D, 0.0D, stack2);
                entityItem.getEntityItem().stackSize = 1;
                entityItem.hoverStart = 0.0F;
                entityItem.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
                RenderManager.instance.renderEntityWithPosYaw(entityItem, 0D, offset, 0D, 0.0F, 0.0F);

                GL11.glPopMatrix();

                GL11.glEnable(GL11.GL_LIGHTING);
            }
        }

        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
