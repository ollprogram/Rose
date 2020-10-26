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
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] arg) {
		//si l'envoyeur est un joueur
		if(sender instanceof Player) {
			Player player = (Player)sender; //on récupère le joueur
			
			/*COMMANDE ROSE (commande de test)*/
			if(cmd.getName().equalsIgnoreCase("rose")) { 
				player.sendMessage("§5Rose version 1.7.1 pour 1.16.3§r"); //TOCHANGE EVERY VERSIONS
				return false;
			}
			
			/*COMMANDE GPS:
			 * description: Donne des coordonnées de personnes aux joueurs
			 * sous commandes:
			 * <gps> donne la coordonnée de la personne qui fait la commande
			 * <gps> <nomJoueur> donne les coords du joueur selectionné
			 * <gps> <tonether> donne les coords mais converties en coords nether
			 * <gps> <toearth> donne les coords mais converties en coords du monde normal*/
			if(cmd.getName().equalsIgnoreCase("gps")) {
				Location coord = player.getLocation(); //coords du joueur qui fait la commande
				if(arg.length!=0) { //si le tableau d'argument est non vide
					String worldName = player.getWorld().getName();
					
					//SOUS COMMANDE TONETHER:
					if(arg[0].equalsIgnoreCase("tonether")&&!worldName.equalsIgnoreCase("nether")) {
						player.sendMessage("[§9earth§r] §6=>§r [§4nether§r] : "
						+(int)(coord.getX())/8+" §bX§r "
						+(int)(coord.getY())/8+" §aY§r "
						+(int)(coord.getZ())/8+" §5Z§r");
						return false;
					}
					//SOUS COMMANDE TOEARTH
					else if (arg[0].equalsIgnoreCase("toearth")&&worldName.equalsIgnoreCase("nether")) {
						player.sendMessage("[§4nether§r] §6=>§r [§9earth§r] : "
						+(int)(coord.getX())*8+" §bX§r "
						+(int)(coord.getY())*8+" §aY§r "
						+(int)(coord.getZ())*8+" §5Z§r");
						return false;
					}
					//SOUS COMMANDE GPS NOMJOUEUR
					else if (player.getServer().getPlayer(arg[0]) != null) { //si le joueur existe en ligne sur le serveur
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
						return false;
					}
					//SI ERREUR D'ARGUMENTS
					else if (!arg[0].equalsIgnoreCase("tonether")&&!arg[0].equalsIgnoreCase("toearth")) {
						player.sendMessage("[§4oops§r] §c Les argmuments valides sont <gps> <rien/nomJoueur/tonether/toearth>§r");
						return false;
					}
					//SI ERREUR DE DIMENTIONS
					else {
						player.sendMessage("[§4oops§r] §c Tu n'est pas dans la bonne dimension pour utiliser cette commande!");
						return false;
					}
				}
				//SINON SOUS COMMANDE GPS (principale)
				else Bukkit.broadcastMessage("[§6"+player.getName()+"§r]§r : "
					+(int)coord.getX()+" §bX§r "
					+(int)coord.getZ()+" §5Z§r");//message à tous
				return false;
			}
			
			/*COMMANDE DATE
			 * description: donne la date et l'heure locale*/
			if(cmd.getName().equalsIgnoreCase("date")) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
				LocalDateTime now = LocalDateTime.now();
				player.sendMessage("§aDate§r: §b"+dtf.format(now));
				return false;
			}
			
			/*COMMANDE TOEARTH
			 * description: convertit les coordonnées passées en argument
			 * en coordonnées normales*/
			if(cmd.getName().equalsIgnoreCase("toearth")) {
				try {
					int x = Integer.parseInt(arg[0]);
					int y = Integer.parseInt(arg[1]);
					int z = Integer.parseInt(arg[2]);
					player.sendMessage("[§4nether§r] §6=>§r [§9earth§r] : "+ x*8+" §bX§r "+y*8+" §aY§r "+z*8+" §5Z§r ");
				} catch(Exception e) {// si erreur de type en argument ou autres erreurs
					player.sendMessage("[§4oops§r] §cUne écriture valide: toearth x y z");
				}
				return false;
			}
			
			/*COMMANDE TONETHER
			 * description:convertit les coordonnées passées en argument
			 * en coordonnées nether*/
			if(cmd.getName().equalsIgnoreCase("tonether")) {
				try {
					int x = Integer.parseInt(arg[0]);
					int y = Integer.parseInt(arg[1]);
					int z = Integer.parseInt(arg[2]);
					player.sendMessage("[§9earth§r] §6=>§r [§4nether§r] : "+ x/8+" §bX§r "+y/8+" §aY§r "+z/8+" §5Z§r ");
				} catch(Exception e) {// si erreur de type en argument ou autres erreurs
					player.sendMessage("[§4oops§r] §cUne écriture valide: tonether x y z");
				}
				return false;
			}
			
			/*COMMANDE WEARIT
			 * description: enlève l'item en main et le met sur la tête de la personne*/
			if(cmd.getName().equalsIgnoreCase("wearit")) {
				PlayerInventory inventory = (PlayerInventory)player.getInventory();
				if(inventory.getHelmet() == null) {
					ItemStack selected = inventory.getItemInMainHand();
					inventory.remove(selected); //enlève l'item de la personne
					inventory.setHelmet(selected); //remet l'item mais sur sa tête :)
					player.updateInventory();
					return false;
				}
				else {
					player.sendMessage("[§4oops§r] §cAttention tu n'as pas retiré ton casque avant d'essayer d'en mettre un nouveau!§r");
				}
			}
			
			/*COMMANDE SORT
			 * description: Trie le coffre à un block en face du joueur*/
			if(cmd.getName().equalsIgnoreCase("sort")) {
				Block lookAt = player.getTargetBlockExact(5);
				if(lookAt == null) { //si le block regardé est trop loin
					player.sendMessage("[§4oops§r] §cLe block que tu regardes est trop loin! §d(MAX = 5)§r");
					return false;
				}
				if(lookAt.getType() == Material.CHEST) { //si on regarde bien un coffre
					Chest chest = (Chest) lookAt.getState();
					Inventory inventory = chest.getInventory();
					
					if(inventory.isEmpty()) { //si le coffre est vide
						player.sendMessage("[§4oops§r] §cLe coffre est vide!§r");
						return false;
					}
	
					ItemStack[] items = inventory.getContents(); //retourne les items du coffre. Peuvent êtres nulls?
					for(int i = 0; i<items.length; i++) { // on parcours le coffre
						int som = 0; //somme d'occurences de item[i]
						if(items[i]!= null) { //si l'item n'est pas vide
							for(int j = i+1; j<items.length;j++) { //on parcours à nouveau le coffre à partir de i
								if(items[j]!= null && items[i].isSimilar(items[j])) { //si une occurence  
									som++;
									//on remplace item[i] par item[j]
									ItemStack temp = items[i+som];
									items[i+som] = items[j];
									items[j] = temp;
								}
							}
						}
						i += som;  // on ignore les éléments remplacés
					}
					//on replace tout dans le bon ordre
					/* j'aurais pu le faire dans la seconde boucle, mais 
					 * pour faciliter la compréhention je le fais ainsi*/
					inventory.clear();
					for(int i = 0; i<items.length;i++) {
						if(items[i]!=null) inventory.addItem(items[i]); //on peut pas ajouter d'items null
					}
					player.sendMessage("[§5Rose§r] §aC'est trié!!");
					return false;
				}
				else player.sendMessage("[§4oops§r] §cLe block que tu regardes n'est pas un coffre!");
			}
		}
		return false;
	}

}
