/*
 * Copyright � 2014 - 2016 | Wurst-Imperium | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.mods;

import net.minecraft.entity.EntityLivingBase;
import tk.wurst_client.events.listeners.UpdateListener;
import tk.wurst_client.utils.EntityUtils;

@Mod.Info(
	description = "A bot that follows the closest entity.\n" + "Very annoying.",
	name = "Follow",
	tutorial = "Mods/Follow")
public class FollowMod extends Mod implements UpdateListener
{
	private EntityLivingBase entity;
	private float range = 12F;
	
	@Override
	public String getRenderName()
	{
		if(entity != null)
			return "Following " + entity.getName();
		else
			return "Follow";
	}
	
	@Override
	public void onEnable()
	{
		entity = null;
		EntityLivingBase en = EntityUtils.getClosestEntity(false, true);
		if(en != null && mc.player.getDistanceToEntity(en) <= range)
			entity = en;
		wurst.events.add(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		if(entity == null)
		{
			setEnabled(false);
			return;
		}
		if(entity.isDead || mc.player.isDead)
		{
			entity = null;
			setEnabled(false);
			return;
		}
		double xDist = Math.abs(mc.player.posX - entity.posX);
		double zDist = Math.abs(mc.player.posZ - entity.posZ);
		EntityUtils.faceEntityClient(entity);
		if(xDist > 1D || zDist > 1D)
			mc.gameSettings.keyBindForward.pressed = true;
		else
			mc.gameSettings.keyBindForward.pressed = false;
		if(mc.player.isCollidedHorizontally && mc.player.onGround)
			mc.player.jump();
		if(mc.player.isInWater() && mc.player.posY < entity.posY)
			mc.player.motionY += 0.04;
	}
	
	@Override
	public void onDisable()
	{
		wurst.events.remove(UpdateListener.class, this);
		if(entity != null)
			mc.gameSettings.keyBindForward.pressed = false;
	}
	
	public void setEntity(EntityLivingBase entity)
	{
		this.entity = entity;
	}
}
