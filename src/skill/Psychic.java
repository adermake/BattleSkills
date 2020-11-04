package skill;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import skillcore.Skill;

public class Psychic extends Skill {

	Entity grabbed;
	double dist;
	int drain = 0;
	int step = 0;
	@Override
	public void onSkillLoop() {
		// TODO Auto-generated method stub
		
		if (grabbed == null) {
			
			grabbed = pointEntity(user);
			if (grabbed == null) {
				return;
			}
			dist = grabbed.getLocation().toVector().subtract(user.getLocation().toVector()).length();
			drain = 1;
			step = 0;
		}
		else {
			step++;
			drain = 1+step/20;
			if (grabbed instanceof Player) {
				drain *= 4;
			}
			if (grabbed instanceof LivingEntity) {
				drain *= 2;
			}
			if (grabbed.getPassengers().size() > 0) {
				drain *= 4;
			}
			if (step % 5 == 0) {
				if (drainXp(drain)) {
					
				}
				else {
					toggleSkill(false);
				}
			}
			
			
			doPin(grabbed, user.getLocation().add(user.getLocation().getDirection().multiply(dist)));
			if (!grabbed.getPassengers().contains(user)) 
			dist += 0.3F;
			
		}
	
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
	public void onSkillToggleOff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillToggleOn() {
		// TODO Auto-generated method stub
		grabbed = null;
	}

	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

}
