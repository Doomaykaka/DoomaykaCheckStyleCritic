package doomaykacheckstylecritic;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "checkstyle")
@XmlAccessorType(XmlAccessType.FIELD)
public class CheckStyleModel {
	@XmlElement(name = "file")
	private List<CheckStyleFileModel> files = null;

	public List<CheckStyleFileModel> getFiles() {
		return files;
	}
}