package com.github.smk7758.TosoGame_by_smk7758.Game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.smk7758.TosoGame_by_smk7758.Main;
import com.github.smk7758.TosoGame_by_smk7758.Files.GameFile;
import com.github.smk7758.TosoGame_by_smk7758.Game.ScorebordTeam.TeamName;
import com.github.smk7758.TosoGame_by_smk7758.Util.Book;

public class Game extends GameParents {
	private Main main = null;
	private boolean is_game_starting = false;
	private ScorebordTeam team_manager = null;
	private Sidebar sidebar = null;
	public Location prison_loc = null;
	public List<Location> hunter_loc = null;
	public int game_length = 20;
	public ItemStack itemstack_book = null;
	public GameFile game_file = null;

	public Game(Main main) {
		this.main = main;
		team_manager = new ScorebordTeam(main);
	}

	@Override
	public void start() {
		switchIsGameStarting();
		super.runTaskLater(main, game_length * 20); // finishMethod will run later.
		team_manager.sendTeamPlayers(TeamName.Hunter, "TosoGameStart!");
		team_manager.sendTeamPlayers(TeamName.Runner, "TosoGameStart!");
		team_manager.getTeamPlayers(TeamName.Runner).forEach(player -> giveBook(player));
		sidebar = new Sidebar(main, game_length, team_manager.getAllRunner().size());
	}

	@Override
	public void loop() {
		// new BukkitRunnable() {
		// @Override
		// public void run() {
		// PotionEffect potion_effect = new PotionEffect(PotionEffectType.SPEED, 10, 1);
		// LocalDateTime time = LocalDateTime.now();
		// while (is_game_starting) {
		// if (LocalDateTime.now().isAfter(time)) {
		// main.getGameManager().getTeamManager().getTeamPlayers(TeamName.Hunter)
		// .forEach(player -> player.addPotionEffect(potion_effect));
		// time = time.plusSeconds(5);
		// } // TODO
		// }
		// }
		// }.runTask(main);
	}

	@Override
	public void finish() {
		team_manager.sendTeamPlayers(TeamName.Hunter, "TosoGame Finished!");
		team_manager.sendTeamPlayers(TeamName.Runner, "TosoGame Finished!");
		team_manager.clearTeam();
		switchIsGameStarting();
		sidebar.close();
	}

	@Override
	public void stop() {
		team_manager.sendTeamPlayers(TeamName.Hunter, "TosoGame stop!");
		team_manager.sendTeamPlayers(TeamName.Runner, "TosoGame stop!");
		switchIsGameStarting();
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
