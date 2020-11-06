package helper;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class MobCharacter {

	
	public float flySpeed = 0;
	public float flyHeight = 0;
	public float walkSpeed = 0;
	public float swimSpeed = 0;
	public EntityType entity;
	public MobCharacter(float walkSpeed,float flySpeed,float swimSpeed,float flyHeight) {
		this.flySpeed = flySpeed;
		this.walkSpeed = walkSpeed;
		this.swimSpeed = swimSpeed;
		this.flyHeight = flyHeight;
	}
	
	
	
	public static MobCharacter createMobCharacter(LivingEntity le) {
		
		float ws = 0;
		float fs = 0;
		float ss = 0;
		float fh = 0;
		ws = (float) le.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
		
		//FLY 
		if (le.getType() == EntityType.GHAST) {
			fs = 0.04F;
			fh = 30;
		}
		if (le.getType() == EntityType.PHANTOM) {
			fs = 0.1F;
			fh = 20;
		}
		if (le.getType() == EntityType.BAT) {
			fs = 0.05F;
			fh = 3;
		}
		if (le.getType() == EntityType.VEX) {
			fs = 0.05F;
			fh = 10;
		}
		if (le.getType() == EntityType.BLAZE) {
			fs = 0.04F;
			fh = 10;
			
		}
		if (le.getType() == EntityType.BEE) {
			fs = 0.05F;
			fh = 6;
		}
		if (le.getType() == EntityType.WITHER) {
			fs = 0.05F;
			fh = 20;
		}
		if (le.getType() == EntityType.PARROT) {
			fs = 0.08F;
			fh = 10;
		}
		if (le.getType() == EntityType.ENDER_DRAGON) {
			fs = 0.1F;
			fh = 40;
		}
		
		//SWIMMING
		if (le.getType() == EntityType.SQUID) {
			ss = 1F;
		}
		if (le.getType() == EntityType.COD) {
			ss = 1F;
		}
		if (le.getType() == EntityType.TROPICAL_FISH) {
			ss = 1F;
		}
		if (le.getType() == EntityType.SALMON) {
			ss = 1F;
		}
		if (le.getType() == EntityType.PUFFERFISH) {
			ss = 0.5F;
		}
		if (le.getType() == EntityType.GUARDIAN) {
			ss = 0.4F;
		}
		if (le.getType() == EntityType.ELDER_GUARDIAN) {
			ss = 0.3F;
		}
		if (le.getType() == EntityType.DOLPHIN) {
			ss = 1.5F;
		}
		if (le.getType() == EntityType.DROWNED) {
			ss = 0.2F;
			ws = 0.2F;
		}
		if (le.getType() == EntityType.TURTLE) {
			ss = 0.8F;
			
		}
		
		//WALKER 
		
		if (le.getType() == EntityType.COW) {
			ws = 0.15F;
		}
		if (le.getType() == EntityType.CHICKEN) {
			ws = 0.15F;
		}
		if (le.getType() == EntityType.PIG) {
			ws = 0.15F;
		}
		if (le.getType() == EntityType.SHEEP) {
			ws = 0.15F;
		}
		if (le.getType() == EntityType.LLAMA) {
			ws = 0.15F;
		}
		if (le.getType() == EntityType.SLIME) {
			ws = 0.05F;
		}
		if (le.getType() == EntityType.RABBIT) {
			ws = 0.2F;
		}
		if (ws > 0.65) {
			ws = 0;
		}
		
		if (fs > 0) {
			ws = 0;
			ss = 0;
		}
		
		if (le.getType() == EntityType.HORSE) {
			ws *= 3;
		}
		
		
			
			
			MobCharacter mc = new MobCharacter(ws,fs,ss,fh);
			mc.entity = le.getType();
		return mc;
	}
}
