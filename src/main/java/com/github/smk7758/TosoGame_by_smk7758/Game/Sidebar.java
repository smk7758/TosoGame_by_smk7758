package com.github.smk7758.TosoGame_by_smk7758.Game;

import java.io.Closeable;

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

	public Sidebar(Main main, int game_length, int runner) {
		// TODO: Game複数実行時は第一引数を変更。
		scoreboard = main.getScoreBoard();
		objective = scoreboard.registerNewObjective(objective_name, "dummy");
		objective.setDisplayName(dispray_name);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		setSidebar(SidebarContents.Time, game_length);
		setSidebar(SidebarContents.Runner, runner);
		setSidebar(SidebarContents.Prisoner, 0);
		Bukkit.getOnlinePlayers().forEach(player -> player.setScoreboard(scoreboard));
		SendLog.debug("Setted scoreboard!");
	}

	public enum SidebarContents {
		Time, Runner, Prisoner;
	}

	public void setSidebar(SidebarContents content, int value) {
		objective.getScore(content.toString()).setScore(value);
	}

	@Override
	public void close() {
		objective.unregister();
	}
}
