package com.github.smk7758.TosoGame_by_smk7758;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class Book {

	private Book(Main main) {
	}

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
}
