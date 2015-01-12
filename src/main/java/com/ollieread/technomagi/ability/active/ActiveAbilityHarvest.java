package com.ollieread.technomagi.ability.active;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.MovingObjectPosition;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityPayload;
import com.ollieread.ennds.ability.AbilityPayload.AbilityUseTarget;
import com.ollieread.ennds.ability.AbilityPayload.AbilityUseType;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;

public class ActiveAbilityHarvest extends AbilityActive
{

    protected int cost;

    public ActiveAbilityHarvest(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        cost = Config.harvestCost;
    }

    @Override
    public int getMaxFocus()
    {
        return 100;
    }

    @Override
    public boolean canIntervalFocus()
    {
        return true;
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon, AbilityPayload cast)
    {
        return cast.type.equals(AbilityUseType.FOCUS) && cast.target.equals(AbilityUseTarget.BLOCK);
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityPayload cast, ItemStack staff)
    {
        EntityPlayer player = charon.player;
        int x = cast.blockX;
        int y = cast.blockY;
        int z = cast.blockZ;
        int meta = player.worldObj.getBlockMetadata(x, y, z);

        MovingObjectPosition mouse = Minecraft.getMinecraft().objectMouseOver;

        if (mouse.blockX != x || mouse.blockY != y || mouse.blockZ != z) {
            return false;
        }

        Block block = player.worldObj.getBlock(x, y, z);
        int level = 3;

        if (block != null && block.getHarvestLevel(meta) <= level) {
            String tool = block.getHarvestTool(meta);
            int h = 10 - ((level - 1) * 2);

            if (cast.duration > h) {
                int harvested = cast.duration / h;

                if ((cast.duration % 20) == 0) {
                    decreaseNanites(charon, 2);
                }

                if (harvested < 10) {
                    player.worldObj.destroyBlockInWorldPartially(Block.getIdFromBlock(block), x, y, z, harvested);
                } else {
                    decreaseNanites(charon, 5);

                    if (!player.worldObj.isRemote) {
                        block.onBlockHarvested(player.worldObj, x, y, z, meta, player);
                        block.onBlockDestroyedByPlayer(player.worldObj, x, y, z, meta);
                        block.harvestBlock(player.worldObj, player, x, y, z, meta);
                        player.worldObj.setBlockToAir(x, y, z);

                        EntityPlayerMP mp = (EntityPlayerMP) player;
                        mp.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, player.worldObj));
                    } else {
                        PlayerControllerMP con = Minecraft.getMinecraft().playerController;
                        player.worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta));
                        block.onBlockDestroyedByPlayer(player.worldObj, x, y, z, meta);
                    }

                    cast.duration = cast.maxDuration;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public void stoppedUsing(ExtendedPlayerKnowledge charon, AbilityPayload cast, ItemStack staff)
    {
        if (cast != null) {
            int x = cast.blockX;
            int y = cast.blockY;
            int z = cast.blockZ;
            Block block = charon.player.worldObj.getBlock(x, y, z);
            charon.player.worldObj.destroyBlockInWorldPartially(Block.getIdFromBlock(block), x, y, z, -1);
        }
    }

}
