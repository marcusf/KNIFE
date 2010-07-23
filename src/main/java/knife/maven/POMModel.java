package knife.maven;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.google.common.collect.HashMultimap;
import com.google.inject.internal.Lists;

import knife.maven.generated_slim_pom_4_0_0.Dependency;
import knife.maven.generated_slim_pom_4_0_0.Model;

/**
 * Simplifed representation of the fun parts of a Maven POM file.
 * 
 * Used as a wrapper around the whole JAXB-generated Maven model
 * files, picking out the good parts and providing sugar on eg
 * dependencies.
 *
 */
public class POMModel {

    private final Model pomModel;
    private final POMName name;
    
    private List<POMModel> subModules;
    private final HashMultimap<POMModel,POMName> depMap;

    POMModel(Model pomModel, 
             File baseDirectory, 
             HashMultimap<POMModel, POMName> depMap) 
             throws IOException, JAXBException 
    {
        this.pomModel = pomModel;
        this.depMap = depMap;
        
        subModules = Lists.newArrayList();
        
        name = POMName.newPomName(pomModel);
        
        constructSubModules(baseDirectory);
        
        mapDependencies();
        
    }

    private void mapDependencies()
    {
        for (Dependency d: getModuleDependencies()) {
            depMap.put(this, POMName.newPomName(d));
        }
    }

    private void constructSubModules(File baseDirectory) throws IOException,
            JAXBException
    {
        for (String moduleName: getModuleNames()) {
            final String pathname = baseDirectory.getCanonicalPath() 
                                  + File.separator 
                                  + moduleName 
                                  + File.separator 
                                  + "pom.xml"
                                  ;
            POMLoader loader = new POMLoader(new File(pathname), this.depMap);
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
    
    public List<POMModel> getModules() 
    {
        return subModules;
    }
    
    public List<Dependency> getModuleDependencies() {
        if (pomModel.getDependencies() != null && 
            pomModel.getDependencies().getDependency() != null) {
            return pomModel.getDependencies().getDependency();
        } else {
            return new ArrayList<Dependency>();
        }
    }

    public POMName getName()
    {
        return name;
    }
    
    public String getArtifactId() 
    {
        return pomModel.getArtifactId();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        POMModel other = (POMModel) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
    
}
