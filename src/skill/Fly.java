package skill;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import skillcore.Skill;

public class Fly extends Skill {

	
	

	int costPerTick = 1;
	@Override
	public void onSkillLoop() {
		
		Bukkit.broadcastMessage(""+(user.getTotalExperience() > costPerTick));
		Bukkit.broadcastMessage(""+user.getTotalExperience());
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
	public void onSkillToggleOff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillToggleOn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
