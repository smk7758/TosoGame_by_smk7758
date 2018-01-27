package com.github.smk7758.TosoGame_by_smk7758;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.smk7758.TosoGame_by_smk7758.Game.ScorebordTeam.TeamName;
import com.github.smk7758.TosoGame_by_smk7758.Util.SendLog;

public class CommandExecuter implements CommandExecutor {
	public Main main = null;

	public CommandExecuter(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("TosoGame")) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("set")) {
					if (args.length > 1) {
						if (args[1].equalsIgnoreCase("hunter")) {
							setTeam(TeamName.Hunter, args[2], sender);
						} else if (args[1].equalsIgnoreCase("runner")) {
							setTeam(TeamName.Runner, args[2], sender);
						} else if (args[1].equalsIgnoreCase("otherplayer")) {
							setTeam(TeamName.OtherPlayer, args[2], sender);
						} else if (args[1].equalsIgnoreCase("prison")) {
							if (sender instanceof Player) {
								Location loc = ((Player) sender).getLocation();
								main.getGameManager().prison_loc = loc;
								SendLog.send("Prison has been set to: x: " + loc.getX() + " y: " + loc.getY() + " z: "
										+ loc.getZ(), sender);
							} else {
								SendLog.error("This command must use from player.", sender);
								return false;
							}
						}
					}
				} else if (args[0].equalsIgnoreCase("remove")) {
					if (args.length > 1) {
						if (args[1].equalsIgnoreCase("hunter")) {
							removeTeam(TeamName.Hunter, args[2], sender);
						} else if (args[1].equalsIgnoreCase("runner")) {
							removeTeam(TeamName.Runner, args[2], sender);
						} else if (args[1].equalsIgnoreCase("otherplayer")) {
							removeTeam(TeamName.OtherPlayer, args[2], sender);
						}
					}
				} else if (args[0].equalsIgnoreCase("show")) {
					if (args.length > 1) {
						if (args[1].equalsIgnoreCase("hunter")) {
							showTeam(TeamName.Hunter, sender);
						} else if (args[1].equalsIgnoreCase("runner")) {
							showTeam(TeamName.Runner, sender);
						} else if (args[1].equalsIgnoreCase("otherplayer")) {
							showTeam(TeamName.OtherPlayer, sender);
						}
					} else {
						for (TeamName name : TeamName.values()) {
							showTeam(name, sender);
						}
					}
				} else if (args[0].equalsIgnoreCase("start")) {
					main.startGame();
					SendLog.send("Game has been started.", sender);
				} else if (args[0].equalsIgnoreCase("stop")) {
					main.getGameManager().stop();
				} else if (args[0].equalsIgnoreCase("out")) {
					Player player_out = Bukkit.getPlayer(args[1]);
					main.getGameManager().getTeamManager().removeTeam(TeamName.Runner, player_out);
					SendLog.send("You have been out from the TosoGame.", player_out);
					main.getGameManager().getTeamManager().sendTeamPlayers(TeamName.Runner,
							args[1] + " has been out from the TosoGame.");
					main.getGameManager().getTeamManager().sendTeamPlayers(TeamName.Hunter,
							args[1] + " has been out from the TosoGame.");
					// これを条件にするのあり。
				} else if (args[0].equalsIgnoreCase("help")) {
					showCommandList(sender);
				}
			} else {
				SendLog.error("Please write arguments.", sender);
				showCommandList(sender);
			}
			return true;
		}
		return false;
	}

	public void setTeam(TeamName name, String player_name, CommandSender sender) {
		main.getGameManager().getTeamManager().setTeam(name, Bukkit.getPlayer(player_name));
		SendLog.send(player_name + " has been added to Team: " + name.toString(), sender);
	}

	public void removeTeam(TeamName name, String player_name, CommandSender sender) {
		main.getGameManager().getTeamManager().removeTeam(name, Bukkit.getPlayer(player_name));
		SendLog.send(player_name + " has been removed from Team: " + name.toString(), sender);
	}

	public void showTeam(TeamName name, CommandSender sender) {
		SendLog.send("Team: " + name, sender);
		main.getGameManager().getTeamManager().getTeamPlayers(name)
				.forEach(player -> SendLog.send(player.getName(), sender));
	}

	private void showCommandList(CommandSender sender) {
		SendLog.send("<TosoGame Command List>", sender);
		SendLog.send("TosoGame (help)", sender);
		SendLog.send("-- show you command list.", sender);
		SendLog.send("TosoGame set prison", sender);
		SendLog.send("-- set prison.", sender);
		SendLog.send("TosoGame set <hunter, runner, otherplayer> <player name>", sender);
		SendLog.send("-- set player to each team.", sender);
		SendLog.send("TosoGame remove <hunter, runner, otherplayer> <player name>", sender);
		SendLog.send("-- remove player from each team.", sender);
		SendLog.send("TosoGame show <hunter, runner, otherplayer> (<player name>)", sender);
		SendLog.send("-- show which player is in the team.", sender);
		SendLog.send("TosoGame start", sender);
		SendLog.send("-- start TosoGame!", sender);
		SendLog.send("TosoGame stop", sender);
		SendLog.send("-- stop TosoGame", sender);
	}
}
