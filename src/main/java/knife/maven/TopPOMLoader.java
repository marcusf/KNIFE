package knife.maven;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import nu.xom.ParsingException;

import com.google.common.collect.HashMultimap;
import com.google.inject.internal.Maps;

/**
 * This is used to construct a tree of POM models from a 
 * top-level POM file.
 * 
 * It reads the POM file you give it in the constructor and 
 * loads all artifacts referenced as modules from that POM
 * file recursively until it has iterated over the entire
 * POM tree.
 */
public class TopPOMLoader {

    private POMLoader pomLoader;
    private POMModel tree;
    private HashMultimap<POMModel, POMName> depMap;
    private Map<POMName, POMModel> models;
    
    /**
     * Constructs a POM tree from the file given. 
     * This actually does the recursion, meaning it is potentially
     * a very expensive operation.
     * @throws ParsingException 
     */
    public TopPOMLoader(File pomFile) throws IOException, ParsingException {
        depMap = HashMultimap.create();
        models = Maps.newHashMap();

        pomLoader = new POMLoader(pomFile, depMap);
        tree = pomLoader.getPOMTree();        
        
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
