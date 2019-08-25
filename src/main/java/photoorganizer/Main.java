package photoorganizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;



public class Main {
	private static String getDateTimeFromMetadata(File file) {
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(file);
			for (Directory d : metadata.getDirectories()) {
				for (Tag tag : d.getTags()) {
					System.out.println(tag.getTagName());
					System.out.println(tag.getDescription());
					System.out.println(tag.getDirectoryName());
					if (tag.getDirectoryName().equals("Exif SubIFD") && tag.getTagName().equals("Date/Time Original")) {
						return tag.getDescription();
					}
				}
			}
		} catch (Throwable e) {
			return null;
		}
		return null;
	}

	public static String getLastModifiedTime(File file) {
		try {
			return Files.getLastModifiedTime(file.toPath()).toString();
		} catch (IOException e) {
			return null;
		}
	}

	public static void error(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static boolean confirm(String msg) {
		return JOptionPane.showConfirmDialog(null, msg) == JOptionPane.YES_OPTION;
	}

	public static void main(String[] args) {
		// Configure LookAndFeel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());

		} catch (Exception ex) {
			
		}

		if (args.length >= 1) {
			File dir = new File(args[0]);
			if (dir.isDirectory()) {
				File[] files = dir.listFiles();
				
				if (files.length > 0) {
					if (confirm("Confirm file reorganization?\nCannot undo this operation!")) {
						for (File file : files) {
							String dateTime = getDateTimeFromMetadata(file);

							if (dateTime == null)
								dateTime = getLastModifiedTime(file);

							String year = dateTime.substring(0, 4);
							String directory = dir + "\\" + year + "\\" + dateTime.substring(0, 10);
							String folderName = dateTime.substring(0, 10);

							if (!new File(directory).exists()) {
								new File(dir + "\\" + year + "\\" + folderName).mkdirs();
								try {
									Files.move(Paths.get(dir + "\\" + file.getName()),
											Paths.get((dir + "\\" + year + "\\" + folderName + "\\" + file.getName())));
								} catch (Throwable e) {
									System.out.println(e.getMessage());
								}
							} else {
								try {
									Files.move(Paths.get(dir + "\\" + file.getName()),
											Paths.get((dir + "\\" + year + "\\" + folderName + "\\" + file.getName())));
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					} 
				} else {
					error("No files!");
				}
			} else {
				error("This isn't a directory!");
			}
		} else {
			error("Could not get directory.\n"
					+ "You can fix this error in shell command folder");
		}
	}
}