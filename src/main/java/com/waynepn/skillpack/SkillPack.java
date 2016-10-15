package com.waynepn.skillpack;

import org.bukkit.plugin.java.JavaPlugin;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.SkillPlugin;
import com.waynepn.skillpack.skills.*;

public class SkillPack extends JavaPlugin implements SkillPlugin {
	public void onEnable() {
        getServer().getPluginManager().registerEvents(new ArrowTNT(), this);
    }
    @Override
    public void registerSkills(SkillAPI skillAPI) {
        skillAPI.addSkills(new Earthquake(), new ArrowTNT());
    }
    /* 
     * There is some error adding class(non-Javadoc)
     * @see com.sucy.skill.api.SkillPlugin#registerClasses(com.sucy.skill.SkillAPI)
     */
     @Override
     public void registerClasses(SkillAPI skillAPI) {   }
     
	
}
