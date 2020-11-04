package skillcore;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import core.main;
import skill.Enchanter;

public class InventoryListener implements Listener {

	
	public static HashMap<Player,ItemStack[]> inSkillMenu = new HashMap<Player,ItemStack[]>();
	@EventHandler
	public void onInventoryInteract(InventoryInteractEvent e) {
		
	}
	@EventHandler
	public void onInventoryDrag(InventoryDragEvent e) {
		if (!inSkillMenu.containsKey(e.getWhoClicked()))
			return;
		e.setCancelled(true);
	}
	//
	@EventHandler
	public void onInventoryInteract(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (!inSkillMenu.containsKey(e.getWhoClicked()))
			return;
		
	
		//Bukkit.broadcastMessage(""+e.getAction());
		Inventory inv = e.getInventory();
		Inventory bottominv = e.getView().getBottomInventory();
		if (e.getAction() == InventoryAction.DROP_ALL_CURSOR ) {
			if (e.getCursor().getType() != Material.NETHER_STAR)
			e.setCursor(null);
			e.setCancelled(true);
		}
		
		if (e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_HALF || e.getAction() == InventoryAction.PLACE_ALL
				|| e.getAction() == InventoryAction.PLACE_ONE || e.getAction() == InventoryAction.PLACE_SOME || e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
			
			
		if (e.getRawSlot() < e.getView().getTopInventory().getSize()) {
			
			//GLASS PANE HANDLING
			if(e.getCurrentItem().getType().toString().toLowerCase().contains("glass_pane") && e.getRawSlot() >= 18 && e.getCurrentItem().getType() != SkillMenu.passiveskill) {
				
				//RESET ROW
				for (int i = e.getSlot()%9+18;i<54;i+=9) {
					ItemStack a = inv.getItem(i);
					a.setType(Material.GRAY_STAINED_GLASS_PANE);
					inv.setItem(i,a );
				}
				
				
				ItemStack is = e.getCurrentItem();
				is.setType(Material.LIME_STAINED_GLASS_PANE);
				e.setCurrentItem(is);
				e.setCancelled(true);
			}
			
			
			//GLASSPANE HANDLING
			
			if (e.getCurrentItem().getType() == SkillMenu.item || e.getCurrentItem().getType() == SkillMenu.skill || e.getCurrentItem().getType() == SkillMenu.passiveskill) {
				//PLACE ITEM
				
				if (e.getCursor() == null || e.getCursor().getType() == Material.AIR) {
					
					e.setCancelled(true);
					
				}
				else if (e.getRawSlot() >= 9 && e.getRawSlot() < 18 && e.getCursor().getType() != Material.NETHER_STAR) {
					
					e.setCancelled(true);
					
				}
				else if (e.getRawSlot() %9 == 8 && e.getCursor().getType() != Material.NETHER_STAR) {
					
					e.setCancelled(true);
					
				}
				else {
					new BukkitRunnable() {
						public void run() {
							e.setCursor(null);
						}
					}.runTaskLater(main.plugin,1);
					
				}
				
				//PLACE ITEM
			}
			else {
				if (e.getCursor() == null || e.getCursor().getType() == Material.AIR) 
				{
					
					//PICKUP ITEM
					if (e.getRawSlot() < 8) {
						e.setCursor(e.getCurrentItem());
						inv.setItem( e.getSlot(),SkillMenu.createItem(SkillMenu.item,"§ePlace Item here"));
						
						e.setCancelled(true);
					}
					else if(e.getRawSlot()%9 == 8) {
						e.setCursor(e.getCurrentItem());
						inv.setItem( e.getSlot(),SkillMenu.createItem(SkillMenu.passiveskill,"§ePassive"));
						
						e.setCancelled(true);
					}
					else if (e.getRawSlot() < 17){
						e.setCursor(e.getCurrentItem());
						inv.setItem( e.getSlot(),SkillMenu.createItem(SkillMenu.skill,"§ePlace Skill here"));
						
						e.setCancelled(true);
					}
					
					//PICKUP ITEM
				}
				
				
			}
			
			
			
		}
		else {
			if(e.getCursor().getType() == Material.NETHER_STAR) {
				if (e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
					e.setCancelled(true);
					return;
				}else {
					e.setCancelled(false);
					return;
				}
				
				
			}
			if (e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_HALF || e.getCursor().getType() == Material.NETHER_STAR) {
				
			
			ItemStack is  = e.getCurrentItem().clone();
			if (is.getType() != Material.NETHER_STAR) {
				//Bukkit.broadcastMessage(""+(is != null));
				e.setCursor(is);
				e.setCancelled(true);
				//Bukkit.broadcastMessage("CANCEL");
			}
		
			}
			else {
				
				e.setCancelled(true);
			}
		
		}
		
		}
		else {
			e.setCancelled(true);
		}
	
		//Bukkit.broadcastMessage("RAW SLOT" +e.getRawSlot());
	
		
	}
	
	
	
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent e) {
		if (Enchanter.enchanters.contains(e.getPlayer())) {
			Enchanter.enchanters.remove(e.getPlayer());
			
		}
		Player p = (Player) e.getPlayer();
		if (inSkillMenu.containsKey(e.getPlayer())) {
			if (p.getItemOnCursor().getType() != Material.NETHER_STAR)
			p.setItemOnCursor(null);
			SkillMenu.invs.get(p).updateSkills();
			inSkillMenu.remove(e.getPlayer());
		}
		
		//e.getPlayer().getInventory().setContents(inSkillMenu.get(e.getPlayer()));
	
		//Bukkit.broadcastMessage("Y");
	}
	
	
	public static void onOpenInventory(Player p) {
		
		inSkillMenu.put(p,p.getInventory().getContents().clone());
	}
}
