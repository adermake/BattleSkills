package skillcore;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;






public class EventCollector implements Listener {

	public static ArrayList<Player> gliding = new ArrayList<Player>();
	public static ArrayList<Player> quickSwap = new ArrayList<Player>();
	@EventHandler
	public void onPayRespectF(PlayerSwapHandItemsEvent e) {
		if (!quickSwap.contains(e.getPlayer())) {
			quickSwap.add(e.getPlayer());
		}
		
		
		e.setCancelled(true);
	}
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Skill.sendEvent(e.getPlayer(), e);
	}
	
	
	
	
	@EventHandler
	public void plsDontLeave(PlayerToggleSneakEvent e) {
		if (e.getPlayer().getVehicle() != null) {
			Bukkit.broadcastMessage("STAY");
			e.setCancelled(true);
		}
		
	}
	
	
	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		
		e.setCancelled(true);

	}

	@EventHandler
	public void cancelGilding(EntityToggleGlideEvent e) {
		if (gliding.contains(e.getEntity())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void totem(EntityResurrectEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	public void onPressSpell(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {
			
		/*
			if (Spell.silenced.contains(p)) {
				Actionbar bar = new Actionbar("§cDu bist verstummt!");
				return;
			}
			*/
		ItemStack is = p.getInventory().getItemInMainHand();
		if(is.getType() == Material.TOTEM_OF_UNDYING) {
			SkillMenu.open(p);
		}
		if (is != null) {
			if (is.hasItemMeta()) {
				if (is.getItemMeta().hasDisplayName()) {
					
						String name = is.getItemMeta().getDisplayName();
					
						
						name = name.substring(2, name.length());
						name = name.replace(" ", "");
						try {
							name = "skill." + name;
							// Bukkit.broadcastMessage("F" + s);
							Class clazz = Class.forName(name);
							Skill sp = (Skill) clazz.newInstance();
							
							
							sp.activate(p);
							
							//p.getInventory().setItemInMainHand(new ItemStack(Material.PRISMARINE_CRYSTALS));
							
						} catch (Exception ex) {
							ex.printStackTrace(System.out);
						}
					
							
						
							

					}
					

				}

			}

		}
		
		
	
		}
		

	

}
