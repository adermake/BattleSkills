package skillcore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import core.main;


public abstract class Skill {

	public Player user;
	public String name;
	public boolean dead = false;
	public static HashMap<Player,ArrayList<Skill>> skills = new HashMap<Player,ArrayList<Skill>>();
	
	public Skill() {
		
		
		
	}
	
	
	
	
	
	public void activate(Player p) {
		p.sendMessage("�eGained Skill: �7" +name);
		onSkillStart();
		user = p;
		addSkill(p);
		new BukkitRunnable() {
			public void run() {
				onSkillLoop();
				if (dead) {
					skills.get(user).remove(this);
					this.cancel();
					onSkillEnd();
					
				}
			}
		}.runTaskTimer(main.plugin, 1, 1);
	}
	
	
	
	public static void sendEvent(Player p,Event e) {
		if (!skills.containsKey(p))
			return;
		for (Skill s : skills.get(p)) {
			s.onEvent(e);
		}
	}
	
	
	public void addSkill(Player p) {
		if (skills.containsKey(p)) {
			skills.get(p).add(this);
		}
		else {
			skills.put(p, new ArrayList<Skill>());
			addSkill(p);
		}
	}
	public abstract void onSkillLoop();
	public abstract void onSkillStart();
	public abstract void onSkillEnd();
	public abstract void onEvent(Event e);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean drainXp(int xp) {
		
		if (user.getTotalExperience() > xp) {
			//user.setTotalExperience((user.getTotalExperience()-xp));
			user.updateInventory();
			user.giveExp(-xp);
			return true;
		}
		return false;
		
	}
	
	
	public void killSkill() {
		dead = true;
	}
	
	

	//MEHTHODS
	
	
	public static int randInt(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
	public static double randDouble(double min, double max) {
		double randomNum = ThreadLocalRandom.current().nextDouble(min, max);
		return randomNum;
	}
	
	
	
	public static Vector randVector() {
		int ix = randInt(-100,100);
		int iy = randInt(-100,100);
		int iz = randInt(-100,100);
		double dx = ((double)ix)/100;
		double dy = ((double)iy)/100;
		double dz = ((double)iz)/100;
		Vector v = new Vector(dx,dy,dz);
		return v;
		
	}
	
	public void playSound(Sound s,Location loc,double volume,double pitch) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(loc, s, (float)volume, (float)pitch);
		}
	}
	
	public void playGlobalSound(Sound s,double volume,double pitch) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(p.getLocation(), s, (float)volume, (float)pitch);
		}
	}
	
	public void playSingleSound(Sound s,Player p,double volume,double pitch) {
			p.playSound(p.getLocation(), s, (float)volume, (float)pitch);
	}
	
	
	
	public void damage(Entity ent, double damage,Player damager) {
		if (ent instanceof Player) {
			tagPlayer((Player) ent);
		}
		if (ent instanceof LivingEntity) {
			((LivingEntity) ent).damage(damage);
		}
		//Damage will be an Damage cause even when tagPlayers has been disabled
	}

	public void tagPlayer(Player ent) {
		;
		Bukkit.broadcastMessage("FIINISH TAG System");
		
	}
	
	public void heal(LivingEntity ent, double damage,Player healer) {
		double newhealth = ent.getHealth()+damage;
		
		if (ent.getMaxHealth()<newhealth) {
			ent.setHealth(ent.getMaxHealth());
		}
		else {
			ent.setHealth(newhealth);
		}
		
		
	}
	

	
	public double calcLerpFactor(double s,double sr) {
		double dstep = s;
		double dsteprange = sr;
		
		return dstep/dsteprange;
	}
	
	public void doKnockback(Entity e, Location fromLocation,double speed) {
		// multiply default 0.25
		if (e instanceof Player) {
			if(e != user) {
				tagPlayer((Player) e);
			}
		}
		if (fromLocation.toVector().distance(e.getLocation().toVector()) > 0.1) {
			e.setVelocity(fromLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(-speed));
		}
	}
	
	
	public void doPull(Entity e, Location toLocation,double speed) {
		// multiply default 0.25
		if (e instanceof Player) {
			if(e != user) {
				tagPlayer((Player) e);
			}
		}
		if (toLocation.toVector().distance(e.getLocation().toVector()) > 0.1) {
			e.setVelocity(toLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(speed));
		}
	}
	public void doPin(Entity e, Location toLocation) {
		// multiply default 0.25
		if (e instanceof Player) {
			if(e != user) {
				tagPlayer((Player) e);
			}
		}
		if (toLocation.toVector().distance(e.getLocation().toVector()) > 0.1) {
			double s = e.getLocation().distance(toLocation)/5;
			e.setVelocity(toLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(s));
		}
	}
	public void doPin(Entity e, Location toLocation,double power) {
		// multiply default 0.25
		if (e instanceof Player) {
			if(e != user) {
				tagPlayer((Player) e);
			}
		}
		if (toLocation.toVector().distance(e.getLocation().toVector()) > 0.1) {
			double s = e.getLocation().distance(toLocation)/5;
			e.setVelocity(toLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(s*power));
		}
	}
	public Player pointEntity(Player p) {
		int range = 300;
		int toleranz = 3;
		Location loc = p.getLocation();
		for (double t = 1; t <= range; t=t+0.5) {

			Vector direction = loc.getDirection().normalize();
			double x = direction.getX() * t;
			double y = direction.getY() * t + 1.5;
			double z = direction.getZ() * t;
			loc.add(x, y, z);
			Location lo = loc.clone();

			// Particel


			if (loc.getBlock().getType().isSolid()) {

				break;
			}

			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (!(pl.getName().equalsIgnoreCase(p.getName())) && pl.getGameMode() != GameMode.ADVENTURE) {
					
					Location ploc1 = pl.getLocation();
					Location ploc2 = pl.getLocation();
					ploc2.add(0, 1, 0);
					if (ploc1.distance(loc) <= toleranz || ploc2.distance(loc) <= toleranz) {
						

						return pl;
					}
				}
			}
			
			// SUBTRACTING LOCATION UM den prozess
			// von vorne zu
			// starten
			loc.subtract(x, y, z);
		}
		return null;

	}
	
	public Entity pointRealEntity(Player p) {
		int range = 300;
		int toleranz = 3;
		Location loc = p.getLocation();
		for (double t = 1; t <= range; t=t+0.5) {

			Vector direction = loc.getDirection().normalize();
			double x = direction.getX() * t;
			double y = direction.getY() * t + 1.5;
			double z = direction.getZ() * t;
			loc.add(x, y, z);
			Location lo = loc.clone();

			// Particel


			if (loc.getBlock().getType().isSolid()) {

				break;
			}

			for (Entity ent : p.getWorld().getEntities()) {
				
				if (ent instanceof Player) {
					if (ent == p || ((Player) ent).getGameMode() == GameMode.ADVENTURE) {
						continue;
					}
				}
				
				
					
					Location ploc1 = ent.getLocation();
					Location ploc2 = ent.getLocation();
					ploc2.add(0, 1, 0);
					if (ploc1.distance(loc) <= toleranz || ploc2.distance(loc) <= toleranz) {
						

						return ent;
					}
				
			}
			
			// SUBTRACTING LOCATION UM den prozess
			// von vorne zu
			// starten
			loc.subtract(x, y, z);
		}
		return null;

	}
	

	public Player pointEntity(Player p,int range) {
		
		int toleranz = 1;
		Location loc = p.getLocation();
		for (double t = 1; t <= range; t=t+0.5) {

			Vector direction = loc.getDirection().normalize();
			double x = direction.getX() * t;
			double y = direction.getY() * t + 1.5;
			double z = direction.getZ() * t;
			loc.add(x, y, z);
			Location lo = loc.clone();

			// Particel


			if (loc.getBlock().getType().isSolid()) {

				break;
			}

			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (!(pl.getName().equalsIgnoreCase(p.getName())) && pl.getGameMode() != GameMode.ADVENTURE) {
					
					Location ploc1 = pl.getLocation();
					Location ploc2 = pl.getLocation();
					ploc2.add(0, 1, 0);
					if (ploc1.distance(loc) <= toleranz || ploc2.distance(loc) <= toleranz) {
						

						return pl;
					}
				}
			}
			
			// SUBTRACTING LOCATION UM den prozess
			// von vorne zu
			// starten
			loc.subtract(x, y, z);
		}
		return null;

	}
	public Location block(Player p) {
		Location loc = p.getEyeLocation();
		for (int t = 1; t <= 300; t++) {

			Vector direction = loc.getDirection().normalize();
			double x = direction.getX() * t;
			double y = direction.getY() * t + 1.5;
			double z = direction.getZ() * t;
			loc.add(x, y, z);
			Location lo = loc.clone();
		

			if (loc.getBlock().getType() != Material.AIR) {
				return loc;

			}

			loc.subtract(x, y, z);
		}
		return null;

	}
	public Location block(Player p,int range) {
		Location loc = p.getLocation();
		for (int t = 1; t <= range; t++) {

			Vector direction = loc.getDirection().normalize().multiply(0.5);
			double x = direction.getX() * t;
			double y = direction.getY() * t + 1.5;
			double z = direction.getZ() * t;
			loc.add(x, y, z);
			Location lo = loc.clone();

			if (loc.getBlock().getType() != Material.AIR) {
				return loc;

			}

			loc.subtract(x, y, z);
		}
		return null;

	}
	public Location loc(Player p,double range) {
		Location loc = p.getLocation();
		Location ret = p.getLocation();
		for (int t = 1; t <= range; t++) {

			Vector direction = loc.getDirection().normalize().multiply(0.5);
			double x = direction.getX() * t;
			double y = direction.getY() * t + 1.5;
			double z = direction.getZ() * t;
			loc.add(x, y, z);
			

			ret = loc.clone();
			loc.subtract(x, y, z);
		}
		return ret;

	}
	public Location loc(Player p,int range) {
		Location loc = p.getLocation();
		Location ret = p.getLocation();
		for (int t = 1; t <= range; t++) {

			Vector direction = loc.getDirection().normalize().multiply(0.5);
			double x = direction.getX() * t;
			double y = direction.getY() * t + 1.5;
			double z = direction.getZ() * t;
			loc.add(x, y, z);
			

			ret = loc.clone();
			loc.subtract(x, y, z);
		}
		return ret;

	}
	public Location getTop(Location loca) {
		
		while (loca.getBlock().getType().isSolid()) {
			loca.add(0,1,0);
		}
		return loca.getBlock().getLocation().add(0.5,0.5,0.5);
		
	}
	public Location getFloor(Location loca) {
		
		while (!loca.getBlock().getType().isSolid()) {
			loca.add(0,-1,0);
		}
		return loca.getBlock().getLocation().add(0.5,0.5,0.5);
		
	}
	public String getName() {
		return name;
	}
	
	public void setGliding(Player p,boolean glide) {
		p.setGliding(glide);
		Bukkit.broadcastMessage("G");
		if (glide) {
			EventCollector.gliding.add(p);
		}
		else {
			EventCollector.gliding.remove(p);
		}
		
		
		
	}
	
	public boolean checkHit(LivingEntity le,Location loc,Entity p,double range) {
		
		if (le instanceof Player) {
			
			
			if (((Player)le).getGameMode() == GameMode.ADVENTURE) {
				return false;
			}
			
		}
		if (le != p) { 
			
				if (le instanceof Cow || le instanceof ArmorStand)
					return false;
			
			if (le.getLocation().distance(loc)<range || le.getEyeLocation().distance(loc)<range ) {
				return true;
			}
			
		}
		
		
		return false;
	}
	

	

	
	public boolean swap() {
		
			
			
				
			if (EventCollector.quickSwap.contains(user)) {
				new BukkitRunnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (EventCollector.quickSwap.contains(user)) {
						EventCollector.quickSwap.remove(user);
					}
				}
			}.runTaskLater(main.plugin, 1);
			return true;
			
		}
			
		return false;
	}
	
	public void clearswap() {
		
		
		if (EventCollector.quickSwap.contains(user)) {
			EventCollector.quickSwap.remove(user);
		}
		
	}

	public static Location lookAt(Location loc, Location lookat) {

		loc = loc.clone();

		double dx = lookat.getX() - loc.getX();
		double dy = lookat.getY() - loc.getY();
		double dz = lookat.getZ() - loc.getZ();

		if (dx != 0) {

			if (dx < 0) {
				loc.setYaw((float) (1.5 * Math.PI));
			} else {
				loc.setYaw((float) (0.5 * Math.PI));
			}
			loc.setYaw((float) loc.getYaw() - (float) Math.atan(dz / dx));
		} else if (dz < 0) {
			loc.setYaw((float) Math.PI);
		}

		double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

		loc.setPitch((float) -Math.atan(dy / dxz));

		loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
		loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);

		return loc;
	}
	
	public static Player getNearestPlayer(Player c) {
		double distance = 10000;
		Player nearest = c;
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (nearest == c) {
				nearest = p;
				distance = c.getLocation().distance(p.getLocation());
			}
			if (p != c && p.getGameMode() != GameMode.ADVENTURE) {
				double dis = c.getLocation().distance(p.getLocation());
				if (dis<distance) {
					nearest = p;
					distance = dis;
				}
			}
		}
		

		return nearest;
		
	}
	
	public static Player getNearestPlayer(Player c,Location l,double range) {
		double distance = 10000;
		Player nearest = null;
		for (Player p : Bukkit.getOnlinePlayers()) {
			
			if (p != c && p.getGameMode() != GameMode.ADVENTURE) {
				double dis = l.distance(p.getLocation());
				if (dis<distance&& dis < range) {
					nearest = p;
					distance = dis;
				}
			}
		}
		

		return nearest;
		
	}
	public static LivingEntity getNearestEntity(Player c,Location l,double range) {
		double distance = 10000;
		LivingEntity nearest = null;
		for (LivingEntity p : c.getWorld().getLivingEntities()) {
			
			if (p != c) {
				double dis = l.distance(p.getLocation());
				if (dis<distance&& dis < range) {
					nearest = p;
					distance = dis;
				}
			}
		}
		

		return nearest;
		
	}
	public ArmorStand createArmorStand(Location loca) {
		ArmorStand a = (ArmorStand) loca.getWorld().spawnEntity(loca, EntityType.ARMOR_STAND);
		
		a.setInvulnerable(true);
		a.setVisible(false);
		a.setGravity(false);
		
		
		return a;
	}
	
	
	
	
	
}
