package knife;

import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

import org.apache.commons.cli.CommandLine;

import com.google.inject.Inject;

/**
 * Constructs JavaFileObject's from the input file
 * stream.
 */
public class JavacFileSupplierImpl extends JavacFileSupplierBase implements JavacFileSupplier {

    private final StandardJavaFileManager fileManager;

    @Inject
    public JavacFileSupplierImpl(CommandLine arguments, StandardJavaFileManager fileManager) {
        super(arguments);
        this.fileManager = fileManager;
    }

    @Override
    public Iterable<? extends JavaFileObject> getFiles()
    {
        return fileManager.getJavaFileObjectsFromStrings(getFileNames());
    }
    
}
