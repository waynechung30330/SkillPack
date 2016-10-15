package com.waynepn.skillpack.skills;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import com.sucy.skill.api.skills.Skill;
import com.sucy.skill.api.skills.SkillAttribute;
import com.sucy.skill.api.skills.SkillShot;

public class ArrowTNT extends Skill implements SkillShot, Listener {

	public static String NAME = "ArrowTNT";
	public static Double DAMAGE_BASE = 10.0;
	public static Double DAMAGE_SCALE = 2.0;
	public static Float RANGE_BASE = 2.0f;
	public static Float RANGE_SCALE = 0.5f;
	public static Integer MAX_LEVEL = 5;
	public static Plugin plugin = Bukkit.getPluginManager().getPlugin("SkillPack");	

	public ArrowTNT() {
		super(NAME, "AOE", Material.TNT , MAX_LEVEL);
				
		this.settings.set(SkillAttribute.LEVEL , 1, 0);
		this.settings.set(SkillAttribute.COOLDOWN , 1, 0);
		this.settings.set(SkillAttribute.MANA , 0, 0);
		this.settings.set(SkillAttribute.RANGE, RANGE_BASE, RANGE_SCALE);
	}

	@Override
	public boolean cast(LivingEntity user, int level) {		
		Arrow arrow = user.launchProjectile(Arrow.class);		
		arrow.setMetadata(NAME, new FixedMetadataValue(plugin, level));        
		return true;		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST) 
    public void onProjectileHit(ProjectileHitEvent event) {		
        Entity e = event.getEntity();
        if (e.hasMetadata(NAME)) {
        	int level = event.getEntity().getMetadata(NAME).get(0).asInt();
	        TNTPrimed tnt = e.getWorld().spawn(e.getLocation(), TNTPrimed.class);
	        tnt.setFuseTicks(0);	        
			tnt.setMetadata(NAME, new FixedMetadataValue(plugin, level));
        }
        e.remove();
    }
	
	@EventHandler(priority = EventPriority.HIGHEST) 
    public void onExplosionPrime(ExplosionPrimeEvent event) {	
		if(event.getEntity() instanceof TNTPrimed && event.getEntity().hasMetadata(NAME)){
			int level = event.getEntity().getMetadata(NAME).get(0).asInt();
			Float range = RANGE_BASE+RANGE_SCALE*level;
			event.setRadius(range);	
		}
    }
	
	@EventHandler(priority = EventPriority.HIGHEST) 
    public void onEntityExplode(EntityExplodeEvent event) {				
		if(event.getEntity() instanceof TNTPrimed && event.getEntity().hasMetadata(NAME)){
			event.blockList().clear();
		}
    }
	
	@EventHandler(priority = EventPriority.HIGHEST) 
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {	
		if(event.getDamager() instanceof TNTPrimed && event.getDamager().hasMetadata(NAME)){
			TNTPrimed tnt = (TNTPrimed)event.getDamager();
			int level = tnt.getMetadata(NAME).get(0).asInt();
			if(event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof Player)){
				Double dmg = DAMAGE_BASE + DAMAGE_SCALE*level;
				((LivingEntity)event.getEntity()).damage(dmg);
			}else if(event.getEntity() instanceof Player){
				event.setCancelled(true);
			}			
		}
		
    }

}
