package com.github.smk7758.TosoGame_by_smk7758.Files.DataFiles;

import org.bukkit.plugin.Plugin;

import com.github.smk7758.TosoGame_by_smk7758.Files.YamlFile;

public class ConfigFile extends YamlFile {
	private final String file_name = "config.yml"; // TODO final じゃないといけない！！
	boolean DebugMode = false;
	public Hunter Hunter;
	public Runner Runner;
	public OtherPlayer OtherPlayer;

	public class Hunter {
		public String Prefix;
	}

	public class Runner {
		public String Prefix;
	}

	public class OtherPlayer {
		public String Prefix;
	}

	public ConfigFile(Plugin plugin) {
		super(plugin);
	}

	@Override
	public String getFileName() {
		return file_name;
	}

	@Override
	public void loadField() {
	}

	@Override
	public void saveField() {
	}
}
