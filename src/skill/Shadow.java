package skill;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import core.main;
import net.minecraft.server.v1_15_R1.Particles;
import skillcore.Skill;
import utils.Actionbar;
import utils.ParUtils;

public class Shadow extends Skill {

	public static ArrayList<Player> hiding = new ArrayList<Player>();
	
	@Override
	public boolean onSkillToggleOff() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean onSkillToggleOn() {
		// TODO Auto-generated method stub
		return true;
	}

	BossBar bar;
	boolean hidden = false;
	int hideCooldown = 20 * 3;
	@Override
	public void onSkillLoop() {
		// TODO Auto-generated method stub
		hideCooldown--;
		if (hideCooldown <= 0) {
			
			if (user.getLocation().getBlock().getLightLevel() < 6) {
				
				if (!hidden) {
					
					playSound(Sound.AMBIENT_CAVE,user.getLocation(),1,1);
					new Actionbar("Hidden").send(user);
					for (Player p : Bukkit.getOnlinePlayers()) {
						if(p != user) {
							if (!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
								p.hidePlayer(main.plugin, user);
							}
						}
					}
					hiding.add(user);
					user.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*10000, 1));
					ParUtils.createFlyingParticle(Particles.LARGE_SMOKE, user.getLocation(), 1, 2, 1, 11,1, user.getLocation().getDirection());
					
				}
				
				
				hidden = true;
			}
			else {
				if (hidden) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if(p != user) {
							p.showPlayer(main.plugin,user);
						}
					}
					hiding.remove(user);
					user.removePotionEffect(PotionEffectType.INVISIBILITY);
					ParUtils.createFlyingParticle(Particles.LARGE_SMOKE, user.getLocation(), 1, 2, 1, 11,1, user.getLocation().getDirection());
					new Actionbar("Shown").send(user);
					hideCooldown = 20 * 3;
				}
				
				hidden = false;
			}
		}
	
		
	}

	@Override
	public void onSkillStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillEnd() {
		// TODO Auto-generated method stub
		for (Player p : Bukkit.getOnlinePlayers()) {
			if(p != user) {
				p.showPlayer(main.plugin,user);
			}
		}
		user.removePotionEffect(PotionEffectType.INVISIBILITY);
		ParUtils.createFlyingParticle(Particles.LARGE_SMOKE, user.getLocation(), 1, 2, 1, 11,1, user.getLocation().getDirection());
		new Actionbar("Shown").send(user);
		hideCooldown = 20 * 3;
	}

	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

}
