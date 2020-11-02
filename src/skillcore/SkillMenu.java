package skillcore;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SkillMenu {
	
	Inventory inv;
	Player p;
	public static HashMap<Player,SkillMenu> invs = new HashMap<Player,SkillMenu>();
	
	public SkillMenu(Player p) {
		this.p = p;
		inv = Bukkit.createInventory(p, 9 * 5);
		inv.setItem(getItemSlot(0,0), createItem(Material.SPRUCE_SIGN,"§eShift"));
		inv.setItem(getItemSlot(2,0), createItem(Material.JUNGLE_SIGN,"§eSwap"));
		inv.setItem(getItemSlot(4,0), createItem(Material.OAK_SIGN,"§eLaunch"));
		inv.setItem(getItemSlot(6,0), createItem(Material.ACACIA_SIGN,"§eClick"));
		inv.setItem(getItemSlot(8,0), createItem(Material.SPRUCE_SIGN,"§ePassive"));
	}
	public static void open(Player p) {
		if (invs.containsKey(p)) {
			p.openInventory(invs.get(p).inv);
		}
		else {
			invs.put(p,new SkillMenu(p));
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

}
