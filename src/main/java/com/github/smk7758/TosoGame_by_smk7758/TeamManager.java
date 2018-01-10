package com.github.smk7758.TosoGame_by_smk7758;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamManager {
	public Scoreboard board = null;
	public Map<TeamName, String> team_colors = new HashMap<>();

	public TeamManager() {
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		for (TeamName name : TeamName.values()) {
			board.registerNewTeam(name.toString());
		}
		team_colors.put(TeamName.Hunter, "%C");
		team_colors.put(TeamName.Runner, "%A");
		team_colors.put(TeamName.OtherPlayer, "%F");
		team_colors.forEach((name, color) -> {
			// getTeam(name).setColor(ChatColor.getByChar(ChatColor.translateAlternateColorCodes('%', color)));
			getTeam(name).setPrefix(ChatColor.translateAlternateColorCodes('%', color));
		});
	}

	public enum TeamName {
		Hunter, Runner, OtherPlayer
	}

	public Team getTeam(TeamName name) {
		return board.getTeam(name.name());
	}

	public Set<Player> getTeamPlayers(TeamName name) {
		Set<Player> players = new HashSet<>();
		getTeam(name).getEntries().forEach(uuid -> players.add(Bukkit.getPlayer(UUID.fromString(uuid))));
		return players;
	}

	public void sendTeamPlayers(TeamName name, String text) {
		getTeamPlayers(TeamName.Hunter).forEach(player -> SendLog.send(text, player));
	}

	public boolean isTeam(TeamName name, Player player) {
		return getTeam(name).getEntries().contains(player.getUniqueId().toString());
	}

	public void setTeam(TeamName name, Player player) {
		getTeam(name).addEntry(player.getUniqueId().toString());
	}

	public void removeTeam(TeamName name, Player player) {
		getTeam(name).removeEntry(player.getUniqueId().toString());
	}

	public void removeTeamAll(TeamName name) {
		getTeam(name).getEntries().forEach(uuid -> getTeam(name).removeEntry(uuid));
	}

	public void clearTeam() {
		for (TeamName name : TeamName.values()) {
			removeTeamAll(name);
		}
	}

	// 値を保持する。
	// 値を指定(表示)する。
}
