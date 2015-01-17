package com.ollieread.technomagi.tileentity;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.IResearchObservation;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.item.ItemCapture;
import com.ollieread.technomagi.tileentity.abstracts.MachineResearch;
import com.ollieread.technomagi.tileentity.component.IHasFiller;
import com.ollieread.technomagi.tileentity.component.Inventory;

public class TileEntityMachineObservation extends MachineResearch implements IHasFiller
{

    protected String entity = null;
    protected int health = 0;
    protected Random rand = new Random();
    protected NBTTagCompound entityNBT;

    protected int checking = 0;

    public TileEntityMachineObservation()
    {
        super(6400, 5, 0, 100, new Inventory(2));

        setMaxProgress(200);
        setUsage(3);
    }

    @Override
    public boolean isConnected()
    {
        return false;
    }

    @Override
    public Container getContainer(InventoryPlayer playerInventory, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public int getGui(World world, int x, int y, int z, EntityPlayer player)
    {
        return -1;
    }

    public boolean activated(EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (player != null) {
            ItemStack stack = player.getHeldItem();
            if (isOwner(player.getCommandSenderName())) {
                if (stack != null && stack.isItemEqual(new ItemStack(Items.itemCapture, 1, 1))) {
                    ItemCapture capture = (ItemCapture) stack.getItem();

                    if (capture.hasEntity(stack)) {
                        String name = capture.getName(stack);
                        String entityName = capture.getEntityName(stack);

                        if (entityName != null) {
                            EntityLiving entityLiving = (EntityLiving) EntityList.createEntityByName(entityName, worldObj);

                            if (entityLiving != null) {
                                entityLiving.readEntityFromNBT(ItemCapture.getEntityNBT(stack));

                                if (name != null) {
                                    entityLiving.setCustomNameTag(name);
                                }

                                capture.setEntity(null, stack);
                                if (ResearchRegistry.getObservableEntities().contains(entityLiving.getClass())) {
                                    setEntity(entityLiving);
                                    stack.stackSize--;

                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean isProcessing()
    {
        return inProgress();
    }

    public void setEntity(EntityLivingBase entity)
    {
        if (entity != null) {
            if (ResearchRegistry.getObservableEntities().contains(entity.getClass())) {
                this.entity = (String) EntityList.classToStringMapping.get(entity.getClass());
                this.entityNBT = new NBTTagCompound();
                entity.writeEntityToNBT(entityNBT);
                health = (int) entity.getHealth();

                return;
            }
        }

        this.entityNBT = new NBTTagCompound();
        this.entity = null;
        health = 0;
    }

    public String getEntity()
    {
        return entity;
    }

    public void setHealth(int m)
    {
        health += m;

        if (health <= 0) {
            setEntity(null);
        }
    }

    public boolean hasEntity()
    {
        return entity != null;
    }

    public EntityLiving getEntityLiving()
    {
        if (entity != null && !entity.isEmpty()) {
            EntityLiving entityLiving = (EntityLiving) EntityList.createEntityByName(entity, worldObj);
            entityLiving.readEntityFromNBT(entityNBT);

            return entityLiving;
        }

        return null;
    }

    public Class getEntityClass()
    {
        if (entity != null && !entity.isEmpty()) {
            Class entityClass = (Class) EntityList.stringToClassMapping.get(entity);

            return entityClass;
        }

        return null;
    }

    public int getHealth()
    {
        return health;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        health = compound.getInteger("Health");

        if (!compound.hasKey("Empty")) {
            entity = compound.getString("Entity");
            entityNBT = compound.getCompoundTag("EntityNBT");
            checking = compound.getInteger("Waiting");
        } else {
            entityNBT = new NBTTagCompound();
            entity = null;
            health = 0;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Health", health);

        if (entity != null) {
            compound.setString("Entity", entity);
            compound.setTag("EntityNBT", entityNBT);
            compound.setInteger("Waiting", checking);
        } else {
            compound.setString("Empty", "");
        }
    }

    @Override
    public void create()
    {
        worldObj.setBlock(xCoord, yCoord + 1, zCoord, Blocks.blockEmptyFiller);
        worldObj.setBlock(xCoord, yCoord + 2, zCoord, Blocks.blockEmptyFiller);

        if (!worldObj.isRemote) {
            ((TileEntityFiller) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord)).setParent(xCoord, yCoord, zCoord);
            ((TileEntityFiller) worldObj.getTileEntity(xCoord, yCoord + 2, zCoord)).setParent(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void destroy()
    {
        worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
        worldObj.setBlockToAir(xCoord, yCoord + 2, zCoord);
        invalidate();
        worldObj.setBlockToAir(xCoord, yCoord, zCoord);
    }

    public boolean canProcess()
    {
        if (!worldObj.isRemote) {
            ItemStack input = inventory.getStackInSlot(0);

            if (input != null && input.getItem() != null) {
                EntityPlayer player = getOwner(worldObj);

                if (player != null) {
                    ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

                    if (!charon.canSpecialise()) {
                        Map<Class, Map<ItemStack, List<Integer>>> researchObservation = ResearchRegistry.getObservableResearch();
                        Class entityClass = (Class) EntityList.stringToClassMapping.get(entity);

                        if (researchObservation.containsKey(entityClass)) {
                            for (Iterator<Entry<ItemStack, List<Integer>>> i = researchObservation.get(entityClass).entrySet().iterator(); i.hasNext();) {
                                Entry<ItemStack, List<Integer>> current = i.next();

                                if (current.getKey().isItemEqual(input)) {
                                    List<Integer> list = current.getValue();

                                    for (int x = 0; x < list.size(); x++) {
                                        if (list.get(x) != null) {
                                            IResearch research = ResearchRegistry.getResearch(ResearchRegistry.getResearchName(list.get(x)));
                                            IResearchObservation observation = (IResearchObservation) research;

                                            if (!charon.hasResearched(research.getName()) && research.canPerform(charon) && (data + research.getProgress() < 100)) {
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public void process()
    {
        if (!worldObj.isRemote) {
            progress++;

            if (progress >= maxProgress) {
                ItemStack input = inventory.getStackInSlot(0);

                if (input == null || input.getItem() == null) {
                    setInProgress(false);
                    progress = 0;
                    return;
                }

                EntityPlayer player = getOwner(worldObj);

                if (player != null) {
                    ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

                    if (!charon.canSpecialise()) {
                        Map<Class, Map<ItemStack, List<Integer>>> researchObservation = ResearchRegistry.getObservableResearch();
                        Class entityClass = (Class) EntityList.stringToClassMapping.get(entity);

                        if (researchObservation.containsKey(entityClass)) {
                            for (Iterator<Entry<ItemStack, List<Integer>>> i = researchObservation.get(entityClass).entrySet().iterator(); i.hasNext();) {
                                Entry<ItemStack, List<Integer>> current = i.next();

                                if (input == null) {
                                    break;
                                }

                                if (current.getKey().isItemEqual(input)) {
                                    List<Integer> list = current.getValue();

                                    for (int x = 0; x < list.size(); x++) {
                                        if (list.get(x) != null) {
                                            IResearch research = ResearchRegistry.getResearch(ResearchRegistry.getResearchName(list.get(x)));
                                            IResearchObservation observation = (IResearchObservation) research;

                                            if (energy.modifyEnergyStored(usage * 2) && rand.nextInt(research.getChance()) == 0 && !charon.hasResearched(research.getName()) && research.canPerform(charon) && (data + research.getProgress() < 100)) {
                                                addResearch(research.getKnowledge(), research.getProgress());

                                                if (!research.isRepeating()) {
                                                    charon.addNonRepeatibleResearch(research.getName());
                                                }

                                                setHealth(observation.getModifiedHealth());
                                            }

                                            input.stackSize--;

                                            if (input.stackSize == 0) {
                                                input = null;
                                            }

                                            inventory.setInventorySlotContents(0, input);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                setInProgress(false);
                progress = 0;
            }

            sync();
        }
    }

    public void release()
    {
        EntityLiving release = getEntityLiving();
        ForgeDirection dir = ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));

        release.setHealth(health);
        release.setWorld(worldObj);
        release.setPosition(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
        worldObj.spawnEntityInWorld(release);
        setEntity(null);
        sync();
    }

    public void flush()
    {
        EntityLiving flush = getEntityLiving();
        ForgeDirection dir = ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));

        flush.setHealth(health);
        flush.setWorld(worldObj);
        flush.setPosition(xCoord, yCoord, zCoord);
        setEntity(null);
        worldObj.spawnEntityInWorld(flush);
        flush.attackEntityFrom(DamageSource.wither, 300F);

        sync();
    }

}
