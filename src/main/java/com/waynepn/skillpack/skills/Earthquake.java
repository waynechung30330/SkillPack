package com.waynepn.skillpack.skills;


import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;

import com.sucy.skill.api.skills.Skill;
import com.sucy.skill.api.skills.SkillAttribute;
import com.sucy.skill.api.skills.SkillShot;

public class Earthquake extends Skill implements SkillShot {
	public static String NAME = "Earthquake";
	public static Double DAMAGE_BASE = 10.0;
	public static Double DAMAGE_SCALE = 2.0;
	private static Logger log = Logger.getLogger(Earthquake.class.toString());

	public Earthquake() {
		super(NAME, "AOE", Material.DIRT, 1);
		
		this.settings.set(SkillAttribute.LEVEL , 1, 0);
		this.settings.set(SkillAttribute.COOLDOWN , 1, 0);
		this.settings.set(SkillAttribute.MANA , 0, 0);
		this.settings.set(SkillAttribute.RANGE, 5, 1);
	
	}

	@Override
	public boolean cast(LivingEntity user, int level) {
		List<Entity> entities = user.getNearbyEntities(5.0, 5.0, 5.0);
		for(Entity e : entities){
			log.info(e.getCustomName());
			if(e instanceof Monster){
				e = (Monster)e;
			}
		}
		
		
		
		return true;
	}

}
