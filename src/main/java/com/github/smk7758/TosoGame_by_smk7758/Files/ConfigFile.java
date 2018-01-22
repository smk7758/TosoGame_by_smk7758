package com.github.smk7758.TosoGame_by_smk7758.Files;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigFile {
	private FileConfigurationEx fc = null;
	public String start, finish, stop, win_runner, win_hunter;

	public ConfigFile(FileConfiguration fc) {
		this.fc = (FileConfigurationEx) fc;
	}

	public enum ConfigFileKeys {
		book_name, book_lore_texts, pages;
	}

	// リファクタリングしたい！
	public void reload() {
		start = fc.getString(ConfigFileKeys.start);
		finish = fc.getString(ConfigFileKeys.finish);
		stop = fc.getString(ConfigFileKeys.stop);
		win_runner = fc.getString(ConfigFileKeys.win_runner);
		win_hunter = fc.getString(ConfigFileKeys.win_hunter);
	}

	public void save() {
		fc.set(ConfigFileKeys.start, start);
	}

	private abstract class FileConfigurationEx extends FileConfiguration {
		private String getString(ConfigFileKeys key) {
			return super.getString(key.toString());
		}

		private void set(ConfigFileKeys key, Object object) {
			super.set(key.toString(), object);
		}
	}
}
