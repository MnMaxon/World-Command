package me.MnMaxon.WorldCommand;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MainListener implements Listener {
	@EventHandler
	public void onTeleport(final PlayerTeleportEvent e) {
		if (e.getTo().getWorld() != e.getFrom().getWorld()) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				public void run() {
					String world = e.getTo().getWorld().getName();
					YamlConfiguration cfg = Main.setupConfig();
					if (cfg.get(world) != null) {
						List<String> cmds = cfg.getStringList(world);
						for (String cmd : cmds) {
							e.getPlayer().performCommand(cmd);
						}
					}
				}
			}, 1L);
		}
	}
}
