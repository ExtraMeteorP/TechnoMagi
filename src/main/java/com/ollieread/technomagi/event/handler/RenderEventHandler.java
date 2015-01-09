package com.ollieread.technomagi.event.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Post;
import net.minecraftforge.client.event.RenderWorldEvent;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.potion.PotionTM;
import com.ollieread.technomagi.util.PlayerHelper;
import com.ollieread.technomagi.world.region.IRegionController;
import com.ollieread.technomagi.world.region.PerceptionManager;
import com.ollieread.technomagi.world.region.RegionManager;
import com.ollieread.technomagi.world.region.RegionManager.RegionControllerType;
import com.ollieread.technomagi.world.region.RegionNetwork;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RenderEventHandler
{

    public Field manager;

    public RenderEventHandler(Field f)
    {
        manager = f;
    }

    @SubscribeEvent
    public void onPlayerPostSpecialsRender(Post event)
    {
        EntityPlayer player = event.entityPlayer;
        ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(player);

        if (playerKnowledge != null && !playerKnowledge.canSpecialise()) {
            ItemStack staffStack = new ItemStack(Items.itemTechnomageStaff, 1, 1);

            boolean flag1 = PlayerHelper.hasInventoryItem(player, staffStack) && (player.getHeldItem() == null || !player.getHeldItem().isItemEqual(staffStack));
            boolean flag2 = playerKnowledge.hasStaff();

            if (flag1 || flag2) {
                if (!player.isInvisible()) {
                    IItemRenderer staffRenderer = MinecraftForgeClient.getItemRenderer(staffStack, ItemRenderType.EQUIPPED);

                    // GL11.glColor3f(1F, 1F, 1F);

                    GL11.glPushMatrix();

                    staffRenderer.renderItem(ItemRenderType.EQUIPPED, staffStack, 1);

                    GL11.glPopMatrix();
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerPostRender(RenderPlayerEvent.Post event)
    {
        /*
         * EntityPlayer player = event.entityPlayer; ExtendedPlayerKnowledge
         * playerKnowledge = ExtendedPlayerKnowledge.get(player);
         * 
         * if (playerKnowledge != null && !playerKnowledge.canSpecialise()) { if
         * (playerKnowledge.abilities.isCasting()) { float f6 = 0.0F; float f7 =
         * 0.0F; event.renderer.modelBipedMain.bipedRightArm.rotateAngleZ =
         * 0.0F; event.renderer.modelBipedMain.bipedRightArm.rotateAngleY =
         * -(0.8F - f6 * 0.6F) +
         * event.renderer.modelBipedMain.bipedHead.rotateAngleY;
         * event.renderer.modelBipedMain.bipedRightArm.rotateAngleX = -((float)
         * Math.PI / 2F) + event.renderer.modelBipedMain.bipedHead.rotateAngleX;
         * event.renderer.modelBipedMain.bipedRightArm.rotateAngleX -= f6 * 1.2F
         * - f7 * 0.4F; event.renderer.modelBipedMain.bipedRightArm.rotateAngleZ
         * += MathHelper.cos(0.4F * 0.09F) * 0.05F + 0.05F;
         * event.renderer.modelBipedMain.bipedRightArm.rotateAngleX +=
         * MathHelper.sin(0.4F * 0.067F) * 0.05F; } }
         */
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderFog(RenderFogEvent event)
    {
        if (event.entity.isPotionActive(PotionTM.passify)) {
            float f1 = 5.0F;
            int j = event.entity.getActivePotionEffect(Potion.blindness).getDuration();

            if (j < 20) {
                f1 = 5.0F + (event.farPlaneDistance - 5.0F) * (1.0F - (float) j / 20.0F);
            }

            GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);

            if (event.fogMode < 0) {
                GL11.glFogf(GL11.GL_FOG_START, 0.0F);
                GL11.glFogf(GL11.GL_FOG_END, f1 * 0.8F);
            } else {
                GL11.glFogf(GL11.GL_FOG_START, f1 * 0.25F);
                GL11.glFogf(GL11.GL_FOG_END, f1);
            }

            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi(34138, 34139);
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorldPre(RenderWorldEvent.Pre event)
    {
        ChunkCache cache = event.chunkCache;
        EntityPlayer player = TechnoMagi.proxy.getClientPlayer();

        if (player == null) {
            return;
        }

        World world = player.worldObj;
        List<IRegionController> controllers = RegionManager.getControllers(RegionControllerType.PERCEPTION);

        if (world == null || !(controllers.size() > 0)) {
            return;
        }

        if (PerceptionManager.hasPerceptionDimension(world.provider.dimensionId)) {
            int perceptionDimension = PerceptionManager.getForDimension(world.provider.dimensionId);
            WorldServer perception = MinecraftServer.getServer().worldServerForDimension(perceptionDimension);
            ChunkProviderServer provider = perception.theChunkProviderServer;
            boolean flag = false;
            Chunk[][] chunkArray;
            for (Field f : ChunkCache.class.getDeclaredFields()) {
                f.setAccessible(true);

                try {
                    if (f.getName().equals("chunkArray")) {
                        Field modfield = Field.class.getDeclaredField("modifiers");
                        modfield.setAccessible(true);
                        modfield.setInt(f, f.getModifiers() & ~Modifier.PRIVATE);

                        chunkArray = (Chunk[][]) f.get(cache);
                        int j2;
                        int k2;
                        int chunkX = ((int) player.posX) - 1 - 1 >> 4;
                        int chunkZ = ((int) player.posZ) - 1 - 1 >> 4;
                        int l1 = ((int) player.posX) + 16 + 1 + 1 >> 4;
                        int i2 = ((int) player.posZ) + 16 + 1 + 1 >> 4;

                        for (Iterator<IRegionController> it = controllers.iterator(); it.hasNext();) {
                            IRegionController controller = it.next();
                            RegionNetwork network = RegionManager.getNetwork(controller.getNetworkId());

                            for (int x = 0; x < chunkArray.length; x++) {
                                if (chunkArray[x] != null) {
                                    for (int i = 0; i < chunkArray[x].length; i++) {
                                        if (chunkArray[x][i] != null) {
                                            int xc = chunkArray[x][i].xPosition;
                                            int zc = chunkArray[x][i].zPosition;
                                            long id = ChunkCoordIntPair.chunkXZ2Int(xc, zc);

                                            if (network.isInside(xc, zc)) {
                                                chunkArray[x][i] = provider.loadChunk(xc, zc);
                                                flag = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (flag) {
                            f.set(cache, chunkArray);
                        }
                        break;
                    }
                } catch (Exception e) {
                    return;
                }
            }
        }
    }
}
