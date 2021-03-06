package com.github.smk7758.TosoGame_by_smk7758.Game;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.github.smk7758.TosoGame_by_smk7758.Main;
import com.github.smk7758.TosoGame_by_smk7758.Game.ScorebordTeam.TeamName;

public class GameListener implements Listener {
	private Main main = null;

	public GameListener(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerAttackPlayer(EntityDamageByEntityEvent event) {
		if (!main.getGameManager().isGameStarting()) return;
		Entity attacker_ = event.getDamager();
		Entity damager_ = event.getEntity();
		if (!(attacker_ instanceof Player && damager_ instanceof Player)) return;
		Player attacker = (Player) attacker_;
		Player damager = (Player) damager_;
		if (!(main.getGameManager().getTeamManager().isTeam(TeamName.Hunter, attacker)
				&& main.getGameManager().getTeamManager().isTeam(TeamName.Runner, damager))) return;
		// --- finish check ---

		main.getGameManager().caught(damager);
	}

}
