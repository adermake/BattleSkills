package core;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Move implements Listener {

	
	@EventHandler
	public void oMove(PlayerMoveEvent e) {
		/*
		Player p = e.getPlayer();
		if (p.getLocation().add(0,-1,0).getBlock().getType() == Material.AIR) {
			if (p.getInventory().getItemInMainHand().getType().isBlock() && p.getInventory().getItemInMainHand().getType() != Material.AIR) {
				//p.getLocation().add(0,-1,0).getBlock().setType(p.getInventory().getItemInMainHand().getType());
				
				Vector v = p.getVelocity();
				p.getLocation().add(0,-1,0).add(v).getBlock().setType(p.getInventory().getItemInMainHand().getType());
				//removeOneItem(p.getInventory().getItemInMainHand());
			}
			else if (p.getInventory().getItemInOffHand().getType().isBlock()) {
				
			}
		}
		*/
	}
	
	
	
	public void removeOneItem(ItemStack is) {
		if (is.getAmount() > 1) {
			is.setAmount(is.getAmount()-1);
		}
		else {
			is.setAmount(0);
		}
	}
}
