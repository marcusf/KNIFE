package analyzer;

import javax.tools.JavaFileObject;

public interface FileSupplier {

    Iterable<? extends JavaFileObject> getFiles();

}