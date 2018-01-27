package com.github.smk7758.TosoGame_by_smk7758.Files.FileUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.bukkit.plugin.Plugin;

import com.github.smk7758.TosoGame_by_smk7758.Util.SendLog;

public class YamlFileManager {
	// private Plugin plugin; // TODO

	public YamlFileManager(Plugin plugin) {
		// this.plugin = plugin; // TODO a bit it using
	}

	public YamlFile reloadYamlFile(YamlFile file) {
		return loadFields(file, file, null);
	}

	/**
	 * YamlFileにfields
	 *
	 * @param file_object 保存するYamlFileのObject。
	 * @param parent 取得するFieldを持ったObject?
	 * @param parent_yaml_path YamlFileの保存する親のパス。親パスが無い時はnull。
	 */
	private YamlFile loadFields(YamlFile file_object, Object parent, String parent_yaml_path) {
		if (file_object == null || parent == null) throw new IllegalArgumentException(
				"FileObject: " + file_object + " | Parent: " + parent);
		// TODO

		boolean is_root_class = false; // YamlFileのObject=rootのclassのObjectであるか。
		String yaml_path_access = ""; // for文で更新され、実際のYAMLファイルに探しに行くパス。
		Object object = null; // for文で更新され、YamlFileのフィールドに代入されるObject。
		Field[] fields = parent.getClass().getFields(); // YamlFileのフィールド。

		// is root yaml file.
		if (parent_yaml_path == null || parent_yaml_path.isEmpty()) {
			is_root_class = true;
			parent_yaml_path = "";
		} else {
			parent_yaml_path += ".";
		}

		for (Field field : fields) {
			SendLog.debug(field.getName() + " | " + field.getType() + " | " + field.getGenericType());
			// アノテーション付きを弾く。
			if (field.isAnnotationPresent(YamlFileExceptField.class)) continue;
			// TODO: テスト必要。
			yaml_path_access = parent_yaml_path + field.getName();

			if (field.getType().equals(String.class)) {
				SendLog.debug("String: " + yaml_path_access);
				object = file_object.getFileConfiguration().getString(yaml_path_access);
				setField(parent, field, object);
			} else if (field.getType().equals(int.class)) {
				SendLog.debug("int: " + yaml_path_access);
				object = file_object.getFileConfiguration().getInt(yaml_path_access);
				setField(parent, field, object);
			} else if (field.getType().equals(double.class)) {
				SendLog.debug("double: " + yaml_path_access);
				object = file_object.getFileConfiguration().getDouble(yaml_path_access);
				setField(parent, field, object);
			} else if (field.getType().equals(List.class)) {
				if (field.getGenericType() instanceof ParameterizedType) {
					ParameterizedType ptype = (ParameterizedType) field.getGenericType();
					SendLog.debug("List<String>: " + yaml_path_access);
					SendLog.debug(ptype.getTypeName() + " | " + ptype.getRawType());
					for (Type ptype_arg : ptype.getActualTypeArguments()) {
						SendLog.debug("pType arg: " + ptype_arg.getTypeName());
						if (ptype_arg.equals(String.class)) {
							object = file_object.getFileConfiguration().getStringList(yaml_path_access);
							setField(parent, field, object);
						}
					}
				} else {
					SendLog.debug(field.getName() + " is not Parametreized List.");
				}
			} else {
				SendLog.debug(field.getName() + " is not value.");

				// 値型でなかったら、新しいインスタンスのオブジェクトをつくり、そのフィールドを再帰的にこのメソッドを用いて取得→代入。
				Object field_object = null;
				try {
					field_object = field.getType().getConstructor(file_object.getClass()).newInstance(file_object);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				// 再帰的呼び出し
				loadFields(file_object, field_object, yaml_path_access);

				// フィールドを代入されたオブジェクトを代入。
				setField(file_object, field, field_object);
				SendLog.debug(field.getName() + " has been set.");
			}
		}
		if (is_root_class) file_object.loadField(); // TODO: 正しい動作をするにはどうすればよいか。
		return file_object;
	}

	private void setField(Object dest_class_object, Field field, Object set_object) {
		if (dest_class_object == null || field == null) throw new IllegalArgumentException("agrument is null.");
		try {
			field.set(dest_class_object, set_object);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void saveYamlFile(YamlFile file) {
		saveFields(file, file, null);
	}

	private void saveFields(YamlFile file_object, Object parent, String parent_yaml_path) {
		boolean is_root_class = false;
		String yaml_path_access;
		Field[] fields = parent.getClass().getFields();
		if (parent_yaml_path == null || parent_yaml_path.isEmpty()) is_root_class = true;
		else parent_yaml_path += ".";

		for (Field field : fields) {
			if (field.isAnnotationPresent(YamlFileExceptField.class)) continue;

			yaml_path_access = parent_yaml_path + field.getName();
			if (isFieldValueType(field)) {
				file_object.getFileConfiguration().set(yaml_path_access, field);
			} else {
				saveFields(file_object, field, yaml_path_access);
			}
		}
		if (is_root_class) file_object.saveField();
	}

	private boolean isFieldValueType(Field field) {
		if (field.getType().equals(String.class)
				|| field.getType().equals(List.class) && field.getGenericType().equals(String.class)
				|| field.getType().equals(int.class)
				|| field.getType().equals(double.class)
				|| field.getType().equals(float.class)
				|| field.getType().equals(boolean.class)) return true;
		else return false;
	}

	// public void saveDefaultYamlFile(YamlFile file, boolean replace) {
	// plugin.saveResource(file.getFileName(), replace);
	// }
}