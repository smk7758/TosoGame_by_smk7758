package com.github.smk7758.TosoGame_by_smk7758;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.smk7758.TosoGame_by_smk7758.TeamManager.TeamName;
import com.github.smk7758.TosoGame_by_smk7758.Util.SendLog;

public class CommandExecuter implements CommandExecutor {
	public Main main = null;

	public CommandExecuter(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("TosoGame")) {
			if (args[0].equalsIgnoreCase("set")) {
				if (args[1].equalsIgnoreCase("hunter")) {
					setTeam(TeamName.Hunter, args[2], sender);
				} else if (args[1].equalsIgnoreCase("runner")) {
					setTeam(TeamName.Runner, args[2], sender);
				} else if (args[1].equalsIgnoreCase("otherplayer")) {
					setTeam(TeamName.OtherPlayer, args[2], sender);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("remove")) {
				if (args[1].equalsIgnoreCase("hunter")) {
					removeTeam(TeamName.Hunter, args[2], sender);
				} else if (args[1].equalsIgnoreCase("runner")) {
					removeTeam(TeamName.Runner, args[2], sender);
				} else if (args[1].equalsIgnoreCase("otherplayer")) {
					removeTeam(TeamName.OtherPlayer, args[2], sender);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("show")) {
				if (args[1].equalsIgnoreCase("hunter")) {
					showTeam(TeamName.Hunter, sender);
				} else if (args[1].equalsIgnoreCase("runner")) {
					showTeam(TeamName.Runner, sender);
				} else if (args[1].equalsIgnoreCase("otherplayer")) {
					showTeam(TeamName.OtherPlayer, sender);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("start")) {
				main.startGame();
				SendLog.send("Game has been started.", sender);
				return true;
			} else if (args[0].equalsIgnoreCase("stop")) {

			} else if (args[0].equalsIgnoreCase("test")) {
				main.getTeamManager().sendTeamPlayers(TeamName.valueOf(args[1]), args[2]);
				SendLog.debug("AA", sender);
				return true;
			}
		}
		return false;
	}

	public void setTeam(TeamName name, String player_name, CommandSender sender) {
		main.getTeamManager().setTeam(name, Bukkit.getPlayer(player_name));
		SendLog.send(player_name + " has been added to Team: " + name.toString(), sender);
	}

	public void removeTeam(TeamName name, String player_name, CommandSender sender) {
		main.getTeamManager().removeTeam(name, Bukkit.getPlayer(player_name));
		SendLog.send(player_name + " has been removed from Team: " + name.toString(), sender);
	}

	public void showTeam(TeamName name, CommandSender sender) {
		SendLog.send("Team: " + name, sender);
		main.getTeamManager().getTeamPlayers(name).forEach(player -> SendLog.send(player.getName(), sender));
	}
}
