package com.github.smk7758.TosoGame_by_smk7758;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.github.smk7758.TosoGame_by_smk7758.Util.SendLog;

public class TeamManager {
	private Main main = null;
	private Scoreboard board = null;

	public TeamManager(Main main) {
		board =	main.getBoard();
		for (TeamName name : TeamName.values()) {
			// initialize teams
			Team team = board.registerNewTeam(name.toString());
			team.setDisplayName(name.displayname);
			team.setPrefix(name.prefix);
		}
	}

	public enum TeamName {
		Hunter("Hunter", "&C"), Runner("Runner", "&A"), OtherPlayer("OtherPlayer", "&F");

		public String displayname, prefix;

		private TeamName(String name, String prefix) {
			this.displayname = name;
			this.prefix = prefix;
		}
	}

	public Scoreboard getBoard() {
		return board;
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
		getTeamPlayers(name).forEach(player -> SendLog.send(text, player));
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
