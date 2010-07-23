package knife.analysis;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import knife.FileSupplier;
import knife.maven.TopPOMLoader;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TopPOMLoaderProvider implements Provider<TopPOMLoader> {

    private final FileSupplier fileSupplier;

    @Inject
    public TopPOMLoaderProvider(FileSupplier fileSupplier) {
        this.fileSupplier = fileSupplier;

    }

    @Override
    public TopPOMLoader get()
    {
        String fileName = Iterables.get(fileSupplier.getFileNames(), 0);
        TopPOMLoader topPOMLoader;
        try {
            File file = new File(fileName).getCanonicalFile();
            topPOMLoader = new TopPOMLoader(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return topPOMLoader;
    }
    
    
}
