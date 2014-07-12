package com.ollieread.technomagi.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.model.ModelMachineConstruct;
import com.ollieread.technomagi.client.model.ModelMachineReplicator;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.TileEntityNaniteReplicator;

public class TileEntityNaniteReplicatorRenderer extends TileEntitySpecialRenderer
{

    private final ModelMachineConstruct construct;
    private final ModelMachineReplicator replicator;

    public TileEntityNaniteReplicatorRenderer()
    {
        construct = new ModelMachineConstruct();
        replicator = new ModelMachineReplicator();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        TileEntityNaniteReplicator machine = (TileEntityNaniteReplicator) te;

        replicator.setTileEntity(machine);
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
        ResourceLocation textureReplicator = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelReplicator.png"));

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

        Minecraft.getMinecraft().renderEngine.bindTexture(textureReplicator);
        replicator.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
        GL11.glPopMatrix();

        int progress = machine.getProgress();
        int type = machine.getSampleType();
        MovingObjectPosition mouse = Minecraft.getMinecraft().objectMouseOver;

        if (type > -1 && mouse.blockX == machine.xCoord && mouse.blockY == machine.yCoord && mouse.blockZ == machine.zCoord) {
            String s = null;

            switch (type) {
                case 2:
                    s = EnumChatFormatting.DARK_GREEN + "Cow";
                    break;
                case 3:
                    s = EnumChatFormatting.DARK_GREEN + "Sheep";
                    break;
                case 4:
                    s = EnumChatFormatting.DARK_GREEN + "Pig";
                    break;
                case 5:
                    s = EnumChatFormatting.DARK_GREEN + "Chicken";
                    break;
            }

            s += "  " + EnumChatFormatting.AQUA + progress + "%";
            FontRenderer fontrenderer = RenderManager.instance.getFontRenderer();
            float f2 = 1F;
            float f1 = 0.016666668F * f2;
            GL11.glPushMatrix();
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.25F, (float) z + 0.16F);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glScalef(-f1, -f1, f1);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthMask(false);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            byte b0 = 0;

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            tessellator.startDrawingQuads();
            int j = fontrenderer.getStringWidth(s) / 2;
            tessellator.setColorRGBA_I(67083, 200);
            tessellator.addVertex((double) (-j - 2), (double) (-2 + b0), 0.0D);
            tessellator.addVertex((double) (-j - 2), (double) (9 + b0), 0.0D);
            tessellator.addVertex((double) (j + 2), (double) (9 + b0), 0.0D);
            tessellator.addVertex((double) (j + 2), (double) (-2 + b0), 0.0D);
            tessellator.draw();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, b0, 553648127);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, b0, -1);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        }
    }
}
