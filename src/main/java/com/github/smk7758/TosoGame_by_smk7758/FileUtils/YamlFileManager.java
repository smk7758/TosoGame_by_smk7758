package com.github.smk7758.TosoGame_by_smk7758.FileUtils;

import java.awt.List;
import java.io.File;
import java.lang.reflect.Field;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class YamlFileManager {
	protected FileConfiguration config = null;
	protected File config_file = null;
	private final String file_name;
	private final Plugin plugin;
	private final YamlFile file;

	public YamlFileManager(Plugin plugin, YamlFile file) {
		this.plugin = plugin;
		this.file = file;
		this.file_name = file.getFileName();
		// config_file = new File(plugin.getDataFolder(), file_name);
	}

	public YamlFile reloadYamlFile() {
		loadFields(file, file.getClass().getFields());
		return file;
	}

	private void loadFields(YamlFile file_object, Field[] fields) {
		try {
			for (Field field : fields) {
				// TODO
				// if (field.isAnnotationPresent(annotationClass)) break;
				// yaml value として読み込まないもの。
				// yaml value ではあるが、loadFieldで読み込むものを各自記載。
				if (field.getType().equals(String.class)) {
					field.set(file_object, config.getString(field.getName()));
				} else if (field.getType().equals(List.class) && field.getGenericType().equals(String.class)) {
					field.set(file_object, config.getStringList(field.getName()));
				} else if (field.getType().equals(int.class)) {
					field.set(file_object, config.getInt(field.getName()));
				} else {
					loadFields(file_object, field.getClass().getFields());
				}
			}
			loadField();
		} catch (IllegalArgumentException | IllegalAccessException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Please override this and use for your own type field.
	 */
	public void loadField() {
	}

	/**
	 * @return This will maybe return YamlFile Object that has null fields.
	 */
	public YamlFile getYamlFile() {
		return file;
	}

	public void saveYamlFile() {
		saveFields(file);
	}

	private void saveFields(YamlFile file_object) {
		// TODO
		for (Field field : file_object.getClass().getFields()) {
			if (field.getType().equals(String.class)
					|| field.getType().equals(List.class) && field.getGenericType().equals(String.class)
					|| field.getType().equals(int.class)) {
				config.set(field.getName(), field);
			} else {
				// TODO
				// saveFields();
			}
		}
	}

	public void saveDefaultYamlFile() {
		plugin.saveResource(file_name, false);
	}
}
