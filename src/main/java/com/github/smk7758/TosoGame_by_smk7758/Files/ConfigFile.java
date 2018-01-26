package com.github.smk7758.TosoGame_by_smk7758.Files;

import org.bukkit.plugin.Plugin;

import com.github.smk7758.TosoGame_by_smk7758.FileUtils.YamlFile;

public class ConfigFile extends YamlFile {
	String file_name = "config.yml";
	boolean DebugMode = false;
	Hunter Hunter;
	Runner Runner;
	OtherPlayer OtherPlayer;

	public class Hunter {
		String Prefix;
	}

	public class Runner {
		String Prefix;
	}

	public class OtherPlayer {
		String Prefix;
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
