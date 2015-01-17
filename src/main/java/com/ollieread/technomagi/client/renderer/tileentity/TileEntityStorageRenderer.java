package com.ollieread.technomagi.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.model.ModelTank;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.TileEntityStorageItems;

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
        TileEntityStorageItems storage = (TileEntityStorageItems) te;
        Tessellator tessellator = Tessellator.instance;

        ItemStack itemStack = storage.getItem();
        int amount = storage.getAmount();
        int pass = MinecraftForgeClient.getRenderPass();

        // This will make your block brightness dependent from surroundings
        // lighting.
        float f = Blocks.blockStorage.getLightValue(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
        int l = te.getWorldObj().getLightBrightnessForSkyBlocks(te.xCoord, te.yCoord, te.zCoord, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        tessellator.setColorOpaque_F(f, f, f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) l1, (float) l2);

        if (pass == 0) {
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

            if (itemStack != null) {
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

        } else {
            if (itemStack != null && amount > 0) {
                RenderManager renderManager = RenderManager.instance;
                float f3 = 1.6F;
                float f1 = 0.016666668F * f3;
                double d3 = renderManager.livingPlayer.getDistanceSq(te.xCoord, te.yCoord, te.zCoord);
                float f2 = renderManager.livingPlayer.isSneaking() ? 32.0F : 64.0F;

                if (d3 < (double) (f2 * f2)) {
                    MovingObjectPosition mouse = Minecraft.getMinecraft().objectMouseOver;

                    if (mouse.blockX == storage.xCoord && mouse.blockY == storage.yCoord && mouse.blockZ == storage.zCoord) {
                        ForgeDirection dir = ForgeDirection.getOrientation(mouse.sideHit);
                        String s = itemStack.getDisplayName();
                        String s2 = amount + "/" + storage.getCapacity();

                        FontRenderer fontrenderer = renderManager.getFontRenderer();
                        GL11.glPushMatrix();
                        GL11.glTranslatef((float) x + 0.5F + (dir.offsetX * 0.65F), (float) y + 0.9F, (float) z + 0.5F + (dir.offsetZ * 0.65F));
                        GL11.glNormal3f(0.0F, 1.0F, 0.0F);

                        switch (mouse.sideHit) {
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

                        GL11.glScalef(-f1, -f1, f1);
                        GL11.glScalef(0.7F, 0.7F, 0.7F);
                        GL11.glDisable(GL11.GL_LIGHTING);
                        GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);

                        GL11.glDepthMask(false);
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                        GL11.glDisable(GL11.GL_TEXTURE_2D);
                        tessellator.startDrawingQuads();
                        int i = fontrenderer.getStringWidth(s) / 2;
                        tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.5F);
                        tessellator.addVertex((double) (-i - 1), -1.0D, 0.0D);
                        tessellator.addVertex((double) (-i - 1), 8.0D, 0.0D);
                        tessellator.addVertex((double) (i + 1), 8.0D, 0.0D);
                        tessellator.addVertex((double) (i + 1), -1.0D, 0.0D);
                        tessellator.draw();
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glDepthMask(true);
                        fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);

                        GL11.glScalef(0.5F, 0.5F, 0.5F);
                        GL11.glDepthMask(false);
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                        GL11.glDisable(GL11.GL_TEXTURE_2D);
                        GL11.glTranslatef(0.0F, 18F, 0.0F);
                        tessellator.startDrawingQuads();
                        int i2 = fontrenderer.getStringWidth(s2) / 2;
                        tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.5F);
                        tessellator.addVertex((double) (-i2 - 1), -1.0D, 0.0D);
                        tessellator.addVertex((double) (-i2 - 1), 8.0D, 0.0D);
                        tessellator.addVertex((double) (i2 + 1), 8.0D, 0.0D);
                        tessellator.addVertex((double) (i2 + 1), -1.0D, 0.0D);
                        tessellator.draw();
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glDepthMask(true);
                        fontrenderer.drawString(s2, -fontrenderer.getStringWidth(s2) / 2, 0, 553648127);
                        GL11.glDepthMask(false);

                        GL11.glEnable(GL11.GL_LIGHTING);
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glPopMatrix();
                    }
                }
            }
        }
    }
}
