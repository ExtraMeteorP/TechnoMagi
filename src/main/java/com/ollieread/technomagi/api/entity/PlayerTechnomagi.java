package com.ollieread.technomagi.api.entity;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.ability.PlayerAbilities;
import com.ollieread.technomagi.api.event.KnowledgeEvent.Chance;
import com.ollieread.technomagi.api.event.KnowledgeEvent.Progress.Pre;
import com.ollieread.technomagi.api.event.TechnomagiHooks;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.PlayerKnowledge;
import com.ollieread.technomagi.api.knowledge.research.IResearch;
import com.ollieread.technomagi.api.nanites.PlayerNanites;
import com.ollieread.technomagi.api.specialisation.Specialisation;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.network.PacketHandler;
import com.ollieread.technomagi.common.network.packets.MessageSyncPlayer;

import cpw.mods.fml.relauncher.Side;

public class PlayerTechnomagi implements IExtendedEntityProperties
{

    protected EntityPlayer player;
    protected Random rand = new Random();

    protected PlayerKnowledge playerKnowledge;
    protected PlayerNanites playerNanites;
    protected PlayerAbilities playerAbilities;

    protected Specialisation specialisation;

    public PlayerTechnomagi(EntityPlayer player)
    {
        this.player = player;
        this.playerKnowledge = new PlayerKnowledge(this);
        this.playerNanites = new PlayerNanites(this);
        this.playerAbilities = new PlayerAbilities(this);
    }

    public static final void register(EntityPlayer player)
    {
        player.registerExtendedProperties(TechnomagiApi.IDENT_PLAYER, new PlayerTechnomagi(player));
    }

    public static final PlayerTechnomagi get(EntityPlayer player)
    {
        return (PlayerTechnomagi) player.getExtendedProperties(TechnomagiApi.IDENT_PLAYER);
    }

    @Override
    public void init(Entity entity, World world)
    {

    }

    public void sync()
    {
        EntityPlayer player = getPlayer();

        if (!player.worldObj.isRemote) {
            PacketHandler.INSTANCE.sendTo(new MessageSyncPlayer(player), (EntityPlayerMP) player);
        }
    }

    public void syncAbilities()
    {
        playerAbilities.sync();
    }

    public void syncKnowledge()
    {
        playerKnowledge.sync();
    }

    public void syncNanites()
    {
        playerNanites.sync();
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        /**
         * Specialisation
         */
        if (this.specialisation != null) {
            compound.setString("Specialisation", this.specialisation.getName());
        }

        /**
         * Save the players knowledge data.
         */
        NBTTagCompound knowledgeCompound = new NBTTagCompound();
        playerKnowledge.saveNBTData(knowledgeCompound);
        compound.setTag("Knowledge", knowledgeCompound);

        /**
         * Save the players nanite data.
         */
        NBTTagCompound nanitesCompound = new NBTTagCompound();
        playerNanites.saveNBTData(nanitesCompound);
        compound.setTag("Nanites", nanitesCompound);

        /**
         * save the players ability data.
         */
        NBTTagCompound abilityCompound = new NBTTagCompound();
        playerAbilities.saveNBTData(abilityCompound);
        compound.setTag("Abilities", abilityCompound);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        /**
         * Specialisation
         */
        if (compound.hasKey("Specialisation")) {
            this.specialisation = TechnomagiApi.specialisation().getSpecialisation(compound.getString("Specialisation"));
        } else {
            this.specialisation = null;
        }

        /**
         * Load the players knowledge data.
         */
        playerKnowledge.loadNBTData(compound.getCompoundTag("Knowledge"));

        /**
         * Load the players nanite data.
         */
        playerNanites.loadNBTData(compound.getCompoundTag("Nanites"));

        /**
         * Load the players ability data.
         */
        playerAbilities.loadNBTData(compound.getCompoundTag("Abilities"));
    }

    public static void saveProxyData(EntityPlayer player)
    {
        PlayerTechnomagi technomage = PlayerTechnomagi.get(player);
        NBTTagCompound savedData = new NBTTagCompound();
        technomage.saveNBTData(savedData);

        CommonProxy.storeEntityData(getSaveKey(player), savedData);
    }

    public static void loadProxyData(EntityPlayer player)
    {
        PlayerTechnomagi technomage = PlayerTechnomagi.get(player);
        NBTTagCompound savedData = CommonProxy.getEntityData(getSaveKey(player));

        if (savedData != null) {
            technomage.loadNBTData(savedData);
        }

        technomage.sync();
    }

    public void copyFrom(PlayerTechnomagi technomage)
    {
        this.playerAbilities = technomage.playerAbilities;
        this.playerKnowledge = technomage.playerKnowledge;
        this.playerNanites = technomage.playerNanites;
        this.specialisation = technomage.specialisation;
    }

    protected static String getSaveKey(EntityPlayer player)
    {
        return player.getCommandSenderName() + ":" + TechnomagiApi.IDENT_PLAYER;
    }

    public PlayerKnowledge knowledge()
    {
        return playerKnowledge;
    }

    public PlayerNanites nanites()
    {
        return playerNanites;
    }

    public PlayerAbilities abilities()
    {
        return playerAbilities;
    }

    public void setSpecialisation(Specialisation specialisation)
    {
        this.specialisation = specialisation;
        this.nanites().setActive(true);
        sync();
    }

    public boolean hasSpecialised()
    {
        return specialisation != null;
    }

    public Specialisation getSpecialisation()
    {
        return this.specialisation;
    }

    public EntityPlayer getPlayer()
    {
        return this.player;
    }

    /**
     * Perform a piece of research.
     *
     * This checks to make sure that the knowledge hasn't already been
     * discovered, the research hasn't already been performed and whether or not
     * the research has been performed the maximum amount of times. It will also
     * automatically add knowledge progress.
     *
     * The chance of research succeeding is denoted by
     * {@link IResearch#getChance()}, but can be modified outside of this using
     * {@link Chance} on the {@link TechnomagiApi.EVENT_BUS}.
     *
     * @param technomage
     * @param research
     */
    public boolean performResearch(IResearch research, Knowledge knowledge)
    {
        Technomagi.debug("----");
        Technomagi.debug("Attempting Research");
        Technomagi.debug("Research: " + research.getName());
        Technomagi.debug("Knowledge: " + knowledge.getName());
        // Verify that the knowledge and research is usable
        if (playerKnowledge.canDiscover(knowledge) && playerKnowledge.canResearch(research)) {
            int chance = research.getChance();
            Technomagi.debug("Chance: " + chance);

            // Create and post the chance event, allowing the chance of
            // research completing to be modified
            Chance chanceEvent = TechnomagiHooks.researchChance(player, research, research.getChance());
            Technomagi.debug("Modified Chance: " + chanceEvent.chance);

            if (rand.nextInt(chanceEvent.chance) == 0) {
                Pre pre = TechnomagiHooks.preKnowledgeProgress(player, research, playerKnowledge.getKnowledgeProgress(research.getKnowledge()), research.getProgress());

                if (nanites().canDecreaseNanites(research.getProgress()) && nanites().canIncreaseData(research.getProgress())) {
                    if (!pre.isCanceled()) {
                        int progress = playerKnowledge.addResearch(research, pre.modifier);

                        if (progress > 0) {
                            nanites().decreaseNanites(progress);
                            nanites().increaseData(progress);

                            if (nanites().addKnowledgeProgress(knowledge.getName(), progress)) {
                                Technomagi.debug("Research Unlocked");
                                TechnomagiHooks.postKnowledgeProgress(player, research, nanites().getKnowledgeProgress(research.getKnowledge()), research.getProgress());
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public void update(Side side)
    {
        playerAbilities.update(side);
        playerNanites.update(side);
        playerKnowledge.update(side);
    }

}
