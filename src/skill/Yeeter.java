package skill;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import core.main;
import skillcore.Skill;

public class Yeeter extends Skill{

	ArrayList<Material> throwies = new ArrayList<Material>();
	
	
	@Override
	public void onSkillToggleOff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillToggleOn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillLoop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillStart() {
		throwies.add(Material.APPLE);
		throwies.add(Material.BEETROOT);
		throwies.add(Material.POTATO);
		throwies.add(Material.BAKED_POTATO);
		throwies.add(Material.POISONOUS_POTATO);
		throwies.add(Material.PUFFERFISH);
		throwies.add(Material.SPIDER_EYE);
		throwies.add(Material.ROTTEN_FLESH);
		throwies.add(Material.TROPICAL_FISH);
		throwies.add(Material.COD);
		throwies.add(Material.SALMON);
		throwies.add(Material.CARROT);
		throwies.add(Material.CAKE);
		throwies.add(Material.MELON_SLICE);
		
	}

	@Override
	public void onSkillEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEvent(Event e) {
		if(active) {
			if(e instanceof ProjectileLaunchEvent) {
				ProjectileLaunchEvent launch = (ProjectileLaunchEvent) e;
				Projectile pro = launch.getEntity();
				if (pro.getType() == EntityType.SPLASH_POTION) {
					pro.setVelocity(pro.getVelocity().multiply(2.5));
				}
			}
			
			if(e instanceof PlayerInteractEvent) {
				PlayerInteractEvent event = (PlayerInteractEvent) e;
				if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
				ItemStack is = user.getInventory().getItemInMainHand();
				if(throwies.contains(is.getType())) {
					throwStuff(is);
				}
				if(is.getType() == Material.TNT) {
					user.getWorld().spawnEntity(user.getEyeLocation(), EntityType.PRIMED_TNT).setVelocity(user.getLocation().getDirection().multiply(2));
					is.setAmount(is.getAmount()-1);
					playSound(Sound.ENTITY_TNT_PRIMED, user.getLocation(), 1, 1);
				}
			}
			
		}
	  }
	}
	
	public void throwStuff(ItemStack is) {
		ItemStack fi = new ItemStack(is.getType());
		Item i = user.getWorld().dropItem(user.getEyeLocation(), fi);
		i.setPickupDelay(2000);
		i.setVelocity(user.getLocation().getDirection().multiply(2));
		is.setAmount(is.getAmount()-1);
		playSound(Sound.ENTITY_EGG_THROW, user.getLocation(), 1, 1);
		
		new BukkitRunnable() {
			int step = 0;
			@Override
			public void run() {
				step++;
				if(i.isOnGround()) {
					i.remove();
					this.cancel();
				}
				
				for(LivingEntity e : user.getWorld().getLivingEntities()) {
					if(step<10&&user == e) {
						
						continue;
					}
					if(i.getLocation().distance(e.getLocation())<1 || i.getLocation().distance(e.getEyeLocation())<1) {
						
						e.damage(1);
						e.setVelocity(i.getVelocity());
						if(fi.getType() == Material.PUFFERFISH || fi.getType() == Material.SPIDER_EYE || fi.getType() == Material.POISONOUS_POTATO) {
							e.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 1));
						}
						if(fi.getType() == Material.ROTTEN_FLESH) {
							e.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 600, 0));
						}
						i.remove();
						this.cancel();
					}
				}
				
			}
		}.runTaskTimer(main.plugin, 1, 1);
	}

}
