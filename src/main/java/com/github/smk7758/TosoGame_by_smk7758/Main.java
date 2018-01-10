package com.github.smk7758.TosoGame_by_smk7758;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static final String plugin_name = "TosoGame_by_smk7758";
	public static boolean debug_mode = false;
	private CommandExecuter command_executer = new CommandExecuter(this);
	private TeamManager team_manager = null;
	private GameManager game_manager = null;

	@Override
	public void onEnable() {
		if (!Main.plugin_name.equals(getDescription().getName())) getPluginLoader().disablePlugin(this);
		// getServer().getPluginManager().registerEvents(command_listner, this);
		getCommand("TosoGame").setExecutor(command_executer);
		saveDefaultConfig();
		team_manager = new TeamManager();
	}

	@Override
	public void onDisable() {
	}

	public CommandExecuter getCommandExecuter() {
		return command_executer;
	}

	public TeamManager getTeamManager() {
		return team_manager;
	}

	public GameManager getGameManager() {
		return game_manager;
	}

	public void startGame() {
		game_manager = new GameManager(this);
		Bukkit.getOnlinePlayers().forEach(player -> player.setScoreboard(team_manager.getBoard()));
	}
}
