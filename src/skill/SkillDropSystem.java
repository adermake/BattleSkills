package skill;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import utils.MathUtils;

public class SkillDropSystem implements Listener {
	public static HashMap<Player,Double> breakCount = new HashMap<Player,Double>();
	public static HashMap<Player,Double> succesCount = new HashMap<Player,Double>();
	
	double mineDropChance = 1;
	double mobDropChance = 5;
	double exp = 1;
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		if (e.getBlock().getType().toString().contains("_ORE")) {
			
			if (!breakCount.containsKey(p)) {
				breakCount.put(p, (100/mineDropChance));
			}
			if (!succesCount.containsKey(p)) {
				succesCount.put(p, 1D);
			}
			
			double bc = breakCount.get(p);
			double sc = succesCount.get(p);
			
			double per = sc/bc;
			
			double currentDropChance = Math.pow(((mineDropChance/100) / per),exp) * mineDropChance;
			Bukkit.broadcastMessage(""+currentDropChance);
			if (currentDropChance > MathUtils.randDouble(0, 100)) {
				Bukkit.broadcastMessage("OMG RARE ITEM");
				succesCount.put(p, succesCount.get(p)+1);
			}
			breakCount.put(p, breakCount.get(p)+1);
		}
		
		
	}
	
	
}
