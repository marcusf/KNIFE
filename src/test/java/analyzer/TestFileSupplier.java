package analyzer;

import java.io.File;
import java.util.List;

import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

import org.apache.commons.cli.CommandLine;

import com.google.inject.Inject;
import com.google.inject.internal.Lists;

public class TestFileSupplier extends FileSupplierBase implements FileSupplier {

    private final String testBase = "simple-test";
    private final StandardJavaFileManager fm;
    
    @Inject
    public TestFileSupplier(CommandLine arguments, StandardJavaFileManager fm) {
        super(arguments);
        this.fm = fm;
    }
    
    @Override
    public Iterable<? extends JavaFileObject> getFiles()
    {
        List<File> returnVal = Lists.newArrayList();
        List<String> fileNames = getFileNames();
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        
        for (String fileName: fileNames) {
            String fname = testBase + "/" + fileName;
            File f = new File(loader.getResource(fname).getPath());
            returnVal.add(f);
        }
        return fm.getJavaFileObjectsFromFiles(returnVal);
    }

}
