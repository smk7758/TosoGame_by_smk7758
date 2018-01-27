package com.github.smk7758.TosoGame_by_smk7758.Files.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class BookFileManager {
	Path book_folder_path = null;

	public BookFileManager(Path plugins_folder_path) {
		try {
			book_folder_path = Files.createDirectories(Paths.get(book_folder_path.toString(), "Book")); // これ正解？TODO
			// TODO
			Path book_file_path = Paths.get(book_folder_path.toString(), "book.txt");
			book_file_path = Files.createFile(book_file_path);
			// TODO
			BufferedWriter dos = Files.newBufferedWriter(book_file_path);
			dos.write("book.txt test!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void savePages(String name, String... pages) {
		try {
			for (String page : pages) {
				BufferedWriter br = Files.newBufferedWriter(Paths.get(book_folder_path.toString(), name));
				br.write(page);
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public String getPage(String name) {
		String page = "";
		try {
			BufferedReader br = Files.newBufferedReader(Paths.get(book_folder_path.toString(), name));
			String line = "";
			while ((line = br.readLine()) != null) {
				page += line;
			}
			// TODO: Files.readAllLine?
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return page;
	}

	public Stream<Path> getBookFiles() {
		Stream<Path> list = null;
		try {
			list = Files.list(book_folder_path);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return list;
	}
}
