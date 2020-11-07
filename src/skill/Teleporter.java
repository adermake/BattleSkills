package skill;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.Event;

import net.minecraft.server.v1_15_R1.Particles;
import skillcore.Skill;
import utils.ParUtils;

public class Teleporter extends Skill{

	@Override
	public boolean onSkillToggleOff() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onSkillToggleOn() {
		// TODO Auto-generated method stub
		if (drainXp(10) ) {
			
		
		Location b = block(user,20);
		if (b == null) {
			
			b = loc(user,100);
		}
			if (b != null) {
			playSound(Sound.ENTITY_ENDERMAN_TELEPORT,user.getLocation(),1,1);
			playSound(Sound.ENTITY_ENDERMAN_TELEPORT,b,1,1);
			
			b = getFloor(b);
			b = getTop(b);
			b.setDirection(user.getLocation().getDirection());
			ParUtils.createFlyingParticle(Particles.PORTAL, user.getLocation(), 1, 2, 1, 70,-8, b.toVector().subtract(user.getLocation().toVector()).normalize());
			user.teleport(b);
			ParUtils.createFlyingParticle(Particles.PORTAL, user.getLocation(), 1, 2, 1, 70,-8,user.getLocation().getDirection());
			}
		
		return true;
		
		}
		toggleSkill(false);
		return false;
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
