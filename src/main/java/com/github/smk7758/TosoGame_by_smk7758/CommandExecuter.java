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
			if (args.length <= 0) {
				SendLog.error("Please write arguments.", sender);
				showCommandList(sender);
				return false;
			}
			if (args[0].equalsIgnoreCase("set")) {
				if (args.length <= 1) {
					// TODO
					return false;
				}
				if (args[1].equalsIgnoreCase("hunter")) {
					setTeam(TeamName.Hunter, args[2], sender);
				} else if (args[1].equalsIgnoreCase("runner")) {
					setTeam(TeamName.Runner, args[2], sender);
				} else if (args[1].equalsIgnoreCase("otherplayer")) {
					setTeam(TeamName.OtherPlayer, args[2], sender);
				} else if (args[1].equalsIgnoreCase("prison")) {
					if (sender instanceof Player) {
						Location loc = ((Player) sender).getLocation();
						main.getGameManager().gamefile.prison_loc = loc;
						SendLog.send("Prison has been set to: "
								+ "x: " + loc.getX() + " y: " + loc.getY() + " z: " + loc.getZ(), sender);
					} else {
						SendLog.error("This command must use from player.", sender);
						return false;
					}
				}
			} else if (args[0].equalsIgnoreCase("remove")) {
				if (args.length <= 1) {
					// TODO
					return false;
				}
				if (args[1].equalsIgnoreCase("hunter")) {
					removeTeam(TeamName.Hunter, args[2], sender);
				} else if (args[1].equalsIgnoreCase("runner")) {
					removeTeam(TeamName.Runner, args[2], sender);
				} else if (args[1].equalsIgnoreCase("otherplayer")) {
					removeTeam(TeamName.OtherPlayer, args[2], sender);
				}
			} else if (args[0].equalsIgnoreCase("show")) {
				if (args.length <= 1) {
					for (TeamName name : TeamName.values()) {
						showTeam(name, sender);
					}
					return false;
				}
				if (args[1].equalsIgnoreCase("hunter")) {
					showTeam(TeamName.Hunter, sender);
				} else if (args[1].equalsIgnoreCase("runner")) {
					showTeam(TeamName.Runner, sender);
				} else if (args[1].equalsIgnoreCase("otherplayer")) {
					showTeam(TeamName.OtherPlayer, sender);
				}
			} else if (args[0].equalsIgnoreCase("start")) {
				if (main.getGameManager().start()) {
					SendLog.send("Game has been started.", sender);
				} else {
					SendLog.error("Can't start the game.", sender);
				}
			} else if (args[0].equalsIgnoreCase("stop")) {
				if (main.getGameManager().stop()) {
					SendLog.send("Stop command has been success.", sender);
				} else {
					SendLog.error("Can't stop the game.", sender);
				}
			} else if (args[0].equalsIgnoreCase("out")) {
				Player player_out = null;
				if (args.length <= 1) {
					if (sender instanceof Player) {
						player_out = (Player) sender;
					} else {
						return false;
					}
				} else {
					player_out = Bukkit.getPlayer(args[1]);
				}
				main.getGameManager().out(player_out);
			} else if (args[0].equalsIgnoreCase("caught")) {
				Player player_out = null;
				if (args.length <= 1) {
					if (sender instanceof Player) {
						player_out = (Player) sender;
					} else {
						return false;
					}
				} else {
					player_out = Bukkit.getPlayer(args[1]);
				}
				main.getGameManager().caught(player_out);
			} else if (args[0].equalsIgnoreCase("addpage")) {
				if (!main.getGameManager().addNextPage()) SendLog.error("Can't add page to the book.", sender);
				else SendLog.send("The book has been added from the file.", sender);
			} else if (args[0].equalsIgnoreCase("save")) {
				main.getYamlFileManager().saveYamlFile(main.configfile);
				main.getYamlFileManager().saveYamlFile(main.gamefile);
				SendLog.send("Save has been compleated.", sender);
			} else if (args[0].equalsIgnoreCase("reload")) {
				main.getYamlFileManager().reloadYamlFile(main.configfile);
				main.getYamlFileManager().reloadYamlFile(main.gamefile);
				SendLog.send("Reload has been compleated.", sender);
			} else if (args[0].equalsIgnoreCase("help")) {
				showCommandList(sender);
			} else if (args[0].equalsIgnoreCase("test")) {
				SendLog.send("test!", sender);
			}
			return true;
		}
		return false;

	}

	public void setTeam(TeamName name, String player_name, CommandSender sender) {
		boolean success = main.getGameManager().getTeamManager().setTeam(name, player_name);
		if (success) SendLog.send(player_name + " has been added to Team: " + name.toString(), sender);
		else SendLog.error("Can't add " + player_name + " to Team: " + name.toString(), sender);
	}

	public void removeTeam(TeamName name, String player_name, CommandSender sender) {
		boolean success = main.getGameManager().getTeamManager().removeTeam(name, player_name);
		if (success) SendLog.send(player_name + " has been removed from Team: " + name.toString(), sender);
		else SendLog.error("Can't remove " + player_name + " to Team: " + name.toString(), sender);
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