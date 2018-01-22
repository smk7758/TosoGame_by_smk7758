package com.github.smk7758.TosoGame_by_smk7758.Files;

import java.util.HashMap;
import java.util.List;

import org.bukkit.plugin.Plugin;

import com.github.smk7758.TosoGame_by_smk7758.FileUtils.YamlFile;
import com.github.smk7758.TosoGame_by_smk7758.FileUtils.YamlFileManagerField;
import com.github.smk7758.TosoGame_by_smk7758.FileUtils.YamlFileManager;
import com.github.smk7758.TosoGame_by_smk7758.Game.ScorebordTeam.TeamName;

public class GameFile extends YamlFileManager implements YamlFile {

	public GameFile(Plugin plugin, YamlFile file) {
		super(plugin, file);
	}

	private final String file_name = "Game";
	public String GameName;
	public int GameLength;
	@YamlFileManagerField(false)
	public List<HashMap<String, TeamName>> Players;
	public Book Book; // TODO

	public class Book {
		public String Name, Title;
		public List<String> Lore, Pages;
	}

	@Override
	public String getFileName() {
		return file_name;
	}

	@Override
	public void loadField() {
		Players = (List<HashMap<String, TeamName>>) config.getList(Players.getClass().getName());
		// TODO
	}
}
