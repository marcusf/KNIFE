package knife;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.CommandLine;

import com.google.inject.Inject;
import com.google.inject.internal.Lists;
import com.google.inject.name.Named;

public class TestFileSupplierImpl extends FileSupplierBase implements FileSupplier {

    private final String testCase;

    @Inject
    public TestFileSupplierImpl(@Named("testCase") String testCase, 
            CommandLine arguments) 
    {
        super(arguments);
        this.testCase = testCase;
    }
    
    @Override
    public Iterable<String> getFileNames()
    {
        Iterable<String> fileNames = super.getFileNames();
        List<String> returnVal = Lists.newArrayList();
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        
        
        for (String fileName: fileNames) {
            String fname = testCase + "/" + fileName;
            try {
                File f = new File(loader.getResource(fname).getPath());
                returnVal.add(f.getCanonicalPath());
            } catch (IOException e) {
                // No file found. Do nothing.
            }
        }
        return returnVal;
    }

}
