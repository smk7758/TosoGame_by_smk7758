package com.github.smk7758.TosoGame_by_smk7758.Game;

import org.bukkit.scoreboard.Scoreboard;

import com.github.smk7758.TosoGame_by_smk7758.Main;

public class Sidebar {
	// private Main main = null;
	private Scoreboard board = null;

	public Sidebar(Main main) {
		// this.main = main;
		this.board = main.getBoard();
		// for (SidebarLine line : SidebarLine.values()) {
		// Objective line_o = board.registerNewObjective(line.toString(), "dummy");
		// line_o.setDisplayName(line.toString());
		// }
	}

	public enum SidebarLine {
		first(""), second(""), third(""), fourth("");

		public String value;

		private SidebarLine(String value) {
			this.value = value;
		}
	}
}
