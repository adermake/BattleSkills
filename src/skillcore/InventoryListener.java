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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import core.main;

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
	@EventHandler
	public void onInventoryInteract(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (!inSkillMenu.containsKey(e.getWhoClicked()))
			return;
		
		Bukkit.broadcastMessage(""+e.getAction());
		Inventory inv = e.getInventory();
		Inventory bottominv = e.getView().getBottomInventory();
		if (e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_HALF || e.getAction() == InventoryAction.PLACE_ALL
				|| e.getAction() == InventoryAction.PLACE_ONE || e.getAction() == InventoryAction.PLACE_SOME || e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
			
		
		if (e.getRawSlot() < e.getView().getTopInventory().getSize()) {
			
			if (e.getCurrentItem().getType() == SkillMenu.item || e.getCurrentItem().getType() == SkillMenu.skill) {
				if (e.getCursor() == null || e.getCursor().getType() == Material.AIR) {
					
					e.setCancelled(true);
					
				}
				else {
					new BukkitRunnable() {
						public void run() {
							e.setCursor(null);
						}
					}.runTaskLater(main.plugin,1);
					
				}
				
				
			}
			
			
			
		}
		else {
			if (e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_HALF) {
				
			
			ItemStack is  = e.getCurrentItem().clone();
			Bukkit.broadcastMessage(""+(is != null));
			e.setCursor(is);
			e.setCancelled(true);
			Bukkit.broadcastMessage("CANCEL");
			}
			else {
				
				e.setCancelled(true);
			}
		
		}
		
		}
		else {
			e.setCancelled(true);
		}
	
		Bukkit.broadcastMessage("RAW SLOT" +e.getRawSlot());
		/*
		 * 
		
		
		
		
		
		Bukkit.broadcastMessage(""+e.getRawSlot());
		Bukkit.broadcastMessage(""+e.getView().getTopInventory().getSize());
		if (e.getRawSlot() > e.getView().getTopInventory().getSize()) {
			
			
			
			if (e.getCurrentItem() == null) {
				e.setCursor(null);
				e.setCancelled(true);
			}
			else {
				ItemStack is  = e.getCurrentItem().clone();
				Bukkit.broadcastMessage(""+(is != null));
				//is.addEnchantment(Enchantment.VANISHING_CURSE, 1);
				e.setCursor(is);
				e.setCancelled(true);
				Bukkit.broadcastMessage("CANCEL");
				//bottominv.setItem(e.getSlot(), e.getCurrentItem());
			}
			
		}
		
		
		
		
		*/
		
		
		
		
		
		/*
		if (e.getView().getTitle().contains("Skills")) {
			
			//PLACE ITEM IN ITEM
			if (e.getSlot() <= 8) {
				e.setCancelled(true);

				if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
					
					if (e.getCurrentItem().getType() == SkillMenu.item) {
						inv.setItem(e.getSlot(), e.getCursor());
						e.setCursor(null);
					}
					else {
						ItemStack is = e.getCurrentItem();
						inv.setItem(e.getSlot(), e.getCursor());
						e.setCursor(is);
					}							
				}
				else if (e.getCurrentItem().getType() != SkillMenu.item){
					e.setCursor(e.getCurrentItem());
					inv.setItem(e.getSlot(), new ItemStack(SkillMenu.item));
				}
			}
		}
		else if(inSkillMenu.contains(e.getWhoClicked())){
			Bukkit.broadcastMessage("CLIKCED");
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
				inv.setItem(e.getSlot(), e.getCursor());
				e.setCurrentItem(e.getCurrentItem());
			}
			
		}
		Bukkit.broadcastMessage("HBB"+e.getRawSlot());
		Bukkit.broadcastMessage("S"+e.getSlot());
		*/
		
		
	}
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent e) {
		
		//e.getPlayer().getInventory().setContents(inSkillMenu.get(e.getPlayer()));
		inSkillMenu.remove(e.getPlayer());
		Bukkit.broadcastMessage("Y");
	}
	
	
	public static void onOpenInventory(Player p) {
		
		inSkillMenu.put(p,p.getInventory().getContents().clone());
	}
}
