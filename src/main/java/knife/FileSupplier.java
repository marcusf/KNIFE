package knife;

/**
 * Base interface of objects providing files munged by
 * the various bootstrapping stages before the analyses.
 */
public interface FileSupplier {

    Iterable<String> getFileNames();
    
}
