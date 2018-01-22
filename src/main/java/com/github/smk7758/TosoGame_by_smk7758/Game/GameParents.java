package com.github.smk7758.TosoGame_by_smk7758.Game;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class GameParents extends BukkitRunnable {

	@Override
	public void run() {
		finish();
	}

	public abstract void finish();

	public abstract void start();

	public abstract void loop();

	public abstract void stop();
}
