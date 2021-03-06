package com.ollieread.technomagi.tileentity.component;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.ollieread.technomagi.world.region.RegionManager.RegionControllerType;
import com.ollieread.technomagi.world.region.RegionPayload;

public class ComponentRegionPresence extends ComponentRegion
{

    protected List<Class> allowedEntities = new ArrayList<Class>();
    protected boolean spawn = true;

    public ComponentRegionPresence(TileEntity tileEntity)
    {
        super(tileEntity);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        spawn = compound.getBoolean("Spawn");

        NBTTagList entityList = compound.getTagList("Entities", compound.getId());
        allowedEntities = new ArrayList<Class>();

        for (int i = 0; i < entityList.tagCount(); i++) {
            NBTTagCompound entity = entityList.getCompoundTagAt(i);
            try {
                allowedEntities.add(Class.forName(entity.getString("Entity")));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("Spawn", spawn);

        NBTTagList entityList = new NBTTagList();

        for (Class entity : allowedEntities) {
            NBTTagCompound entityCompound = new NBTTagCompound();
            entityCompound.setString("Entity", entity.getName());
            entityList.appendTag(entityCompound);
        }

        compound.setTag("Entities", entityList);
    }

    @Override
    public RegionControllerType getType()
    {
        return RegionControllerType.PRESENCE;
    }

    @Override
    public void perform(RegionPayload payload)
    {
        // TODO Auto-generated method stub

    }

}
