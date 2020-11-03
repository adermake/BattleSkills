package crafting;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import core.main;
import net.minecraft.server.v1_15_R1.Particles;
import utils.ParUtils;

public class FireSelection {

	public static ArrayList<FireSelection> fireSelections = new ArrayList<FireSelection>();
	ArrayList<ArmorStand> as;
	boolean selected = false;
	public FireSelection(Location loc,ArrayList<Player> list) {
		as = new ArrayList<ArmorStand>();
		for (Player p : list) {
			as.add(createArmorStand(loc, p));
		}
		fireSelections.add(this);
		new BukkitRunnable() {
			int step = 0;
			public void run() {
				step++;
				int i = 0;
				ParUtils.createFlyingParticle(Particles.CAMPFIRE_COSY_SMOKE, loc, 0.1D, 0.1D, 0.1D, 1, 0.3,new Vector(0,1,0) );
				for (ArmorStand a : as) {
					
					Location l = ParUtils.stepCalcCircle(loc, 0.4f+0.2F *as.size(), new Vector(0,1,0), -1.5F, step+i*(44/as.size()));
					
					
					double angle =  step*(Math.PI*2D/44D)+i*(Math.PI*2D/(double)as.size())-Math.PI/2D;
					a.setHeadPose(new EulerAngle(0, angle, 0));
					a.teleport(l);
					i++;
				}
				if (loc.getBlock().getType() != Material.FIRE || selected) {
					this.cancel();
					for (ArmorStand a : as) {
						a.remove();
					}
					fireSelections.remove(this);
				}
			}
		}.runTaskTimer(main.plugin,2,2);
		
		
	}
	
	public void clicked(ArmorStand a) {
		
		if (as.contains(a)) {
			Bukkit.broadcastMessage(""+a.getCustomName());
			selected = true;
			onSelect(a.getCustomName());
		}
	}
	public void onSelect(String name) {
		
	}
	
	public void doPin(Entity e, Location toLocation) {
		// multiply default 0.25
		
		if (toLocation.toVector().distance(e.getLocation().toVector()) > 0.1) {
			double s = e.getLocation().distance(toLocation)/5;
			e.setVelocity(toLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(s));
		}
	}
	
	public ArmorStand createArmorStand(Location loc,Player p) {
		
		ArmorStand a = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		a.setVisible(false);
		a.setGravity(false);
		a.setSmall(true);
		a.setCustomName(p.getName());
		p.setCustomNameVisible(true);
		ItemStack playerhead = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
		String name = p.getName();
	    SkullMeta playerheadmeta = (SkullMeta) playerhead.getItemMeta();
	    playerheadmeta.setOwner(name);
	    playerheadmeta.setDisplayName(name+"'s Kopf");
	    playerhead.setItemMeta(playerheadmeta);
		a.setHelmet(playerhead);
		return a;
	}
}
