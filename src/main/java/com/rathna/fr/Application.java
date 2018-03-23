package com.rathna.fr;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Application {
	private static final String OLD_FILE_NAME = "a";
	private static final String NEW_FILE_NAME = "b";
	private static final String COMMIT = "c";
	private static final String DIRECTORY = "d";
	private static final String RECURSIVE = "e";
	private static final String FORCE_RENAME = "f";
	private static final String VERBOSE = "v";
	private static final String JARNAME = new java.io.File(
			Application.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
	private static final String CLASSNAME = Application.class.getName();

	public static void main(String[] args) {
		final Options options = options();
		try {
			final CommandLineParser parser = new DefaultParser();
			final CommandLine cl = parser.parse(options, args);
			final File dir = directory(cl);
			process(cl, dir);
		} catch (ParseException e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -jar " + JARNAME + ".jar", options);
			System.exit(1);
		}
	}
	private static void process(final CommandLine cl, final File dir) {
		final String oldFileName = oldFileName(cl);
		final Boolean isVerbo = verbose(cl);
		Tracer tracer = null;
		File[] files = dir.listFiles();
		if (files != null) {
			for (File oldF : files) {
				String oldFullName = oldF.getName();
				String newFullName = oldFullName.replace(oldFileName, newFileName(cl));
				String oldName = oldF.getAbsolutePath();
				String newName = oldF.getAbsolutePath().replace(oldFullName, newFullName);
				if (!oldName.equals(newName)) {
					tracer = new Tracer();
					tracer.setOldFileName(oldName);
					tracer.setNewFileName(newName);
				}
				if (!oldName.equals(newName)) {
					if (newName.indexOf(CLASSNAME) > 0 || oldName.indexOf(CLASSNAME) > 0 || newName.indexOf(JARNAME) > 0
							|| oldName.indexOf(JARNAME) > 0) {
						tracer.setIgnored(true);
					} else if (autocommit(cl)) {
						File newF = new File(newName);
						if (newF.exists()) {
							if (force(cl)) {
								boolean renamedNewF = newF.renameTo(new File(newF + "_" + System.currentTimeMillis()));
								if (renamedNewF) {
									tracer.setReplaced(true);
									boolean renamed = oldF.renameTo(newF);
									if (!renamed) {
										tracer.setFailed(true);
									} else {
										oldF = newF;
									}
								} else {
									tracer.setFailed(true);
								}
							} else {
								tracer.setIgnored(true);
							}
						} else {
							boolean renamed = oldF.renameTo(newF);
							if (!renamed) {
								tracer.setFailed(true);
							} else {
								oldF = newF;
							}
						}
					} else {
						tracer.setDry(true);
					}
				}
				if (isVerbo && tracer != null) {
					System.out.println(tracer);
				}
				if (oldF.isDirectory() && recursive(cl)) {
					process(cl, oldF);
				}
			}
		}
	}
	private static boolean force(CommandLine cl) {
		return cl.hasOption(FORCE_RENAME);
	}
	private static boolean recursive(CommandLine cl) {
		return cl.hasOption(RECURSIVE);
	}
	private static boolean verbose(CommandLine cl) {
		return cl.hasOption(VERBOSE);
	}
	private static String oldFileName(CommandLine cl) {
		return cl.getOptionValue(OLD_FILE_NAME);
	}
	private static String newFileName(CommandLine cl) {
		return cl.getOptionValue(NEW_FILE_NAME);
	}
	private static Options options() {
		Options options = new Options();
		options.addOption(Option.builder(OLD_FILE_NAME).required(true).hasArg()
				.desc("Old file name, required, eg: \".jpg\" ").build());
		options.addOption(Option.builder(NEW_FILE_NAME).required(true).hasArg()
				.desc("New file name, required, eg: \"_M.jpg\" ").build());
		options.addOption(Option.builder(RECURSIVE).required(false)
				.desc("Recursive, include sub directories, optional, defaults to false").build());
		options.addOption(Option.builder(DIRECTORY).required(false).hasArg()
				.desc("Directory to perform the renames, optional, defaults to current directory").build());
		options.addOption(Option.builder(COMMIT).required(false)
				.desc("Auto-commit file renames, optional, defaults to false - only dry run").build());
		options.addOption(
				Option.builder(VERBOSE).required(false).desc("Verbose output, optional, defaults to false").build());
		options.addOption(Option.builder(FORCE_RENAME).required(false).desc(
				"Force-rename, if new file already exists it will be renamed as new_file_time, optional, defaults to false")
				.build());
		return options;
	}
	private static boolean autocommit(CommandLine cl) {
		return cl.hasOption(COMMIT);
	}
	private static File directory(CommandLine cl) throws RuntimeException {
		String dir = cl.getOptionValue(DIRECTORY);
		Path path = Paths.get(dir == null ? "" : dir.trim()).normalize();
		File file = new File(path.normalize().toAbsolutePath().toString());
		if (file.isDirectory()) {
			return file;
		} else {
			throw new RuntimeException("Invalid Dir: " + path.toAbsolutePath().toString());
		}
	}
}
