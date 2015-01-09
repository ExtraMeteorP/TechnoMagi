package com.ollieread.technomagi.item;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSampleVile extends ItemTMNBT
{

    @SideOnly(Side.CLIENT)
    protected IIcon itemIconFull;

    public ItemSampleVile(String name)
    {
        super(name);

        setMaxStackSize(64);
        setHasSubtypes(true);
    }

    public static String getEntity(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);

        if (compound != null && compound.hasKey("Entity")) {
            return compound.getString("Entity");
        }

        return null;
    }

    public static String getPlayer(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);

        if (compound != null && compound.hasKey("Entity")) {
            if (compound.getString("Entity") == "player") {
                return compound.getString("PlayerName");
            }
        }

        return null;
    }

    public static void setEntity(ItemStack stack, EntityLivingBase entity)
    {
        NBTTagCompound compound = getNBT(stack);
        String entityName;

        if (entity instanceof EntityPlayer) {
            entityName = "player";
            compound.setString("PlayerName", entity.getCommandSenderName());
            compound.setString("Entity", entityName);
        } else {
            setEntity(stack, entity.getClass());
            return;
        }
    }

    public static void setEntity(ItemStack stack, Class entityClass)
    {
        NBTTagCompound compound = getNBT(stack);
        String entityName;

        entityName = (String) EntityList.classToStringMapping.get(entityClass);
        compound.setString("Entity", entityName);
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + (getEntity(stack) != null ? ".full.name" : ".name"));
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        String info = "";

        String entityName = getEntity(stack);
        Class entityClass = (Class) EntityList.stringToClassMapping.get(entityName);

        if (entityName != null) {
            if (entityClass != null) {
                info = StatCollector.translateToLocal("entity." + entityName + ".name");
            } else if (entityName.equals("player")) {
                info = EnumChatFormatting.DARK_PURPLE + getPlayer(stack);
            }

            list.add(info);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString());
        itemIconFull = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString() + "Full");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
        String entityName = this.getEntity(stack);

        if (entityName != null) {
            return itemIconFull;
        }

        return itemIcon;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack initial = new ItemStack(item, 1, 0);
        resetNBT(initial);
        setEntity(initial, EntityPlayer.class);
        list.add(initial);

        Iterator iterator = ResearchRegistry.getMonitorableEntities().iterator();

        while (iterator.hasNext()) {
            Class entityClass = (Class) iterator.next();

            ItemStack stack = new ItemStack(item, 1, 1);
            initial.stackTagCompound = new NBTTagCompound();
            this.setEntity(stack, entityClass);

            list.add(stack);
        }
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        return stack;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
        stack.stackSize--;

        if (stack.stackSize == 0) {
            stack = null;
        }

        return true;
    }

}
