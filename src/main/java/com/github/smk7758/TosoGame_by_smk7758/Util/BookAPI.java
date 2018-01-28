package com.github.smk7758.TosoGame_by_smk7758.Util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.github.smk7758.TosoGame_by_smk7758.Main;

public class BookAPI {
	// TODO
	private BookAPI(Main main) {
	}

	// public static ItemStack createBook(String name, String title, List<String> lore, List<String> pages) {
	// return createBook(name, title, lore, pages.toArray(new String[pages.size()]));
	// }

	/**
	 * Creates the book which is same as other arguments.
	 *
	 * @param name
	 * @param title
	 * @param lore_texts
	 * @param pages
	 * @return
	 */
	public static ItemStack createBook(String name, String title, List<String> lore_texts, String... pages) {
		ItemStack itemstack = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta book = (BookMeta) itemstack.getItemMeta();
		book.setDisplayName(name);
		book.setTitle(title);
		book.setLore(lore_texts);
		// for (String text : pages) {
		book.addPage(pages);
		// }
		itemstack.setItemMeta(book);
		return itemstack;
	}

	/**
	 * Adds the page to the book which is same as other aruguments.
	 *
	 * @param player Who has a book which is same as other arguments.
	 * @param name book
	 * @param title book
	 * @param lore_text book
	 * @param pages Adding pages.
	 * @return Success full or not.
	 */
	public static boolean addPage(Player player, String name, String title, List<String> lore_text, String... pages) {
		boolean is = false;
		for (ItemStack itemstack_player : player.getInventory()) {
			if (itemstack_player.getType() != Material.WRITTEN_BOOK) break;
			BookMeta book = (BookMeta) itemstack_player.getItemMeta();
			if (book.getDisplayName().equals(name)
					&& book.getTitle().equals(title)
					&& book.getLore().equals(lore_text)) {
				book.addPage(pages);
				itemstack_player.setItemMeta(book);
				if (!is) is = true;
			}
		}
		return is;
	}

	/**
	 * Gets the book in main hand.
	 *
	 * @param player Player who has the written book.
	 * @return Returns book. If player doesn't have the written book, this will return null.
	 */
	public static BookMeta getPlayerBookInMainHand(Player player) {
		BookMeta book = null;
		ItemStack itemstack_player = player.getInventory().getItemInMainHand();
		if (itemstack_player.getType().equals(Material.WRITTEN_BOOK)) {
			book = (BookMeta) itemstack_player.getItemMeta();
		}
		return book;
	}
}
