package skill;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

import core.main;
import net.minecraft.server.v1_15_R1.Particles;
import skillcore.Skill;
import utils.ParUtils;

public class Hellbringer extends Skill {

	@Override
	public boolean onSkillToggleOff() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onSkillToggleOn() {
		// TODO Auto-generated method stub
		if (drainXp(15)) {
			
		
		playSound(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH,user.getLocation(),3,2);
		for (double i = 0;i<80;i+=0.5) {
			Location l = user.getEyeLocation().add(user.getLocation().getDirection().multiply(i));
			ParUtils.createRedstoneParticle(l, 0, 0, 0, 1, Color.ORANGE, 1);
			if (l.getBlock().getType().isSolid()) {
				l.getBlock().setType(Material.AIR);
				l.getBlock().setType(Material.MAGMA_BLOCK);
				playSound(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH,user.getLocation(),3,2);
				
				new BukkitRunnable() {
					int t = 0;
					@Override
					public void run() {
						t++;
						
						ParUtils.createParticle(Particles.FLAME, l, 1, 1, 1, 2, 0.3F);
						if (t > 20 * 2) {
							//playSound(Sound.BLOCK_LAVA_EXTINGUISH,l,1,0.4F);
							l.getBlock().getWorld().createExplosion(l, 6, true);
							this.cancel();
						}
						
					}
				}.runTaskTimer(main.plugin, 1, 1);
				
				break;
			}
		}
		}
		toggleSkill(false);
		return true;
	}

	@Override
	public void onSkillLoop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

}
