package knife.maven;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.google.inject.internal.Lists;

import knife.maven.generated_pom_4_0_0.Dependency;
import knife.maven.generated_pom_4_0_0.Model;

public class SimplePOMModel {

    private final Model pomModel;
    
    private List<SimplePOMModel> subModules;

    public SimplePOMModel(Model pomModel, File baseDirectory) throws IOException, JAXBException 
    {
        this.pomModel = pomModel;
        subModules = Lists.newArrayList();
        
        for (String moduleName: getModuleNames()) {
            final String pathname = baseDirectory.getCanonicalPath() 
                                  + File.separator 
                                  + moduleName 
                                  + File.separator 
                                  + "pom.xml"
                                  ;
            TopPOMLoader loader = new TopPOMLoader(new File(pathname));
            subModules.add(loader.getPOMTree());
        }
        
    }

    public List<String> getModuleNames()
    {
        if (pomModel.getModules() != null) {
            return pomModel.getModules().getModule();
        } else {
            return Lists.newArrayList();
        }
    }
    
    public List<SimplePOMModel> getModules() 
    {
        return subModules;
    }
    
    public List<Dependency> getModuleDependencies() {
        return pomModel.getDependencies().getDependency();
    }

    public String getName()
    {
        return pomModel.getName();
    }
    
    public String getArtifactId() 
    {
        return pomModel.getArtifactId();
    }
    
    
}
