package fr.olleroy.rose;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import fr.olleroy.rose.commands.CommandBase;
import fr.olleroy.rose.customInit.CustomInit;
import fr.olleroy.rose.listeners.RoseListener;

public class Rose extends JavaPlugin {
	public String pluginVersion = this.getDescription().getVersion();
	public String APIVersion = this.getDescription().getAPIVersion();
	
	@Override
	public void onEnable() {
		System.out.println("\033[35m plugin Rose en marche! version: "+pluginVersion+" pour "+APIVersion+"\033[37m");
		//intit commandes
		getCommand("rose").setExecutor(new CommandBase());
		getCommand("gps").setExecutor(new CommandBase());
		getCommand("date").setExecutor(new CommandBase());
		getCommand("tonether").setExecutor(new CommandBase());
		getCommand("toearth").setExecutor(new CommandBase());
		getCommand("wearit").setExecutor(new CommandBase());
		getCommand("sort").setExecutor(new CommandBase());
		//Listener / events
		getServer().getPluginManager().registerEvents(new RoseListener(), this);
		
		//Custom Recipes
		//La boussole Complexe
		Material[] bouMat = {Material.GOLD_BLOCK, Material.GOLD_BLOCK, Material.GOLD_BLOCK,
			                 Material.GOLD_BLOCK, Material.COMPASS, Material.GOLD_BLOCK,
			                 Material.GOLD_BLOCK, Material.GOLD_BLOCK, Material.GOLD_BLOCK};
		//matrice dans la table de craft
		
		ItemStack boussoleComplexe = CustomInit.getItem(Material.COMPASS, "§6Boussole Complexe", 1);
		//nouveau item
		CustomInit.addEnchant(boussoleComplexe, Enchantment.KNOCKBACK, 3);
		
		Bukkit.addRecipe(CustomInit.newRecipe(this, bouMat, boussoleComplexe, "boussole_complexe"));
		//ajoute la recette et l'item au serveur
		
	}
	@Override
	public void onLoad() {
		System.out.println("\033[35m Chargement du plugin Rose... \033[37m");
	}
	@Override
	public void onDisable() {
		System.out.println("\033[35m Plugin Rose eteint \033[37m");
	}
}
