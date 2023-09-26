package doomaykacheckstylecritic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class CheckStyleParser {
	private String checkRootPath = "";
	private String fsSeparator = "";
	private String XMLname = "main.xml";
	private String XMLpath = "";
	private List<String> rootFilesPathStrings;

	private String XMLString = "";
	
	private CheckStyleModel data = null;

	public CheckStyleParser() {
		try {
			String separator = "";

			checkRootPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().toString();
			int dirSlashIdx = 0;
			dirSlashIdx = checkRootPath.lastIndexOf("/");
			if (dirSlashIdx != -1) {
				checkRootPath = checkRootPath.substring(0, dirSlashIdx);
				separator = "/";
			} else {
				dirSlashIdx = checkRootPath.lastIndexOf("\\");
				separator = "\\";
				if (dirSlashIdx != -1) {
					checkRootPath = checkRootPath.substring(0, dirSlashIdx);
				} else {
					throw new URISyntaxException("checkRootPathString", "Bad path");
				}
			}

			dirSlashIdx = checkRootPath.indexOf(separator);
			checkRootPath = checkRootPath.substring(dirSlashIdx + 1);

			fsSeparator = separator;

		} catch (URISyntaxException e) {
			System.out.println("Work directory not parsed");
		}

		rootFilesPathStrings = new ArrayList<String>();
	}

	public CheckStyleParser(String xmlName) {
		this();
		XMLname = xmlName;
	}

	public CheckStyleParser(String path, String xmlName) {
		checkRootPath = path;
		rootFilesPathStrings = new ArrayList<String>();
		XMLname = xmlName;
	}

	public void readXML() {
		if (XMLpath.equals("")) {
			File rootDir = new File(checkRootPath);
			String[] files = rootDir.list();

			buildPathsRecursive(files, checkRootPath);

			XMLpath = rootFilesPathStrings.get(0);
		}

		readXMLFile(XMLpath);
		
		parseXMLFile(XMLString);
	}

	private void buildPathsRecursive(String[] files, String rootPath) {
		if (files != null) {
			for (String file : files) {
				File dot = new File(rootPath + fsSeparator + file);
				if (dot.isFile()) {
					if (dot.getName().equals(this.XMLname)) {
						rootFilesPathStrings.add(dot.getPath());
					}
				}
				if (dot.isDirectory()) {
					String[] subFiles = dot.list();
					for (int i = 0; i < subFiles.length; i++) {
						subFiles[i] = rootPath + fsSeparator + subFiles[i];
					}

					File rootDir = new File(rootPath + fsSeparator + file);
					String[] subSubfiles = rootDir.list();

					buildPathsRecursive(subSubfiles, rootPath + fsSeparator + file);
				}
			}
		}
	}

	private void readXMLFile(String filePath) {
		if(!filePath.equals("")) {
			Scanner scanner;
			try {
				scanner = new Scanner(new File(filePath));
				
				scanner.useDelimiter(System.getProperty("line.separator"));
				while (scanner.hasNext()) {
					XMLString += scanner.next();
				}
				scanner.close();
			} catch (FileNotFoundException e) {
				System.out.println("XML file reading error");
			}
		}
	}
	
	private void parseXMLFile(String xmlString) {
		if(!xmlString.equals("")) {
			Reader fis = new StringReader(xmlString);
			
			JAXBContext context;
			try {
				context = org.eclipse.persistence.jaxb.JAXBContextFactory
						.createContext(new Class[] { CheckStyleModel.class }, null);
				
				Unmarshaller unmarshaller = context.createUnmarshaller();
				CheckStyleModel checkStyleReport = (CheckStyleModel) unmarshaller.unmarshal(fis);
				this.data = checkStyleReport;
			} catch (JAXBException e) {
				System.out.println("XML file not parsed");
			}
		}
	}
	
	public CheckStyleModel getXmlUnparsed() {
		return this.data;
	}
}
