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

		// SendLog.debug("GameLength: " + getFileConfiguration().getString("GameLength"));
		// String[] game_length_splitted = getFileConfiguration().getString("GameLength").split(",");
		// GameLength = LocalTime.of(Integer.parseInt(game_length_splitted[0]), Integer.parseInt(game_length_splitted[1]));

		prison_loc = getLocation();

		// Players = new HashMap<>();
		// for (String key : getFileConfiguration().getConfigurationSection("Players").getKeys(false)) {
		// if (key != null) {
		// String value = getFileConfiguration().getString(Players.getClass().getName() + "." + key);
		// Players.put(key, TeamName.valueOf(value));
		// }
		// }
		testFields();
		// Playersがnull→Players.getClass().getName() x
	}

	public Location getLocation() {
		String path = "Prison.Location";
		World pl_world = Bukkit.getServer().getWorld(getFileConfiguration().getString(path + ".World"));
		double pl_x = getFileConfiguration().getDouble(path + ".X");
		double pl_y = getFileConfiguration().getDouble(path + ".Y");
		double pl_z = getFileConfiguration().getDouble(path + ".Z");
		return new Location(pl_world, pl_x, pl_y, pl_z);

		// String world = player.getWorld().getName();
		// double x = player.getLocation().getX();
		// double y = player.getLocation().getY();
		// double z = player.getLocation().getZ();
		// float yaw = player.getLocation().getYaw();
		// float pitch = player.getLocation().getPitch();
		// plugin.FileIO.LocationIO(true, "config", type, world, x, y, z, yaw, pitch);
		// plugin.cLog.sendMessage(player, "Player:" + player.getName(), 3);
		// plugin.cLog.sendMessage(player, "World:" + world, 3);
		// plugin.cLog.sendMessage(player, "X:" + x, 3);
		// plugin.cLog.sendMessage(player, "Y:" + y, 3);
		// plugin.cLog.sendMessage(player, "Z:" + z, 3);
		// plugin.cLog.sendMessage(player, "Yaw:" + yaw, 3);
		// plugin.cLog.sendMessage(player, "Pitch:" + pitch, 3);
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
	}
}
