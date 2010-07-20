package knife.maven;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import com.google.common.collect.HashMultimap;
import com.google.inject.internal.Maps;

public class TopPOMLoader {

    private POMLoader pomLoader;
    private POMModel tree;
    private HashMultimap<POMModel, POMName> depMap;
    private Map<POMName, POMModel> models;
    
    public TopPOMLoader(File pom) throws IOException, JAXBException {
        depMap = HashMultimap.create();
        pomLoader = new POMLoader(pom, depMap);
        tree = pomLoader.getPOMTree();        
        models = Maps.newHashMap();
        
        for (POMModel m: depMap.keySet()) {
            models.put(m.getName(), m);
        }
        
    }
    
    public Set<POMName> getPOMs() {
        return models.keySet();
    }
    
    public Set<POMName> getDependencies(POMName pom) {
        return depMap.get(models.get(pom));
    }
    
    public POMModel getModel(POMName pomName) {
        return models.get(pomName);
    }
        
    public POMModel getTopPOM() {
        return tree;
    }
}
