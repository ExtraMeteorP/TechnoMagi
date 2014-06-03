package com.ollieread.technomagi.ability;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.ability.AbilityActive;
import com.ollieread.technomagi.api.ability.IAbilityActive;
import com.ollieread.technomagi.player.PlayerKnowledge;

public class ActiveAbilityFireball extends AbilityActive {

	public ActiveAbilityFireball(String name) {
		super(name);
	}

	@Override
	public boolean canUse(PlayerKnowledge charon)
	{
		return true;
	}

	@Override
	public boolean isAvailable(PlayerKnowledge charon)
	{
		return false;
	}

	@Override
	public void use(PlayerKnowledge charon)
	{
		
	}

}
