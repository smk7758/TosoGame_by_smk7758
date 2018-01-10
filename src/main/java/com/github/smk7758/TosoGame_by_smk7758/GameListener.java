package com.github.smk7758.TosoGame_by_smk7758;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.github.smk7758.TosoGame_by_smk7758.TeamManager.TeamName;
import com.github.smk7758.TosoGame_by_smk7758.Util.SendLog;

public class GameListener implements Listener {
	private Main main = null;

	public GameListener(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerAttackPlayer(EntityDamageByEntityEvent event) {
		if (!main.getGameManager().getIsGameStarting()) return;
		Entity attacker_ = event.getDamager();
		Entity damager_ = event.getEntity();
		if (!(attacker_ instanceof Player && damager_ instanceof Player)) return;
		Player attacker = (Player) attacker_;
		Player damager = (Player) damager_;
		if (!(main.getTeamManager().isTeam(TeamName.Hunter, attacker)
				&& main.getTeamManager().isTeam(TeamName.Runner, damager))) return;
		// send mail
		main.getTeamManager().getTeamPlayers(TeamName.Runner)
				.forEach(runner -> SendLog.send(damager.getName() + " has been cought.", runner));
		// wait
		// tp
		damager.teleport(main.getGameManager().getPrison().loc);
	}
}
