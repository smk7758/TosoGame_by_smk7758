package com.github.smk7758.TosoGame_by_smk7758.Game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.smk7758.TosoGame_by_smk7758.Files.DataFiles.GameFile;
import com.github.smk7758.TosoGame_by_smk7758.Util.BookAPI;

public class BookManager {
	private Path book_folder_path = null;
	private String folder_name = "Books";
	private GameFile gamefile = null;
	private String book_name = "book";
	private int curent_page_number = 0;

	/**
	 * The manager of book. create book
	 * 
	 * @param plugin_data_folder_path
	 * @param gamefile
	 * @param book_name
	 */
	public BookManager(Path plugin_data_folder_path, GameFile gamefile, String book_name) {
		if (gamefile.Book.FolderName != null && !gamefile.Book.FolderName.isEmpty()) {
			folder_name = gamefile.Book.FolderName;
		}
		book_folder_path = Paths.get(plugin_data_folder_path.toString(), folder_name);
		if (isDirectoryExists()) copyDefaultBookPageTextFile();
		else createDirectory();
		this.gamefile = gamefile;
		this.book_name = book_name;
	}

	public void createDirectory() {
		try {
			Files.createDirectories(book_folder_path);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public boolean isDirectoryExists() {
		return Files.exists(book_folder_path);
	}

	// TODO
	public void copyDefaultBookPageTextFile() {
	}

	/**
	 * Give the book at the firist time.
	 *
	 * @param player
	 */
	public void giveBook(Player player) {
		player.getInventory().addItem(createBook(getPageTextFile(0)));
	}

	public ItemStack createBook(String pages) {
		return BookAPI.createBook(gamefile.Book.Name, gamefile.Book.Title, gamefile.Book.Lore, pages);
	}

	public void addNextPage(Player player) {
		curent_page_number += 1;
		addPage(player, curent_page_number);
	}

	private boolean addPage(Player player, int page_number) {
		return addPage(player, getPageTextFile(page_number));
	}

	private boolean addPage(Player player, String... pages) {
		return BookAPI.addPage(player, gamefile.Book.Name, gamefile.Book.Title, gamefile.Book.Lore, pages);
	}

	private String getPageTextFile(int page_number) {
		String page_text_file = gamefile.Book.PageTextFiles.get(page_number);
		String page = "";
		try {
			List<String> pages = Files.readAllLines(Paths.get(book_folder_path.toString(), page_text_file));
			for (String line : pages) {
				page += line;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return page;
	}

	public Stream<Path> getPageTextFilesFromFolder() {
		Stream<Path> list = null;
		try {
			list = Files.list(book_folder_path);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return list;
	}

	public List<String> getPageTextFilesFromGameFile() {
		return gamefile.Book.PageTextFiles;
	}

	public String getBookName() {
		return book_name;
	}
	// public void savePages(String name, String... pages) {
	// try {
	// for (String page : pages) {
	// BufferedWriter br = Files.newBufferedWriter(Paths.get(book_folder_path.toString(), name),
	// StandardOpenOption.CREATE);
	// br.write(page);
	// }
	// } catch (IOException ex) {
	// ex.printStackTrace();
	// }
	// }
}
