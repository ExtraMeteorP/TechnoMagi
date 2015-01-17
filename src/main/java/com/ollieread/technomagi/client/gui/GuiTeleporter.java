package com.ollieread.technomagi.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.TileEntityMachineTeleporter;
import com.ollieread.technomagi.util.PacketHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTeleporter extends GuiScreen
{

    protected int xSize;
    protected int ySize;
    protected int xOffset;
    protected int yOffset;
    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/teleporter.png");
    protected TileEntityMachineTeleporter teleporter;
    protected int mode;
    protected int mouseX;
    protected int mouseY;

    public GuiTeleporter(TileEntityMachineTeleporter tile)
    {
        super();

        teleporter = tile;

        xSize = 185;
        ySize = 55;

        mode = teleporter.getMode();
    }

    public void initGui()
    {
        super.initGui();

        xOffset = (width - xSize) / 2;
        yOffset = (height - ySize) / 2;

        this.buttonList.clear();
        this.buttonList.add(new GuiTeleporterButton(1, xOffset + 22, yOffset + 27, 0));
        this.buttonList.add(new GuiTeleporterButton(2, xOffset + 62, yOffset + 27, 1));
        this.buttonList.add(new GuiTeleporterButton(3, xOffset + 102, yOffset + 27, 2));
        this.buttonList.add(new GuiTeleporterButton(4, xOffset + 142, yOffset + 27, 3));

    }

    public void drawScreen(int par1, int par2, float par3)
    {
        this.mouseX = par1;
        this.mouseY = par2;

        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.xOffset, this.yOffset, 0, 0, this.xSize, this.ySize);

        this.fontRendererObj.drawString(I18n.format("tile.teleporter." + teleporter.getBlockMetadata() + ".name"), this.xOffset + 8, this.yOffset + 8, 16777215);

        super.drawScreen(par1, par2, par3);

        int x = xOffset;
        int y = yOffset + 27;

        if (mode == 0) {
            x += 22;
        } else if (mode == 1) {
            x += 62;
        } else if (mode == 2) {
            x += 102;
        } else if (mode == 3) {
            x += 142;
        }

        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(x - 1, y - 1, 185, 0, 22, 22);

        for (Iterator i = buttonList.iterator(); i.hasNext();) {
            GuiButton button = (GuiButton) i.next();

            if (!(button instanceof GuiTeleporterButton)) {
                continue;
            }

            GuiTeleporterButton teleporterButton = (GuiTeleporterButton) button;

            if (mouseX >= teleporterButton.xPosition && mouseX <= (teleporterButton.xPosition + teleporterButton.width)) {
                if (mouseY >= teleporterButton.yPosition && mouseY <= (teleporterButton.yPosition + teleporterButton.height)) {
                    List text = new ArrayList();

                    if (teleporterButton.type == 0) {
                        text.add("Players only");
                    } else if (teleporterButton.type == 1) {
                        text.add("Owner only");
                    } else if (teleporterButton.type == 2) {
                        text.add("Mobs Only");
                    } else if (teleporterButton.type == 3) {
                        text.add("All");
                    }

                    this.drawHoveringText(text, mouseX, mouseY, this.fontRendererObj);
                }
            }
        }
    }

    protected void actionPerformed(GuiButton button)
    {
        if (button instanceof GuiTeleporterButton) {
            mode = ((GuiTeleporterButton) button).type;
            PacketHelper.setTeleporterMode(teleporter, mode);
        }
    }

    public static class GuiTeleporterButton extends GuiButton
    {
        /** Button width in pixels */
        protected int width;
        /** Button height in pixels */
        protected int height;
        /** The x position of this control. */
        public int xPosition;
        /** The y position of this control. */
        public int yPosition;

        public int type;

        private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/teleporter.png");

        public GuiTeleporterButton(int i, int x, int y, int t)
        {
            super(i, x, y, 20, 20, "");

            this.xPosition = x;
            this.yPosition = y;
            this.width = 20;
            this.height = 20;
            this.type = t;
        }

        @Override
        public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
        {
            if (this.visible) {
                FontRenderer fontrenderer = p_146112_1_.fontRenderer;
                p_146112_1_.getTextureManager().bindTexture(texture);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
                int k = this.getHoverState(this.field_146123_n);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);

                if (type == 0) {
                    this.drawTexturedModalRect(this.xPosition + 1, this.yPosition + 1, 0, 55, 18, 18);
                } else if (type == 1) {
                    this.drawTexturedModalRect(this.xPosition + 1, this.yPosition + 1, 18, 55, 18, 18);
                } else if (type == 2) {
                    this.drawTexturedModalRect(this.xPosition, this.yPosition + 1, 36, 55, 20, 18);
                } else if (type == 3) {
                    this.drawTexturedModalRect(this.xPosition + 1, this.yPosition + 1, 56, 55, 18, 18);
                }

                this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
                int l = 14737632;

                if (packedFGColour != 0) {
                    l = packedFGColour;
                } else if (!this.enabled) {
                    l = 10526880;
                } else if (this.field_146123_n) {
                    l = 16777120;
                }

                this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
            }
        }

    }

}