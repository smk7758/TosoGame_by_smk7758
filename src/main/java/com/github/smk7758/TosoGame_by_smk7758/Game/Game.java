package com.github.smk7758.TosoGame_by_smk7758.Game;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.github.smk7758.TosoGame_by_smk7758.Main;
import com.github.smk7758.TosoGame_by_smk7758.Files.DataFiles.GameFile;
import com.github.smk7758.TosoGame_by_smk7758.Game.ScorebordTeam.TeamName;
import com.github.smk7758.TosoGame_by_smk7758.Util.SendLog;

public class Game {
	private Main main = null;
	private boolean is_game_starting = false;

	private int time_count = 1;
	private int wait_time = 10;
	public ItemStack itemstack_book = null;
	public List<Location> hunter_loc = null;
	private BukkitTask loop = null, finish = null;

	private BookManager bfm = null;
	private ScorebordTeam team_manager = null;
	private Sidebar sidebar = null;
	public GameFile gamefile = null;

	public Game(Main main) {
		this.main = main;
		team_manager = new ScorebordTeam(main);
		this.gamefile = main.gamefile; // TODO
	}

	public boolean start() {
		if (!canStart()) return false;

		bfm = new BookManager(main, gamefile);
		time_count = gamefile.GameLength.getAsSecond();
		// wait_time = gamefile.WaitTeleportTime
		sidebar = new Sidebar(main, time_count, team_manager.getAllRunner().size(),
				team_manager.getTeamPlayers(TeamName.Hunter).size());

		switchIsGameStarting();

		clearInventoryies(TeamName.Runner);
		clearInventoryies(TeamName.Hunter);
		removeAllPotionEffects(TeamName.Hunter);

		giveBooks(TeamName.Runner);

		loop();
		finish();

		// send start!
		team_manager.sendTeamPlayers(TeamName.Hunter, main.languagefile.startToHunter);
		team_manager.sendTeamPlayers(TeamName.Runner, main.languagefile.startToRunner);
		return true;
	}

	public void loop() {
		loop = new BukkitRunnable() {
			PotionEffect potion_effect = new PotionEffect(PotionEffectType.SPEED, Short.MAX_VALUE, 0);

			@Override
			public void run() {
				if (0 == team_manager.getTeamPlayers(TeamName.Runner).size()) {
					finishByCaught();
				} else {
					time_count -= 1;
					sidebar.update(time_count,
							team_manager.getTeamPlayers(TeamName.Runner).size(),
							team_manager.getTeamPlayers(TeamName.RunnerPrisoner).size(),
							team_manager.getTeamPlayers(TeamName.Hunter).size());
					team_manager.getTeamPlayers(TeamName.Hunter)
							.forEach(player -> player.addPotionEffect(potion_effect));
				}
			}
		}.runTaskTimer(main, 0, 1 * 20);
	}

	public void finish() {
		finish = new BukkitRunnable() {
			@Override
			public void run() {
				if (isGameStarting()) {
					team_manager.sendTeamPlayers(TeamName.Hunter, main.languagefile.finishByTimeToHunter);
					team_manager.sendTeamPlayers(TeamName.Runner, main.languagefile.finishByTimeToRunner);
					team_manager.sendTeamPlayers(TeamName.RunnerPrisoner, main.languagefile.finishByTimeToRunner);
					close();
				}
			}
		}.runTaskLater(main, gamefile.GameLength.getAsSecond() * 20);
	}

	public boolean finishByCaught() {
		// TODO: 直で出すのはなんか物足りない。
		if (!isGameStarting()) return false;
		team_manager.sendTeamPlayers(TeamName.Hunter, main.languagefile.finishByCaughtToHunter);
		team_manager.sendTeamPlayers(TeamName.Runner, main.languagefile.finishByCaughtToRunner);
		team_manager.sendTeamPlayers(TeamName.RunnerPrisoner, main.languagefile.finishByCaughtToRunner);
		close();
		return true;
	}

	public boolean stop() {
		if (!isGameStarting()) return false;
		team_manager.sendTeamPlayers(TeamName.Hunter, main.languagefile.stop);
		team_manager.sendTeamPlayers(TeamName.Runner, main.languagefile.stop);
		close();
		return true;
	}

	private boolean close() {
		if (!isGameStarting()) return false;
		clearInventoryies(TeamName.Runner);
		clearInventoryies(TeamName.RunnerPrisoner);
		removeAllPotionEffects(TeamName.Hunter);

		team_manager.clearTeam();
		loop.cancel();
		finish.cancel();
		sidebar.close();
		switchIsGameStarting();
		return true;
	}

	public void caught(Player player) {
		if (!isGameStarting()) return;
		SendLog.debug("Player: " + player.getName() + " has been caught.");
		// send to player
		SendLog.send(main.languagefile.convertText(main.languagefile.catchRunnerToPlayer, player), player);
		// send mail
		getTeamManager().sendTeamPlayers(TeamName.Hunter,
				main.languagefile.convertText(main.languagefile.catchRunnerToOthers, player));
		getTeamManager().sendTeamPlayers(TeamName.Runner,
				main.languagefile.convertText(main.languagefile.catchRunnerToOthers, player));
		getTeamManager().sendTeamPlayers(TeamName.RunnerPrisoner,
				main.languagefile.convertText(main.languagefile.catchRunnerToOthers, player));

		// change team
		getTeamManager().changeTeam(player, TeamName.RunnerPrisoner);

		// teleport
		SendLog.send(
				main.languagefile.convertText(main.languagefile.catchRunnerWaitTeleportToPlayer, player, wait_time),
				player);
		teleportDelay(player);
	}

	public void out(Player player) {
		if (!isGameStarting()) return;
		getTeamManager().changeTeam(player, TeamName.OtherPlayer);
		SendLog.debug("Player: " + player.getName() + " has been out.");
		SendLog.send(main.languagefile.convertText(main.languagefile.outRunnerToPlayer, player), player);

		getTeamManager().sendTeamPlayers(TeamName.Runner,
				main.languagefile.convertText(main.languagefile.outRunnerToOthers, player));
		getTeamManager().sendTeamPlayers(TeamName.Hunter,
				main.languagefile.convertText(main.languagefile.outRunnerToOthers, player));
	}

	private void teleportDelay(Player damager) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (isGameStarting()) {
					SendLog.debug("Player: " + damager.getName() + " has been teleported to the prison.");
					damager.teleport(main.getGameManager().gamefile.prison_loc);
				} else {
					SendLog.debug("The game has been already finished, so woun't tp.");
				}
			}
		}.runTaskLater(main, wait_time * 20);
	}

	private void giveBooks(TeamName name) {
		team_manager.getTeamPlayers(name).forEach(player -> bfm.giveBook(player));
	}

	private void clearInventoryies(TeamName name) {
		team_manager.getTeamPlayers(name).forEach(player -> player.getInventory().clear());
	}

	private boolean canStart() {
		boolean can_start = true;
		if (isGameStarting()) can_start = false;
		if (gamefile.prison_loc == null) {
			SendLog.error(main.languagefile.startCheckNotSetPrison);
			can_start = false;
		}
		if (0 == team_manager.getTeamPlayers(TeamName.Runner).size()
				|| 0 == team_manager.getTeamPlayers(TeamName.Hunter).size()) {
			SendLog.error(main.languagefile.startCheckNoPlayers);
			can_start = false;
		}
		return can_start;
	}

	private void removeAllPotionEffects(TeamName name) {
		// remove name's all potion effect
		getTeamManager().getTeamPlayers(name).forEach(player -> player.getActivePotionEffects()
				.forEach(potion_effect -> player.removePotionEffect(potion_effect.getType())));
	}

	public boolean addNextPage() {
		boolean is_successful = true;
		for (Player player : team_manager.getTeamPlayers(TeamName.Runner)) {
			if (!bfm.addNextPage(player)) is_successful = false;
		}
		return is_successful;
	}

	public boolean isGameStarting() {
		return is_game_starting;
	}

	private void switchIsGameStarting() {
		is_game_starting = !is_game_starting;
	}

	public ScorebordTeam getTeamManager() {
		return team_manager;
	}

	public BookManager getBookFileManager() {
		return bfm;
	}

	public Sidebar getSidebar() {
		return sidebar;
	}
}