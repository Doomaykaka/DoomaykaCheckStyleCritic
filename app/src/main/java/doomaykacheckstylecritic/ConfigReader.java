package doomaykacheckstylecritic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ConfigReader {
	private String path;

	private String XMLpath;
	private String XMLname;

	private List<String> errorMessages;
	private List<String> warningMessages;
	private List<String> refactorMessages;
	private List<String> conventionMessages;
	private int[] multipliers;
	private int[] counter;

	private String[] messages;

	public ConfigReader() {
		try {
			String separator = "";
			path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().toString();

			int dirSlashIdx = 0;
			dirSlashIdx = path.lastIndexOf("/");
			if (dirSlashIdx != -1) {
				path = path.substring(0, dirSlashIdx);
				separator = "/";
			} else {
				separator = "/";
				dirSlashIdx = path.lastIndexOf("\\");
				if (dirSlashIdx != -1) {
					path = path.substring(0, dirSlashIdx);
				} else {
					throw new URISyntaxException("checkRootPathString", "Bad path");
				}
			}

			dirSlashIdx = path.indexOf(separator);
			path = path.substring(dirSlashIdx + 1);
			path = path + separator + "properties.conf";

		} catch (URISyntaxException e) {
			System.out.println("Root path error");
		}
	}

	public ConfigReader(String path) {
		this.path = path;
	}

	public void readConfig() {
		Properties prop = new Properties();

		if (!path.equals("")) {
			Scanner scanner;
			try {
				scanner = new Scanner(new File(path));
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					prop.load(new StringReader(line));
				}
				scanner.close();
			} catch (FileNotFoundException e) {
				System.out.println("Config file not parsed");
			} catch (IOException e) {
				System.out.println("Config file not parsed");
			}
		}

		if (prop.getProperty("xml-path") != null) {
			XMLpath = prop.getProperty("xml-path");
		}

		if (prop.getProperty("xml-name") != null) {
			XMLname = prop.getProperty("xml-name");
		}

		if (prop.getProperty("error-messages") != null) {
			String errorMessagesInline = prop.getProperty("error-messages");
			if (errorMessagesInline.split(";") != null) {
				errorMessages = Arrays.asList(errorMessagesInline.split(";"));
			}
		}

		if (prop.getProperty("warning-messages") != null) {
			String warningMessagesInline = prop.getProperty("warning-messages");
			if (warningMessagesInline.split(";") != null) {
				warningMessages = Arrays.asList(warningMessagesInline.split(";"));
			}
		}

		if (prop.getProperty("refactor-messages") != null) {
			String refactorMessagesInline = prop.getProperty("refactor-messages");
			if (refactorMessagesInline.split(";") != null) {
				refactorMessages = Arrays.asList(refactorMessagesInline.split(";"));
			}
		}

		if (prop.getProperty("convention-messages") != null) {
			String conventionMessagesInline = prop.getProperty("convention-messages");
			if (conventionMessagesInline.split(";") != null) {
				conventionMessages = Arrays.asList(conventionMessagesInline.split(";"));
			}
		}

		if (prop.getProperty("multipliers") != null) {
			String multipliersInline = prop.getProperty("multipliers");
			if (multipliersInline.split(";") != null) {
				String[] multStr = multipliersInline.split(";");
				int[] multInt = new int[multStr.length];
				for (int i = 0; i < multStr.length; i++) {
					multInt[i] = Integer.parseInt(multStr[i]);
				}

				multipliers = multInt;
			}
		}

		if (prop.getProperty("counters") != null) {
			String countersInline = prop.getProperty("counters");
			if (countersInline.split(";") != null) {
				String[] countStr = countersInline.split(";");
				int[] countInt = new int[countStr.length];
				for (int i = 0; i < countInt.length; i++) {
					countInt[i] = Integer.parseInt(countStr[i]);
				}

				counter = countInt;
			}
		}

		if (prop.getProperty("messages") != null) {
			String messagesInline = prop.getProperty("messages");
			if (messagesInline.split(";") != null) {
				messages = messagesInline.split(";");
			}
		}
	}

	public String getXMLpath() {
		return XMLpath;
	}

	public List<String> getWarningMessages() {
		return warningMessages;
	}

	public List<String> getRefactorMessages() {
		return refactorMessages;
	}

	public List<String> getConventionMessages() {
		return conventionMessages;
	}

	public int[] getMultipliers() {
		return multipliers;
	}

	public int[] getCounter() {
		return counter;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public String getXMLname() {
		return XMLname;
	}

	public String[] getMessages() {
		return messages;
	}
}
