package com.github.smk7758.TosoGame_by_smk7758.Util;

import org.bukkit.configuration.file.FileConfiguration;

public abstract class _FileConfigurationEx extends FileConfiguration {
	public String getString(_SettingKeys key) {
		return super.getString(key.toString());
	}

	public void set(_SettingKeys key) {
		super.set(key.toString(), key.getText());
	}
}