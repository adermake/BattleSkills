package skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import core.main;
import net.minecraft.server.v1_15_R1.ContainerEnchantTable;
import net.minecraft.server.v1_15_R1.EntityHuman;
import net.minecraft.server.v1_15_R1.PlayerInventory;
import skillcore.Skill;

public class Enchanter extends Skill {
	public static ArrayList<Player> enchanters = new ArrayList<Player>();
	@Override
	public boolean onSkillToggleOff() {
		// TODO Auto-generated method stub
		Location l1 = user.getLocation();
		l1.setY(0);
		l1.setX(0);
		l1.setZ(0);
		l1.getBlock().setType(Material.BEDROCK);
		return true;
	}

	@Override
	public boolean onSkillToggleOn() {
		// TODO Auto-generated method stub
		/*
		EntityHuman h =((CraftPlayer)user).getHandle();
		ContainerEnchantTable cet = new ContainerEnchantTable(0, h.inventory);
		Bukkit.broadcastMessage("ABBBBBBBBBB");
		cet.shiftClick(h, 1);
		*/
		Location l1 = user.getLocation();
		l1.setY(0);
		l1.setX(0);
		l1.setZ(0);
		l1.getBlock().setType(Material.ENCHANTING_TABLE);
		enchanters.add(user);
		user.openEnchanting(l1, true);
		//user.openEnchanting(user.getLocation().add(2,0,0), true);
		toggleSkill(false);
		return true;
	
	}

	@Override
	public void onSkillLoop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

}
