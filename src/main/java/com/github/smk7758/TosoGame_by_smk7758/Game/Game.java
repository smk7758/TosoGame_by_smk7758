package com.github.smk7758.TosoGame_by_smk7758.Game;

import java.util.ArrayList;
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
import com.github.smk7758.TosoGame_by_smk7758.Util.Book;
import com.github.smk7758.TosoGame_by_smk7758.Util.SendLog;

public class Game implements GameParents {
	private Main main = null;
	private boolean is_game_starting = false;

	public int game_length = 20;
	public int loop_interval = 5;
	public ItemStack itemstack_book = null;
	public Location prison_loc = null;
	public List<Location> hunter_loc = null;
	private BukkitTask loop = null;

	private ScorebordTeam team_manager = null;
	private Sidebar sidebar = null;
	public GameFile game_file = null;

	public Game(Main main) {
		this.main = main;
		team_manager = new ScorebordTeam(main);
		this.game_file = main.game_file;
	}

	@Override
	public void start() {
		if (prison_loc == null) {
			// TODO
			SendLog.warn("Please set prison loc!");
			return;
		}
		switchIsGameStarting();
		team_manager.sendTeamPlayers(TeamName.Hunter, "TosoGameStart!");
		team_manager.sendTeamPlayers(TeamName.Runner, "TosoGameStart!");
		team_manager.getTeamPlayers(TeamName.Runner).forEach(player -> player.getInventory().clear());
		team_manager.getTeamPlayers(TeamName.Runner).forEach(player -> giveBook(player));
		sidebar = new Sidebar(main, game_length, team_manager.getAllRunner().size());
		removeAllPotionEffects(TeamName.Hunter);
		loop();
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
				// sidebar.setSidebar(SidebarContents.Time, value);
			}
		}.runTaskTimer(main, 0, loop_interval * 20);
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
		}.runTaskLater(main, game_length * 20);
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

	public boolean getIsGameStarting() {
		return is_game_starting;
	}

	private void switchIsGameStarting() {
		is_game_starting = !is_game_starting;
	}

	public ScorebordTeam getTeamManager() {
		return team_manager;
	}

	public void giveBook(Player player) {
		List<String> lore = new ArrayList<>();
		lore.add("test");
		player.getInventory().addItem(
				Book.createBook(game_file.Book.Name, game_file.Book.Title, game_file.Book.Lore, game_file.Book.Pages));
	}
}
