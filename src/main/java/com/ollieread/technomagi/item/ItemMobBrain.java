package com.ollieread.technomagi.item;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMobBrain extends ItemTMNBT
{
    @SideOnly(Side.CLIENT)
    private IIcon theIcon;
    private static final String __OBFID = "CL_00000070";

    public ItemMobBrain(String name)
    {
        super(name);

        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        super.registerIcons(register);

        theIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString() + "Preserved");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int dmg)
    {
        return dmg == 0 ? itemIcon : theIcon;
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
        if (world.isRemote) {
            return;
        }

        if (stack.getItemDamage() == 0) {
            int ticks = getTicks(stack);
            int life = getLife(stack);
            ticks++;

            if (ticks >= 84) {
                life--;
                ticks = 0;

                if (life <= 0) {
                    stack.stackSize--;
                    return;
                }
            }

            setTicks(stack, ticks);
            setLife(stack, life);
        }
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        stack.stackTagCompound = new NBTTagCompound();
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return getLife(stack) < 100;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return (double) 1 - ((double) getLife(stack) / (double) 100);
    }

    protected String getEntity(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);

        if (compound.hasKey("Entity")) {
            return compound.getString("Entity");
        }

        return null;
    }

    public static void setEntity(ItemStack stack, Class entityClass)
    {
        NBTTagCompound compound = getNBT(stack);

        if (ResearchRegistry.getBrainableEntities().contains(entityClass)) {
            compound.setString("Entity", (String) EntityList.classToStringMapping.get(entityClass));
            compound.setInteger("Ticks", 0);
            compound.setInteger("Life", 100);
        }
    }

    protected int getTicks(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);

        if (compound.hasKey("Ticks")) {
            return compound.getInteger("Ticks");
        }

        return 0;
    }

    protected void setTicks(ItemStack stack, int ticks)
    {
        NBTTagCompound compound = getNBT(stack);

        compound.setInteger("Ticks", ticks);
    }

    protected int getLife(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);

        if (compound.hasKey("Life")) {
            return compound.getInteger("Life");
        }

        return 0;
    }

    protected void setLife(ItemStack stack, int ticks)
    {
        NBTTagCompound compound = getNBT(stack);

        compound.setInteger("Life", ticks);
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        String entityName = getEntity(stack);

        if (entityName != null) {
            s = StatCollector.translateToLocal("entity." + entityName + ".name") + " " + s;
        }

        return s;
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        List<Class> brainable = ResearchRegistry.getBrainableEntities();

        for (Iterator<Class> i = brainable.iterator(); i.hasNext();) {
            Class entity = i.next();
            ItemStack stack = new ItemStack(item, 1);
            setEntity(stack, entity);
            list.add(stack);
        }
    }

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return 1;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemStack)
    {
        return 1;
    }

}