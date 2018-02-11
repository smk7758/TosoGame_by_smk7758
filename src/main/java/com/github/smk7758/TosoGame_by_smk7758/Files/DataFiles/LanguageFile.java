package com.github.smk7758.TosoGame_by_smk7758.Files.DataFiles;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.github.smk7758.TosoGame_by_smk7758.Files.YamlFile;
import com.github.smk7758.TosoGame_by_smk7758.Util.Utilities;

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

	public String convertText(String text, CommandSender sender, int wait_time) {
		return Utilities.convertText(text, sender).replaceAll("%wait_time%", String.valueOf(wait_time));
	}
}
