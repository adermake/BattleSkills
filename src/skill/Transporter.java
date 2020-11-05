package skill;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;
import org.bukkit.block.Smoker;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_15_R1.util.CraftMagicNumbers.NBT;
import org.bukkit.event.Event;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.Item;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.TileEntity;
import net.minecraft.server.v1_15_R1.TileEntityFurnace;
import net.minecraft.server.v1_15_R1.Vec3D;
import skillcore.Skill;
import utils.NBTUtils;

public class Transporter extends Skill{
	
	ArrayList<BlockData> storedBlocks = new ArrayList<BlockData>();
	//ArrayList<ItemStack[]> invs = new ArrayList<ItemStack[]>();
	
	NBTTagCompound ntc = new NBTTagCompound();
	

	@Override
	public void onSkillToggleOff() { //blöcke hinsetzen
		Location loc = user.getLocation().add(1, 0, 0);
		Bukkit.broadcastMessage("Hingesetzt");
		
		loc.getBlock().setBlockData(storedBlocks.get(0));
		storedBlocks.remove(0);
		
		if(!loc.getBlock().getState().getClass().getName().endsWith("CraftBlockState")) {
		
			Block b = user.getWorld().getBlockAt(loc);
			CraftWorld cw = (CraftWorld) user.getWorld();
			BlockPosition bs = new BlockPosition(new Vec3D(loc.getX(), loc.getY(), loc.getZ()));
			TileEntity tileEntity = cw.getHandle().getTileEntity(bs);
			
			ntc.setInt("x", loc.getBlockX());
			ntc.setInt("y", loc.getBlockY());
			ntc.setInt("z", loc.getBlockZ());		
			
			tileEntity.load(ntc);
			tileEntity.update();
			
			if(loc.getBlock().getState() instanceof Furnace) {
				Bukkit.broadcastMessage("Im in");
				Furnace f = (Furnace) b.getState();
				Recipe recipe = null;
				ItemStack item = f.getInventory().getItem(0);
                ItemStack result = null;
                
                if(item!=null) {
	                Iterator<Recipe> iter = Bukkit.recipeIterator();
	                while (iter.hasNext()) {
	                   recipe = iter.next();
	                   if (!(recipe instanceof FurnaceRecipe)) continue;
	                   if (!((FurnaceRecipe) recipe).getInputChoice().test(item)) continue;
	                   result = recipe.getResult();
	                   break;
	                }
	                if(result!=null) {
		                //result.setAmount(item.getAmount());
		                FurnaceRecipe fr = (FurnaceRecipe) recipe;
		                Bukkit.broadcastMessage(fr.getKey().toString());
		                if(f.getInventory().getResult() == null||f.getInventory().getResult().getType() == result.getType()) {
		                	
		                	Bukkit.broadcastMessage(NBTUtils.getNBT("BurnTime", f.getInventory().getFuel()).toString());
		                	//TileEntityFurnace.f().get(f.getInventory().getFuel());
		                	int fuel = 0;
		                	for(Item i : TileEntityFurnace.f().keySet()) {
		                		
		                		net.minecraft.server.v1_15_R1.ItemStack is = new net.minecraft.server.v1_15_R1.ItemStack(i);
		                		ItemStack is2 = CraftItemStack.asBukkitCopy(is);
		                		if(is2.getType() == f.getInventory().getFuel().getType()) {
		                			fuel = TileEntityFurnace.f().get(i);
		                		}
		                		
		                	}
		                	Bukkit.broadcastMessage("Fuel : " + fuel);
		                	int gf1;
		                	if(f.getInventory().getFuel() == null) {
		                		gf1 = (f.getBurnTime())/fr.getCookingTime();
		                	} else {
		                		gf1 = (fuel*f.getInventory().getFuel().getAmount() + f.getBurnTime())/fr.getCookingTime();
		                	}
		                	int gf2 = item.getAmount();
		                	int gf3;
		                	if(f.getInventory().getResult() == null) {
		                		gf3 = result.getMaxStackSize();
		                	} else {
		                		gf3 = result.getMaxStackSize()-f.getInventory().getResult().getAmount();
		                	}
		                	int min = Math.min(Math.min(gf1, gf2), gf3);
		                	f.getInventory().getItem(0).setAmount(f.getInventory().getItem(0).getAmount()-min);
		                	if(f.getInventory().getResult() == null) {
		                		f.getInventory().setResult(new ItemStack(result.getType(), min));
		                	} else {
		                		f.getInventory().getResult().setAmount(f.getInventory().getResult().getAmount()+min);
		                	}
		                	if(f.getInventory().getFuel() != null) {
		                		f.getInventory().getFuel().setAmount(((fuel*f.getInventory().getFuel().getAmount() + f.getBurnTime())-min*fr.getCookingTime())/fuel);
		                	}
		                	if(f.getInventory().getFuel() == null) {
		                		f.setBurnTime((short)(((f.getBurnTime())-min*fr.getCookingTime())%fuel));
		                	} else {
		                		f.setBurnTime((short)(((fuel*f.getInventory().getFuel().getAmount() + f.getBurnTime())-min*fr.getCookingTime())%fuel));
		                	}
		                	tileEntity.save(ntc);
		                	
		                	ntc.setString("RecipeLocation"+ntc.getShort("RecipesUsedSize"), fr.getKey().toString());
		                	ntc.setInt("RecipeAmount"+ntc.getShort("RecipesUsedSize"), min);
		                	ntc.setShort("RecipesUsedSize", (short)(ntc.getShort("RecipesUsedSize")+1));
		                	
		                	tileEntity.load(ntc);
		        			tileEntity.update();
		                }
	                }
                }
			}
		
			Bukkit.broadcastMessage("Im out");
		
		}	
		
	}

	
	int timer = 0;
	
	@Override
	public void onSkillToggleOn() { //blöcke aufsammeln
		Location loc = user.getLocation().add(1, 0, 0);
		Bukkit.broadcastMessage("Aufgesammmmmmelt");
		storedBlocks.add(loc.getBlock().getBlockData().clone());
		
		if (!loc.getBlock().getState().getClass().getName().endsWith("CraftBlockState")){
			Block b = user.getWorld().getBlockAt(loc);
			CraftWorld cw = (CraftWorld) user.getWorld();
			BlockPosition bs = new BlockPosition(new Vec3D(loc.getX(), loc.getY(), loc.getZ()));
			TileEntity tileEntity = cw.getHandle().getTileEntity(bs);
			
			tileEntity.update();
			tileEntity.save(ntc);	
		}
		
		if (loc.getBlock().getState() instanceof InventoryHolder) {
		    Bukkit.broadcastMessage("Hey");
			((InventoryHolder) loc.getBlock().getState()).getInventory().clear();
		}
		
		timer = 0;
		
		loc.getBlock().setType(Material.AIR); 

	}

	@Override
	public void onSkillLoop() {
		// TODO Auto-generated method stub
		timer ++;
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
