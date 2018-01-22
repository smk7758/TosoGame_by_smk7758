package com.github.smk7758.TosoGame_by_smk7758;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import com.github.smk7758.TosoGame_by_smk7758.Files.ConfigFile;
import com.github.smk7758.TosoGame_by_smk7758.Game.Game;
import com.github.smk7758.TosoGame_by_smk7758.Game.GameListener;

public class Main extends JavaPlugin {
	public static final String plugin_name = "TosoGame_by_smk7758";
	public static boolean debug_mode = false;
	private CommandExecuter command_executer = new CommandExecuter(this);
	private GameListener game_listner = new GameListener(this);
	private Game game_manager = null;
	private Scoreboard board = null;
	private ConfigFile config_file = null;

	@Override
	public void onEnable() {
		if (!Main.plugin_name.equals(getDescription().getName())) getPluginLoader().disablePlugin(this);
		getServer().getPluginManager().registerEvents(game_listner, this);
		getCommand("TosoGame").setExecutor(command_executer);
		saveDefaultConfig();
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		game_manager = new Game(this);
		config_file = new ConfigFile();
	}

	@Override
	public void onDisable() {
	}

	public CommandExecuter getCommandExecuter() {
		return command_executer;
	}

	public Game getGameManager() {
		return game_manager;
	}

	public void startGame() {
		if (!game_manager.getIsGameStarting()) game_manager.start();
	}

	public Scoreboard getBoard() {
		return board;
	}
}