package skill;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityTargetEvent;

import skillcore.Skill;

public class MobManipulator extends Skill {

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
		if (e instanceof EntityTargetEvent) {
			EntityTargetEvent event = (EntityTargetEvent) e;
			if (((EntityTargetEvent) e).getEntity() instanceof Mob) {
				Mob m = (Mob) event.getEntity();
			Player p = getNearestPlayer(user, user.getLocation(), 70);
			if (p != null) {
				m.setTarget(p);
				
				m.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(80);
				event.setCancelled(true);
			}
			
			}
		}
	}

}
