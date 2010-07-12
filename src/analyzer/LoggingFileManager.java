package analyzer;

import java.io.IOException;

import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

public class LoggingFileManager extends DelegatingFileManager {


    public LoggingFileManager(StandardJavaFileManager fileManager) {
        super(fileManager);
    }
    
    @Override
    public JavaFileObject getJavaFileForInput(Location location,
            String className, Kind kind) throws IOException
    {
        System.out.println("getJavaFileForInput: " + className);
        return super.getJavaFileForInput(location, className, kind);
    }

    @Override
    public FileObject getFileForInput(Location location, String packageName,
            String relativeName) throws IOException
    {
        System.out.println("getFileForInput: " + packageName + "." + relativeName);
        return super.getFileForInput(location, packageName, relativeName);
    }


}
