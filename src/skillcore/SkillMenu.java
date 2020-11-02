package skillcore;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_15_R1.PlayerInventory;

public class SkillMenu {
	
	Inventory inv;
	Player p;
	
	public static Material item = Material.BARRIER;
	public static Material skill = Material.STRUCTURE_VOID;
	public static HashMap<Player,SkillMenu> invs = new HashMap<Player,SkillMenu>();
	//
	public SkillMenu(Player p) {
		this.p = p;
		inv = Bukkit.createInventory(p, 9 * 6,"§eSkills");
		for (int x = 0;x<9;x++) {
			inv.setItem(getItemSlot(x,0), createItem(item,"§ePlace Item here"));
		}
		for (int x = 0;x<9;x++) {
			inv.setItem(getItemSlot(x,1), createItem(skill,"§ePlace Skill here"));
		}
		for (int x = 0;x<9;x++) {
			inv.setItem(getItemSlot(x,2), createItem(Material.CYAN_STAINED_GLASS,"§eRightClick"));
		}
		
	}
	public static void open(Player p) {
		if (invs.containsKey(p)) {
			
			p.openInventory(invs.get(p).inv);
			InventoryListener.onOpenInventory(p);
		}
		else {
			invs.put(p,new SkillMenu(p));
			p.openInventory(invs.get(p).inv);
			InventoryListener.onOpenInventory(p);
		}
		
	}
	
	public int getItemSlot(int x,int y) {
		return 9 * y + x;
	}
	
	public ItemStack createItem(Material m,String disp) {
		
		
		ItemStack is = new ItemStack(m);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(disp);
		is.setItemMeta(im);
		return is;
		
		
	}
	
	
	
	@EventHandler
	public void onInvetoryInteract(PlayerInventory e) {
		
	}

}
