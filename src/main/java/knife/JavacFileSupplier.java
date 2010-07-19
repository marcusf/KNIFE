package knife;

import javax.tools.JavaFileObject;

public interface JavacFileSupplier extends FileSupplier {

    Iterable<? extends JavaFileObject> getFiles();

}