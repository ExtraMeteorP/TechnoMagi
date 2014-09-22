package com.ollieread.technomagi.client.gui;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.ContainerArchive;
import com.ollieread.technomagi.tileentity.TileEntityArchive;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiArchive extends GuiContainer
{

    protected EntityPlayer player;
    protected TileEntityArchive archive;

    protected ResourceLocation texture;

    protected int type = 0;
    protected int subtype = 0;
    protected int page = 0;
    protected boolean pages;

    protected FontRenderer obfFontRendererObj;

    public GuiArchive(EntityPlayer player, TileEntityArchive archive)
    {
        super(new ContainerArchive(player.inventory, archive));

        this.player = player;
        this.archive = archive;

        this.xSize = 233;
        this.ySize = 248;
        this.type = archive.getType();
        this.subtype = archive.getSubType();
        this.page = archive.getPage();
        this.obfFontRendererObj = Minecraft.getMinecraft().standardGalacticFontRenderer;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public void setSubType(int subtype)
    {
        this.subtype = subtype;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    private String trimStringNewline(String string)
    {
        while (string != null && string.endsWith("\n")) {
            string = string.substring(0, string.length() - 1);
        }

        return string;
    }

    private String bidiReorder(String s)
    {
        try {
            Bidi bidi = new Bidi((new ArabicShaping(8)).shape(s), 127);
            bidi.setReorderingMode(0);
            return bidi.writeReordered(2);
        } catch (ArabicShapingException arabicshapingexception) {
            return s;
        }
    }

    protected void drawStringPage(String string, int x, int y, int w, int h, boolean obf)
    {
        FontRenderer f = obf ? obfFontRendererObj : fontRendererObj;
        String s = trimStringNewline(string);
        List l = f.listFormattedStringToWidth(s, w);
        int rh = 1;
        int o = h * page;

        for (Iterator<String> i = l.iterator(); i.hasNext();) {
            String s1 = (String) i.next();

            if (rh == 1 && o == 0) {
                f.drawString(s1, x, y, 16777215);
            } else if (rh >= o) {
                f.drawString(s1, x, y + (rh - o), 16777215);
            }

            if (s1.indexOf("\n\n") > -1) {
                rh += (f.FONT_HEIGHT * 2);
            }

            rh += f.FONT_HEIGHT;

            if (rh > (o + h)) {
                pages = true;
                break;
            } else {
                pages = false;
            }
        }
    }

    protected void drawItemStack(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_)
    {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        if (p_146982_1_ != null)
            font = p_146982_1_.getItem().getFontRenderer(p_146982_1_);
        if (font == null)
            font = fontRendererObj;
        itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_);
        itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_ - (this.draggedStack == null ? 0 : 8), p_146982_4_);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }

    public static class GuiPaginationButton extends GuiButton
    {

        protected ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/overlay.png");
        /** Button width in pixels */
        protected int width;
        /** Button height in pixels */
        protected int height;
        /** The x position of this control. */
        public int xPosition;
        /** The y position of this control. */
        public int yPosition;
        protected int direction = 0;

        public GuiPaginationButton(int id, int x, int y, int d)
        {
            super(id, x, y, 7, 11, "");

            direction = d;
            xPosition = x;
            yPosition = y;
            width = 7;
            height = 11;
        }

        @Override
        public void drawButton(Minecraft mc, int mx, int my)
        {
            if (this.visible) {
                mc.getTextureManager().bindTexture(texture);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                int k = this.getHoverState(this.field_146123_n);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);
                this.drawTexturedModalRect(this.xPosition, this.yPosition, (direction == 0 ? 37 : 49), 44, this.width, this.height);
                this.mouseDragged(mc, mx, my);
            }
        }
    }

}
