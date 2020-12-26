package fr.olleroy.rose.customInit;

import org.bukkit.Bukkit;
import util.ACX;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import java.io.*;
//Cette classe permet de simplifier/réduire le code et de le rendre plus compréhensible
//avec l'aide de nouvelles fonctions
public class CustomInit {
	
	//permet la création d'une nouvelle recette pour un item précis
	public static ShapedRecipe newRecipe(Plugin rose, Material[]matrice, ItemStack newItem, String nameKey) {
		if(matrice.length>9) System.out.println("\\033[35m [ERROR] Argument matrice must be of length 9");
		NamespacedKey itemKey = new NamespacedKey(rose, nameKey);
		ShapedRecipe itemRec = new ShapedRecipe(itemKey, newItem);
		itemRec.shape("ABC","DEF","GHI");
		char j = 'A';
		for(int i = 0; i<9; i++) {
			try{ itemRec.setIngredient((char)j, matrice[i]); }
			catch(Exception e){System.out.println("\\033[35m [ERROR] matrice elements can't be null");}
			j++;
		}
		return itemRec;
	}
	//retourne un item à partir des arguments 
	public static ItemStack getItem(Material mat, String displayName, int amount) {
		ItemStack newItem = new ItemStack(mat,amount);
		ItemMeta meta = newItem.getItemMeta();
		meta.setDisplayName(displayName);
		newItem.setItemMeta(meta);
		return newItem;
	}
	//enchante un item
	public static void addEnchant(ItemStack item, Enchantment enchant, int lvl) {
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(enchant, lvl, true);
		item.setItemMeta(meta);
	}
	//lit des noms dans un fichier
	public static String[] readNames(String fileName) throws IOException {
		return ACX.lectureDico(fileName);
	}
	//écrit des noms dans un fichier
	public static void writeNames(String fileName, String[] lines) throws IOException {
		ACX.ecritureFichierString(lines, fileName);
	}
	//ajoute additionne deux tableaux de string
	public static String[] stringArrayAppend(String[]array,String[]append) {
		String[] resultat = new String[array.length+append.length];
		int j = 0;
		for(int i = 0; i<resultat.length; i++) {
			if(i<array.length) {
				resultat[i] = array[i];
			}
			else {
				resultat[i] = append[j++];
			}
		}
		return resultat;
	}
	//retourne un menu de couleurs
	public static Inventory createColorMenu(String menuName) {
		Inventory menu = Bukkit.createInventory(null, 27, menuName);
		String matType = "_WOOL";
		String[] colors = {"WHITE","BLUE","GREEN","RED","LIGHT_BLUE","LIGHT_GREEN","ORANGE","PINK","PURPLE",
							"MAGENTA","CYAN","BROWN","BLACK","GREY","YELLOW","LIME"};
		ItemStack[] wools = new ItemStack[colors.length];
		for(int i = 0; i<wools.length;i++) {
			wools[i] = getItem(Material.getMaterial(colors[i]+matType), colors[i], 1);
		}
		menu.setContents(wools);
		return menu;
	}
	public static String addColorToText(String text, String color) {//TODO pas fini
		String[] colors = {"WHITE","BLUE","GREEN","RED","LIGHT_BLUE","LIGHT_GREEN","ORANGE","PINK","PURPLE",
				"MAGENTA","CYAN","BROWN","BLACK","GREY","YELLOW","LIME"};
		String[] codes = {"§r","§1","§2","§4","§b","§a","§6","§d","§5",
				"MAGENTA","CYAN","BROWN","BLACK","GREY","YELLOW","LIME","LEGACY"};
		return colors[0]+codes[0];//no warnings
	}
	
}
