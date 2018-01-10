package com.github.smk7758.TosoGame_by_smk7758;

import com.github.smk7758.TosoGame_by_smk7758.TeamManager.TeamName;

public class GameManager {
	// private Main main = null;
	private TeamManager team_manager = null;
	private Prison prison = new Prison();
	private int game_length = 20;
	private boolean is_game_starting = false;

	public GameManager(Main main) {
		// this.main = main;
		main.getTeamManager().sendTeamPlayers(TeamName.Hunter, "TosoGameStart!");
		main.getTeamManager().sendTeamPlayers(TeamName.Runner, "TosoGameStart!");
		new FinishGame(main).runTaskLater(main, game_length * 20);
		team_manager = new TeamManager();
	}

	public Prison getPrison() {
		return prison;
	}

	public boolean getIsGameStarting() {
		return is_game_starting;
	}

	public void sitchIsGameStarting() {
		is_game_starting = !is_game_starting;
	}

	public TeamManager getTeamManager() {
		return team_manager;
	}
}
