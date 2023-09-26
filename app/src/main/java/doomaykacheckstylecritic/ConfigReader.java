package doomaykacheckstylecritic;

import java.awt.geom.IllegalPathStateException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ConfigReader {
	public final static String PROPERTIE_NAME_ERROR_MULTIPLIER = "error-multiplier";
	public final static String PROPERTIE_NAME_WARNING_MULTIPLIER = "warning-multiplier";
	public final static String PROPERTIE_NAME_REFACTOR_MULTIPLIER = "refactor-multiplier";
	public final static String PROPERTIE_NAME_CONVENTION_MULTIPLIER = "convention-multiplier";

	private String path;

	private String XMLpath;
	private String XMLname;

	private List<String> errorMessages;
	private List<String> warningMessages;
	private List<String> refactorMessages;
	private List<String> conventionMessages;
	private int errorMultiplier;
	private int warningMultiplier;
	private int refactorMultiplier;
	private int conventionMultiplier;

	private String[] messages;

	public ConfigReader() {
		try {
			String separator = "";
			path = this.getClass().
					getProtectionDomain().
					getCodeSource().
					getLocation().
					toURI().
					toString();

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
			try {
				Path filePath = Paths.get(path);
				//List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
				List<String> lines = Files.readAllLines(filePath);
				for(String line:lines) {
					prop.load(new StringReader(line));
				}
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
		
		this.errorMultiplier = getIntProperty(prop, ConfigReader.PROPERTIE_NAME_ERROR_MULTIPLIER, 1);
		this.warningMultiplier = getIntProperty(prop, ConfigReader.PROPERTIE_NAME_WARNING_MULTIPLIER, 1);
		this.refactorMultiplier = getIntProperty(prop, ConfigReader.PROPERTIE_NAME_REFACTOR_MULTIPLIER, 1);
		this.conventionMultiplier = getIntProperty(prop, ConfigReader.PROPERTIE_NAME_CONVENTION_MULTIPLIER, 1);

		if (prop.getProperty("messages") != null) {
			String messagesInline = prop.getProperty("messages");
			if (messagesInline.split(";") != null) {
				messages = messagesInline.split(";");
			}
		}
	}
	
	private int getIntProperty(Properties prop, String propertyName, int defaultValue) {
		String propertyValue = prop.getProperty(propertyName);
		return propertyValue == null ? defaultValue : Integer.parseInt(propertyValue);
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

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public String getXMLname() {
		return XMLname;
	}

	public String[] getMessages() {
		return messages;
	}

	public int getErrorMultiplier() {
		return errorMultiplier;
	}
	public int getWarningMultiplier() {
		return warningMultiplier;
	}
	public int getRefactorMultiplier() {
		return refactorMultiplier;
	}
	public int getConventionMultiplier() {
		return conventionMultiplier;
	}
}
