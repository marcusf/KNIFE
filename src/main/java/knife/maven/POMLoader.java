package knife.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import knife.maven.xom.Model;
import knife.maven.xom.POMParser;
import nu.xom.ParsingException;

import com.google.common.collect.HashMultimap;

/**
 * Internal class used by {@link TopPOMLoader}. Does the
 * actual bootstrapping of the recursion. Also used by 
 * {@POMModel} objects to construct its sub modules when
 * recursing.
 *
 */
class POMLoader {

    protected final InputStream inputStream;
    protected final File pomFile;
    private final HashMultimap<POMModel, POMName> depMap;

    POMLoader(File pom, HashMultimap<POMModel,POMName> p) throws IOException {
        pomFile = pom;
        this.depMap = p;
        inputStream = new FileInputStream(pom);
    }
    
    POMModel getPOMTree() throws IOException, ParsingException {
        Model pom = unmarshal();
        POMModel pomModel = new POMModel(pom, pomFile.getParentFile(), depMap);
        return pomModel;
    }
    
    private knife.maven.xom.Model unmarshal() throws IOException, ParsingException
    {
        POMParser p = new POMParser(pomFile);
        return p.parse();
    }

}
