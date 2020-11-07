package skill;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import skillcore.Skill;

public class Fly extends Skill {

	
	

	int costPerTick = 1;
	@Override
	public void onSkillLoop() {
		
		
		user.setAllowFlight(user.getTotalExperience() > costPerTick);
			
		if (user.isFlying()) {
			boolean xp = drainXp(costPerTick);
			
			user.setFlying(xp);
		}
		
		
		
	}

	@Override
	public void onSkillStart() {
		
		
	}

	@Override
	public void onSkillEnd() {
		
		
	}

	

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

	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
