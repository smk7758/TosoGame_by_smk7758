package com.github.smk7758.TosoGame_by_smk7758.Files.DataFiles;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import com.github.smk7758.TosoGame_by_smk7758.Files.YamlFile;
import com.github.smk7758.TosoGame_by_smk7758.Files.YamlFileExceptField;
import com.github.smk7758.TosoGame_by_smk7758.Game.ScorebordTeam.TeamName;
import com.github.smk7758.TosoGame_by_smk7758.Util.SendLog;

public class GameFile extends YamlFile {
	private final String file_name = "game.yml"; // TODO final じゃないといけない！！
	public String GameName;
	@YamlFileExceptField
	public Time GameLength;
	@YamlFileExceptField
	public Map<String, TeamName> Players; // TODO
	@YamlFileExceptField
	public Location prison_loc;
	public Book Book;

	public class Book {
		public String Name, Title, FolderName;
		public List<String> Lore, PageTextFiles;
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
		reloadFileConfiguration(); // TODO これがなぜか必要。Why!?
		SendLog.debug("loadField Method!");
		GameLength = new Time(getFileConfiguration().getInt("GameLength"));

		prison_loc = getLocation();

		// Players = new HashMap<>();
		// testFields();
		// Playersがnull→Players.getClass().getName() x
	}

	public Location getLocation() {
		String path = "Prison.Location";
		String world_name = getFileConfiguration().getString(path + ".World");
		World world = null;
		if (world_name == null || (world = Bukkit.getServer().getWorld(world_name)) == null) {
			SendLog.error("Can't find: " + path + ".World");
			world = Bukkit.getWorlds().get(0);
		}
		double x = getFileConfiguration().getDouble(path + ".X");
		double y = getFileConfiguration().getDouble(path + ".Y");
		double z = getFileConfiguration().getDouble(path + ".Z");
		return new Location(world, x, y, z);
	}

	public class Time {
		public int minute = 0;

		public Time(int minute) {
			this.minute = minute;
		}

		public int getAsSecond() {
			return minute * 60;
		}
	}

	private void testFields() {
		SendLog.debug("LOADING...");
		SendLog.debug(file_name);
		SendLog.debug(GameName);
		SendLog.debug(GameLength.toString());
		SendLog.debug(Book.Name);
		SendLog.debug(Book.Title);
		Book.Lore.forEach(lore_text -> SendLog.debug(lore_text));
		Book.PageTextFiles.forEach(page_text -> SendLog.debug(page_text));
		// Players.forEach((player, teamname) -> SendLog.debug(player + " , " + teamname));
	}

	@Override
	public void saveField() {
		saveLocaton();
	}

	private void saveLocaton() {
		if (prison_loc == null) return;
		String path = "Prison.Location";
		getFileConfiguration().set(path + ".World", prison_loc.getWorld().getName());
		getFileConfiguration().set(path + ".X", prison_loc.getX());
		getFileConfiguration().set(path + ".Y", prison_loc.getY());
		getFileConfiguration().set(path + ".Z", prison_loc.getZ());
	}
}
