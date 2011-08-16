/*
 Created by Julian Weiss (insanj), updates frequent on Google+ (and sometimes Twitter)! 

 Please do not modify or decompile at any date, but feel free to distribute with credit.
 Production began on Thursday, August 4th, 2011.
 Last edited on: 8/15/11

 FarmControl 1.0!
 Special thanks to: 
 		heifinator, for the idea and feature designs!

 		
 Works with the current CraftBukkit Build (#1000).
 All other information should be available at bukkit.org under FarmControl.


 THIS VERSION CURRENT HAS TWO CLASSES:
			FarmControl.java
			FarmListener.java

*/

//TODO: ADD CHUNK LIMITATIONS TO EVERY FARM THINGY

package me.insanj.FarmControl;

import java.io.File;
import java.util.Scanner;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.material.Tree;

public class FarmListener extends BlockListener
{
	FarmControl plugin;
	
	public FarmListener(FarmControl instance){
		plugin = instance;	
	}//end method FarmListener
	
	public void onBlockSpread(BlockSpreadEvent event){
		
		if(event.getBlock().equals(Material.BROWN_MUSHROOM) || event.getBlock().equals(Material.RED_MUSHROOM) )
			theWholeDeal(event, "mushroom");	
		
		if( event.getBlock().equals(Material.GRASS) )
			theWholeDeal(event, "grass");	

		if( event.getBlock().equals(Material.RED_ROSE) || event.getBlock().equals(Material.YELLOW_FLOWER) )
			theWholeDeal(event, "flower");	

	}//end onBlockSpread
	
	public void onBlockForm(BlockFormEvent event){
		
		if( event.getNewState() instanceof Tree)
			theWholeDeal(event, "tree");	

		if( event.getNewState().getType().equals(Material.CACTUS) )
			theWholeDeal(event, "cactus");	
		
		if( event.getNewState().getType().equals(Material.WHEAT) )
			theWholeDeal(event, "wheat");	

		if( event.getNewState().getType().equals(Material.SUGAR_CANE_BLOCK) )
			theWholeDeal(event, "sugarcane");	

	}//end onBlockSpread
	
	public void onBlockPlace(BlockPlaceEvent event){
				
	}//end onBlockPlace

	public void theWholeDeal(BlockFormEvent event, String name){
		if(netherChecker(event, name + "_nether: "))
			return;
		
		slowerTry(event, name + "_slower_by: ");
		spreadsTry(event, name + "_spreads_on: ");		
	}//end theWholeDeal()
	
	public void spreadsTry(BlockFormEvent event, String property){
		String[] spread = giveProperty(property);
		if(spread != null){
			for(int i = 0; i < spread.length; i++)
				if( event.getNewState().getBlock().getTypeId() == Integer.parseInt(spread[i]) )
					return;

			event.setCancelled(true);
		}//end if
		
	}//end spreadsCheck()
	
	public void slowerTry(BlockFormEvent event, String property){
		String[] slow = giveProperty(property);
		if(slow != null){
			try{ event.wait( (long) (Double.parseDouble(slow[0]) * 1000) ); } 
			catch (Exception e) { 
				System.out.println("{FarmControl} had an issue implementing the \"" + property + "\" property: ");
				e.printStackTrace();
			}
			
		}//end if
	}//end slowerTry()
	
	public boolean netherChecker(BlockFormEvent event, String property){
		String[] nether = giveProperty(property);
		if(nether != null && Boolean.parseBoolean(nether[0])){
			if(event.getNewState().getWorld().getEnvironment().equals(World.Environment.NETHER)){
				event.setCancelled(true);
				return true;
			}
		}//end if
		
		return false;
	}//end netherChecker()
	
	public String[] giveProperty(String line){
		try{
	    	Scanner outdoors = new Scanner(new File("plugins/FarmControl/config.txt"));
	    	
	    	while(outdoors.hasNextLine() ){
	    		String next = outdoors.nextLine();
	    		if(next.contains(line))
	    			return next.substring(line.length()).split(", ");
	    	}//end while
	 	}//end try
	    
	    catch(Exception e){
	    	System.out.println("{FarmControl} couldn't find the \"" + line + "\" line in the config file! Not implementing property until fixed.");
	    }
	    
		return null;
	}//end method giveProperty()

}//end class FarmListener
