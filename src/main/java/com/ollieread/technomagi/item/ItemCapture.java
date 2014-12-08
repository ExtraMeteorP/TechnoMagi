package com.ollieread.technomagi.item;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCapture extends ItemTMNBT
{
    @SideOnly(Side.CLIENT)
    private IIcon capturedIcon;
    private Map<String, IIcon> entityIcons;

    public ItemCapture(String name)
    {
        super(name);

        setMaxStackSize(16);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        super.registerIcons(register);
        capturedIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString() + "Entity");
        entityIcons = new HashMap<String, IIcon>();

        for (Iterator<Class> i = ResearchRegistry.getObservableEntities().iterator(); i.hasNext();) {
            Class entityClass = i.next();
            String name = (String) EntityList.classToStringMapping.get(entityClass);

            if (name != null) {
                entityIcons.put(name, register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString() + "Entity." + name));
            }
        }

        entityIcons.put("unknown", register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString() + "Entity.unknown"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass)
    {
        if (hasEntity(stack)) {
            if (pass == 0) {
                return capturedIcon;
            } else {
                String name = getEntityName(stack);

                if (entityIcons.containsKey(name)) {
                    return entityIcons.get(name);
                }

                return entityIcons.get("unknown");
            }
        }

        return itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        return getIcon(stack, renderPass);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int d)
    {
        return d == 0 ? itemIcon : capturedIcon;
    }

    public static void setEntity(EntityLiving entity, ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);

        if (entity == null) {
            compound.setString("Name", "none");
            compound.setString("EntityName", "none");
            compound.setTag("EntityNBT", new NBTTagCompound());
        } else {
            if (entity.hasCustomNameTag()) {
                compound.setString("Name", entity.getCustomNameTag());
            }

            compound.setString("EntityName", (String) EntityList.classToStringMapping.get(entity.getClass()));

            NBTTagCompound entityNBT = new NBTTagCompound();
            entity.writeEntityToNBT(entityNBT);
            compound.setTag("EntityNBT", entityNBT);
        }
    }

    public static String getName(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);

        if (compound.hasKey("Name")) {
            return compound.getString("Name");
        }

        return null;
    }

    public static boolean hasEntity(ItemStack stack)
    {
        return getEntityName(stack) != null && !getEntityName(stack).isEmpty() && !getEntityName(stack).equals("none");
    }

    public static String getEntityName(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);

        if (compound.hasKey("EntityName")) {
            return compound.getString("EntityName");
        }

        return null;
    }

    public static NBTTagCompound getEntityNBT(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);

        if (compound.hasKey("EntityNBT")) {
            return (NBTTagCompound) compound.getTag("EntityNBT");
        }

        return null;
    }

    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        if (!hasEntity(stack)) {
            if (entity instanceof EntityLiving) {
                if (ResearchRegistry.getObservableEntities().contains(entity.getClass())) {
                    ItemStack newStack = new ItemStack(this, 1, 1);
                    newStack.stackTagCompound = new NBTTagCompound();
                    setEntity((EntityLiving) entity, newStack);
                    player.inventory.addItemStackToInventory(newStack);
                    stack.stackSize--;
                    entity.setDead();

                    return true;
                }
            }
        }

        return super.itemInteractionForEntity(stack, player, entity);
    }

    /*
     * public ItemStack onItemRightClick(ItemStack stack, World world,
     * EntityPlayer player) { if (hasEntity(stack)) { String name =
     * getName(stack); String entityName = getEntityName(stack);
     * 
     * if (entityName != null) { EntityLiving entityLiving = (EntityLiving)
     * EntityList.createEntityByName(entityName, world);
     * 
     * if (entityLiving != null) {
     * entityLiving.readEntityFromNBT(ItemCapture.getEntityNBT(stack));
     * MovingObjectPosition mouse = Minecraft.getMinecraft().objectMouseOver;
     * 
     * if (mouse.typeOfHit.equals(MovingObjectType.BLOCK)) { ForgeDirection dir
     * = ForgeDirection.getOrientation(mouse.sideHit);
     * entityLiving.setPosition(mouse.blockX + dir.offsetX, mouse.blockY +
     * dir.offsetY, mouse.blockZ + dir.offsetZ);
     * 
     * if (name != null) { entityLiving.setCustomNameTag(name); }
     * 
     * if (!world.isRemote) { world.spawnEntityInWorld(entityLiving); }
     * 
     * setEntity(null, stack); stack.stackSize--; } } } }
     * 
     * return stack; }
     */

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if (hasEntity(stack)) {
            String info = "";
            String name = getName(stack);
            String entityName = getEntityName(stack);

            if (name != null) {
                list.add(name);
            }

            if (entityName != null) {
                info = StatCollector.translateToLocal("entity." + entityName + ".name");

                list.add(info);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack empty = new ItemStack(item, 1);
        empty.stackTagCompound = new NBTTagCompound();
        list.add(empty);
    }

}
