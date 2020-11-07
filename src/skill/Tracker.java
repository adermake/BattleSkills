package skill;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import core.main;
import skillcore.Skill;
import utils.ParUtils;

public class Tracker extends Skill {

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

	
	int t = 0;
	@Override
	public void onSkillLoop() {
		t++;
		if (t > 20 ) {
			Player p = getNearestPlayer(user);
			if (p != null) {
				new BukkitRunnable() {
					Location l = user.getEyeLocation();
					int i = 0;
					@Override
					public void run() {
						i++;
						Vector d =  p.getLocation().toVector().subtract(user.getLocation().toVector()).normalize().multiply(1F);
						l.add(d);
						double rgb = p.getLocation().distance(user.getLocation());
						if (rgb > 510) {
							rgb = 510;
						}
						if (rgb > 255) {
							ParUtils.createRedstoneParticle(l.clone().add(0,-0.5,0), 0, 0, 0, 1, Color.fromBGR(0, 255, 510-(int)rgb), 1,user);
						}
						else {
							ParUtils.createRedstoneParticle(l.clone().add(0,-0.5,0), 0, 0, 0, 1, Color.fromBGR(0, (int)rgb, 255), 1,user);
						}
						
						
						if (i > 10) {
							this.cancel();
						}
					}
				}.runTaskTimer(main.plugin, 1, 1);
			}
			
			
			t = 0;
		}
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
