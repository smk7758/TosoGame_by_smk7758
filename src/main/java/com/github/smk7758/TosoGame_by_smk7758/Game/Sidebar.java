package com.github.smk7758.TosoGame_by_smk7758.Game;

import java.io.Closeable;
import java.util.EnumMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.github.smk7758.TosoGame_by_smk7758.Main;
import com.github.smk7758.TosoGame_by_smk7758.Util.SendLog;

public class Sidebar implements Closeable {
	private Scoreboard scoreboard = null;
	private Objective objective = null;
	private String objective_name = "TosoGame";
	private String dispray_name = "TosoGame";
	private Map<SidebarContents, Integer> lines = new EnumMap<>(SidebarContents.class);;

	public Sidebar(Main main, int game_length, int runner_count, int hunter_count) {
		// TODO: Game複数実行時は第一引数を変更。
		scoreboard = main.getScoreBoard();
		objective = scoreboard.registerNewObjective(objective_name, "dummy");
		objective.setDisplayName(dispray_name);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		update(game_length, runner_count, 0, hunter_count);
		Bukkit.getOnlinePlayers().forEach(player -> player.setScoreboard(scoreboard));
		SendLog.debug("Setted scoreboard!");
	}

	public enum SidebarContents {
		Time(0), Runner(-1), Prisoner(-2), Hunter(-3);

		public int line;

		private SidebarContents(int line) {
			this.line = line;
		}
	}

	public void update(int time, int runner_count, int prisoner_count, int hunter_count) {
		lines.put(SidebarContents.Time, time);
		lines.put(SidebarContents.Runner, runner_count);
		lines.put(SidebarContents.Prisoner, prisoner_count);
		lines.put(SidebarContents.Hunter, hunter_count);

		removeLines();

		// set new lines.
		for (SidebarContents content : SidebarContents.values()) {
			objective.getScore(content.toString() + ": " + lines.get(content)).setScore(content.line);
		}
	}

	public void removeLines() {
		objective.getScoreboard().getEntries().forEach(entry -> objective.getScoreboard().resetScores(entry));
	}

	@Override
	public void close() {
		scoreboard.clearSlot(DisplaySlot.SIDEBAR);
		objective.unregister();
	}
}
