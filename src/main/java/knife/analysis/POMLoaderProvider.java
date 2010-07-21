package knife.analysis;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import knife.FileSupplierBase;
import knife.maven.TopPOMLoader;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class POMLoaderProvider implements Provider<TopPOMLoader> {

    private final FileSupplierBase fileSupplier;

    @Inject
    public POMLoaderProvider(FileSupplierBase fileSupplier) {
        this.fileSupplier = fileSupplier;

    }

    @Override
    public TopPOMLoader get()
    {
        String fileName = Iterables.get(fileSupplier.getFileNames(), 0);
        File file = new File(fileName);
        TopPOMLoader topPOMLoader;
        try {
            topPOMLoader = new TopPOMLoader(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return topPOMLoader;
    }
    
    
}
