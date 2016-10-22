package com.waynepn.skillpack.skills;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.rit.sucy.player.TargetHelper;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.enums.ManaCost;
import com.sucy.skill.api.event.PlayerCastSkillEvent;
import com.sucy.skill.api.player.PlayerData;
import com.sucy.skill.api.player.PlayerSkill;
import com.sucy.skill.api.skills.Skill;

public class TargetSkill extends Skill implements Listener {
		
	private static Logger log = Logger.getLogger(TargetSkill.class.toString());
	
	public TargetSkill(String name, String type, ItemStack indicator, int maxLevel, String skillReq, int skillReqLevel) {
		super(name, type, indicator, maxLevel, skillReq, skillReqLevel);
	}
	public TargetSkill(String name, String type, Material indicator, int maxLevel, String skillReq, int skillReqLevel) {
		super(name, type, indicator, maxLevel, skillReq, skillReqLevel);
	}
	public TargetSkill(String name, String type, ItemStack indicator, int maxLevel) {
		super(name, type, indicator, maxLevel, null, 0);
	}
	public TargetSkill(String name, String type, Material indicator, int maxLevel) {
		super(name, type, indicator, maxLevel, null, 0);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event)    {

        //log.info("PlayerInteractEvent Catched.");
        Player player = event.getPlayer();
        if (!SkillAPI.getSettings().isWorldEnabled(player.getWorld()))
        {
            return;
        }
        PlayerData data = SkillAPI.getPlayerData(player);
        Material heldItem = player.getItemInHand().getType();

        // Must be on right click
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
        {
            return;
        }
        // Cannot be cancelled if clicking on a block
        if (event.isCancelled() && event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            return;
        }
        // Must have a valid item
        if (heldItem == null || data.getBoundSkill(heldItem) == null || !SkillAPI.isSkillRegistered(data.getBoundSkill(heldItem).getData().getName()))
        {
            return;
        }
        //log.info("Call playerData_cast.");
        playerData_cast(data.getBoundSkill(heldItem));   
        
    }
	void playerData_cast(PlayerSkill skill){
		PlayerData data = skill.getPlayerData();
		Player player = skill.getPlayerData().getPlayer();
		int level = skill.getLevel();
		
		if (skill == null) throw new IllegalArgumentException("Skill cannot be null");
		
        // Not unlocked or on cooldown
        if (level <= 0 || !data.check(skill, true, true))return ;
        
		if (skill.getData() instanceof com.sucy.skill.api.skills.TargetSkill)
        {
			//log.info("A");
            LivingEntity target = TargetHelper.getLivingTarget(player, skill.getData().getRange(level));

            // Must have a target
            if (target == null) return;
            //log.info("B");
            PlayerCastSkillEvent playerCastSkillEvent = new PlayerCastSkillEvent(data, skill, (Player)player);
            Bukkit.getPluginManager().callEvent(playerCastSkillEvent);

            // Make sure it isn't cancelled
            boolean cancelled = playerCastSkillEvent.isCancelled();
            //log.info("Cancelled: " + cancelled);
            if (!cancelled)
            {
            	//log.info("C");
                try
                {
                    if (((com.sucy.skill.api.skills.TargetSkill) skill.getData()).cast( (Player)player, target, 1, !SkillAPI.getSettings().canAttack( (Player)player, target)))
                    {
                    	//log.info("D");
                        skill.startCooldown();
                        if (SkillAPI.getSettings().isShowSkillMessages())
                        {
                        	//log.info("E");
                            skill.getData().sendMessage((Player)player, SkillAPI.getSettings().getMessageRadius());
                        }
                        if (SkillAPI.getSettings().isManaEnabled())
                        {
                        	//log.info("F");
                        	data.useMana(skill.getManaCost(), ManaCost.SKILL_CAST);
                        }
                    }
                }
                catch (Exception ex)
                {
                	//log.info("Failed to cast skill - " + skill.getData().getName() + ": Internal skill error");
                    ex.printStackTrace();
                }
            }
        }
	}	
}

