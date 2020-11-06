package helper;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftFallingBlock;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;

import net.minecraft.server.v1_15_R1.EntityFallingBlock;

public class BlockIllusion {

	public Location loc;
	public BlockData bd;
	public Material m;
	public boolean illusion = false;
	public Entity re;
	public ArmorStand a;
	
	public BlockIllusion(Location l,BlockData bd,Material m) {
		loc = l;
		this.m = m;
		this.bd = bd;
	}
	
	public void setIllussion(boolean il) {
		if ((re == null || re.isDead())&& a != null) {
			a.remove();
			a = null;
		}
		
		if (il) {
			if (!illusion) {
				FallingBlock fb = loc.getWorld().spawnFallingBlock(loc.clone().add(0.5,0,0.5), bd);
				fb.setGravity(false);
				EntityFallingBlock efb = ((CraftFallingBlock) fb).getHandle();
				efb.setInvulnerable(true);
				//efb.ticksLived = 100;
				a = (ArmorStand) loc.getWorld().spawnEntity(loc.clone().add(0.5,-1.45,0.5), EntityType.ARMOR_STAND);
				a.addPassenger(fb);
				a.setGravity(false);
				a.setVisible(false);
				illusion = true;
				re = fb;
				loc.getBlock().setType(Material.AIR);
				
				
				
			}
			
		}
		else {
			if (illusion) {
				illusion = false;
				re.remove();
				a.remove();
				loc.getBlock().setType(m);
				//loc.getBlock().setBlockData(bd);
			
			}
		}
	
	}
	
	
}
