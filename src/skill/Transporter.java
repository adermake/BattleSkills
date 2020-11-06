package skill;

import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Campfire;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;
import org.bukkit.block.Smoker;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_15_R1.util.CraftMagicNumbers.NBT;
import org.bukkit.event.Event;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.scheduler.BukkitRunnable;

import com.mojang.datafixers.util.Pair;

import core.main;
import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.Item;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.RecipeBlasting;
import net.minecraft.server.v1_15_R1.TileEntity;
import net.minecraft.server.v1_15_R1.TileEntityBlastFurnace;
import net.minecraft.server.v1_15_R1.TileEntityFurnace;
import net.minecraft.server.v1_15_R1.TileEntitySmoker;
import net.minecraft.server.v1_15_R1.Vec3D;
import skillcore.Skill;
import utils.NBTUtils;

public class Transporter extends Skill {

	//ArrayList<BlockData> storedBlocks = new ArrayList<BlockData>();
	
	HashMap<Location,Pair<BlockData,NBTTagCompound>> storedBlockData = new HashMap<Location,Pair<BlockData,NBTTagCompound>>();
	//HashMap<BlockData,Location> storedBlockPos = new HashMap<BlockData,Location>();
	// ArrayList<ItemStack[]> invs = new ArrayList<ItemStack[]>();



	@Override
	public void onSkillToggleOff() { // blöcke hinsetzen
		
		for (Location l : storedBlockData.keySet()) {
			placeBlock(l);
		}
		
/*
		Bukkit.broadcastMessage("" + timer);
		Location loc = user.getLocation().add(1, 0, 0);
		Bukkit.broadcastMessage("Hingesetzt");
		loc.getBlock().setType(Material.AIR);
		loc.getBlock().setBlockData(storedBlocks.get(0));
		storedBlocks.remove(0);

		if (!loc.getBlock().getState().getClass().getName().endsWith("CraftBlockState")) {

			Block b = user.getWorld().getBlockAt(loc);
			CraftWorld cw = (CraftWorld) user.getWorld();
			BlockPosition bs = new BlockPosition(new Vec3D(loc.getX(), loc.getY(), loc.getZ()));
			TileEntity tileEntity = cw.getHandle().getTileEntity(bs);

			ntc.setInt("x", loc.getBlockX());
			ntc.setInt("y", loc.getBlockY());
			ntc.setInt("z", loc.getBlockZ());

			tileEntity.load(ntc);
			tileEntity.update();

			if (loc.getBlock().getState() instanceof Furnace) {
				Bukkit.broadcastMessage("Im in");
				Furnace f = (Furnace) b.getState();
				Recipe recipe = null;
				ItemStack item = f.getInventory().getItem(0);
				ItemStack result = null;

				if (item != null) {
					Iterator<Recipe> iter = Bukkit.recipeIterator();
					while (iter.hasNext()) {
						recipe = iter.next();
						
						
						if (f instanceof BlastFurnace) {
							if (!(recipe instanceof BlastingRecipe)) {
								continue;
							}
						}
						else if (f instanceof Smoker) {
							if (!(recipe instanceof SmokingRecipe)) {
								continue;
							}
							
						}
						else  {
							if (!(recipe instanceof FurnaceRecipe)) {
								continue;
							}
						}
					
							
						
						if (!((CookingRecipe) recipe).getInputChoice().test(item))
							continue;
						result = recipe.getResult();
						break;
					}
					if (result != null) {
						// result.setAmount(item.getAmount());
						CookingRecipe fr = (CookingRecipe) recipe;
						
						Bukkit.broadcastMessage(fr.getKey().toString());
						if (f.getInventory().getResult() == null
								|| f.getInventory().getResult().getType() == result.getType()) {

							Bukkit.broadcastMessage(NBTUtils.getNBT("BurnTime", f.getInventory().getFuel()).toString());
							// TileEntityFurnace.f().get(f.getInventory().getFuel());
							int fuel = 0;
							for (Item i : TileEntityFurnace.f().keySet()) {

								net.minecraft.server.v1_15_R1.ItemStack is = new net.minecraft.server.v1_15_R1.ItemStack(
										i);
								ItemStack is2 = CraftItemStack.asBukkitCopy(is);
								if (f.getInventory().getFuel() != null
										&& f.getInventory().getFuel().getType() != Material.BUCKET) {
									if (is2.getType() == f.getInventory().getFuel().getType()) {
										fuel = TileEntityFurnace.f().get(i);
									}
								} else {
									fuel = 1;
								}
							}
							
							if (f instanceof BlastFurnace || f instanceof Smoker) {
								fuel /=2;
							}
							Bukkit.broadcastMessage("Fuel : " + fuel);
							int gf1;
							int f1;
							if (f.getInventory().getFuel() == null
									|| f.getInventory().getFuel().getType() == Material.BUCKET) {
								f1 = (f.getBurnTime());
							} else {
								f1 = (fuel * f.getInventory().getFuel().getAmount() + f.getBurnTime());
							}
							gf1 = (f1+f.getCookTime()) / fr.getCookingTime();
							
							int gf2 = item.getAmount();
							int f2 = gf2*fr.getCookingTime()-f.getCookTime();
							
							int gf3;
							if (f.getInventory().getResult() == null) {
								gf3 = result.getMaxStackSize();
							} else {
								gf3 = result.getMaxStackSize() - f.getInventory().getResult().getAmount();
							}
							int f3 = gf3*fr.getCookingTime()-f.getCookTime();
							int gf4 = (timer + f.getCookTime()) / fr.getCookingTime();
							int f4 = timer;
							
							int mininmus = Math.min(Math.min(Math.min(f1, f2), f3), f4);
							int min = 0;
							if (f1 == mininmus) {
								min = gf1;	
							}
							if (f2 == mininmus) {
								min = gf2;	
							}
							if (f3 == mininmus) {
								min = gf3;	
							}
							if (f4 == mininmus) {
								min = gf4;	
							}
							f.getInventory().getItem(0).setAmount(f.getInventory().getItem(0).getAmount() - min);

							if (f.getInventory().getResult() == null) {
								f.getInventory().setResult(new ItemStack(result.getType(), min));
							} else {
								
								ItemStack resultStack = f.getInventory().getResult();
								resultStack.setAmount(f.getInventory().getResult().getAmount() + min);
								
								f.getInventory().setResult(resultStack);
							}
							ItemStack fuelClone = f.getInventory().getFuel().clone();
							if (f.getInventory().getFuel() != null
									&& f.getInventory().getFuel().getType() != Material.BUCKET) {
								ItemStack fuelStack = f.getInventory().getFuel();
								fuelStack.setAmount(((f1) - mininmus) / fuel);
								
								Bukkit.broadcastMessage("ITEMSTACK HAS AMOUT" +fuelStack.getAmount());
								f.getInventory().setItem(1, fuelStack);
								Bukkit.broadcastMessage("FORMEL : " + (f1 - mininmus) / fuel);
							}
							if (fuelClone.getType() == Material.LAVA_BUCKET) {
								if (((f1) - mininmus) / fuel == 0)
								f.getInventory().setItem(1, new ItemStack(Material.BUCKET));
							}
						

							int restFuel;
							if (f.getInventory().getFuel() != null
									&& f.getInventory().getFuel().getType() != Material.BUCKET) {
								Bukkit.broadcastMessage("IF");
								restFuel = f1 - timer - fuel * f.getInventory().getFuel().getAmount();
							} else {
								restFuel = f1 - timer;
								Bukkit.broadcastMessage("ELSE");
							}

							Bukkit.broadcastMessage("F1 " + f1 + " RF " + restFuel + " timer " + timer);
							
							
						
							
							
							
							tileEntity.save(ntc);
							if (f4 == mininmus && restFuel >= 0) {
								
								//f.setCookTime((short) ((timer + f.getCookTime()) % fr.getCookingTime()));
								ntc.setShort("CookTime", (short) ((timer + f.getCookTime()) % fr.getCookingTime()));
								
							}
							else {
								ntc.setShort("CookTime",(short)0);
							}
							ntc.setShort("BurnTime",(short) restFuel > 0 ? (short) restFuel : (short) 0);
							ntc.setString("RecipeLocation" + ntc.getShort("RecipesUsedSize"), fr.getKey().toString());
							ntc.setInt("RecipeAmount" + ntc.getShort("RecipesUsedSize"), min);
							ntc.setShort("RecipesUsedSize", (short) (ntc.getShort("RecipesUsedSize") + 1));

							tileEntity.load(ntc);
							tileEntity.update();
							Bukkit.broadcastMessage("jE BOL" +f.getCookTime());
						}
					}
				}
			}
			
			if (loc.getBlock().getState() instanceof BrewingStand) {
				BrewingStand bstand = (BrewingStand) loc.getBlock().getState();
				short bt = (short) (bstand.getBrewingTime()-timer);
				if (bt < 1)
					bt = 1;
				final int fbt = bt;
				final int fuel = bstand.getFuelLevel();
				new BukkitRunnable() {
					
					public void run() {
						ntc.setShort("BrewTime",(short) fbt);
						ntc.setByte("Fuel",(byte) fuel);

						tileEntity.load(ntc);
						tileEntity.update();
						//Bukkit.broadcastMessage("BT: "+bstand.getBrewingTime());
						//bstand.setBrewingTime(fbt);
						//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "data modify block "+loc.getBlockX() +" "+loc.getBlockY()+" "+loc.getBlockZ()+" BrewTime set value "+fbt);
						//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "data modify block "+loc.getBlockX() +" "+loc.getBlockY()+" "+loc.getBlockZ()+" Fuel set value "+fuel);
						//Bukkit.broadcastMessage(""+ "data modify block "+loc.getBlockX() +" "+loc.getBlockY()+" "+loc.getBlockZ()+" BrewTime set value "+fbt);
						//Bukkit.broadcastMessage("BT: "+bstand.getBrewingTime());
					}
				}.runTaskLater(main.plugin,3);
				

			}
			
			if (loc.getBlock().getState() instanceof Campfire) {
				Campfire cfire = (Campfire) loc.getBlock().getState();
				int[]ctimes = new int[4];
				for (int i = 0;i<4;i++) {
					int bt =(cfire.getCookTime(i)+timer);
				
					ctimes[i] = bt;
				}
				ntc.setIntArray("CookingTimes", ctimes);
				
				

				tileEntity.load(ntc);
				tileEntity.update();
			}
			
			Bukkit.broadcastMessage("Im out");

		}
 	*/
	}

	int timer = 0;

	@Override
	public void onSkillToggleOn() { // blöcke aufsammeln
		
		storeBlock(new Location(user.getWorld(),0,0,1));
		/*
		Location loc = user.getLocation().add(1, 0, 0);
		Bukkit.broadcastMessage("Aufgesammmmmmelt");
		storedBlocks.add(loc.getBlock().getBlockData().clone());

		if (!loc.getBlock().getState().getClass().getName().endsWith("CraftBlockState")) {
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
		if (loc.getBlock().getState() instanceof Campfire) {
			Campfire cfire = (Campfire) loc.getBlock().getState();
			
			for (int i = 0;i<4;i++) {
				ItemStack citem= cfire.getItem(i);
				if (citem == null)
					continue;
				citem.setAmount( 0);
				cfire.setItem(i, citem);
			}
			cfire.update();
		}
		timer = 0;

		loc.getBlock().setType(Material.AIR);
		*/
	}

	@Override
	public void onSkillLoop() {
		// TODO Auto-generated method stub
		timer++;
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

	public void storeBlock(Location l) {
		Location loc = l.clone().add(user.getLocation());
		Bukkit.broadcastMessage("Aufgesammmmmmelt");
		BlockData toStore = loc.getBlock().getBlockData().clone();
		NBTTagCompound ntc = new NBTTagCompound();
		if (!loc.getBlock().getState().getClass().getName().endsWith("CraftBlockState")) {
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
		if (loc.getBlock().getState() instanceof Campfire) {
			Campfire cfire = (Campfire) loc.getBlock().getState();
			
			for (int i = 0;i<4;i++) {
				ItemStack citem= cfire.getItem(i);
				if (citem == null)
					continue;
				citem.setAmount( 0);
				cfire.setItem(i, citem);
			}
			cfire.update();
		}
		timer = 0;

		loc.getBlock().setType(Material.AIR);
		storedBlockData.put(l.clone(),new Pair<BlockData,NBTTagCompound>(toStore,ntc));
	}
	
	
	public void placeBlock(Location l) {
		Bukkit.broadcastMessage("" + timer);
		NBTTagCompound ntc = storedBlockData.get(l).getSecond();
		BlockData bd = storedBlockData.get(l).getFirst();
		Bukkit.broadcastMessage("Hingesetzt");
		Location loc = l.clone();
		loc = loc.add(user.getLocation());
		loc.getBlock().setType(Material.AIR);
		loc.getBlock().setBlockData(bd);
		

		if (!loc.getBlock().getState().getClass().getName().endsWith("CraftBlockState")) {

			Block b = user.getWorld().getBlockAt(loc);
			CraftWorld cw = (CraftWorld) user.getWorld();
			BlockPosition bs = new BlockPosition(new Vec3D(loc.getX(), loc.getY(), loc.getZ()));
			TileEntity tileEntity = cw.getHandle().getTileEntity(bs);

			ntc.setInt("x", loc.getBlockX());
			ntc.setInt("y", loc.getBlockY());
			ntc.setInt("z", loc.getBlockZ());

			tileEntity.load(ntc);
			tileEntity.update();

			if (loc.getBlock().getState() instanceof Furnace) {
				Bukkit.broadcastMessage("Im in");
				Furnace f = (Furnace) b.getState();
				Recipe recipe = null;
				ItemStack item = f.getInventory().getItem(0);
				ItemStack result = null;

				if (item != null) {
					Iterator<Recipe> iter = Bukkit.recipeIterator();
					while (iter.hasNext()) {
						recipe = iter.next();
						
						
						if (f instanceof BlastFurnace) {
							if (!(recipe instanceof BlastingRecipe)) {
								continue;
							}
						}
						else if (f instanceof Smoker) {
							if (!(recipe instanceof SmokingRecipe)) {
								continue;
							}
							
						}
						else  {
							if (!(recipe instanceof FurnaceRecipe)) {
								continue;
							}
						}
					
							
						
						if (!((CookingRecipe) recipe).getInputChoice().test(item))
							continue;
						result = recipe.getResult();
						break;
					}
					if (result != null) {
						// result.setAmount(item.getAmount());
						CookingRecipe fr = (CookingRecipe) recipe;
						
						Bukkit.broadcastMessage(fr.getKey().toString());
						if (f.getInventory().getResult() == null
								|| f.getInventory().getResult().getType() == result.getType()) {

							Bukkit.broadcastMessage(NBTUtils.getNBT("BurnTime", f.getInventory().getFuel()).toString());
							// TileEntityFurnace.f().get(f.getInventory().getFuel());
							int fuel = 0;
							for (Item i : TileEntityFurnace.f().keySet()) {

								net.minecraft.server.v1_15_R1.ItemStack is = new net.minecraft.server.v1_15_R1.ItemStack(
										i);
								ItemStack is2 = CraftItemStack.asBukkitCopy(is);
								if (f.getInventory().getFuel() != null
										&& f.getInventory().getFuel().getType() != Material.BUCKET) {
									if (is2.getType() == f.getInventory().getFuel().getType()) {
										fuel = TileEntityFurnace.f().get(i);
									}
								} else {
									fuel = 1;
								}
							}
							
							if (f instanceof BlastFurnace || f instanceof Smoker) {
								fuel /=2;
							}
							Bukkit.broadcastMessage("Fuel : " + fuel);
							int gf1;
							int f1;
							if (f.getInventory().getFuel() == null
									|| f.getInventory().getFuel().getType() == Material.BUCKET) {
								f1 = (f.getBurnTime());
							} else {
								f1 = (fuel * f.getInventory().getFuel().getAmount() + f.getBurnTime());
							}
							gf1 = (f1+f.getCookTime()) / fr.getCookingTime();
							
							int gf2 = item.getAmount();
							int f2 = gf2*fr.getCookingTime()-f.getCookTime();
							
							int gf3;
							if (f.getInventory().getResult() == null) {
								gf3 = result.getMaxStackSize();
							} else {
								gf3 = result.getMaxStackSize() - f.getInventory().getResult().getAmount();
							}
							int f3 = gf3*fr.getCookingTime()-f.getCookTime();
							int gf4 = (timer + f.getCookTime()) / fr.getCookingTime();
							int f4 = timer;
							
							int mininmus = Math.min(Math.min(Math.min(f1, f2), f3), f4);
							int min = 0;
							if (f1 == mininmus) {
								min = gf1;	
							}
							if (f2 == mininmus) {
								min = gf2;	
							}
							if (f3 == mininmus) {
								min = gf3;	
							}
							if (f4 == mininmus) {
								min = gf4;	
							}
							f.getInventory().getItem(0).setAmount(f.getInventory().getItem(0).getAmount() - min);

							if (f.getInventory().getResult() == null) {
								f.getInventory().setResult(new ItemStack(result.getType(), min));
							} else {
								
								ItemStack resultStack = f.getInventory().getResult();
								resultStack.setAmount(f.getInventory().getResult().getAmount() + min);
								
								f.getInventory().setResult(resultStack);
							}
							ItemStack fuelClone = f.getInventory().getFuel().clone();
							if (f.getInventory().getFuel() != null
									&& f.getInventory().getFuel().getType() != Material.BUCKET) {
								ItemStack fuelStack = f.getInventory().getFuel();
								fuelStack.setAmount(((f1) - mininmus) / fuel);
								
								Bukkit.broadcastMessage("ITEMSTACK HAS AMOUT" +fuelStack.getAmount());
								f.getInventory().setItem(1, fuelStack);
								Bukkit.broadcastMessage("FORMEL : " + (f1 - mininmus) / fuel);
							}
							if (fuelClone.getType() == Material.LAVA_BUCKET) {
								if (((f1) - mininmus) / fuel == 0)
								f.getInventory().setItem(1, new ItemStack(Material.BUCKET));
							}
							/*
							 * if (f.getInventory().getFuel() == null) { f.setBurnTime((short)
							 * (((f.getBurnTime()) - min * fr.getCookingTime()) % fuel)); } else {
							 * f.setBurnTime( (short) (((fuel * f.getInventory().getFuel().getAmount() +
							 * f.getBurnTime()) - min * fr.getCookingTime()) % fuel)); }
							 */

							int restFuel;
							if (f.getInventory().getFuel() != null
									&& f.getInventory().getFuel().getType() != Material.BUCKET) {
								Bukkit.broadcastMessage("IF");
								restFuel = f1 - timer - fuel * f.getInventory().getFuel().getAmount();
							} else {
								restFuel = f1 - timer;
								Bukkit.broadcastMessage("ELSE");
							}

							Bukkit.broadcastMessage("F1 " + f1 + " RF " + restFuel + " timer " + timer);
							
							
						
							
							
							
							tileEntity.save(ntc);
							if (f4 == mininmus && restFuel >= 0) {
								
								//f.setCookTime((short) ((timer + f.getCookTime()) % fr.getCookingTime()));
								ntc.setShort("CookTime", (short) ((timer + f.getCookTime()) % fr.getCookingTime()));
								
							}
							else {
								ntc.setShort("CookTime",(short)0);
							}
							ntc.setShort("BurnTime",(short) restFuel > 0 ? (short) restFuel : (short) 0);
							ntc.setString("RecipeLocation" + ntc.getShort("RecipesUsedSize"), fr.getKey().toString());
							ntc.setInt("RecipeAmount" + ntc.getShort("RecipesUsedSize"), min);
							ntc.setShort("RecipesUsedSize", (short) (ntc.getShort("RecipesUsedSize") + 1));

							tileEntity.load(ntc);
							tileEntity.update();
							Bukkit.broadcastMessage("jE BOL" +f.getCookTime());
						}
					}
				}
			}
			
			if (loc.getBlock().getState() instanceof BrewingStand) {
				BrewingStand bstand = (BrewingStand) loc.getBlock().getState();
				short bt = (short) (bstand.getBrewingTime()-timer);
				if (bt < 1)
					bt = 1;
				final int fbt = bt;
				final int fuel = bstand.getFuelLevel();
				new BukkitRunnable() {
					
					public void run() {
						ntc.setShort("BrewTime",(short) fbt);
						ntc.setByte("Fuel",(byte) fuel);

						tileEntity.load(ntc);
						tileEntity.update();
						//Bukkit.broadcastMessage("BT: "+bstand.getBrewingTime());
						//bstand.setBrewingTime(fbt);
						//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "data modify block "+loc.getBlockX() +" "+loc.getBlockY()+" "+loc.getBlockZ()+" BrewTime set value "+fbt);
						//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "data modify block "+loc.getBlockX() +" "+loc.getBlockY()+" "+loc.getBlockZ()+" Fuel set value "+fuel);
						//Bukkit.broadcastMessage(""+ "data modify block "+loc.getBlockX() +" "+loc.getBlockY()+" "+loc.getBlockZ()+" BrewTime set value "+fbt);
						//Bukkit.broadcastMessage("BT: "+bstand.getBrewingTime());
					}
				}.runTaskLater(main.plugin,3);
				

			}
			
			if (loc.getBlock().getState() instanceof Campfire) {
				Campfire cfire = (Campfire) loc.getBlock().getState();
				int[]ctimes = new int[4];
				for (int i = 0;i<4;i++) {
					int bt =(cfire.getCookTime(i)+timer);
				
					ctimes[i] = bt;
				}
				ntc.setIntArray("CookingTimes", ctimes);
				
				

				tileEntity.load(ntc);
				tileEntity.update();
			}
			
			Bukkit.broadcastMessage("Im out");

		}
	}
}
/*
if (loc.getBlock().getState() instanceof BlastFurnace) {
	Bukkit.broadcastMessage("Im in");
	BlastFurnace f = (BlastFurnace) b.getState();
	Recipe recipe = null;
	ItemStack item = f.getInventory().getItem(0);
	ItemStack result = null;

	if (item != null) {
		Iterator<Recipe> iter = Bukkit.recipeIterator();
		while (iter.hasNext()) {
			recipe = iter.next();
			if (!(recipe instanceof BlastingRecipe))
				continue;
			if (!((BlastingRecipe) recipe).getInputChoice().test(item))
				continue;
			result = recipe.getResult();
			break;
		}
		if (result != null) {
			// result.setAmount(item.getAmount());
			BlastingRecipe fr = (BlastingRecipe) recipe;
			Bukkit.broadcastMessage(fr.getKey().toString());
			if (f.getInventory().getResult() == null
					|| f.getInventory().getResult().getType() == result.getType()) {

				Bukkit.broadcastMessage(NBTUtils.getNBT("BurnTime", f.getInventory().getFuel()).toString());
				// TileEntityBlastFurnace.f().get(f.getInventory().getFuel());
				int fuel = 0;
				for (Item i : TileEntityBlastFurnace.f().keySet()) {

					net.minecraft.server.v1_15_R1.ItemStack is = new net.minecraft.server.v1_15_R1.ItemStack(
							i);
					ItemStack is2 = CraftItemStack.asBukkitCopy(is);
					if (f.getInventory().getFuel() != null
							&& f.getInventory().getFuel().getType() != Material.BUCKET) {
						if (is2.getType() == f.getInventory().getFuel().getType()) {
							fuel = TileEntityBlastFurnace.f().get(i);
						}
					} else {
						fuel = 1;
					}
				}
				Bukkit.broadcastMessage("Fuel : " + fuel);
				int gf1;
				int f1;
				if (f.getInventory().getFuel() == null
						|| f.getInventory().getFuel().getType() == Material.BUCKET) {
					f1 = (f.getBurnTime());
				} else {
					f1 = (fuel * f.getInventory().getFuel().getAmount() + f.getBurnTime());
				}
				gf1 = (f1+f.getCookTime()) / fr.getCookingTime();
				
				int gf2 = item.getAmount();
				int f2 = gf2*fr.getCookingTime()-f.getCookTime();
				
				int gf3;
				if (f.getInventory().getResult() == null) {
					gf3 = result.getMaxStackSize();
				} else {
					gf3 = result.getMaxStackSize() - f.getInventory().getResult().getAmount();
				}
				int f3 = gf3*fr.getCookingTime()-f.getCookTime();
				int gf4 = (timer + f.getCookTime()) / fr.getCookingTime();
				int f4 = timer;
				
				int mininmus = Math.min(Math.min(Math.min(f1, f2), f3), f4);
				int min = 0;
				if (f1 == mininmus) {
					min = gf1;	
				}
				if (f2 == mininmus) {
					min = gf2;	
				}
				if (f3 == mininmus) {
					min = gf3;	
				}
				if (f4 == mininmus) {
					min = gf4;	
				}
				f.getInventory().getItem(0).setAmount(f.getInventory().getItem(0).getAmount() - min);

				if (f.getInventory().getResult() == null) {
					f.getInventory().setResult(new ItemStack(result.getType(), min));
				} else {
					
					ItemStack resultStack = f.getInventory().getResult();
					resultStack.setAmount(f.getInventory().getResult().getAmount() + min);
					
					f.getInventory().setResult(resultStack);
				}
				ItemStack fuelClone = f.getInventory().getFuel().clone();
				if (f.getInventory().getFuel() != null
						&& f.getInventory().getFuel().getType() != Material.BUCKET) {
					ItemStack fuelStack = f.getInventory().getFuel();
					fuelStack.setAmount(((f1) - mininmus) / fuel);
					
					Bukkit.broadcastMessage("ITEMSTACK HAS AMOUT" +fuelStack.getAmount());
					f.getInventory().setItem(1, fuelStack);
					Bukkit.broadcastMessage("FORMEL : " + (f1 - mininmus) / fuel);
				}
				if (fuelClone.getType() == Material.LAVA_BUCKET) {
					if (((f1) - mininmus) / fuel == 0)
					f.getInventory().setItem(1, new ItemStack(Material.BUCKET));
				}
			

				int restFuel;
				if (f.getInventory().getFuel() != null
						&& f.getInventory().getFuel().getType() != Material.BUCKET) {
					Bukkit.broadcastMessage("IF");
					restFuel = f1 - timer - fuel * f.getInventory().getFuel().getAmount();
				} else {
					restFuel = f1 - timer;
					Bukkit.broadcastMessage("ELSE");
				}

				Bukkit.broadcastMessage("F1 " + f1 + " RF " + restFuel + " timer " + timer);
				
				
			
				
				
				
				tileEntity.save(ntc);
				if (f4 == mininmus && restFuel >= 0) {
					
					//f.setCookTime((short) ((timer + f.getCookTime()) % fr.getCookingTime()));
					ntc.setShort("CookTime", (short) ((timer + f.getCookTime()) % fr.getCookingTime()));
					
				}
				else {
					ntc.setShort("CookTime",(short)0);
				}
				ntc.setShort("BurnTime",(short) restFuel > 0 ? (short) restFuel : (short) 0);
				ntc.setString("RecipeLocation" + ntc.getShort("RecipesUsedSize"), fr.getKey().toString());
				ntc.setInt("RecipeAmount" + ntc.getShort("RecipesUsedSize"), min);
				ntc.setShort("RecipesUsedSize", (short) (ntc.getShort("RecipesUsedSize") + 1));

				tileEntity.load(ntc);
				tileEntity.update();
				Bukkit.broadcastMessage("jE BOL" +f.getCookTime());
			}
		}
	}
}

if (loc.getBlock().getState() instanceof Smoker) {
	Bukkit.broadcastMessage("Im in");
	Smoker f = (Smoker) b.getState();
	Recipe recipe = null;
	ItemStack item = f.getInventory().getItem(0);
	ItemStack result = null;

	if (item != null) {
		Iterator<Recipe> iter = Bukkit.recipeIterator();
		while (iter.hasNext()) {
			recipe = iter.next();
			if (!(recipe instanceof SmokingRecipe))
				continue;
			if (!((SmokingRecipe) recipe).getInputChoice().test(item))
				continue;
			result = recipe.getResult();
			break;
		}
		if (result != null) {
			// result.setAmount(item.getAmount());
			SmokingRecipe fr = (SmokingRecipe) recipe;
			Bukkit.broadcastMessage(fr.getKey().toString());
			if (f.getInventory().getResult() == null
					|| f.getInventory().getResult().getType() == result.getType()) {

				Bukkit.broadcastMessage(NBTUtils.getNBT("BurnTime", f.getInventory().getFuel()).toString());
				// TileEntityBlastFurnace.f().get(f.getInventory().getFuel());
				int fuel = 0;
				for (Item i : TileEntitySmoker.f().keySet()) {

					net.minecraft.server.v1_15_R1.ItemStack is = new net.minecraft.server.v1_15_R1.ItemStack(
							i);
					ItemStack is2 = CraftItemStack.asBukkitCopy(is);
					if (f.getInventory().getFuel() != null
							&& f.getInventory().getFuel().getType() != Material.BUCKET) {
						if (is2.getType() == f.getInventory().getFuel().getType()) {
							fuel = TileEntitySmoker.f().get(i);
						}
					} else {
						fuel = 1;
					}
				}
				Bukkit.broadcastMessage("Fuel : " + fuel);
				int gf1;
				int f1;
				if (f.getInventory().getFuel() == null
						|| f.getInventory().getFuel().getType() == Material.BUCKET) {
					f1 = (f.getBurnTime());
				} else {
					f1 = (fuel * f.getInventory().getFuel().getAmount() + f.getBurnTime());
				}
				gf1 = (f1+f.getCookTime()) / fr.getCookingTime();
				
				int gf2 = item.getAmount();
				int f2 = gf2*fr.getCookingTime()-f.getCookTime();
				
				int gf3;
				if (f.getInventory().getResult() == null) {
					gf3 = result.getMaxStackSize();
				} else {
					gf3 = result.getMaxStackSize() - f.getInventory().getResult().getAmount();
				}
				int f3 = gf3*fr.getCookingTime()-f.getCookTime();
				int gf4 = (timer + f.getCookTime()) / fr.getCookingTime();
				int f4 = timer;
				
				int mininmus = Math.min(Math.min(Math.min(f1, f2), f3), f4);
				int min = 0;
				if (f1 == mininmus) {
					min = gf1;	
				}
				if (f2 == mininmus) {
					min = gf2;	
				}
				if (f3 == mininmus) {
					min = gf3;	
				}
				if (f4 == mininmus) {
					min = gf4;	
				}
				f.getInventory().getItem(0).setAmount(f.getInventory().getItem(0).getAmount() - min);

				if (f.getInventory().getResult() == null) {
					f.getInventory().setResult(new ItemStack(result.getType(), min));
				} else {
					
					ItemStack resultStack = f.getInventory().getResult();
					resultStack.setAmount(f.getInventory().getResult().getAmount() + min);
					
					f.getInventory().setResult(resultStack);
				}
				ItemStack fuelClone = f.getInventory().getFuel().clone();
				if (f.getInventory().getFuel() != null
						&& f.getInventory().getFuel().getType() != Material.BUCKET) {
					ItemStack fuelStack = f.getInventory().getFuel();
					fuelStack.setAmount(((f1) - mininmus) / fuel);
					
					Bukkit.broadcastMessage("ITEMSTACK HAS AMOUT" +fuelStack.getAmount());
					f.getInventory().setItem(1, fuelStack);
					Bukkit.broadcastMessage("FORMEL : " + (f1 - mininmus) / fuel);
				}
				if (fuelClone.getType() == Material.LAVA_BUCKET) {
					if (((f1) - mininmus) / fuel == 0)
					f.getInventory().setItem(1, new ItemStack(Material.BUCKET));
				}
			

				int restFuel;
				if (f.getInventory().getFuel() != null
						&& f.getInventory().getFuel().getType() != Material.BUCKET) {
					Bukkit.broadcastMessage("IF");
					restFuel = f1 - timer - fuel * f.getInventory().getFuel().getAmount();
				} else {
					restFuel = f1 - timer;
					Bukkit.broadcastMessage("ELSE");
				}

				Bukkit.broadcastMessage("F1 " + f1 + " RF " + restFuel + " timer " + timer);
				
				
			
				
				
				
				tileEntity.save(ntc);
				if (gf4 == min && restFuel > 0) {
					
					//f.setCookTime((short) ((timer + f.getCookTime()) % fr.getCookingTime()));
					ntc.setShort("CookTime", (short) ((timer + f.getCookTime()) % fr.getCookingTime()));
					
				}
				else {
					ntc.setShort("CookTime",(short)0);
				}
				ntc.setShort("BurnTime",(short) restFuel > 0 ? (short) restFuel : (short) 0);
				ntc.setString("RecipeLocation" + ntc.getShort("RecipesUsedSize"), fr.getKey().toString());
				ntc.setInt("RecipeAmount" + ntc.getShort("RecipesUsedSize"), min);
				ntc.setShort("RecipesUsedSize", (short) (ntc.getShort("RecipesUsedSize") + 1));

				tileEntity.load(ntc);
				tileEntity.update();
				Bukkit.broadcastMessage("jE BOL" +f.getCookTime());
			}
		}
	}
	
}
*/