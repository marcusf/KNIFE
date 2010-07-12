package analyzer;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

public class DelegatingFileManager implements JavaFileManager {

    StandardJavaFileManager fileManager;


    public DelegatingFileManager(StandardJavaFileManager fileManager) {
        super();
        this.fileManager = fileManager;
    }
    
    public int isSupportedOption(String option)
    {
        return fileManager.isSupportedOption(option);
    }

    public ClassLoader getClassLoader(Location location)
    {
        return fileManager.getClassLoader(location);
    }

    public boolean isSameFile(FileObject a, FileObject b)
    {
        return fileManager.isSameFile(a, b);
    }

    public Iterable<? extends JavaFileObject> getJavaFileObjectsFromFiles(
            Iterable<? extends File> files)
    {
        return fileManager.getJavaFileObjectsFromFiles(files);
    }

    public Iterable<JavaFileObject> list(Location location, String packageName,
            Set<Kind> kinds, boolean recurse) throws IOException
    {
        return fileManager.list(location, packageName, kinds, recurse);
    }

    public Iterable<? extends JavaFileObject> getJavaFileObjects(File... files)
    {
        return fileManager.getJavaFileObjects(files);
    }

    public Iterable<? extends JavaFileObject> getJavaFileObjectsFromStrings(
            Iterable<String> names)
    {
        return fileManager.getJavaFileObjectsFromStrings(names);
    }

    public Iterable<? extends JavaFileObject> getJavaFileObjects(
            String... names)
    {
        return fileManager.getJavaFileObjects(names);
    }

    public String inferBinaryName(Location location, JavaFileObject file)
    {
        return fileManager.inferBinaryName(location, file);
    }

    public void setLocation(Location location, Iterable<? extends File> path)
            throws IOException
    {
        fileManager.setLocation(location, path);
    }

    public boolean handleOption(String current, Iterator<String> remaining)
    {
        return fileManager.handleOption(current, remaining);
    }

    public Iterable<? extends File> getLocation(Location location)
    {
        return fileManager.getLocation(location);
    }

    public boolean hasLocation(Location location)
    {
        return fileManager.hasLocation(location);
    }

    public JavaFileObject getJavaFileForInput(Location location,
            String className, Kind kind) throws IOException
    {
        return fileManager.getJavaFileForInput(location, className, kind);
    }

    public JavaFileObject getJavaFileForOutput(Location location,
            String className, Kind kind, FileObject sibling) throws IOException
    {
        return fileManager.getJavaFileForOutput(location, className, kind,
                sibling);
    }

    public FileObject getFileForInput(Location location, String packageName,
            String relativeName) throws IOException
    {
        return fileManager.getFileForInput(location, packageName, relativeName);
    }

    public FileObject getFileForOutput(Location location, String packageName,
            String relativeName, FileObject sibling) throws IOException
    {
        return fileManager.getFileForOutput(location, packageName,
                relativeName, sibling);
    }

    public void flush() throws IOException
    {
        fileManager.flush();
    }

    public void close() throws IOException
    {
        fileManager.close();
    }

    
}
