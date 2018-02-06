package com.github.smk7758.TosoGame_by_smk7758;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import com.github.smk7758.TosoGame_by_smk7758.Files.YamlFile;
import com.github.smk7758.TosoGame_by_smk7758.Files.YamlFileManager;
import com.github.smk7758.TosoGame_by_smk7758.Files.DataFiles.ConfigFile;
import com.github.smk7758.TosoGame_by_smk7758.Files.DataFiles.GameFile;
import com.github.smk7758.TosoGame_by_smk7758.Files.DataFiles.LanguageFile;
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
	public ConfigFile configfile = null;
	public GameFile gamefile = null;
	public LanguageFile languagefile = null;

	@Override
	public void onEnable() {
		if (!Main.plugin_name.equals(getDescription().getName())) getPluginLoader().disablePlugin(this);
		getServer().getPluginManager().registerEvents(game_listner, this);
		getCommand("TosoGame").setExecutor(command_executer);
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		yfm = new YamlFileManager(this);
		SendLog.debug("start config file!");

		configfile = new ConfigFile(this);
		saveResource(configfile, false);
		configfile = (ConfigFile) yfm.reloadYamlFile(configfile);
		// !!! yfm.saveDefaultYamlFile(config_file, false);

		gamefile = new GameFile(this);
		saveResource(gamefile, false);
		gamefile = (GameFile) yfm.reloadYamlFile(gamefile);

		languagefile = new LanguageFile(this);
		saveResource(languagefile, false);
		languagefile = (LanguageFile) yfm.reloadYamlFile(languagefile);

		// !!! yfm.saveDefaultYamlFile(game_file, false);
		// reloadFiles(); // load field to class object.

		game_manager = new Game(this);
	}

	@Override
	public void onDisable() {
	}

	public void reloadFiles() {
		configfile = (ConfigFile) yfm.reloadYamlFile(configfile);
		gamefile = (GameFile) yfm.reloadYamlFile(gamefile);
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

	public Scoreboard getScoreBoard() {
		return scoreboard;
	}

	public void saveResource(YamlFile file, boolean replace) {
		if (replace || !file.getFile().exists()) saveResource(file.getFileName(), replace);
	}
	// TODO: tab complete, command args, book or chat, recalling?reusing? the game, config use!, config if none!
	// DONE: config, 0.0.6.3間違えた！
}