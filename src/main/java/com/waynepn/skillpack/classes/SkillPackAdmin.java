package com.waynepn.skillpack.classes;

import com.sucy.skill.api.classes.ClassAttribute;
import com.sucy.skill.api.classes.RPGClass;
import com.sucy.skill.api.enums.ExpSource;
import com.sucy.skill.log.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * @author wayne_chung
 *
 */
public class SkillPackAdmin extends RPGClass {
    // You can set up the icon with descriptive text as well

    public SkillPackAdmin() {
        super("SKillPackAdmin", new ItemStack(Material.POTION){{
        	ItemMeta meta = getItemMeta();
        	ArrayList<String> lore = new ArrayList<String>();
            lore.add("");
            lore.add(ChatColor.GRAY + "A hybrid between offense");
            lore.add(ChatColor.GRAY + "and support, the alchemist");
            lore.add(ChatColor.GRAY + "uses potions to apply AOE");
            lore.add(ChatColor.GRAY + "debilitation and healing.");
            
            meta.setDisplayName(ChatColor.GOLD + "Admin");
            meta.setLore(lore);
            setItemMeta(meta);
        }}, 40);
        
        settings.set(ClassAttribute.HEALTH, 25.375, 0.375);
        settings.set(ClassAttribute.MANA, 100, 0);

        setPrefix(ChatColor.GOLD + "SkillPackAdmin");
        setManaName("Power");
        setManaRegen(100);
        setAllowedExpSources(ExpSource.MOB, ExpSource.QUEST, ExpSource.COMMAND);

        addSkills("Earthquake");
        addSkills("ArrowTNT");
        addSkills("Thunder");
    }
}