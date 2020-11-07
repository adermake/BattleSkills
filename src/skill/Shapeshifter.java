package skill;

import java.awt.Desktop.Action;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Flying;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import helper.MobCharacter;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import skillcore.Skill;
import utils.Actionbar;

public class Shapeshifter extends Skill {

	HashMap<EntityType,MobCharacter> mc = new HashMap<EntityType,MobCharacter>();
	MobCharacter mobCharacter;
	@Override
	public boolean onSkillToggleOff() {
		
		
		unMorph();
		return true;
		
	}

	
	public void unMorph() {
		user.setWalkSpeed(0.2F);
		user.setFlySpeed(0.1F);
		user.setFlying(false);
		user.setAllowFlight(false);
		
		DisguiseAPI.undisguiseToAll(user);
		mobCharacter = null;
		Actionbar a = new Actionbar("You are no longer diguised");
		a.send(user);
		active = false;
	}
	@Override
	public boolean onSkillToggleOn() {
		// TODO Auto-generated method stub
		
		LivingEntity ent = pointLivingEntity(user,10);
		if (ent != null && !(ent instanceof Player)) {
			
			
			mobCharacter = MobCharacter.createMobCharacter(ent);
			MobDisguise mobDisguise = new MobDisguise(DisguiseType.getType(ent));
			Bukkit.broadcastMessage("WS: " +mobCharacter.walkSpeed+" --- FS: " +mobCharacter.flySpeed+" --- SS: " +mobCharacter.swimSpeed);
			ArrayList<Player> players = new ArrayList<Player>();
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p != user)
				players.add(p);
			}
			DisguiseAPI.disguiseToPlayers(user, mobDisguise,players);
			return true;
		}
		
		
		
		toggleSkill(false);
		return false;
	}
	int tick = 0;
	int air = 20;
	@Override
	public void onSkillLoop() {
		// TODO Auto-generated method stub
		if (mobCharacter != null) {
			
			if (mobCharacter.flySpeed > 0) {
				if (getHeight(user.getLocation())<mobCharacter.flyHeight) {
					user.setAllowFlight(true);
					user.setFlying(true);
				}
				else {
					user.setAllowFlight(false);
					user.setFlying(false);
				}
				
				user.setFlySpeed(mobCharacter.flySpeed);
			}
			user.setWalkSpeed(mobCharacter.walkSpeed);
			
			
				if (mobCharacter.swimSpeed > 0) {
					if (user.getEyeLocation().getBlock().getType() == Material.WATER || user.getLocation().getBlock().getType()== Material.WATER) {
						air+=5;
					}
					else {
						air-=2;
					}
					if (air<= 0) {
						air = 0;
						user.damage(1);
					}
					if (air> 280) {
						air = 280;
						
					}
				}
			
			
			
			
			if (mobCharacter.swimSpeed > 0 && mobCharacter.entity != EntityType.TURTLE) {
				
				user.setRemainingAir(air);
			}
		
			if (user.isSwimming()) {
				
				
				LivingEntity ent = (LivingEntity) DisguiseAPI.getDisguise(user).getEntity();
				user.setVelocity(ent.getLocation().getDirection().multiply(mobCharacter.swimSpeed));
			}
			boolean hungerNerf = false;
			if (mobCharacter.walkSpeed > 0.35) {
				hungerNerf = true;
			}
			if (mobCharacter.flyHeight > 10 ) {
				hungerNerf = true;
			}
			if (mobCharacter.swimSpeed> 1 ) {
				hungerNerf = true;
			}
			if (mobCharacter.entity == EntityType.BAT || mobCharacter.entity == EntityType.ENDERMAN || mobCharacter.entity == EntityType.PHANTOM || mobCharacter.entity == EntityType.SPIDER || mobCharacter.entity == EntityType.CAVE_SPIDER) {
				user.removePotionEffect(PotionEffectType.NIGHT_VISION);
				user.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 40, 1,true));	
			}
			
			if (mobCharacter.entity == EntityType.SLIME || mobCharacter.entity == EntityType.MAGMA_CUBE || mobCharacter.entity == EntityType.RABBIT) {
				user.removePotionEffect(PotionEffectType.JUMP);
				user.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 4,true));	
			}
			
			if (mobCharacter.entity == EntityType.ZOMBIE_VILLAGER ||mobCharacter.entity == EntityType.STRAY || mobCharacter.entity == EntityType.DROWNED ||mobCharacter.entity == EntityType.ZOMBIE || mobCharacter.entity == EntityType.SKELETON || mobCharacter.entity == EntityType.PHANTOM) {
				if (user.getLocation().getBlock().getLightFromSky() >= 15 && user.getWorld().getTime() < 14000 && user.getWorld().getWeatherDuration() <= 0  && user.getEquipment().getHelmet() == null) {
					user.setFireTicks(20);
				}
			}
			
			if (mobCharacter.entity == EntityType.PIG_ZOMBIE || mobCharacter.entity == EntityType.MAGMA_CUBE || mobCharacter.entity == EntityType.BLAZE|| mobCharacter.entity == EntityType.GHAST|| mobCharacter.entity == EntityType.WITHER_SKELETON) {
				user.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
				user.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 40, 1,true));	
				hungerNerf = true;
			}
			
			if (hungerNerf) {
				
				user.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,60,0));
			}
		}
		else {
			toggleSkill(false);
		}
		
	}
	
	public double getHeight(Location loc) {
		Location l = loc.clone();
				double dist = 0;
		while (l.getBlock().getType().isAir()) {
			l.add(0,-1,0);
			dist++;
			if (l.getY()<0) {
				break;
			}
		}
		return dist;
	}

	@Override
	public void onSkillStart() {

		
		
	}

	@Override
	public void onSkillEnd() {
		// TODO Auto-generated method stub
		unMorph();
	
	}

	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub
		if (e instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
			if (event.getEntity() == user && event.getDamager() instanceof Player) {
				unMorph();
			}
		}
	}

}
