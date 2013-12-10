/**
 * LIZARDNET CRAFTBUKKIT-PLUGINS/GOLDEN-ANVIL
 * by Andrew "FastLizard4" Adams, TLUL, and the LizardNet CraftBukkit Plugins Development Team <https://gerrit.fastlizard4.org/r/#/admin/groups/15,members>
 *
 * Copyright (C) 2013 by Andrew "FastLizard4" Adams, TLUL, and the LizardNet CraftBukkit Plugins Development Team.  Some rights reserved.
 *
 * License GPLv3+: GNU General Public License version 3 or later (at your choice): <http://gnu.org/licenses/gpl.html>.
 * This is free software: you are free to change and redistribute it at your will provided that your redistribution,
 * with or without modifications, is also licensed under the GNU GPL.
 *
 * There is NO WARRANTY FOR THIS SOFTWARE to the extent permitted by law.
 *
 * This is an open source project.  The source Git repositories, which you are welcome to contribute to, can be
 * found here: <https://gerrit.fastlizard4.org/r/gitweb?p=craftbukkit-plugins/golden-anvil.git;a=summary>
 *
 * Gerrit Code Review for the project: <https://gerrit.fastlizard4.org/r/#/q/project:craftbukkit-plugins/golden-anvil,n,z>
 *
 * Continuous Integration for this project: <https://integration.fastlizard4.org/jenkins/job/craftbukkit-plugins-golden-anvil/>
 *
 * Alternatively, the project source code can be found on the PUBLISH-ONLY mirror on GitHub:
 * <https://github.com/LizardNet/craftbukkit-plugins-golden-anvil>
 *
 * To build this plugin, ensure you have Apache Maven verion 2 or 3 installed on your system, and run Maven with the "package" goal.
 * The .jar file will appear in target/ folder.  Install the plugin by moving it to the plugins/ directory in the root of your CraftBukkit
 * server directory.  You can also download the jarfiles from <https://integration.fastlizard4.org/jenkins/job/craftbukkit-plugins-golden-anvil/promotion/>
 * Do NOT download builds that are not listed under at least one of "Development", "Beta", or "Stable"!
 */

package org.fastlizard4.git.craftbukkit_plugins.golden_anvil;

import java.util.Iterator;

//Import necessary Bukkit API components
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class GoldenAnvils extends JavaPlugin {
 
	//Declare public class fields that contain the "definition" of the golden anvil blocks; they will be defined in GoldenAnvils::onEnable()
	//We want the ItemStacks for the golden anvils to be available outside of just this class
	public ItemStack goldenAnvil; //Material.ANVIL, damage value 3
	public ItemStack goldenAnvilDamaged; //Material.ANVIL, damage value 4
	public ItemStack goldenAnvilVeryDamaged; //Material.ANVIL, damage value 5
	private boolean m_recipeLoaded = false; //Boolean variable that will indicate whether the golden anvils recipe is loaded or not
	
    @Override
    public void onEnable() {
    	getLogger().info("Adding crafting recipe for golden anvils....");
    	
	//Define the Golden Anvil block
    	goldenAnvil = new ItemStack(Material.ANVIL, 1, (short)3);
    	ItemMeta goldenAnvilMeta = goldenAnvil.getItemMeta();
    	goldenAnvilMeta.setDisplayName("Golden Anvil");
    	goldenAnvil.setItemMeta(goldenAnvilMeta);
    	
	//Define the Slightly Damaged Golden Anvil block
    	goldenAnvilDamaged = new ItemStack(Material.ANVIL, 1, (short)4);
    	goldenAnvilMeta = goldenAnvilDamaged.getItemMeta();
    	goldenAnvilMeta.setDisplayName("Slighly Damaged Golden Anvil");
    	goldenAnvilDamaged.setItemMeta(goldenAnvilMeta);
    	
	//Define the Very Damaged Golden Anvil block
    	goldenAnvilVeryDamaged = new ItemStack(Material.ANVIL, 1, (short)5);
    	goldenAnvilMeta = goldenAnvilVeryDamaged.getItemMeta();
    	goldenAnvilMeta.setDisplayName("Very Damaged Golden Anvil");
    	goldenAnvilVeryDamaged.setItemMeta(goldenAnvilMeta);
    	
	//Define the Golden Anvil crafting recipe
    	ShapedRecipe goldenAnvilRecipe = new ShapedRecipe(goldenAnvil);
    	goldenAnvilRecipe.shape("GGG", " g ", "ggg");
    	goldenAnvilRecipe.setIngredient('G', Material.GOLD_BLOCK);
    	goldenAnvilRecipe.setIngredient('g', Material.GOLD_INGOT);
    	
	//Attempt to register Golden Anvil crafting recipe
        if(getServer().addRecipe(goldenAnvilRecipe)) {
        	getLogger().info("Successfully added golden anvil crafting recipe!");
		m_recipeLoaded = true;
        } else {
        	getLogger().severe("There is no crafting recipe, only Zuul!  (Adding the crafting recipe failed for some reason; I am now cowardly refusing to continue.)");
        	getPluginLoader().disablePlugin(this);
        }
    }
 
    @Override
    public void onDisable() {
    	getLogger().info("Attempting to unload golden anvils crafting recipe....");
    	
	if(this.m_recipeLoaded) {
		//Iterate through the server's list of registered recipes and attempt to cleanly unload the Golden Anvil crafting recipe
		//After the plugin is unloaded, Golden Anvils will behave like standard Anvils, and Golden Anvils will be uncraftable
		//Only attempt to do this, though, if the recipe got loaded in the first place (this.m_recipeLoaded)
	        Iterator<Recipe> recipeList = getServer().recipeIterator();
	        Recipe recipe;
	        try {
	        	while(recipeList.hasNext()) {
	        		recipe = recipeList.next();
	        		
	        		if(recipe.getResult() == this.goldenAnvil) {
	        			recipeList.remove();
					this.m_recipeLoaded = false;
	        			break;
	        		}
	        	}
	        } catch(Exception e) {
	        	getLogger().severe("Unable to unload golden anvils crafting recipe!  Expect undefined behavior!");
	        	getLogger().severe("The exception that occurred was: "+e.getMessage());
	        	getLogger().severe("A stack trace will now be printed to stderr.");
	        	e.printStackTrace();
	        }
	}
        
        getLogger().info("Successfully unloaded golden anvils crafting recipe.  Bye!");
    }
}
