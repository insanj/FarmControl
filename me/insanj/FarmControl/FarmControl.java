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
package me.insanj.FarmControl;

import org.bukkit.plugin.PluginManager;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

public class FarmControl extends JavaPlugin
{
	private static final Logger log = Logger.getLogger("Minecraft");
	private final FarmListener blockListener = new FarmListener(this);
	private static final String version = "1.0";
	
	@Override
	public void onEnable(){		
				
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_SPREAD, blockListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_FORM, blockListener, Event.Priority.Normal, this);
		
		generateFiles();
		log.info("{FarmControl} plugin version " + version + " has successfully started.");
	}//end method onEnable()
	
	@Override
	public void onDisable() {
		log.info("{FarmControl} plugin version " + version + " disabled.");
	}//end method onDisable()
	
	void generateFiles(){
			ArrayList<String> text = new ArrayList<String>();
			text.add("FARMCONTROL was created by Julian (insanj) Weiss. Please do not modify, but feel free to distribute with credit.");
			text.add("\nAll needed information for this plugin can be found in the Bukkit forums under FarmControl.");
			text.add("\nFrequent updates and news can be found on Google+ (and sometimes Twitter) from Julian (insanj) Weiss!");

			text.add("\n\nThis is an automatically generated configuration file, that holds all the FarmControl persistant information.");
			text.add("\nTampering with this file directly is highly unadvised, and can cause major errors with FarmControl!");
			text.add("\nDeleting this file will make the folder regenerate from default.");
			text.add("\nIf a property below has no value, it will revert to the default Minecraft settings.");
			text.add("\nFor the slower_by properties, put the values are slower by amount of seconds, as in: 10.5");
			text.add("\nFor the spreads_on properties, put the values in the following format: 1, 2, 3, 4 - Leaving this property blank will make it resume as normal.\n\n\n");
			
			
			text.add("mushroom_nether: true \n");
			text.add("mushroom_slower_by: 0.0 \n");
			text.add("mushroom_spreads_on: \n\n");

			text.add("grass_nether: true \n");
			text.add("grass_slower_by: 0.0 \n");
			text.add("grass_spreads_on: \n\n");

			text.add("flower_nether: true \n");
			text.add("flower_slower_by: 0.0 \n");
			text.add("flower_spreads_on: \n\n");
			
			text.add("tree_nether: true\n");
			text.add("tree_slower_by: 0.0 \n");
			text.add("tree_spreads_on: \n\n");

			text.add("wheat_nether: true \n");
			text.add("wheat_slower_by: 0.0 \n");
			text.add("wheat_spreads_on: \n\n");

			text.add("sugarcane_nether: true \n");
			text.add("sugarcane_slower_by: 0.0 \n");
			text.add("sugarcane_spreads_on: \n\n");
			
			text.add("cactus_nether: true \n");
			text.add("cactus_slower_by: 0.0 \n");
			text.add("cactus_spreads_on: \n\n");
			
		//If the config file doesn't already exist, generate the two text files.
		if( !(new File("plugins/FarmControl/config.txt").exists()) ){
			try{
				new File("plugins/FarmControl").mkdir();
				File config = new File("plugins/FarmControl/config.txt");
				BufferedWriter configWriter = new BufferedWriter(new FileWriter(config));
				
				for(int i = 0; i < text.size(); i++)
					configWriter.write(text.get(i));
				
				configWriter.close();
				
				log.info("{FarmControl} successfully created the FarmControl folder and containing files!");
			}//end try
			
			catch (Exception e){
				log.severe("{FarmControl} had a problem creating/storing in the directory! Error: " + e.getMessage());
			}
			
		}//end if
	}//end generateFiles()
	
}//end class FarmControl


/***********************************Contents of "plugin.yml":*******************************
name: FarmControl
version: 1.0
author: insanj
main: me.insanj.FarmControl.FarmControl
description: Limits the growth of certain plants, as determined by the generated config file.
******************************************************************************************/