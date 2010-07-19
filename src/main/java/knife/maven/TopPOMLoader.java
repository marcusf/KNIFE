package knife.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import knife.maven.generated_pom_4_0_0.Model;

public class TopPOMLoader {
    
    private InputStream inputStream;

    public TopPOMLoader(File pom) throws IOException {
        inputStream = new FileInputStream(pom);
    }
    
    public TopPOMLoader(URL pom) throws IOException {
        inputStream = pom.openStream();
    }
    
    public List<String> getModules() throws FileNotFoundException, JAXBException {
        Model m = unmarshal();
        return m.getModules().getModule();
    }
    
    private Model unmarshal() throws JAXBException 
    {
        JAXBContext ctx = JAXBContext.newInstance(Model.class.getPackage().getName());
        Unmarshaller u = ctx.createUnmarshaller();
        @SuppressWarnings("unchecked")
        JAXBElement<Model> doc = (JAXBElement<Model>) u.unmarshal(inputStream);
        return doc.getValue();
    }
}
