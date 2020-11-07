package skillcore;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import core.main;
import net.minecraft.server.v1_15_R1.EnchantmentManager;
import skill.Enchanter;
import utils.ReconnectUtils;






public class EventCollector implements Listener {

	public static ArrayList<Player> gliding = new ArrayList<Player>();
	public static ArrayList<Player> quickSwap = new ArrayList<Player>();
	@EventHandler
	public void onPayRespectF(PlayerSwapHandItemsEvent e) {
		if (!quickSwap.contains(e.getPlayer())) {
			quickSwap.add(e.getPlayer());
		}
		Player p = e.getPlayer();
		
		if (!Skill.skills.containsKey(p))
			return;
		
		
		for (SkillActionPair ska : Skill.skills.get(p)) {
			
			if (ska.skillAction == SkillAction.F && (p.getInventory().getItemInMainHand().getType() == ska.itemType || ska.itemType== Material.AIR)) {
				e.setCancelled(true);
				ska.skill.toggleSkill(!ska.skill.active);
				
			}
			
			
		}
		
		
	}
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Skill.sendEvent(p, e);
		if (e.getHand() == EquipmentSlot.OFF_HAND)
			return;
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.SMITHING_TABLE) {
				SkillMenu.open(p);
				e.setCancelled(true);
				return;
			}
		}
		if (!Skill.skills.containsKey(p))
			return;
	
		if (e.getAction()== Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			for (SkillActionPair ska : Skill.skills.get(p)) {
				
				if (ska.skillAction == SkillAction.RIGHTCLICK && (p.getInventory().getItemInMainHand().getType() == ska.itemType || ska.itemType== Material.AIR)) {
					//e.setCancelled(true);
					ska.skill.toggleSkill(!ska.skill.active);
				}
				
				
			}
		}
		if (e.getAction()== Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			for (SkillActionPair ska : Skill.skills.get(p)) {
				
				if (ska.skillAction == SkillAction.LEFTCLICK && (p.getInventory().getItemInMainHand().getType() == ska.itemType || ska.itemType== Material.AIR) ) {
					//e.setCancelled(true);
					ska.skill.toggleSkill(!ska.skill.active);
				}
				
				
			}
		}
	}
	
	
	
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (!Skill.skills.containsKey(p))
			return;

		for (SkillActionPair ska : Skill.skills.get(p)) {

			if (ska.skillAction == SkillAction.SHIFT && (p.getInventory().getItemInMainHand().getType() == ska.itemType || ska.itemType== Material.AIR)) {
				
				
				ska.skill.toggleSkill(!p.isSneaking());
			}
			
			
		}
		
	}
	
	
	

	@EventHandler
	public void cancelGilding(EntityToggleGlideEvent e) {
		if (gliding.contains(e.getEntity())) {
			e.setCancelled(true);
		}
	}
/*
	@EventHandler
	public void totem(EntityResurrectEvent e) {
		e.setCancelled(true);
	}
	*/
	/*
	@EventHandler
	public void onPressSkill(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {
			
		/*
			if (Spell.silenced.contains(p)) {
				Actionbar bar = new Actionbar("§cDu bist verstummt!");
				return;
			}
			
		ItemStack is = p.getInventory().getItemInMainHand();
		
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
		*/
		
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		
		Player p = e.getPlayer();
		Player rec = null;
		
		for (Player pl : Skill.skills.keySet()) {
			Bukkit.broadcastMessage("PL"+pl.getName());
			if (pl.getName().equals(p.getName())) {
				rec = pl;
				
			}
		}
		if (rec != null) {
			
			ArrayList<SkillActionPair> skills = Skill.skills.get(rec);
			Skill.skills.remove(rec);
			Skill.skills.put(p,skills);
			
			
			
			Skill.setUserOfAllSkills(p);
		}
		 rec = null;
		for (Player pl : SkillMenu.invs.keySet()) {
			Bukkit.broadcastMessage("PL"+pl.getName());
			if (pl.getName().equals(p.getName())) {
				rec = pl;
				
			}
		}
		
		if (rec != null) {
			
			SkillMenu sm = SkillMenu.invs.get(rec);
			SkillMenu.invs.remove(rec);
			SkillMenu.invs.put(p, sm);
			sm.p = p;
			
			
			
			
		}
		ReconnectUtils.reconnectPlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onEntityHit(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Skill.sendEvent((Player) e.getDamager(), e);
		}
		if (e.getEntity() instanceof Player) {
			Skill.sendEvent((Player) e.getEntity(), e);
		}
		
	}
	
	@EventHandler
	public void onEntityTarget(EntityTargetEvent e) {
		if (e.getTarget() instanceof Player) {
			Skill.sendEvent((Player) e.getTarget(), e);
		}
		
		
	}
	@EventHandler
	public void onPreEnchant(PrepareItemEnchantEvent e) {
			if (Enchanter.enchanters.contains(e.getEnchanter())) {
				Player p = e.getEnchanter();
				int i = p.getLevel();
				if (i <=0)
					i = 1;
			    for(EnchantmentOffer offer : e.getOffers()) {
			    	if (offer != null) {
			    		
			    	
			    	offer.setCost(i);
			    	}
			    }
			}
		
		
        
		
	}
	
	@EventHandler
	public void onYeet(ProjectileLaunchEvent e) {
		if (e.getEntity().getShooter() instanceof Player) {
			Skill.sendEvent((Player) e.getEntity().getShooter(), e);
		}
	}
	
	

}
