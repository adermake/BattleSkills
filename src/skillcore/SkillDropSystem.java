package skillcore;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;

import net.minecraft.server.v1_15_R1.Particles;
import utils.MathUtils;
import utils.ParUtils;
import utils.SoundUtils;

public class SkillDropSystem implements Listener {
	public static HashMap<Player,Double> breakCount = new HashMap<Player,Double>();
	public static HashMap<Player,Double> succesCount = new HashMap<Player,Double>();
	
	double mineDropChance = 1;
	double mobDropChance = 3;
	double exp = 1;
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		if (e.getBlock().getType().toString().contains("_ORE")) {
			
			if (!breakCount.containsKey(p)) {
				breakCount.put(p, (100D));
			}
			if (!succesCount.containsKey(p)) {
				succesCount.put(p, 1D);
			}
			
			double bc = breakCount.get(p);
			double sc = succesCount.get(p);
			
			double per = sc/bc;
			
			double currentDropChance =  100-100*Math.pow((100-mineDropChance)/100,Math.pow(( 1D/100D/per),exp));
			Bukkit.broadcastMessage(""+currentDropChance);
			if (currentDropChance > MathUtils.randDouble(0, 100)) {
				dropSkill(e.getBlock().getLocation().add(0.5,0.5,0.5));
				succesCount.put(p, succesCount.get(p)+1);
			}
			breakCount.put(p, breakCount.get(p)+mineDropChance);
		}
		
		
	}
	
	
	@EventHandler
	public void onKill(EntityDamageByEntityEvent e) {
		
		Player p;
		if (!(e.getDamager() instanceof Player)) {
			
			if (e.getDamager() instanceof Projectile) {
				Projectile pro = (Projectile) e.getDamager();
				if (pro.getShooter() instanceof Player) {
					p = (Player) pro.getShooter();
				}
				else {
					return;
				}
			}
			else {
				return;
			}
		}
		else {
			p = (Player) e.getDamager();
		}
		
		if (e.getEntity() instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity) e.getEntity();
			
			double d = ent.getHealth()-e.getDamage();
			if (ent.getCustomName() != null && ent.getCustomName().equals("nodrops")) {
				
				return;
			}
			
			if (d <=0 ) {
				
				
				
				if (!breakCount.containsKey(p)) {
					breakCount.put(p, (100D));
				}
				if (!succesCount.containsKey(p)) {
					succesCount.put(p, 1D);
				}
				
				double bc = breakCount.get(p);
				double sc = succesCount.get(p);
				
				double per = sc/bc;
				
				double currentDropChance =  100-100*Math.pow((100-mobDropChance)/100,Math.pow((1D/100D/per),exp));
				Bukkit.broadcastMessage(""+currentDropChance);
				if (currentDropChance > MathUtils.randDouble(0, 100)) {
					
					dropSkill(ent.getLocation());
					succesCount.put(p, succesCount.get(p)+1);
				}
				breakCount.put(p, breakCount.get(p)+mobDropChance);
				
				
			}
		}
		
		
	}
	
	public void dropSkill(Location loc) {
		loc.getWorld().dropItem(loc, Skill.createSkillItem(SkillList.getRandomEntry().name));
		SoundUtils.playSound(Sound.UI_TOAST_CHALLENGE_COMPLETE, loc,1,1);
		ParUtils.createParticle(Particles.END_ROD, loc, 0, 0, 0, 10, 0.3);
		
	}
	
	@EventHandler
	public void onSpawn(SpawnerSpawnEvent e) {
		Bukkit.broadcastMessage("TEST");
		e.getEntity().setCustomName("§eNodrops");
	}
	
	
}
