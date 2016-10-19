package com.waynepn.skillpack.skills;

import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;

import com.sucy.skill.api.skills.Skill;
import com.sucy.skill.api.skills.SkillAttribute;
import com.sucy.skill.api.skills.TargetSkill;

public class Thunder extends Skill implements TargetSkill {
	public static Integer MAX_LEVEL = 5;
	public static String NAME = "Thunder";
	public static Double COOLDOWN = 10.0;
	public static Integer COST_BASE = 1;
	public static Integer COST_SCALE = 0;
	public static Integer LEVEL = 1;
	public static Double MANA_BASE = 10.0;
	public static Double MANA_SCALE = 2.0;
	public static Double RANGE = 5.0;
	
	
	public Thunder() {
		super(NAME, "",  Material.NETHER_STAR , Thunder.MAX_LEVEL);
		
		this.settings.set(SkillAttribute.COOLDOWN, Thunder.COOLDOWN);
		this.settings.set(SkillAttribute.COST , Thunder.COST_BASE, Thunder.COST_SCALE);
		this.settings.set(SkillAttribute.LEVEL, Thunder.LEVEL);
		this.settings.set(SkillAttribute.MANA, Thunder.MANA_BASE, Thunder.MANA_SCALE);
		this.settings.set(SkillAttribute.RANGE, Thunder.RANGE);
		
		this.settings.set("Damage", 5);
		
	}

	@Override
	public boolean cast(LivingEntity user, LivingEntity target, int level, boolean ally) {
		Log.debug("Ally: " + ally);
		if(ally) return false;
	
		target.getWorld().spawnEntity(target.getLocation(),  EntityType.LIGHTNING);
		target.getWorld().spawn(target.getLocation(),  LightningStrike.class);
		target.damage(this.settings.getBase("Damage"));
		
		return true;
	}

}
