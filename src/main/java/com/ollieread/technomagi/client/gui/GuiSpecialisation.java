package com.ollieread.technomagi.client.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.api.ISpecialisation;
import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageAbility;
import com.ollieread.technomagi.network.message.MessageSpecialisation;
import com.ollieread.technomagi.player.PlayerKnowledge;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSpecialisation extends GuiScreen
{

	public static String choice;
	private float xSize_lo;
	private float ySize_lo;
    protected int xSize = 226;
    protected int ySize = 210;
    protected int xOffset;
    protected int yOffset;
    protected HashMap<Integer, String> buttonSpecialisations = new HashMap<Integer, String>();
    
    protected GuiButton buttonSpecialise;

	private static final ResourceLocation iconLocation = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/specialisation.png");

	public void initGui()
    {
		this.buttonList.clear();
		this.xOffset = (this.width - this.xSize) / 2;
		this.yOffset = (this.height - this.ySize) / 2;
		
		this.buttonList.add(this.buttonSpecialise = new GuiButton(1, this.xOffset + 53, this.yOffset + 185, 120, 20, I18n.format("technomagi.specialise.button")));
		
		Collection<ISpecialisation> specialisations = TMRegistry.getSpecialisations();
		int buttonId = 2;
		
		for(Iterator<ISpecialisation> i = specialisations.iterator(); i.hasNext();) {
			ISpecialisation spec = i.next();
			this.buttonSpecialisations.put(buttonId, spec.getName());
			this.buttonList.add(new GuiSpecialiseButton(buttonId, this.xOffset + 57 + ((buttonId - 2) * 40), this.yOffset + 20, I18n.format("technomagi.specialise.button"), spec.getIcon()));
			buttonId++;
		}
    }

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(iconLocation);
		
		this.drawTexturedModalRect(this.xOffset, this.yOffset, 0, 0, this.xSize, this.ySize);
		int i1;
		
		String s = "technomagi.specialise.gui";

		this.fontRendererObj.drawString(I18n.format(s), this.xOffset + 8, this.yOffset + 8, 4210752);
		
		if(choice != null) {
			String spec = "specialisation." + choice;
			this.fontRendererObj.drawString(I18n.format(spec), this.xOffset + 10, this.yOffset + 60, 16777215);
		}
		
		this.xSize_lo = (float) par1;
		this.ySize_lo = (float) par2;
		
		super.drawScreen(par1, par2, par3);
	}
	
	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 1) {
			if(choice != null) {
				PlayerKnowledge charon = PlayerKnowledge.get(this.mc.thePlayer);
				charon.setSpecialisation(choice);
				PacketHandler.INSTANCE.sendToServer(new MessageSpecialisation(TMRegistry.getSpecialisationId(choice)));
				this.mc.displayGuiScreen(null);
			}
		} else {
			String specName = this.buttonSpecialisations.get(button.id);
			
			if(specName != null) {
				this.choice = specName;
			}
		}
	}
	
	public void onGuiClosed() 
	{
		this.choice = null;
	}
	
}