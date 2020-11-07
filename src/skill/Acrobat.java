package skill;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

import core.main;
import skillcore.Skill;



public class Acrobat extends Skill {
	
	
	
public Acrobat() {
		
	}

	@Override
	public void onSkillLoop() {	
		
	}

	@Override
	public void onSkillStart() {		
		
	}

	@Override
	public void onSkillEnd() {
		
	}

	@Override
	public boolean onSkillToggleOff() {
		return false;
	}

	@Override
	public boolean onSkillToggleOn() {	
		Location loc = user.getEyeLocation();
		Block b = null;
		for(double i=0; i<1;i+=0.1) {
			Location ray = loc.clone().add(loc.getDirection().multiply(i));
			if(ray.getBlock().getType().isSolid()) {
				b = ray.getBlock();
				break;
			}
		}
		if(b!=null) {
			user.setVelocity(user.getEyeLocation().getDirection().multiply(-1.5));
			playSound(Sound.ENTITY_ENDER_DRAGON_FLAP, loc, 1, 2);
		}
		new BukkitRunnable() {

			@Override
			public void run() {
				if(user.isOnGround()) {
					this.cancel();
				}
				user.setFallDistance(-10);
			}
			
		}.runTaskTimer(main.plugin, 5, 1);
		
		toggleSkill(false);
		
		return true;
	}

	@Override
	public void onEvent(Event e) {
		
	}

}
