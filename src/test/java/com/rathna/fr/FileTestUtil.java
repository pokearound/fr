package com.rathna.fr;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.TreeSet;
import org.junit.rules.TemporaryFolder;

public class FileTestUtil {
	private static final String[] FILE_EXTS = { ".txt", ".jpg", ".mp3", ".zip" };
	private static final String LOCAL_FILE_SEPARATOR = "_f_";
	private static final String LOCAL_DIR_SEPARATOR = "_d_";
	private static final String LOCAL_SUBDIR_SEPARATOR = "_s_";
	private static Path ROOT = null;
	static {
		try {
			TemporaryFolder ROOT_FOLDER = new TemporaryFolder();
			ROOT_FOLDER.create();
			ROOT = ROOT_FOLDER.newFolder().toPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Path setupDirs(String rnd) {
		for (int j = 0; j < FILE_EXTS.length; j++) {
			for (int i = 0; i < 5; i++) {
				newFile(ROOT.resolve(rnd + LOCAL_FILE_SEPARATOR + i + FILE_EXTS[j]));
			}
		}
		String subs = "";
		for (int i = 0; i < 5; i++) {
			Path newDir = newDir(ROOT.resolve(rnd + LOCAL_DIR_SEPARATOR + i));
			newDir.resolve(subs += rnd + LOCAL_SUBDIR_SEPARATOR + i + File.separator).toFile().mkdirs();
		}
		return ROOT;
	}
	/**
	 * Prints the contents of {@code rootDirPath} recursively in ascending order
	 * 
	 * @param rootDirPath
	 */
	public static void printy(Path rootDirPath) {
		TreeSet<Path> paths = new TreeSet<>();
		try {
			Files.walkFileTree(rootDirPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					paths.add(dir);
					return super.preVisitDirectory(rootDirPath, attrs);
				}
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					paths.add(file);
					return super.visitFile(rootDirPath, attrs);
				}
				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					return super.visitFileFailed(file, exc);
				}
				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					return super.postVisitDirectory(rootDirPath, exc);
				}
			});
			paths.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Creates a new file with {@code file} as name and {@code file} as content
	 * 
	 * @param root
	 * @param path
	 */
	private static void newFile(Path path) {
		try {
			Files.write(path, path.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Creates a dir
	 * @param dir
	 * @return
	 */
	public static Path newDir(Path dir) {
		try {
			return Files.createDirectory(dir);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
