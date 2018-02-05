package com.github.smk7758.TosoGame_by_smk7758.Files.DataFiles;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.smk7758.TosoGame_by_smk7758.Files.YamlFile;

public class LanguageFile extends YamlFile {
	private final String file_name = "language.yml";
	public String startToRunner, startToHunter, stop, finishByTimeToRunner, finishByTimeToHunter,
			finishByCaughtToRunner, finishByCaughtToHunter, catchRunnerToPlayer, catchRunnerWaitTeleportToPlayer,
			catchRunnerToOthers, outRunnerToOthers, outRunnerToPlayer,
			lessCommandArguments, mustSendFromPlayer, saveCommand, reloadCommand, startCommand, startCommandError,
			stopCommand, stopCommandError, setTeam, setTeamError, removeTeam, removeTeamError, addBook, addBookError,
			startCheckNotSetPrison, startCheckNoPlayers;

	public LanguageFile(Plugin plugin) {
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

	public String convertText(String text, CommandSender sender) {
		text = text.replaceAll("%Player%", sender.getName());
		if (sender instanceof Player) {
			Player player = (Player) sender;
			text = text.replaceAll("%World%", player.getWorld().getName());
		}
		text = ChatColor.translateAlternateColorCodes('&', text);
		return text;
	}

	/**
	 * @param text 置き換えが実行される文字列。
	 * @param sender 送信者
	 * @param replaces 交互に置き換えが実行される文字列と置き換えする文字列を指定。
	 * @return 置き換えられた文字列。
	 */
	public String convertText(String text, CommandSender sender, String... replaces) {
		text = convertText(text, sender);
		String rep = "";
		for (String replace : replaces) {
			if (rep == null || rep.isEmpty()) {
				rep = replace;
			} else {
				text.replaceAll(rep, replace);
				rep = "";
			}
		}
		return text;
	}

	public String convertText(String text, CommandSender sender, int wait_time) {
		return convertText(text, sender).replaceAll("%wait_time%", String.valueOf(wait_time));
	}
}
