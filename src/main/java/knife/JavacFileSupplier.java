package knife;

import javax.tools.JavaFileObject;

/**
 * File suppliers that can deliver objects that can be 
 * parsed by the java compiler.
 */
public interface JavacFileSupplier extends FileSupplier {

    Iterable<? extends JavaFileObject> getFiles();

}