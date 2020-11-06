package skill;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.glow.GlowAPI;

import core.main;
import net.minecraft.server.v1_15_R1.DataWatcher;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_15_R1.Particles;
import skillcore.Skill;
import utils.ParUtils;

public class Hyperscreamer extends Skill {

	ArrayList<Player> glowing = new ArrayList<Player>();
	@Override
	public void onSkillToggleOff() {
		// TODO Auto-generated method stub
		
	}
	boolean cast = false;
	@Override
	public void onSkillToggleOn() {
		if (drainXp(5)) {
			
		
		playSound(Sound.ENTITY_WITHER_SPAWN,user.getLocation(),2,0.5F);
		playSound(Sound.BLOCK_BEACON_ACTIVATE,user.getLocation(),8,1);
		cast = true;
		// TODO Auto-generated method stub
		ParUtils.parKreisDot(Particles.CLOUD, user.getLocation(), 3, 1,6, user.getLocation().getDirection());
		Location l = user.getLocation().add(user.getLocation().getDirection().multiply(3));
		for (Entity p : user.getWorld().getEntities()) {
			if (p != user) {
				if (p.getLocation().distance(l)<8) {
					p.setVelocity(user.getLocation().getDirection().multiply((13-l.distance(p.getLocation()))/3));
					
					if (p instanceof Player) {
						
						showPlayer((Player) p,true);
						
					}
				}
			}
			
			
		}
		
		for (Player p : coneHit()) {
			showPlayer(p,true);
		}
		
		new BukkitRunnable() {
			@Override
			public void run() {
				cast = false;
			}
		}.runTaskLater(main.plugin,5);
		
		new BukkitRunnable() {
			int t = 0;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				t++;
				if (t > 20 * 10) {
					for (Player p : glowing) {
						showPlayer(p,false);
						
					}
					this.cancel();
					glowing.clear();
				}
				if (cast == true) {
					this.cancel();
				}
			
			}
		}.runTaskTimer(main.plugin, 10,1);
		}
		toggleSkill(false);
	}

	
	public void showPlayer(Player p,boolean show) {
		if (show) {
			GlowAPI.setGlowing(p, GlowAPI.Color.AQUA, user);
			glowing.add(p);
		}
		else {
		
			GlowAPI.setGlowing(p, false, user);
			
		}
		
	}
	
	
	public ArrayList<Player> coneHit() {
		ArrayList<Player> hitPlayers = new ArrayList<Player>();
		Location loc = user.getEyeLocation();
		double step = 1;
		
		int i = 0;
		while (i<7) {
			i++;
			loc.add(loc.getDirection().multiply(step));
			step = step*2;
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p != user) {
					if (p.getLocation().distance(loc) < step) {
						hitPlayers.add(p);
					}
				}
			}
		}
		return hitPlayers;
		
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
		for (Player p : glowing) {
			showPlayer(p,false);
		}
	}

	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@SuppressWarnings("unchecked")
    public void setGlowing(Player glowingPlayer, Player sendPacketPlayer, boolean glow) {
        try {
            EntityPlayer entityPlayer = ((CraftPlayer) glowingPlayer).getHandle();

            DataWatcher dataWatcher = entityPlayer.getDataWatcher();

           

            // The map that stores the DataWatcherItems is private within the DataWatcher Object.
            // We need to use Reflection to access it from Apache Commons and change it.
            Map<Integer, DataWatcher.Item<?>> map = (Map<Integer, DataWatcher.Item<?>>) FieldUtils.readDeclaredField(dataWatcher, "d", true);

            // Get the 0th index for the BitMask value. http://wiki.vg/Entities#Entity
            DataWatcher.Item item = map.get(0);
            byte initialBitMask = (Byte) item.b(); // Gets the initial bitmask/byte value so we don't overwrite anything.
            byte bitMaskIndex = (byte) 0x40; // The index as specified in wiki.vg/Entities
            if (glow) {
                item.a((byte) (initialBitMask | 1 << bitMaskIndex));
            } else {
                item.a((byte) (initialBitMask & ~(1 << bitMaskIndex))); // Inverts the specified bit from the index.
            }

            PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(glowingPlayer.getEntityId(), dataWatcher, true);

            ((CraftPlayer) sendPacketPlayer).getHandle().playerConnection.sendPacket(metadataPacket);
        } catch (IllegalAccessException e) { // Catch statement necessary for FieldUtils.readDeclaredField()
            e.printStackTrace();
        }
    }
}
