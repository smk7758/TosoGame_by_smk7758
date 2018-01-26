package com.github.smk7758.TosoGame_by_smk7758.Files;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.plugin.Plugin;

import com.github.smk7758.TosoGame_by_smk7758.FileUtils.YamlFile;
import com.github.smk7758.TosoGame_by_smk7758.FileUtils.YamlFileExceptField;
import com.github.smk7758.TosoGame_by_smk7758.Game.ScorebordTeam.TeamName;
import com.github.smk7758.TosoGame_by_smk7758.Util.SendLog;

public class GameFile extends YamlFile {
	private final String file_name = "game.yml";
	public String GameName;
	@YamlFileExceptField public LocalTime GameLength;
	@YamlFileExceptField public Map<String, TeamName> Players; // TODO
	public Book Book;

	public class Book {
		public String Name, Title;
		public List<String> Lore, Pages;
	}

	public GameFile(Plugin plugin) {
		super(plugin);
	}

	@Override
	public String getFileName() {
		return file_name;
	}

	@Override
	public void loadField() {
		String[] game_length_splitted = getFileConfiguration().getString("GameLength").split(",");
		GameLength = LocalTime.of(Integer.parseInt(game_length_splitted[0]), Integer.parseInt(game_length_splitted[1]));

		Players = new HashMap<>();
		for (String key : getFileConfiguration().getConfigurationSection("Players").getKeys(false)) {
			if (key != null) {
				String value = getFileConfiguration().getString(Players.getClass().getName() + "." + key);
				Players.put(key, TeamName.valueOf(value));
			}
		}
		testFields();
		// Playersがnull→Players.getClass().getName() x
	}

	private void testFields() {
		SendLog.debug("LOADING...");
		SendLog.debug(file_name);
		SendLog.debug(GameName);
		SendLog.debug(GameLength.toString());
		SendLog.debug(Book.Name);
		SendLog.debug(Book.Title);
		Book.Lore.forEach(lore_text -> SendLog.debug(lore_text));
		Book.Pages.forEach(page_text -> SendLog.debug(page_text));
		Players.forEach((player, teamname) -> SendLog.debug(player + " , " + teamname));
	}

	@Override
	public void saveField() {
	}
}
