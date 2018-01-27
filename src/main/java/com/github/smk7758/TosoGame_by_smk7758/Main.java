package com.github.smk7758.TosoGame_by_smk7758;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import com.github.smk7758.TosoGame_by_smk7758.Files.ConfigFile;
import com.github.smk7758.TosoGame_by_smk7758.Files.GameFile;
import com.github.smk7758.TosoGame_by_smk7758.Files.FileUtils.YamlFileManager;
import com.github.smk7758.TosoGame_by_smk7758.Game.Game;
import com.github.smk7758.TosoGame_by_smk7758.Game.GameListener;
import com.github.smk7758.TosoGame_by_smk7758.Util.SendLog;

public class Main extends JavaPlugin {
	public static final String plugin_name = "TosoGame_by_smk7758";
	public static boolean debug_mode = true;
	private CommandExecuter command_executer = new CommandExecuter(this);
	private GameListener game_listner = new GameListener(this);
	private Game game_manager = null;
	private Scoreboard scoreboard = null;
	private YamlFileManager yfm = null;
	public ConfigFile config_file = null;
	public GameFile game_file = null;

	@Override
	public void onEnable() {
		if (!Main.plugin_name.equals(getDescription().getName())) getPluginLoader().disablePlugin(this);
		getServer().getPluginManager().registerEvents(game_listner, this);
		getCommand("TosoGame").setExecutor(command_executer);
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		yfm = new YamlFileManager(this);
		SendLog.debug("start config file!");
		config_file = new ConfigFile(this);
		saveResource(config_file.getFileName(), false);
		config_file = (ConfigFile) yfm.reloadYamlFile(config_file);
		// !!! yfm.saveDefaultYamlFile(config_file, false);

		// game_file = new GameFile(this);
		// saveResource(game_file.getFileName(), false);
		// game_file = (GameFile) yfm.reloadYamlFile(game_file);
		// !!! yfm.saveDefaultYamlFile(game_file, false);

		// reloadFiles(); // load field to class object.
		game_manager = new Game(this);
	}

	@Override
	public void onDisable() {
	}

	public void reloadFiles() {
		config_file = (ConfigFile) yfm.reloadYamlFile(config_file);
		game_file = (GameFile) yfm.reloadYamlFile(game_file);
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

	public Scoreboard getScoreBoard() {
		return scoreboard;
	}
	// TODO configfile, sidebar(数値変化Event - HunterTouchRunner), PotionClearEvent.
}