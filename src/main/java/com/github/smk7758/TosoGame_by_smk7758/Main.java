package com.github.smk7758.TosoGame_by_smk7758;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static final String plugin_name = "TosoGame_by_smk7758";
	public static boolean debug_mode = false;
	private CommandExecuter command_executer = new CommandExecuter(this);
	private TeamManager scorebord_manager = null;
	private GameManager gamae_manager = new GameManager(this);

	@Override
	public void onEnable() {
		if (!Main.plugin_name.equals(getDescription().getName())) getPluginLoader().disablePlugin(this);
		// getServer().getPluginManager().registerEvents(command_listner, this);
		getCommand("TosoGame").setExecutor(command_executer);
		scorebord_manager = new TeamManager();
	}

	@Override
	public void onDisable() {
	}

	public CommandExecuter getCommandExecuter() {
		return command_executer;
	}

	public TeamManager getTeamManager() {
		return scorebord_manager;
	}

	public GameManager getGameManager() {
		return gamae_manager;
	}
}
