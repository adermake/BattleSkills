package skill;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_15_R1.Particles;
import skillcore.Skill;
import utils.ParUtils;

public class XRay extends Skill {

	ArrayList<Block> blocks = new ArrayList<Block>();
	ArrayList<Block> oldblocks = new ArrayList<Block>();

	int costPerTick = 1;
	
	int t = 0;
	@Override
	public void onSkillLoop() {
		t++;
		
		if (t > 5 && drainXp(3)) {
			t = 0;
		user.removePotionEffect(PotionEffectType.NIGHT_VISION);
		user.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,20,0,true));
		float range = 0;
		Location loc = user.getEyeLocation();
		if (loc == null)
			return;
		
		loc.setDirection(user.getLocation().getDirection());
		while (range < 200) {
			int size = 2;
			range+=5;
			loc.add(loc.getDirection().multiply(size-1));
			if (loc.getY() <2) 
				break;
			if (loc.getY() > 250)
				break;
			
			
				ParUtils.createParticle(Particles.MYCELIUM, loc,5, 5,5, 11, 1);
			
			for (int x = -size;x<size;x++) {
				for (int y = -size;y<size;y++) {
					for (int z = -size;z<size;z++) {
						Location l1 = loc.clone().add(new Vector(x,y,z));
						if (!blocks.contains(l1)) {
							uncoverBlock(l1.getBlock());
							blocks.add(l1.getBlock());
						}
						
						
						
					}				
				}
			}
		}
		
		for (Block b : oldblocks) {
			if (!blocks.contains(b))
				
			user.sendBlockChange(b.getLocation(), b.getLocation().getBlock().getType(),b.getLocation().getBlock().getData());
		}
		oldblocks = (ArrayList<Block>) blocks.clone();
		blocks.clear();
		}
		
		
	}

	

	public void uncoverBlock(Block b) {
		
		
			if (b.getType().isSolid()) {
				
			if (!b.getType().toString().toLowerCase().contains("ore"))
				user.sendBlockChange(b.getLocation(), Material.BARRIER, ((byte) 0));
				
				
			
			}
			else {
				user.sendBlockChange(b.getLocation(), Material.AIR, ((byte) 0));
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
		for (Block b : oldblocks) {
			if (!blocks.contains(b))
			user.sendBlockChange(b.getLocation(), b.getType(), ((byte) 0));
		}
		oldblocks = (ArrayList<Block>) blocks.clone();
		blocks.clear();
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
