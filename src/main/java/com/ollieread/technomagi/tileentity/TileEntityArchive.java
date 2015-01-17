package com.ollieread.technomagi.tileentity;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.item.ItemResearchStorage;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityMachineResearch;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityBasic;
import com.ollieread.technomagi.tileentity.component.ComponentInventory;
import com.ollieread.technomagi.tileentity.component.ComponentOwner;

public class TileEntityArchive extends TileEntityBasic implements ITileEntityHasOwner, IInventory, ITileEntityFacing
{

    protected ComponentOwner owner = new ComponentOwner();
    protected ComponentInventory inventory = new ComponentInventory(1);
    protected int facing;

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

    protected int syncCheck = 0;
    protected int guiType = 0;
    protected int guiSubType = 0;
    protected int guiPage = 0;

    public void setFacing(int side)
    {
        facing = side;
    }

    public int getFacing()
    {
        return facing;
    }

    public void updateEntity()
    {
        super.updateEntity();
        this.field_145927_n = this.field_145930_m;
        this.field_145925_p = this.field_145928_o;
        EntityPlayer entityplayer = this.worldObj.getClosestPlayer((double) ((float) this.xCoord + 0.5F), (double) ((float) this.yCoord + 0.5F), (double) ((float) this.zCoord + 0.5F), 8.5D);

        if (entityplayer != null && isOwner(entityplayer.getCommandSenderName())) {
            double d0 = entityplayer.posX - (double) ((float) this.xCoord + 0.5F);
            double d1 = entityplayer.posZ - (double) ((float) this.zCoord + 0.5F);
            this.field_145924_q = (float) Math.atan2(d1, d0);
            this.field_145930_m += 0.1F;

            if (this.field_145930_m < 0.5F || field_145923_r.nextInt(40) == 0) {
                float f1 = this.field_145932_k;

                do {
                    this.field_145932_k += (float) (field_145923_r.nextInt(4) - field_145923_r.nextInt(4));
                } while (f1 == this.field_145932_k);
            }
        } else {
            this.field_145924_q += 0.02F;
            this.field_145930_m -= 0.1F;
        }

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
            if (syncCheck == 50) {
                if (canSyncPlayer()) {
                    syncPlayer();
                }

                syncMachine();
                syncCheck = 0;
            }
            syncCheck++;
        }
    }

    public boolean canSyncPlayer()
    {
        EntityPlayer owner = getOwner(worldObj);

        if (owner != null && owner.getDistance(xCoord, yCoord, zCoord) <= 8) {
            return true;
        }

        return false;
    }

    protected void syncPlayer()
    {
        EntityPlayer owner = getOwner(worldObj);

        if (owner == null) {
            // TechnoMagi.logger.error("Something went horribly wrong and the Archive no longer has an owner");
            // worldObj.func_147480_a(xCoord, yCoord, zCoord, true);
            return;
        }

        ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(owner);

        if (playerKnowledge != null && playerKnowledge.nanites != null) {
            Map<String, Integer> researchingKnowledge = playerKnowledge.nanites.getResearchingKnowledge();

            if (researchingKnowledge.size() > 0) {
                for (Iterator<String> i = researchingKnowledge.keySet().iterator(); i.hasNext();) {
                    String knowledge = i.next();
                    int value = researchingKnowledge.get(knowledge);

                    if (playerKnowledge.hasKnowledge(knowledge)) {
                        playerKnowledge.nanites.decreaseData(value, knowledge);
                        continue;
                    }

                    if (value > 0) {
                        if (value >= 5 && playerKnowledge.nanites.decreaseData(5, knowledge)) {
                            playerKnowledge.addKnowledgeProgress(knowledge, 5);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void syncMachine()
    {
        EntityPlayer owner = getOwner(worldObj);

        if (owner == null) {
            // TechnoMagi.logger.error("Something went horribly wrong and the Archive no longer has an owner");
            // worldObj.func_147480_a(xCoord, yCoord, zCoord, true);
            return;
        }

        ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(owner);

        if (playerKnowledge != null && playerKnowledge.nanites != null) {
            for (int x = (xCoord - 8); x < (xCoord + 8); x++) {
                for (int y = (yCoord - 8); y < (yCoord + 8); y++) {
                    for (int z = (zCoord - 8); z < (zCoord + 8); z++) {
                        TileEntity tile = worldObj.getTileEntity(x, y, z);

                        if (tile != null && tile instanceof TileEntityMachineResearch) {
                            TileEntityMachineResearch machine = (TileEntityMachineResearch) tile;

                            if (machine.getData() > 0) {
                                Map<String, Integer> research = machine.getResearch();
                                for (Iterator<String> it = research.keySet().iterator(); it.hasNext();) {
                                    String name = it.next();
                                    int value = research.get(name);
                                    IResearch knowledge = ResearchRegistry.getResearch(name);

                                    if (playerKnowledge.hasKnowledge(knowledge.getName())) {
                                        machine.removeResearch(name);
                                        continue;
                                    }

                                    if (value > 0) {
                                        if (value >= 5) {
                                            machine.decreaseResearch(name, 5);
                                            playerKnowledge.addKnowledgeProgress(knowledge.getName(), 5);
                                            return;
                                        } else {
                                            machine.decreaseResearch(name, value);
                                            playerKnowledge.addKnowledgeProgress(knowledge.getName(), value);
                                            return;
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

    public boolean syncStorage(ItemStack stack)
    {
        if (stack != null && stack.getItem() != null && stack.getItem() instanceof ItemResearchStorage) {
            ItemResearchStorage storage = (ItemResearchStorage) stack.getItem();

            if (storage.getTotal(stack) > 0) {
                Map<String, Integer> researchingKnowledge = storage.getResearch(stack);

                if (researchingKnowledge.size() > 0) {

                    EntityPlayer owner = getOwner(worldObj);
                    ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(owner);

                    if (playerKnowledge != null && playerKnowledge.nanites != null) {
                        for (Iterator<String> i = researchingKnowledge.keySet().iterator(); i.hasNext();) {
                            String knowledge = i.next();
                            int value = researchingKnowledge.get(knowledge);

                            playerKnowledge.addKnowledgeProgress(knowledge, value);
                        }

                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        syncCheck = compound.getInteger("SyncCheck");
        facing = compound.getInteger("Facing");

        owner.readFromNBT(compound.getCompoundTag("Owner"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("SyncCheck", syncCheck);
        compound.setInteger("Facing", facing);

        NBTTagCompound ownerCompound = new NBTTagCompound();

        owner.writeToNBT(ownerCompound);
    }

    public TileEntityArchive setType(int type)
    {
        guiType = type;

        return this;
    }

    public TileEntityArchive setSubType(int subtype)
    {
        guiSubType = subtype;

        return this;
    }

    public TileEntityArchive setPage(int page)
    {
        guiPage = page;

        return this;
    }

    public int getType()
    {
        return guiType;
    }

    public int getSubType()
    {
        return guiSubType;
    }

    public int getPage()
    {
        return guiPage;
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

    /* OWNER */

    @Override
    public boolean isOwner(String name)
    {
        return owner.isOwner(name);
    }

    @Override
    public void setOwner(String name)
    {
        owner.setOwner(name);
    }

    @Override
    public EntityPlayer getOwner(World world)
    {
        return owner.getOwner(world);
    }

}
