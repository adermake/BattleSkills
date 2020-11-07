package skill;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.minecraft.server.v1_15_R1.Particles;
import skillcore.Skill;
import utils.ParUtils;

public class Waterwalker extends Skill {
	
	Block lastblock;

	@Override
	public boolean onSkillToggleOff() {
		if(lastblock != null) {
			user.sendBlockChange(lastblock.getLocation(), lastblock.getBlockData());
			lastblock = null;
		}
		return true;
	}

	@Override
	public boolean onSkillToggleOn() {
		return true;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillLoop() {
		if(lastblock != null) {
			user.sendBlockChange(lastblock.getLocation(), lastblock.getBlockData());
			lastblock = null;
		}
		
		if(user.getLocation().getBlock().getType() == Material.WATER) {
			return;
		}
		
		if(user.getLocation().add(0,-1,0).getBlock().getType() == Material.WATER || user.getLocation().add(0,-1,0).getBlock().getType() == Material.TALL_SEAGRASS 
				|| user.getLocation().add(0,-1,0).getBlock().getType() == Material.SEAGRASS || user.getLocation().add(0,-1,0).getBlock().getType() == Material.KELP) {
			user.sendBlockChange(user.getLocation().add(0, -1,0),Material.BLUE_STAINED_GLASS,(byte)11);
			lastblock = user.getLocation().add(0, -1,0).getBlock();
			user.removePotionEffect(PotionEffectType.SPEED);
			user.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 3));
			ParUtils.createParticle(Particles.RAIN, user.getLocation(), 2, 0.3, 2, 35, 1);
			//ParUtils.createParticle(Particles.CLOUD, user.getLocation().add(0,-1,0), 1, 0.3, 1, 35, 0.1F);
	
		}
		if(user.getLocation().add(0,-1,0).getBlock().getBlockData() instanceof Waterlogged) {
			if(((Waterlogged)user.getLocation().add(0,-1,0).getBlock().getBlockData()).isWaterlogged()) {
				user.sendBlockChange(user.getLocation().add(0, -1,0),Material.BLUE_STAINED_GLASS,(byte)11);
				lastblock = user.getLocation().add(0, -1,0).getBlock();
				user.removePotionEffect(PotionEffectType.SPEED);
				user.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 3));
				ParUtils.createParticle(Particles.RAIN, user.getLocation(), 2, 0.3, 2, 35, 1);
				//ParUtils.createParticle(Particles.CLOUD, user.getLocation().add(0,-1,0), 1, 0.3, 1, 35, 0.1F);
				
				
			
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
