package com.github.smk7758.TosoGame_by_smk7758;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import com.github.smk7758.TosoGame_by_smk7758.FileUtils.YamlFileManager;
import com.github.smk7758.TosoGame_by_smk7758.Files.ConfigFile;
import com.github.smk7758.TosoGame_by_smk7758.Files.GameFile;
import com.github.smk7758.TosoGame_by_smk7758.Game.Game;
import com.github.smk7758.TosoGame_by_smk7758.Game.GameListener;

public class Main extends JavaPlugin {
	public static final String plugin_name = "TosoGame_by_smk7758";
	public static boolean debug_mode = false;
	private CommandExecuter command_executer = new CommandExecuter(this);
	private GameListener game_listner = new GameListener(this);
	private Game game_manager = null;
	private Scoreboard board = null;
	private YamlFileManager yfm = new YamlFileManager(this);
	public ConfigFile config_file = null;
	public GameFile game_file = null;

	@Override
	public void onEnable() {
		if (!Main.plugin_name.equals(getDescription().getName())) getPluginLoader().disablePlugin(this);
		getServer().getPluginManager().registerEvents(game_listner, this);
		getCommand("TosoGame").setExecutor(command_executer);
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		config_file = new ConfigFile(this);
		game_file = new GameFile(this);
		saveDefaultFiles();
		reloadFiles();
	}

	@Override
	public void onDisable() {
	}

	public void saveDefaultFiles() {
		yfm.saveDefaultYamlFile(config_file, false);
		yfm.saveDefaultYamlFile(game_file, false);
	}

	public void reloadFiles() {
		yfm.reloadYamlFile(config_file);
		yfm.reloadYamlFile(game_file);
	}

	public YamlFileManager getYamlFileManager() {
		return yfm;
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