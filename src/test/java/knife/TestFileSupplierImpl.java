package knife;

import java.io.File;
import java.util.List;

import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

import knife.JavacFileSupplier;
import knife.JavacFileSupplierBase;

import org.apache.commons.cli.CommandLine;

import com.google.inject.Inject;
import com.google.inject.internal.Lists;
import com.google.inject.name.Named;

public class TestFileSupplierImpl extends JavacFileSupplierBase implements JavacFileSupplier {

    private final String testCase;
    private final StandardJavaFileManager fm;
    
    @Inject
    public TestFileSupplierImpl(@Named("testCase") String testCase, 
                                CommandLine arguments, 
                                StandardJavaFileManager fm) 
    {
        super(arguments);
        this.fm = fm;
        this.testCase = testCase;
    }
    
    @Override
    public Iterable<? extends JavaFileObject> getFiles()
    {
        List<File> returnVal = Lists.newArrayList();
        Iterable<String> fileNames = getFileNames();
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        
        
        for (String fileName: fileNames) {
            String fname = testCase + "/" + fileName;
            try {
                File f = new File(loader.getResource(fname).getPath());
                returnVal.add(f);
            } catch (NullPointerException e) {
                // No file existed. Do nothing.
            }
        }
        return fm.getJavaFileObjectsFromFiles(returnVal);
    }
}
