package com.waynepn.skillpack.classes;

import com.sucy.skill.api.classes.ClassAttribute;
import com.sucy.skill.api.classes.RPGClass;
import com.sucy.skill.api.enums.ExpSource;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Admin extends RPGClass {
    // You can set up the icon with descriptive text as well
    private static final ItemStack ICON = new ItemStack(Material.POTION) {{
        ItemMeta meta = ICON.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.GRAY + "A hybrid between offense");
        lore.add(ChatColor.GRAY + "and support, the alchemist");
        lore.add(ChatColor.GRAY + "uses potions to apply AOE");
        lore.add(ChatColor.GRAY + "debilitation and healing.");
        
        meta.setDisplayName(ChatColor.GOLD + "Admin");
        meta.setLore(lore);
        setItemMeta(meta);
    }};

    public Admin() {
        super("Admin", ICON, 40);

        settings.set(ClassAttribute.HEALTH, 25.375, 0.375);
        settings.set(ClassAttribute.MANA, 100, 0);

        setPrefix(ChatColor.GOLD + "Admin");
        setManaName("Power");
        setManaRegen(2);
        setAllowedExpSources(ExpSource.MOB, ExpSource.QUEST, ExpSource.COMMAND);

        addSkills("Earthquake");
    }
}