package crafting;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftItem;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import core.main;
import net.minecraft.server.v1_15_R1.Particles;
import utils.ParUtils;

public class FireSorcery implements Listener {

	
	@EventHandler
	public void onCombust(EntityCombustByBlockEvent e) {
		if (e.getEntity() instanceof CraftItem) {
			Item i =(Item) e.getEntity();
			
			if (i.getItemStack().getType() == Material.GOLD_NUGGET) {
				ParUtils.createFlyingParticle(Particles.CAMPFIRE_SIGNAL_SMOKE, i.getLocation(), 0, 0, 0, 1, 1, new Vector(0,1,0));
				ArrayList<Player> list = new ArrayList<Player>();
				for (Player p : Bukkit.getOnlinePlayers() ) {
					list.add(p);
				}
				
				
				for (BlockFace bf : BlockFace.values()) {
					
					if (e.getEntity().getLocation().getBlock().getRelative(bf).getType() == Material.FIRE) {
						Location loc = e.getEntity().getLocation().getBlock().getRelative(bf).getLocation().add(0.5,0.5,0.5);
						//ParUtils.debug(e.getEntity().getLocation().getBlock().getRelative(bf).getLocation().add(0.5,0.5,0.5));
						new FireSelection(loc,list) {
							
							@Override
							public void onSelect(String name) {
								Player p = Bukkit.getPlayer(name);
								
								new BukkitRunnable() {
									int t = 0;
									Vector v = new Vector(0,1,0);
									public void run() {
										t++;
										Vector dir = p.getLocation().toVector().subtract(loc.toVector());
										dir.setY(0);
										v.add(dir.multiply(0.0001)).normalize();
										ParUtils.createFlyingParticle(Particles.CAMPFIRE_COSY_SMOKE, loc, 0.1D, 0.1D, 0.1D, 1, 0.3,v );
										
										if (loc.getBlock().getType() != Material.FIRE || t> 20*10) {
											this.cancel();
										}
									}
								}.runTaskTimer(main.plugin,1,1);
								
							}
							
						};
						
						return;
					}
					
				}
				//new FireSelection(e.getEntity().getLocation().add(0.5,0.5,0.5),list);
				
			}
			
		}
	}
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e) {
		
		if (e.getRightClicked() instanceof ArmorStand) {
			for (FireSelection fs : FireSelection.fireSelections) {
				
				fs.clicked((ArmorStand) e.getRightClicked());	
				e.setCancelled(true);
				
			}
		}
	}
	
	
	
}
