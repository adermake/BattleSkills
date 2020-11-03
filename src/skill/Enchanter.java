package skill;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import skillcore.Skill;

public class Enchanter extends Skill {

	@Override
	public void onSkillToggleOff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillToggleOn() {
		// TODO Auto-generated method stub
		Inventory i = Bukkit.createInventory(user, InventoryType.ENCHANTING,"Enchant");
		user.openInventory(i);
		toggleSkill(false);
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
