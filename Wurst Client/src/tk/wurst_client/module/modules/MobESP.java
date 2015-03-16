/*
 * Copyright � 2014 - 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import tk.wurst_client.event.EventManager;
import tk.wurst_client.event.listeners.RenderListener;
import tk.wurst_client.module.Mod;
import tk.wurst_client.utils.RenderUtils;

public class MobESP extends Mod implements RenderListener
{
	public MobESP()
	{
		super(
			"MobESP",
			"Allows you to see mobs through walls.",
			Category.RENDER);
	}
	
	@Override
	public void onEnable()
	{
		EventManager.addRenderListener(this);
	}
	
	@Override
	public void onRender()
	{
		for(Object entity : Minecraft.getMinecraft().theWorld.loadedEntityList)
			if(entity instanceof EntityLiving)
				RenderUtils.entityESPBox((Entity)entity, 0);
	}
	
	@Override
	public void onDisable()
	{
		EventManager.removeRenderListener(this);
	}
}
