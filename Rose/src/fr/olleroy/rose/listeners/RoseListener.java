package fr.olleroy.rose.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

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
	
}
