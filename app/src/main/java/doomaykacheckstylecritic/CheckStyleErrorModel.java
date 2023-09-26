package doomaykacheckstylecritic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
@XmlAccessorType (XmlAccessType.FIELD)
public class CheckStyleErrorModel {
	@XmlAttribute(name="message")
	private String message;
	
	public String getMessage() {
		return message;
	}
}