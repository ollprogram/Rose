package fr.olleroy.rose.commands;

//imports pour la date
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
//imports de classes pour gérer Minecraft(bukkit)
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


public class CommandBase implements CommandExecutor {
	
	/*COMMANDE ROSE (commande de test)*/
	public void test(Player player) {
		String apiV = player.getServer().getPluginManager().getPlugin("Rose").getDescription().getAPIVersion();
		String pluV = player.getServer().getPluginManager().getPlugin("Rose").getDescription().getVersion();
		player.sendMessage("§5Rose version "+pluV+" pour "+apiV+"§r"); //TOCHANGE EVERY VERSIONS
	}
	
	/*COMMANDE GPS:
	 * description: Donne des coordonnées de personnes aux joueurs
	 * sous commandes:
	 * <gps> donne la coordonnée de la personne qui fait la commande
	 * <gps> <nomJoueur> donne les coords du joueur selectionné
	 * <gps> <tonether> donne les coords mais converties en coords nether
	 * <gps> <toearth> donne les coords mais converties en coords du monde normal*/
	public void gpsNom(Player player, String[] arg) {
		Player asked = player.getServer().getPlayer(arg[0]); //asked représente le joueur cibl§
		Location aCoord = asked.getLocation(); //coords du joueur
		String WrldName2 = asked.getWorld().getName();
		//conditions pour transformer le nom du monde en quelque chose de plus parlant:
		if(WrldName2.endsWith("nether"))WrldName2 = "Nether";
		else if(WrldName2.endsWith("end"))WrldName2 = "The_End";
		else WrldName2 = "Normal";
		//**
		Bukkit.broadcastMessage("[§d"+WrldName2+"§r][§6"+asked.getName()+"§r]§r : "
		+(int)aCoord.getX()+" §bX§r "
		+(int)aCoord.getZ()+" §5Z§r");//message à tous
	}
	public void gps(Player player, String[] arg) {
		Location coord = player.getLocation(); //coords du joueur qui fait la commande
		if(arg.length!=0) { //si le tableau d'argument est non vide
			String worldName = player.getWorld().getName();
			
			//SOUS COMMANDE TONETHER:
			if(arg[0].equalsIgnoreCase("tonether")&&!worldName.equalsIgnoreCase("nether")) {
				player.sendMessage("[§9earth§r] §6=>§r [§4nether§r] : "
				+(int)(coord.getX())/8+" §bX§r "
				+(int)(coord.getY())/8+" §aY§r "
				+(int)(coord.getZ())/8+" §5Z§r");
				return;
			}
			//SOUS COMMANDE TOEARTH
			else if (arg[0].equalsIgnoreCase("toearth")&&worldName.equalsIgnoreCase("nether")) {
				player.sendMessage("[§4nether§r] §6=>§r [§9earth§r] : "
				+(int)(coord.getX())*8+" §bX§r "
				+(int)(coord.getY())*8+" §aY§r "
				+(int)(coord.getZ())*8+" §5Z§r");
				return;
			}
			//SOUS COMMANDE GPS NOMJOUEUR
			else if (player.getServer().getPlayer(arg[0]) != null) { //si le joueur existe en ligne sur le serveur
				gpsNom(player, arg);
				return;
			}
			//SI ERREUR D'ARGUMENTS
			else if (!arg[0].equalsIgnoreCase("tonether")&&!arg[0].equalsIgnoreCase("toearth")) {
				player.sendMessage("[§4oops§r] §c Les argmuments valides sont <gps> <rien/nomJoueur/tonether/toearth>§r");
				return;
			}
			//SI ERREUR DE DIMENTIONS
			else {
				player.sendMessage("[§4oops§r] §c Tu n'est pas dans la bonne dimension pour utiliser cette commande!");
				return;
			}
		}
		//SINON SOUS COMMANDE GPS (principale)
		else Bukkit.broadcastMessage("[§6"+player.getName()+"§r]§r : "
			+(int)coord.getX()+" §bX§r "
			+(int)coord.getZ()+" §5Z§r");//message à tous
	}

	/*COMMANDE DATE
	 * description: donne la date et l'heure locale*/
	public void date(Player player) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
		LocalDateTime now = LocalDateTime.now();
		player.sendMessage("§aDate§r: §b"+dtf.format(now));
	}

	/*COMMANDE TOEARTH
	 * description: convertit les coordonnées passées en argument
	 * en coordonnées normales*/
	public void toEarth(Player player, String[] arg) {
		try {
			int x = Integer.parseInt(arg[0]);
			int y = Integer.parseInt(arg[1]);
			int z = Integer.parseInt(arg[2]);
			player.sendMessage("[§4nether§r] §6=>§r [§9earth§r] : "+ x*8+" §bX§r "+y*8+" §aY§r "+z*8+" §5Z§r ");
		} catch(Exception e) {// si erreur de type en argument ou autres erreurs
			player.sendMessage("[§4oops§r] §cUne écriture valide: toearth x y z");
		}
	}
	
	/*COMMANDE TONETHER
	 * description:convertit les coordonnées passées en argument
	 * en coordonnées nether*/
	public void toNether(Player player, String[] arg) {
		try {
			int x = Integer.parseInt(arg[0]);
			int y = Integer.parseInt(arg[1]);
			int z = Integer.parseInt(arg[2]);
			player.sendMessage("[§9earth§r] §6=>§r [§4nether§r] : "+ x/8+" §bX§r "+y/8+" §aY§r "+z/8+" §5Z§r ");
		} catch(Exception e) {// si erreur de type en argument ou autres erreurs
			player.sendMessage("[§4oops§r] §cUne écriture valide: tonether x y z");
		}
	}
	
	/*COMMANDE WEARIT
	 * description: enlève l'item en main et le met sur la tête de la personne*/
	public void wearIt(Player player) {
		PlayerInventory inventory = (PlayerInventory)player.getInventory();
		if(inventory.getHelmet() == null) {
			ItemStack selected = inventory.getItemInMainHand();
			inventory.remove(selected); //enlève l'item de la personne
			inventory.setHelmet(selected); //remet l'item mais sur sa tête :)
			player.updateInventory();
		}
		else {
			player.sendMessage("[§4oops§r] §cAttention tu n'as pas retiré ton casque avant d'essayer d'en mettre un nouveau!§r");
		}
	}
	
	/*COMMANDE SORT
	 * description: Trie le coffre à un block en face du joueur*/
	//Compare deux ID d'items
	public static int compareInv(String s1, String s2) {
		//IGNORE CASES
		s1 = s1.toUpperCase(); s2 = s2.toUpperCase();
		String[] ts1 = s1.split("_"); String[] ts2 = s2.split("_");
		if(ts1.length < ts2.length)return -1;
		if(ts1.length > ts2.length)return 1;
		if(ts1[ts1.length-1].compareTo(ts2[ts2.length-1]) == 0) {
			if(ts1[0].compareTo(ts2[0]) < 0)return -1;
			else if(ts1[0].compareTo(ts2[0]) > 0)return 1;
			else return 0;
		}
		else {
			return (ts1[ts1.length-1].compareTo(ts2[ts2.length-1]) > 0)?1:-1;
		}		
	}
	public void sort(Player player) {
		Block lookAt = player.getTargetBlockExact(5);
		if(lookAt == null) { //si le block regardé est trop loin
			player.sendMessage("[§4oops§r] §cLe block que tu regardes est trop loin! §d(MAX = 5)§r");
			return;
		}
		if(lookAt.getType() == Material.CHEST) { //si on regarde bien un coffre
			Chest chest = (Chest) lookAt.getState();
			Inventory inventory = chest.getInventory();
			
			if(inventory.isEmpty()) { //si le coffre est vide
				player.sendMessage("[§4oops§r] §cLe coffre est vide!§r");
				return;
			}
			ItemStack[] items = inventory.getContents(); //retourne les items du coffre. Peuvent êtres nulls?
			for(int i = 0; i<items.length; i++) {
				int min = i;
				for(int j = i+1; j<items.length; j++) {
					if(items[i] != null && items[j] != null && compareInv(items[min].getType().toString()
							,items[j].getType().toString()) > 0) {
						min = j;
					}
				}
				ItemStack tmp = items[i];
				items[i] = items[min];
				items[min] = tmp;
			}
			//on replace tout dans le bon ordre
			/* j'aurais pu le faire dans la seconde boucle, mais 
			 * pour faciliter la compréhention je le fais ainsi*/
			inventory.clear();
			for(int i = 0; i<items.length;i++) {
				if(items[i]!=null) inventory.addItem(items[i]); //on peut pas ajouter d'items null
			}
			player.sendMessage("[§5Rose§r] §aC'est trié!!");
		}
		else player.sendMessage("[§4oops§r] §cLe block que tu regardes n'est pas un coffre!");
	}
	
	//MAIN COMMANDES
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] arg) {
		//si l'envoyeur est un joueur
		if(sender instanceof Player) {
			Player player = (Player)sender; //on récupère le joueur
			
			/*COMMANDE ROSE (commande de test)*/
			if(cmd.getName().equalsIgnoreCase("rose")) { 
				test(player);
				return false;
			}
			//COMMANDE GPS:
			if(cmd.getName().equalsIgnoreCase("gps")) {
				gps(player, arg);
				return false;
			}
			//COMMANDE DATE
			if(cmd.getName().equalsIgnoreCase("date")) {
				date(player);
				return false;
			}
			//COMMANDE TOEARTH
			if(cmd.getName().equalsIgnoreCase("toearth")) {
				toEarth(player, arg);
				return false;
			}
			//COMMANDE TONETHER
			if(cmd.getName().equalsIgnoreCase("tonether")) {
				toNether(player, arg);
				return false;
			}
			//COMMANDE WEARIT
			if(cmd.getName().equalsIgnoreCase("wearit")) {
				wearIt(player);
				return false;
			}
			//COMMANDE SORT
			if(cmd.getName().equalsIgnoreCase("sort")) {
				sort(player);
				return false;
			}
		}
		return false;
	}
}
