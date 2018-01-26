package com.github.smk7758.TosoGame_by_smk7758.Files;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.smk7758.TosoGame_by_smk7758.FileUtils._FileConfigurationEx;
import com.github.smk7758.TosoGame_by_smk7758.FileUtils._SettingKeys;

public class _LanguageFile {
	_FileConfigurationEx fc = null;

	public _LanguageFile(FileConfiguration fc) {
		this.fc = (_FileConfigurationEx) fc;
	}

	public enum LanguageFileKeys implements _SettingKeys {
		start(""), finish(""), stop(""), win_runner(""), win_hunter("");

		private String text;

		private LanguageFileKeys(String text) {
			this.setText(text);
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	public void reload() {
		for (LanguageFileKeys key : LanguageFileKeys.values()) {
			key.setText(fc.getString(key));
		}
	}

	public void save() {
		for (LanguageFileKeys key : LanguageFileKeys.values()) {
			fc.set(key);
		}
	}
}