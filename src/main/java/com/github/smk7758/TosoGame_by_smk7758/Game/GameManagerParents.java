package com.github.smk7758.TosoGame_by_smk7758.Game;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class GameManagerParents extends BukkitRunnable {

	@Override
	public void run() {
		finish();
	}

	public abstract void finish();

	public abstract void start();
}