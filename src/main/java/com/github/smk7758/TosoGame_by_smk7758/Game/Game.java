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
import com.github.smk7758.TosoGame_by_smk7758.Game.Sidebar.SidebarContents;
import com.github.smk7758.TosoGame_by_smk7758.Util.SendLog;

public class Game implements GameParents {
	private Main main = null;
	private boolean is_game_starting = false;

	private int time_count = 1;
	public ItemStack itemstack_book = null;
	public List<Location> hunter_loc = null;
	private BukkitTask loop = null;

	private BookManager bfm = null;
	private ScorebordTeam team_manager = null;
	private Sidebar sidebar = null;
	public GameFile gamefile = null;

	public Game(Main main) {
		this.main = main;
		team_manager = new ScorebordTeam(main);
		this.gamefile = main.gamefile; // TODO
	}

	@Override
	public void start() {
		if (gamefile.prison_loc == null) {
			// TODO
			SendLog.warn("Please set prison loc!");
			return;
		}
		bfm = new BookManager(main, gamefile);
		time_count = gamefile.GameLength.getAsSecond();

		switchIsGameStarting();

		// clear inv
		team_manager.getTeamPlayers(TeamName.Runner).forEach(player -> player.getInventory().clear());
		// give book
		team_manager.getTeamPlayers(TeamName.Runner).forEach(player -> bfm.giveBook(player));
		sidebar = new Sidebar(main, gamefile.GameLength.getAsSecond(), team_manager.getAllRunner().size());

		removeAllPotionEffects(TeamName.Hunter);
		loop();
		finish();

		// TODO timer

		// send start!
		team_manager.sendTeamPlayers(TeamName.Hunter, "TosoGameStart!");
		team_manager.sendTeamPlayers(TeamName.Runner, "TosoGameStart!");
	}

	@Override
	public void loop() {
		loop = new BukkitRunnable() {
			PotionEffect potion_effect = new PotionEffect(PotionEffectType.SPEED, Short.MAX_VALUE, 1);

			@Override
			public void run() {
				team_manager.getTeamPlayers(TeamName.Hunter)
						.forEach(player -> player.addPotionEffect(potion_effect));
				sidebar.setSidebar(SidebarContents.Runner, team_manager.getTeamPlayers(TeamName.Runner).size());
				sidebar.setSidebar(SidebarContents.Prisoner,
						team_manager.getTeamPlayers(TeamName.RunnerPrisoner).size());
				if (0 == team_manager.getTeamPlayers(TeamName.Runner).size()) {
					finish();
				}
				time_count -= 1;
				sidebar.setSidebar(SidebarContents.Time, time_count);
			}
		}.runTaskTimer(main, 0, 1 * 20);
	}

	@Override
	public void finish() {
		new BukkitRunnable() {
			@Override
			public void run() {
				team_manager.sendTeamPlayers(TeamName.Hunter, "TosoGame Finished!");
				team_manager.sendTeamPlayers(TeamName.Runner, "TosoGame Finished!");
				close();
			}
		}.runTaskLater(main, gamefile.GameLength.getAsSecond() * 20);
	}

	@Override
	public void stop() {
		team_manager.sendTeamPlayers(TeamName.Hunter, "TosoGame stop!");
		team_manager.sendTeamPlayers(TeamName.Runner, "TosoGame stop!");
		close();
	}

	private void close() {
		team_manager.getTeamPlayers(TeamName.Runner).forEach(player -> player.getInventory().clear());
		team_manager.clearTeam();
		removeAllPotionEffects(TeamName.Hunter);
		loop.cancel();
		sidebar.close();
		switchIsGameStarting();
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

	public boolean getIsGameStarting() {
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
}