package skillcore;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_15_R1.PlayerInventory;
import utils.NBTUtils;

public class SkillMenu {
	
	Inventory inv;
	Player p;
	
	public static Material item = Material.YELLOW_STAINED_GLASS_PANE;
	public static Material skill = Material.CYAN_STAINED_GLASS_PANE;
	public static Material passiveskill = Material.ORANGE_STAINED_GLASS_PANE;
	public static HashMap<Player,SkillMenu> invs = new HashMap<Player,SkillMenu>();
	
	
	//
	public SkillMenu(Player p) {
		this.p = p;
		inv = Bukkit.createInventory(p, 9 * 6,"§eSkills");
		for (int x = 0;x<8;x++) {
			inv.setItem(getItemSlot(x,0), createItem(item,"§ePlace Item here"));
		}
		for (int x = 0;x<8;x++) {
			inv.setItem(getItemSlot(x,1), createItem(skill,"§ePlace Skill here"));
		}
		for (int x = 0;x<8;x++) {
			inv.setItem(getItemSlot(x,2), createItem(Material.GRAY_STAINED_GLASS_PANE,"§eRight Click"));
		}
		for (int x = 0;x<8;x++) {
			inv.setItem(getItemSlot(x,3), createItem(Material.GRAY_STAINED_GLASS_PANE,"§eLeft Click"));
		}
		for (int x = 0;x<8;x++) {
			inv.setItem(getItemSlot(x,4), createItem(Material.GRAY_STAINED_GLASS_PANE,"§eF"));
		}
		for (int x = 0;x<8;x++) {
			inv.setItem(getItemSlot(x,5), createItem(Material.LIME_STAINED_GLASS_PANE,"§eShift"));
		}
		for (int y = 0;y<6;y++) {
			inv.setItem(getItemSlot(8,y), createItem(passiveskill,"§ePassive"));
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
	
	public static ItemStack createItem(Material m,String disp) {
		
		
		ItemStack is = new ItemStack(m);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(disp);
		is.setItemMeta(im);
		return is;
		
		
	}
	
	public void updateSkills() {
		
		if (!Skill.skills.containsKey(p)) {
			Skill.skills.put(p, new ArrayList<SkillActionPair>());
		}
		for (SkillActionPair s : Skill.skills.get(p)) {
			s.skill.toggleSkill(false);
		}
		ArrayList<SkillActionPair> nextPair = new ArrayList<SkillActionPair>();
		for (int x = 8;x<inv.getSize();x++) {
			ItemStack is = inv.getItem(x);
			if (!is.hasItemMeta())
				continue;
			
			if (is.getType() == Material.NETHER_STAR) {
				
				Skill skill = Skill.findSkill(p, is);
				
				if (skill == null) {
					
					skill = Skill.createSkillFromItemName(p, is.getItemMeta().getDisplayName());
					skill.ID = Integer.parseInt(NBTUtils.getNBT("SkillID", is));
					
				}
				if (getSkillActionFromSlot(x) == SkillAction.PASSIVE) {
					skill.toggleSkill(true);
				}
				
				SkillActionPair sap = new SkillActionPair(getSkillActionFromSlot(x),getCasterItem(x), skill);
				nextPair.add(sap);
				
			}
		}
		for (SkillActionPair s : Skill.skills.get(p)) {
			
			if (!containsSkill(nextPair,s.skill)) {
				
				s.skill.killSkill();
			}
		}
		Skill.skills.put(p, nextPair);
		
	}
	
	public boolean containsSkill(ArrayList<SkillActionPair> nextPair,Skill s) {
		
		for (SkillActionPair ska : nextPair) {
			if (ska.skill.equals(s)) {
				return true;
			}
		}
		
		return false;
	}
	public Material getCasterItem(int slot) {
		Material m = Material.AIR;
		if (slot %9 < 8) {
			m = inv.getItem(slot%9).getType();
		}
		
		if (m.toString().toLowerCase().contains("glass_pane")) {
			m = Material.AIR;
		}
		return m;
		
	}
	public SkillAction getSkillActionFromSlot(int slot) {
		for (int i = 18+slot%9;i<54;i+=9) {
			if (inv.getItem(i).getType() == Material.LIME_STAINED_GLASS_PANE) {
				String name = inv.getItem(i).getItemMeta().getDisplayName();
				if (name.contains("F")) {
					return SkillAction.F;
				}
				if (name.contains("Shift")) {
					return SkillAction.SHIFT;
				}
				if (name.contains("Right Click")) {
					return SkillAction.RIGHTCLICK;
				}
				if (name.contains("Left Click")) {
					return SkillAction.LEFTCLICK;
				}
			}
		}
		return SkillAction.PASSIVE;
	}
	
	
	
}
