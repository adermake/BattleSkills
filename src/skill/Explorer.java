package skill;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import core.main;
import net.minecraft.server.v1_15_R1.Particles;
import skillcore.Skill;
import utils.ParUtils;

public class Explorer extends Skill {
	ArrayList<String> structures = new ArrayList<String>();
	Location closest;
	
	
	@Override
	public void onSkillToggleOff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillToggleOn() {
		// TODO Auto-generated method stub
		

		//ParUtils.createFlyingParticle(Particles.TOTEM_OF_UNDYING, user.getEyeLocation(), 0, 0, 0, 1, 2, dir);
		
		
	}
	int searchCooldown = 0;
	@Override
	public void onSkillLoop() {
		// TODO Auto-generated method stub
		searchCooldown++;
		if (searchCooldown> 20 * 5) {
			searchCooldown = 0;
			new BukkitRunnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					closest = closestStructure(user.getLocation());;
					closest.setY(user.getLocation().getY());
					
				}
			}.runTaskAsynchronously(main.plugin);
		}
		if (closest != null) {
			Vector dir = closest.toVector().subtract(user.getLocation().toVector()).normalize();
			//Bukkit.broadcastMessage(""+user.getLocation().getDirection().angle(dir));
			if (user.getLocation().getDirection().angle(dir)*100 < 60) {
				ParUtils.createFlyingParticle(Particles.TOTEM_OF_UNDYING, user.getEyeLocation(), 1, 1, 1, 1, 3,dir,user);
				user.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20, 0));
			}
		}
		
	}

	@Override
	public void onSkillStart() {
		searchCooldown = 20 * 5;
		// TODO Auto-generated method stub
		structures.clear();
		structures.add("Mineshaft");
		structures.add("Desert_Pyramid");
		structures.add("Igloo");
		structures.add("Jungle_Pyramid");
		structures.add("Mansion");
		structures.add("Monument");
		structures.add("Ocean_Ruin");
		structures.add("Pillager_Outpost");
		structures.add("Shipwreck");
		structures.add("Stronghold");
		structures.add("Village");
		
	}

	@Override
	public void onSkillEnd() {
		// TODO Auto-generated method stub
		
	}
	public Location closestStructure(Location l) {
		try {
			Location loc = getLocationFromString(getStructure(user.getLocation(),structures.get(0)));
			
			for (String s : structures) {
				Location l1 = getLocationFromString(getStructure(user.getLocation(),s));
				if (l1.distance(l) < loc.distance(l)) {
					loc = l1;
				}
			}
			
			return loc;
			
			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | ClassNotFoundException | InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return user.getLocation();
	}
	
    private String getStructure(Location l, String structure) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        Method getHandle = l.getWorld().getClass().getMethod("getHandle");
        Object nmsWorld = getHandle.invoke(l.getWorld());
        Object blockPositionString = nmsWorld.getClass().getMethod("a", new Class[] { String.class, getNMSClass("BlockPosition"), int.class, boolean.class }).invoke(nmsWorld, structure,getBlockPosition(l), 100,false);
        return blockPositionString.toString();
    }
    
    private Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = "net.minecraft.server." + version + nmsClassString;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }
    private Object getBlockPosition(Location loc) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
        Class<?> nmsBlockPosition = getNMSClass("BlockPosition");
        Object nmsBlockPositionInstance = nmsBlockPosition
                .getConstructor(new Class[] { Double.TYPE, Double.TYPE, Double.TYPE })
                .newInstance(new Object[] { loc.getX(), loc.getY(), loc.getZ() });
        return nmsBlockPositionInstance;
    }
    
    
    public Location getLocationFromString(String s) {
    	return new Location(user.getWorld(), getNumberAt(s,"x="), getNumberAt(s,"y="), getNumberAt(s,"z="));
    }
    public double getNumberAt(String s,String prefix) {
    	int index = s.indexOf(prefix)+prefix.length();
    	String dString = "";
    	
    	for (int i = index;i<s.length();i++) {
    		if (s.charAt(i) == ',' ||s.charAt(i) == '}') {
    			break;
    		}
    		else {
    			dString += s.charAt(i);
    		}
    	}
    	
    	return Double.parseDouble(dString);
    	
    }

	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub
		
	}
}
