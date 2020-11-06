package skill;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_15_R1.Chunk;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.Packet;
import net.minecraft.server.v1_15_R1.Particles;
import net.minecraft.server.v1_15_R1.TileEntity;
import skillcore.Skill;
import utils.ParUtils;



public class Burrow extends Skill {

	ArrayList<Block> blocks = new ArrayList<Block>();
	ArrayList<Block> oldblocks = new ArrayList<Block>();
	@Override
	public void onSkillToggleOff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkillToggleOn() {
		// TODO Auto-generated method stub

		
	
		
		
	}
	
	int t = 0;
	@Override
	public void onSkillLoop() {
		
		
		t++;
		
		if (t > 5) {
			t = 0;
	
		float range = 0;
		Location loc = user.getEyeLocation();
		if (loc == null)
			return;
		
		loc.setDirection(user.getLocation().getDirection());
		
			
			
				
			int size = 8;
			for (int x = 0;x<size;x++) {
				for (int y = 0;y<size;y++) {
					for (int z = 0;z<size;z++) {
						  Location l = new Location(user.getWorld(), user.getLocation().getX()+ x, user.getLocation().getY() + y, user.getLocation().getZ() + z);
					
						  if (z == 0 || y == 0 || x == 0 || z == size-1 || x == size-1 || y == size-1) {
							  Block b = l.add(new Vector(-size/2,-size/2,-size/2)).getBlock().getLocation().getBlock();
							  user.sendBlockChange(b.getLocation(), Material.STONE , ((byte) 0));
								blocks.add(b);
						  }
						
						
					}				
				}
			}
			size = 6;
			for (int x = 0;x<size;x++) {
				for (int y = 0;y<size;y++) {
					for (int z = 0;z<size;z++) {
						  Location l = new Location(user.getWorld(), user.getLocation().getX()+ x, user.getLocation().getY() + y, user.getLocation().getZ() + z);
					
						  if (z == 0 || y == 0 || x == 0 || z == size-1 || x == size-1 || y == size-1) {
							  uncoverBlock(l.add(new Vector(-size/2,-size/2,-size/2)).getBlock());
						  }
						
						
					}				
				}
			}

			
		
		
		
		/*
		 * 
		 * Location l1 = loc.clone().add(new Vector(x,y,z));
						if (!blocks.contains(l1)) {
							user.sendBlockChange(l1, Material.AIR,((byte) 0));
							blocks.add(l1.getBlock());
						}
		 * 
	*/
		for (Block b : oldblocks) {
			if (!blocks.contains(b))
			user.sendBlockChange(b.getLocation(), b.getType(), ((byte) 0));
		}
		oldblocks = (ArrayList<Block>) blocks.clone();
		blocks.clear();
		}
		
		
	}
	public void uncoverBlock(Block b) {
		
		
		
			
		
			user.sendBlockChange(b.getLocation(), Material.AIR , ((byte) 0));
			blocks.add(b);
			
		
		
		
		
	
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
