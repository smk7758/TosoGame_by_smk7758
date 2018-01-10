package com.github.smk7758.TosoGame_by_smk7758;

import org.bukkit.scheduler.BukkitRunnable;

public class FinishGame extends BukkitRunnable {
	private Main main = null;

	public FinishGame(Main main) {
		this.main = main;
	}

	@Override
	public void run() {
		main.getGameManager().sitchIsGameStarting();
		main.getTeamManager().clearTeam();
	}
}
