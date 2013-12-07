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
 * Continuous Integration for this project: <https://integration.fastlizard4.org/jenkins/job/craftbukkit-plugins-golden-anvil%20maven/>
 *
 * Alternatively, the project source code can be found on the PUBLISH-ONLY mirror on GitHub:
 * <https://github.com/LizardNet/craftbukkit-plugins-golden-anvil>
 */

package org.fastlizard4.git.craftbukkit_plugins.golden_anvil;

import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class GoldenAnvils extends JavaPlugin {
 
	//We want the ItemStacks for the golden anvils to be available outside of just this class
	public ItemStack goldenAnvil; //Material.ANVIL, damage value 3
	public ItemStack goldenAnvilDamaged; //Material.ANVIL, damage value 4
	public ItemStack goldenAnvilVeryDamaged; //Material.ANVIL, damage value 5
	
    @Override
    public void onEnable() {
    	getLogger().info("Adding crafting recipe for golden anvils....");
    	
    	this.goldenAnvil = new ItemStack(Material.ANVIL, 1, (short)3);
    	ItemMeta goldenAnvilMeta = this.goldenAnvil.getItemMeta();
    	goldenAnvilMeta.setDisplayName("Golden Anvil");
    	this.goldenAnvil.setItemMeta(goldenAnvilMeta);
    	
    	this.goldenAnvilDamaged = new ItemStack(Material.ANVIL, 1, (short)4);
    	goldenAnvilMeta = this.goldenAnvilDamaged.getItemMeta();
    	goldenAnvilMeta.setDisplayName("Slighly Damaged Golden Anvil");
    	this.goldenAnvilDamaged.setItemMeta(goldenAnvilMeta);
    	
    	this.goldenAnvilVeryDamaged = new ItemStack(Material.ANVIL, 1, (short)5);
    	goldenAnvilMeta = this.goldenAnvilVeryDamaged.getItemMeta();
    	goldenAnvilMeta.setDisplayName("Very Damaged Golden Anvil");
    	this.goldenAnvilVeryDamaged.setItemMeta(goldenAnvilMeta);
    	
    	ShapedRecipe goldenAnvilRecipe = new ShapedRecipe(this.goldenAnvil);
    	goldenAnvilRecipe.shape("GGG", " g ", "ggg");
    	goldenAnvilRecipe.setIngredient('G', Material.GOLD_BLOCK);
    	goldenAnvilRecipe.setIngredient(' ', Material.AIR);
    	goldenAnvilRecipe.setIngredient('g', Material.GOLD_INGOT);
    	
        if(getServer().addRecipe(goldenAnvilRecipe)) {
        	getLogger().info("Successfully added golden anvil crafting recipe!");
        } else {
        	getLogger().severe("There is no crafting recipe, only Zuul!  (Adding the crafting recipe failed for some reason; I am now cowardly refusing to continue.)");
        	getPluginLoader().disablePlugin(this);
        }
    }
 
    @Override
    public void onDisable() {
    	getLogger().info("Attempting to unload golden anvils crafting recipe....");
    	
        Iterator<Recipe> recipeList = getServer().recipeIterator();
        Recipe recipe;
        try {
        	while(recipeList.hasNext()) {
        		recipe = recipeList.next();
        		
        		if(recipe.getResult() == this.goldenAnvil) {
        			recipeList.remove();
        			break;
        		}
        	}
        } catch(Exception e) {
        	getLogger().severe("Unable to unload golden anvils crafting recipe!  Expect undefined behavior!");
        	getLogger().severe("The exception that occurred was: "+e.getMessage());
        	getLogger().severe("A stack trace will now be printed to stderr.");
        	e.printStackTrace();
        }
        
        getLogger().info("Successfully unloaded golden anvils crafting recipe.  Bye!");
    }
}