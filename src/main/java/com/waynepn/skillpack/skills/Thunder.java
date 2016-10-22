package com.waynepn.skillpack.skills;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;

import com.sucy.skill.api.skills.Skill;
import com.sucy.skill.api.skills.TargetSkill;
import com.sucy.skill.api.skills.SkillAttribute;

public class Thunder extends Skill implements TargetSkill {
	public static Integer MAX_LEVEL = 5;
	public static String NAME = "Thunder";
	public static Double COOLDOWN = 10.0;
	public static Integer COST_BASE = 1;
	public static Integer COST_SCALE = 0;
	public static Integer LEVEL = 1;
	public static Double MANA_BASE = 0.0;
	public static Double MANA_SCALE = 2.0;
	public static Double RANGE_BASE = 5.0;
	public static Double RANGE_SCALE = 1.0;
	
	private static Logger log = Logger.getLogger(Thunder.class.toString());
	
	public Thunder() {
		super(NAME, "Target",  Material.NETHER_STAR , 5);
		
		this.settings.set(SkillAttribute.COOLDOWN, Thunder.COOLDOWN);
		this.settings.set(SkillAttribute.COST , Thunder.COST_BASE, Thunder.COST_SCALE);
		this.settings.set(SkillAttribute.LEVEL, Thunder.LEVEL);
		this.settings.set(SkillAttribute.MANA, Thunder.MANA_BASE, Thunder.MANA_SCALE);
		this.settings.set(SkillAttribute.RANGE, RANGE_BASE, RANGE_SCALE);	
	}

	@Override
	public boolean cast(LivingEntity user, LivingEntity target, int level, boolean ally) {
		if(ally) return false;	
		
		target.getWorld().strikeLightning(target.getLocation());
		target.damage(this.settings.getBase("Damage"));	
		
		return true;
	}
}
