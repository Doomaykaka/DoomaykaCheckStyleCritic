package doomaykacheckstylecritic;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "file")
@XmlAccessorType(XmlAccessType.FIELD)
public class CheckStyleFileModel {
    @XmlAttribute(name = "name")
    private String fileName;
    @XmlElement(name = "error")
    private List<CheckStyleErrorModel> errors = null;

    public String getFileName() {
        return fileName;
    }

    public List<CheckStyleErrorModel> getErrors() {
        return errors;
    }
}
