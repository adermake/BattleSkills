package skill;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_15_R1.Particles;
import skillcore.Skill;
import utils.ParUtils;

public class Authority extends Skill {
	
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
	public void onSkillLoop() {
		// TODO Auto-generated method stub
		if (drainXp(1)) {
			Player target = (Player) pointExactPlayer(user,100);
			if (target != null) {
				
				target.setVelocity(new Vector(0,0,0));
				ParUtils.createFlyingParticle(Particles.SMOKE, target.getLocation(), 1, 0,1, 5, 0.3,new Vector(0,1,0));
				playSound(Sound.ENTITY_GHAST_WARN, target.getLocation(), 0.05F, 1);
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
		
	}

	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

}
