package core;




import org.bukkit.plugin.java.JavaPlugin;

import skill.SkillDropSystem;
import skillcore.EventCollector;
import skillcore.InventoryListener;
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
		this.getCommand("skill").setExecutor(new CommandReciever());
	}
}
