package analyzer;

import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

import org.apache.commons.cli.CommandLine;

import com.google.inject.Inject;

public class FileSupplierImpl extends FileSupplierBase implements FileSupplier {

    private final StandardJavaFileManager fileManager;

    @Inject
    public FileSupplierImpl(CommandLine arguments, StandardJavaFileManager fileManager) {
        super(arguments);
        this.fileManager = fileManager;
    }

    @Override
    public Iterable<? extends JavaFileObject> getFiles()
    {
        return fileManager.getJavaFileObjectsFromStrings(getFileNames());
    }
    
}
