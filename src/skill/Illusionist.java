package skill;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

import core.main;
import helper.BlockIllusion;
import net.minecraft.server.v1_15_R1.Particles;
import skillcore.Skill;
import utils.ParUtils;

public class Illusionist extends Skill{

	HashMap<Location,BlockIllusion> illusions = new HashMap<Location,BlockIllusion>();
	@Override
	public void onSkillToggleOff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillToggleOn() {
		// TODO Auto-generated method stub
		if (drainXp(5)) {
			Location b = block(user,10);
			if (b != null) {
				
				illusions.put(b.getBlock().getLocation(), new BlockIllusion(b.getBlock().getLocation(), b.getBlock().getBlockData(),b.getBlock().getType()));
			
			}
			ParUtils.createParticle(Particles.WITCH, b.getBlock().getLocation().add(0.5,0,0.5), 1, 1, 1, 15, 0);
			playSound(Sound.BLOCK_ENCHANTMENT_TABLE_USE,b.getBlock().getLocation(),1,1);
		}
		
		toggleSkill(false);
	}

	@Override
	public void onSkillLoop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillStart() {
		// TODO Auto-generated method stub
		illusionClock =  true;
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if (illusionClock == false) {
					this.cancel();
					return;
				}
					for (BlockIllusion b : illusions.values()) {
						boolean nearPlayer = false;
						for (Player p : Bukkit.getOnlinePlayers()) {
							
						if (p.getLocation().distance(b.loc)<5) {
							nearPlayer = true;
						}
						
					}
					b.setIllussion(nearPlayer);
				}
				
			}
		}.runTaskTimer(main.plugin, 1, 1);
	}
	boolean illusionClock = false;
	@Override
	public void onSkillEnd() {
		Bukkit.broadcastMessage("SKILL END");
		// TODO Auto-generated method stub
		for (BlockIllusion b : illusions.values()) {
			b.setIllussion(false);
		}
		illusionClock = false;
	}

	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

}
