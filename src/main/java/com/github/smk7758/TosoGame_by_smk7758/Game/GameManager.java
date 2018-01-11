package com.github.smk7758.TosoGame_by_smk7758.Game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.smk7758.TosoGame_by_smk7758.Book;
import com.github.smk7758.TosoGame_by_smk7758.Main;
import com.github.smk7758.TosoGame_by_smk7758.Game.ScorebordManager.TeamName;

public class GameManager extends GameManagerParents {
	private Main main = null;
	private boolean is_game_starting = false;
	private ScorebordManager team_manager = null;
	private Sidebar sidebar = null;
	public Location prison_loc = null;
	public int game_length = 20;
	public ItemStack itemstack_book = null;

	public GameManager(Main main) {
		this.main = main;
		team_manager = new ScorebordManager(main);
	}

	@Override
	public void start() {
		switchIsGameStarting();
		runTaskLater(main, game_length * 20);
		sidebar = new Sidebar(main);
		Bukkit.getOnlinePlayers().forEach(player -> player.setScoreboard(team_manager.getBoard()));
		team_manager.sendTeamPlayers(TeamName.Hunter, "TosoGameStart!");
		team_manager.sendTeamPlayers(TeamName.Runner, "TosoGameStart!");
		team_manager.getTeamPlayers(TeamName.Runner).forEach(player -> giveBook(player));
	}

	@Override
	public void finish() {
		team_manager.sendTeamPlayers(TeamName.Hunter, "TosoGame Finished!");
		team_manager.sendTeamPlayers(TeamName.Runner, "TosoGame Finished!");
		team_manager.clearTeam();
		switchIsGameStarting();
	}

	public boolean getIsGameStarting() {
		return is_game_starting;
	}

	private void switchIsGameStarting() {
		is_game_starting = !is_game_starting;
	}

	public ScorebordManager getTeamManager() {
		return team_manager;
	}

	public void giveBook(Player player) {
		List<String> lore = new ArrayList<>();
		lore.add("test");
		player.getInventory().addItem(Book.createBook("TosoGame", "TosoGame", lore, "test1", "test2"));
	}
}
