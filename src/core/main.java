package core;




import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import crafting.FireSorcery;
import me.libraryaddict.disguise.DisguiseAPI;
import skillcore.EventCollector;
import skillcore.InventoryListener;
import skillcore.Skill;
import skillcore.SkillActionPair;
import skillcore.SkillDropSystem;
import skillcore.SkillList;
import skillcore.SkillMenu;



public class main extends JavaPlugin {

	public static JavaPlugin plugin;
	@Override
	public void onEnable() {
		plugin = this;
		getServer().getPluginManager().registerEvents(new Move(), this);
		getServer().getPluginManager().registerEvents(new EventCollector(), this);
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new SkillDropSystem(), this);
		getServer().getPluginManager().registerEvents(new FireSorcery(), this);
		SkillList.fillList();
		this.getCommand("skill").setExecutor(new CommandReciever());
	}
	
	@Override
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			DisguiseAPI.undisguiseToAll(p);
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			for (SkillActionPair sap : Skill.skills.get(p)) {
				sap.skill.onSkillEnd();
			}
		}
		
	}
}
