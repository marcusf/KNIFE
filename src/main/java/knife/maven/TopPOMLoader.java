package knife.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import knife.maven.generated_pom_4_0_0.Model;

public class TopPOMLoader {
    
    private final InputStream inputStream;
    private final File pomFile;

    public TopPOMLoader(File pom) throws IOException {
        pomFile = pom;
        inputStream = new FileInputStream(pom);
    }
    
    public SimplePOMModel getPOMTree() throws JAXBException, IOException {
        Model pom = unmarshal();
        SimplePOMModel pomModel = new SimplePOMModel(pom, pomFile.getParentFile());
        return pomModel;
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
