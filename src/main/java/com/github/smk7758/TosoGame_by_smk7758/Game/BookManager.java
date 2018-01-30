package com.github.smk7758.TosoGame_by_smk7758.Game;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.smk7758.TosoGame_by_smk7758.Main;
import com.github.smk7758.TosoGame_by_smk7758.Files.DataFiles.GameFile;
import com.github.smk7758.TosoGame_by_smk7758.Util.BookAPI;
import com.github.smk7758.TosoGame_by_smk7758.Util.SendLog;

public class BookManager {
	private Main main = null;
	private Path book_folder_path = null;
	private String folder_name = "Books";
	private GameFile gamefile = null;
	private String book_name = "book";
	private int curent_page_number = 0;
	private String default_book_name_0 = "test_0.txt";
	private String default_book_name_1 = "test_1.txt";

	/**
	 * The manager of book. create book
	 *
	 * @param plugin_data_folder_path
	 * @param gamefile
	 * @param book_name
	 */
	public BookManager(Main main, GameFile gamefile) {
		this.main = main;
		this.gamefile = gamefile;
		this.book_name = gamefile.Book.Name;

		Path plugin_data_folder_path = main.getDataFolder().toPath();

		if (gamefile.Book.FolderName != null && !gamefile.Book.FolderName.isEmpty()) {
			folder_name = gamefile.Book.FolderName;
		}
		book_folder_path = Paths.get(plugin_data_folder_path.toString(), folder_name);
		if (!isDirectoryExists()) {
			createDirectory();
			copyDefaultBookPageTextFile();
		}
	}

	public void createDirectory() {
		try {
			Files.createDirectories(book_folder_path);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public boolean isDirectoryExists() {
		return (Files.isDirectory(book_folder_path) && Files.exists(book_folder_path));
	}

	public void copyDefaultBookPageTextFile() {
		try {
			InputStream is_default_book_0 = null, is_default_book_1 = null;
			if ((is_default_book_0 = main.getResource(default_book_name_0)) != null
					&& (is_default_book_1 = main.getResource(default_book_name_1)) != null) {
				Files.copy(is_default_book_0, Paths.get(book_folder_path.toString(), default_book_name_0));
				Files.copy(is_default_book_1, Paths.get(book_folder_path.toString(), default_book_name_1));
				SendLog.debug("Copied: " + default_book_name_0 + ", " + default_book_name_1);
			} else {
				SendLog.error("Can't find default book text file.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public boolean addNextPage(Player player) {
		curent_page_number += 1;
		return addPage(player, curent_page_number);
	}

	private boolean addPage(Player player, int page_number) {
		return addPage(player, getPageTextFile(page_number));
	}

	private boolean addPage(Player player, String... pages) {
		return BookAPI.addPage(player, gamefile.Book.Name, gamefile.Book.Title, gamefile.Book.Lore, pages);
	}

	private String getPageTextFile(int page_number) {
		SendLog.debug("PageTextNumber: " + page_number);

		if (gamefile.Book.PageTextFiles != null
				&& !gamefile.Book.PageTextFiles.isEmpty()
				&& gamefile.Book.PageTextFiles.size() < page_number) {
			SendLog.error("Page number is too big.");
			return "";
		}
		String page = "";
		String page_text_file = gamefile.Book.PageTextFiles.get(page_number);
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
