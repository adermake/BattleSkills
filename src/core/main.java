package core;

import org.bukkit.plugin.java.JavaPlugin;


import skillcore.EventCollector;



public class main extends JavaPlugin {

	public static JavaPlugin plugin;
	@Override
	public void onEnable() {
		plugin = this;
		getServer().getPluginManager().registerEvents(new Move(), this);
		getServer().getPluginManager().registerEvents(new EventCollector(), this);
		
		this.getCommand("skill").setExecutor(new CommandReciever());
	}
}
