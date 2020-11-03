package core;

import java.io.IOException;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import skillcore.Skill;
//s
public class CommandReciever implements CommandExecutor, TabCompleter{
	public static void dumpHeap(String filePath, boolean live) throws IOException {
		/*
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		memoryMXBean.gc();
		MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
		long used = heapMemoryUsage.getUsed();//bytes used
*/
		/*
	    MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
	    HotSpotDiagnosticMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(
	    		platformMBeanServer, "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class);*/
	    //"/tmp/minecraft-memory-dump-"+System.currentTimeMillis()+".hptof", true
	    //mxBean.dumpHeap(filePath, live);
	}
	
	




	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String [] args) {
		final Player p = (Player) sender;			
		
		if(cmd.getName().equalsIgnoreCase("skill")) {
			if(p.isOp()){
				ItemStack is = Skill.createSkillItem(args[0]);
				p.getInventory().addItem(is);
		        p.sendMessage("§8| §7Skill erstellt!");
			}
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender player, Command cmd, String cmdname, String[] args) {
		return null;
	}
}
