package com.github.smk7758.TosoGame_by_smk7758;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.smk7758.TosoGame_by_smk7758.TeamManager.TeamName;

public class FinishGame extends BukkitRunnable {
	private Main main = null;

	public FinishGame(Main main) {
		this.main = main;
	}

	@Override
	public void run() {
		main.getTeamManager().sendTeamPlayers(TeamName.Hunter, "TosoGame Finished!");
		main.getTeamManager().sendTeamPlayers(TeamName.Runner, "TosoGame Finished!");
		main.getGameManager().sitchIsGameStarting();
		main.getTeamManager().clearTeam();
	}
}
