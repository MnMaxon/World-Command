package me.MnMaxon.WorldCommand;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
	public static String dataFolder;
	public static Main plugin;

	@Override
	public void onEnable() {
		plugin = this;
		dataFolder = this.getDataFolder().getAbsolutePath();
		setupConfig();
		getServer().getPluginManager().registerEvents(new MainListener(), this);
	}

	public static YamlConfiguration setupConfig() {
		List<String> cmds = new ArrayList<String>();
		cmds.add("spawn");
		cmds.add("help 1");
		cmds.add("me has joined the server");
		cfgSetter("ExampleWorld", cmds);
		return Config.Load(dataFolder + "/Config.yml");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CommandSender s = sender;
		if (args.length >= 3) {
			String world = args[args.length - 1];
			YamlConfiguration cfg = Main.setupConfig();
			List<String> cmds = new ArrayList<String>();
			if (cfg.get(world) != null)
				cmds = cfg.getStringList(world);
			int x = 0;
			String command = "";
			boolean first = true;
			while (x < args.length - 1) {
				if (first) {
					command = command + args[x];
					first = false;
				} else
					command = command + " " + args[x];
				x++;
			}
			cmds.add(command);
			cfg.set(world, cmds);
			sender.sendMessage(ChatColor.GREEN + "Command successfully set!");
			Config.Save(cfg, dataFolder + "/Config.yml");
		} else
			displayHelp(s);
		return false;
	}

	private void displayHelp(CommandSender s) {
		s.sendMessage(ChatColor.AQUA + "========= World Command Help =========");
		s.sendMessage(ChatColor.DARK_PURPLE + "/WC <command> <world>");
	}

	public static void cfgSetter(String path, Object value) {
		YamlConfiguration cfg = Config.Load(dataFolder + "/Config.yml");
		if (cfg.get(path) == null) {
			cfg.set(path, value);
			Config.Save(cfg, dataFolder + "/Config.yml");
		}
	}
}