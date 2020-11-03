package utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import skill.SkillDropSystem;
import skillcore.Skill;
import skillcore.SkillActionPair;

public class ReconnectUtils {

	
	
	public static void reconnectPlayer(Player p) {
		
		reconnectRefrence(SkillDropSystem.breakCount, p);
		reconnectRefrence(SkillDropSystem.succesCount, p);
		
	}
	
	
	public static <T> void reconnectRefrence(HashMap<Player,T> h,Player p) {
		Player rec = null;
		for (Player pl : h.keySet()) {
			
			if (pl.getName().equals(p.getName())) {
				rec = pl;
				
			}
		}
		if (rec != null) {
			
			T o = h.get(rec);
			h.remove(rec);
			h.put(p, o);
		}
	}
}
