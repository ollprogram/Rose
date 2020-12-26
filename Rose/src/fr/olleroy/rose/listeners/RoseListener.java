package fr.olleroy.rose.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.olleroy.rose.customInit.CustomInit;

public class RoseListener implements Listener {
	
	/*message de retour sur le serveur*/
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		player.sendMessage("[§5Rose§r] §eCoucou! Nous attendions ton retour impatiemment!§r");
	}
	
	/*Message après la mort*/
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = (Player)event.getEntity();
		Location coord = player.getLocation();
		player.sendMessage("[§5Rose§r] §eTu est §4mort§e ici: §r"
		+(int)coord.getX()+" §bX§r "
		+(int)coord.getY()+" §aY§r "
		+(int)coord.getZ()+" §5Z§r ");
		
	}
	@EventHandler
	public void onInterractWithItem(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack itemEv = event.getItem();
		ItemStack boussoleComplexe = CustomInit.getItem(Material.COMPASS, "§6Boussole Complexe", 1);
	    CustomInit.addEnchant(boussoleComplexe, Enchantment.KNOCKBACK, 3);
		
		if(itemEv != null && boussoleComplexe.isSimilar(itemEv) &&  (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
			Inventory menu = Bukkit.createInventory(null, 27, "§6Menu Boussole Complexe");
			menu.setItem(11, CustomInit.getItem(Material.SLIME_BALL, "§aAjouter une coordonnée", 1));
			menu.setItem(12, CustomInit.getItem(Material.REDSTONE_BLOCK, "§4Enlever une coordonnée", 1));
			menu.setItem(14, CustomInit.getItem(Material.NAME_TAG, "§bChanger le nom et le block", 1));
			menu.setItem(9, CustomInit.getItem(Material.COMPASS, "Lieux enregistrés", 1));
			player.openInventory(menu);
			
		}
	}
	@EventHandler
	public void onClickInv(InventoryClickEvent event) {
		//Inventory menu = (Inventory)event.getInventory(); Deprecated
		Player player = (Player)event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		if(event.getView().getTitle().equalsIgnoreCase("§6Boussole Complexe")) {
			if(current == null)return;
			event.setCancelled(true);
			if(current.getItemMeta().getDisplayName().equals("§aAjouter une coordonnée")) {
				player.closeInventory();
				return;//TODO
			}
			if(current.getItemMeta().getDisplayName().equals("§aEnlever une coordonnée")) {
				player.closeInventory();
				return;//TODO
			}
			if(current.getItemMeta().getDisplayName().equals("§bChanger le nom et le block")) {
				player.closeInventory();
				return;//TODO
			}
			if(current.getItemMeta().getDisplayName().equals("Lieux enregistrés")) {
				player.closeInventory();
				return;//TODO
			}
		}
	}
	
}
