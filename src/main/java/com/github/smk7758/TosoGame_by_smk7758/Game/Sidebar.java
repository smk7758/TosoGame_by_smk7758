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
	private Main main = null;

	public Sidebar(Main main, int time, int runner) {
		this.main = main;
		scoreboard = main.getScoreBoard();
		objective = scoreboard.registerNewObjective("TosoGame", "dummy");
		// TODO: Game複数実行時は第一引数を変更。
		objective.setDisplayName("TosoGame");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		Bukkit.getOnlinePlayers().forEach(player -> player.setScoreboard(scoreboard));
		SendLog.debug("set board!");
		setSidebar(SidebarContents.Time, time);
		setSidebar(SidebarContents.Runner, runner);
	}

	public enum SidebarContents {
		Time, Runner;
	}

	public void setSidebar(SidebarContents content, int score) {
		objective.getScore(content.toString()).setScore(score);
	}

	@Override
	public void close() {
		objective.unregister();
	}
}
