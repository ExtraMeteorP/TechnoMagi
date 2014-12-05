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
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import cofh.lib.util.helpers.EnergyHelper;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.IResearchObservation;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.proxy.BasicEnergy;
import com.ollieread.technomagi.common.proxy.BasicInventory;
import com.ollieread.technomagi.common.proxy.PlayerLocked;

public class TileEntityObservationChamber extends TileEntityResearch implements IHasFiller, IPlayerLocked, IInventory, IEnergyHandler
{
    protected BasicEnergy storage = null;
    protected PlayerLocked locked = null;
    protected BasicInventory inventory = null;

    public int field_145926_a;
    public float field_145933_i;
    public float field_145931_j;
    public float field_145932_k;
    public float field_145929_l;
    public float field_145930_m;
    public float field_145927_n;
    public float field_145928_o;
    public float field_145925_p;
    public float field_145924_q;
    private static Random field_145923_r = new Random();

    protected String entity = null;
    protected int health = 0;
    protected Random rand = new Random();
    protected NBTTagCompound entityNBT;

    protected int maxProgress;
    protected int usage;
    protected int checking = 0;

    public TileEntityObservationChamber()
    {
        locked = new PlayerLocked();
        inventory = new BasicInventory(2);
        storage = new BasicEnergy(6400, 5, 0);

        maxProgress = 200;
        usage = 3;
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

    public int getProgress(int width)
    {
        return progress / (maxProgress / width);
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

        locked.readFromNBT(compound);
        inventory.readFromNBT(compound);
        storage.readFromNBT(compound);

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

        locked.writeToNBT(compound);
        inventory.writeToNBT(compound);
        storage.writeToNBT(compound);

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
            ((TileEntityEmptyFiller) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord)).setParent(xCoord, yCoord, zCoord);
            ((TileEntityEmptyFiller) worldObj.getTileEntity(xCoord, yCoord + 2, zCoord)).setParent(xCoord, yCoord, zCoord);
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

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        this.field_145927_n = this.field_145930_m;
        this.field_145925_p = this.field_145928_o;
        this.field_145924_q += 0.02F;
        this.field_145930_m -= 0.1F;

        while (this.field_145928_o >= (float) Math.PI) {
            this.field_145928_o -= ((float) Math.PI * 2F);
        }

        while (this.field_145928_o < -(float) Math.PI) {
            this.field_145928_o += ((float) Math.PI * 2F);
        }

        while (this.field_145924_q >= (float) Math.PI) {
            this.field_145924_q -= ((float) Math.PI * 2F);
        }

        while (this.field_145924_q < -(float) Math.PI) {
            this.field_145924_q += ((float) Math.PI * 2F);
        }

        float f2;

        for (f2 = this.field_145924_q - this.field_145928_o; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F)) {
            ;
        }

        while (f2 < -(float) Math.PI) {
            f2 += ((float) Math.PI * 2F);
        }

        this.field_145928_o += f2 * 0.4F;

        if (this.field_145930_m < 0.0F) {
            this.field_145930_m = 0.0F;
        }

        if (this.field_145930_m > 1.0F) {
            this.field_145930_m = 1.0F;
        }

        ++this.field_145926_a;
        this.field_145931_j = this.field_145933_i;
        float f = (this.field_145932_k - this.field_145933_i) * 0.4F;
        float f3 = 0.2F;

        if (f < -f3) {
            f = -f3;
        }

        if (f > f3) {
            f = f3;
        }

        this.field_145929_l += (f - this.field_145929_l) * 0.9F;
        this.field_145933_i += this.field_145929_l;

        if (!worldObj.isRemote) {
            if (entity != null && !entity.isEmpty()) {
                if (storage.getEnergyStored(null) >= usage) {
                    if (storage.modifyEnergyStored(usage)) {
                        checking = 0;

                        if (canTest()) {
                            setInProgress(true);
                            test();
                        } else {
                            progress = 0;
                        }
                    }
                } else {
                    checking++;

                    if (checking == 100) {
                        release();
                        checking = 0;
                    }
                }
            }

            if (EnergyHelper.isAdjacentEnergyHandlerFromSide(this, ForgeDirection.DOWN.ordinal())) {
                int input = storage.getMaxReceive();
                int receive = storage.receiveEnergy(ForgeDirection.DOWN, input, true);
                int extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), receive, true);

                if (receive > 0 && extract > 0) {

                    if (receive == extract) {
                        extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), receive, false);
                    } else if (receive > extract) {
                        extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), extract, false);
                    } else if (receive < extract) {
                        extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), receive, false);
                    }
                    receiveEnergy(ForgeDirection.DOWN, extract, false);
                }
            }
        }
    }

    public boolean canTest()
    {
        if (!worldObj.isRemote) {
            ItemStack input = inventory.getStackInSlot(0);

            if (input != null && input.getItem() != null) {
                EntityPlayer player = worldObj.getPlayerEntityByName(getPlayer());

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

    public void test()
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

                EntityPlayer player = worldObj.getPlayerEntityByName(getPlayer());

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

                                            if (storage.modifyEnergyStored(usage * 2) && rand.nextInt(research.getChance()) == 0 && !charon.hasResearched(research.getName()) && research.canPerform(charon) && (data + research.getProgress() < 100)) {
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
        setEntity(null);

        sync();
    }

    /* Everything below is just a proxy for the interfaces */

    /* INVENTORY */

    @Override
    public int getSizeInventory()
    {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int i, int q)
    {
        return inventory.decrStackSize(i, q);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        return inventory.getStackInSlotOnClosing(i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack)
    {
        inventory.setInventorySlotContents(i, stack);
    }

    @Override
    public String getInventoryName()
    {
        return inventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return inventory.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public void openInventory()
    {

    }

    @Override
    public void closeInventory()
    {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack)
    {
        return inventory.isItemValidForSlot(i, stack);
    }

    /* ENERGY */

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return from.equals(ForgeDirection.DOWN);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        int r = storage.receiveEnergy(ForgeDirection.DOWN, maxReceive, simulate);

        if (r > 0) {
            sync();
            return r;
        }

        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        int r = storage.extractEnergy(ForgeDirection.DOWN, maxExtract, simulate);

        if (r > 0) {
            sync();
            return r;
        }

        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return storage.getEnergyStored(null);
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return storage.getMaxEnergyStored(null);
    }

    /* LOCKED */

    @Override
    public boolean hasPlayer()
    {
        return locked.hasPlayer();
    }

    @Override
    public void setPlayer(String name)
    {
        locked.setPlayer(name);
    }

    @Override
    public String getPlayer()
    {
        return locked.getPlayer();
    }

    @Override
    public boolean isPlayer(String name)
    {
        return locked.isPlayer(name);
    }

    public boolean isPlayer(EntityPlayer player)
    {
        return isPlayer(player.getCommandSenderName());
    }

}
