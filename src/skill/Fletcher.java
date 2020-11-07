package skill;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import skillcore.Skill;

public class Fletcher extends Skill {

	@Override
	public boolean onSkillToggleOff() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onSkillToggleOn() {
		// TODO Auto-generated method stub
		if (drainXp(3)) {
			
			user.getInventory().addItem(new ItemStack(Material.ARROW,1));
		}
		
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
