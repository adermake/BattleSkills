package skill;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import skillcore.Skill;

public class Fly extends Skill {

	
	
	public Fly() {
		
		name = "Fly";
	}
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
		setGliding(user, !user.isFlying());
		
		
	}

	@Override
	public void onSkillStart() {
		
		
	}

	@Override
	public void onSkillEnd() {
		
		
	}

	@Override
	public void onEvent(Event e) {
		
		
	}

	
	
}
