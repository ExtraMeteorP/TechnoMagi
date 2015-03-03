package com.ollieread.technomagi.common.event.handler;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.EntityTechnomagi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.specialisation.Specialisation;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class InitialisationEvents
{

    // public static List<InterfaceCreation> interfaceCreations = new
    // LinkedList<InterfaceCreation>();

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event)
    {
        if (event.entity instanceof EntityPlayer) {
            if (PlayerTechnomagi.get((EntityPlayer) event.entity) == null) {
                PlayerTechnomagi.register((EntityPlayer) event.entity);
            } else {
                PlayerTechnomagi technomage = PlayerTechnomagi.get((EntityPlayer) event.entity);

                if (technomage.hasSpecialised()) {
                    Specialisation spec = technomage.getSpecialisation();
                    spec.applyAttributes(((EntityPlayer) event.entity).getAttributeMap());
                }

                technomage.sync();
            }
        } else {
            List<Class> allowedEntities = TechnomagiApi.entity().getNaniteEntities();

            for (Class entity : allowedEntities) {
                if (entity.isInstance(event.entity) && EntityTechnomagi.get((EntityLivingBase) event.entity) == null) {
                    EntityTechnomagi.register((EntityLivingBase) event.entity);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            PlayerTechnomagi.saveProxyData((EntityPlayer) event.entity);
        }
    }

    @SubscribeEvent
    public void onClone(Clone event)
    {
        if (!event.entity.worldObj.isRemote && event.original instanceof EntityPlayer && !event.wasDeath) {
            PlayerTechnomagi technomage = PlayerTechnomagi.get(event.entityPlayer);

            if (technomage != null && technomage.hasSpecialised()) {
                technomage.copyFrom(PlayerTechnomagi.get(event.original));
                Specialisation spec = technomage.getSpecialisation();
                spec.applyAttributes(((EntityPlayer) event.entity).getAttributeMap());
            }

            technomage.sync();
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            PlayerTechnomagi.loadProxyData((EntityPlayer) event.entity);
            PlayerTechnomagi technomage = PlayerTechnomagi.get((EntityPlayer) event.entity);

            if (technomage != null && technomage.hasSpecialised()) {
                Specialisation spec = technomage.getSpecialisation();
                spec.applyAttributes(((EntityPlayer) event.entity).getAttributeMap());
            }

            technomage.sync();
        }
    }

    /*
     * @SubscribeEvent public void interact(PlayerInteractEvent event) { if
     * (event.action.equals(Action.RIGHT_CLICK_BLOCK)) { EntityPlayer player =
     * event.entityPlayer; ItemStack heldStack = player.getHeldItem();
     *
     * if (heldStack != null && heldStack.getItem() != null) { if
     * (heldStack.isItemEqual(ItemHelper.itemSubtype(Items.crystal, "etherium",
     * 0))) { int posX = event.x; int posY = event.y; int posZ = event.z;
     *
     * if (InterfaceCreation.canPerform(event.world, posX, posY, posZ)) {
     * List<EntityItem> itemsOnGround =
     * event.world.getEntitiesWithinAABB(EntityItem.class,
     * AxisAlignedBB.getBoundingBox(posX - 2D, posY - 2D, posZ - 2D, posX + 2D,
     * posY + 2D, posZ + 2D)); List<EntityItem> toBeWorked = new
     * LinkedList<EntityItem>();
     *
     * int iron = 2; int gold = 1; int redstone = 4;
     *
     * for (EntityItem item : itemsOnGround) { ItemStack stack =
     * item.getEntityItem();
     *
     * if (iron == 0 && gold == 0 && redstone == 0) { break; }
     *
     * if (stack != null) { if (iron != 0) { if
     * (stack.isItemEqual(ItemHelper.item("iron_ingot"))) { if (stack.stackSize
     * <= iron) { iron -= stack.stackSize; toBeWorked.add(item); continue; } } }
     * if (gold != 0) { if (stack.isItemEqual(ItemHelper.item("gold_ingot"))) {
     * if (stack.stackSize == 1) { gold--; toBeWorked.add(item); continue; } } }
     * if (redstone != 0) { if (stack.isItemEqual(ItemHelper.item("redstone")))
     * { if (stack.stackSize <= redstone) { redstone -= stack.stackSize;
     * toBeWorked.add(item); continue; } } } } }
     *
     * if (iron == 0 && gold == 0 && redstone == 0) { if (!event.world.isRemote)
     * { World world = event.world; ItemStack floating = heldStack.copy();
     * floating.stackSize = 1; heldStack.stackSize--;
     *
     * EntityItem entity = new EntityItem(world, event.x, event.y, event.z,
     * floating); entity.delayBeforeCanPickup = 1000;
     * world.spawnEntityInWorld(entity);
     *
     * for (EntityItem item : toBeWorked) { item.delayBeforeCanPickup = 1000;
     * item.onGround = true; }
     *
     * new InterfaceCreation((LinkedList<EntityItem>) toBeWorked, entity,
     * event.x, event.y, event.z); } } } } } } }
     *
     * @SubscribeEvent public void onWorldTick(WorldTickEvent event) { if
     * (event.phase.equals(TickEvent.Phase.END)) { for (InterfaceCreation
     * creation : interfaceCreations) { if (creation.update(event.world)) {
     * interfaceCreations.remove(creation); } } } }
     *
     * public static class InterfaceCreation {
     *
     * protected int x; protected int y; protected int z; protected
     * List<EntityItem> entities = new LinkedList<EntityItem>(); protected
     * EntityItem etherium; protected EntityItem currentItem; protected int
     * countdown = 200;
     *
     * public InterfaceCreation(LinkedList<EntityItem> entities, EntityItem
     * etherium, int x, int y, int z) { this.entities = entities; this.etherium
     * = etherium; etherium.hoverStart = 0F; etherium.onGround = true; this.x =
     * x; this.y = y; this.z = z;
     *
     * InitialisationEvents.interfaceCreations.add(this); }
     *
     * public static boolean canPerform(World world, int x, int y, int z) { for
     * (InterfaceCreation event : InitialisationEvents.interfaceCreations) {
     * double d3 = event.x - x; double d4 = event.y - y; double d5 = event.z -
     * z;
     *
     * if (MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5) <= 4) { return
     * false; } }
     *
     * return true; }
     *
     * public boolean update(World world) { if (currentItem == null) { if
     * (countdown <= 0) { etherium.motionY = 0;
     *
     * if (entities.size() == 0) { if (!world.isRemote) { etherium.setDead();
     * ItemStack personalInterface = new ItemStack(Items.personalInterface, 1);
     * ItemHelper.placeInWorld(personalInterface, world, x, y, z);
     *
     * return true; } } else { Iterator<EntityItem> iterator =
     * entities.iterator(); currentItem = iterator.next();
     * currentItem.hoverStart = 0F; entities.remove(currentItem); countdown =
     * 200; } } else { if (!world.isRemote) { countdown--; }
     *
     * etherium.motionY = 0.025D; } } else { if (!world.isRemote) { countdown--;
     * } currentItem.onGround = true; currentItem.motionY = 0.025D;
     *
     * if (countdown <= 0) { for (int i = 0; i < 6; i++) {
     * world.spawnParticle("explode", x, y, z, 0, 0, 0); } if (!world.isRemote)
     * { currentItem.setDead(); currentItem = null; } } }
     *
     * return false; } }
     */

}
