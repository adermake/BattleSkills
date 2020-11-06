package skill;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import skillcore.Skill;

public class Quickbuilder extends Skill {

	@Override
	public void onSkillToggleOff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillToggleOn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillLoop() {
		// TODO Auto-generated method stub
		ItemStack is = user.getInventory().getItemInMainHand();
		if (is.getType().isBlock()) {
			if (!user.getLocation().add(0,-1,0).getBlock().getType().isSolid()) {
				user.getLocation().add(0,-1,0).getBlock().setType(is.getType());
				is.setAmount(is.getAmount()-1);
			}
			
		}
		
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
