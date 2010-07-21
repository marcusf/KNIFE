package knife.analysis;

import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Guice goo for bootstrapping an {@link AbstractFoldingVisitor} 
 * using the {@link VisitorDriver} and its dependencies to get
 * files for parsing etc in place.
 *
 * @param <K>
 */
public abstract class FoldedResultProvider<K> implements Provider<K> {

    private final AbstractFoldingVisitor<K> visitor;
    private final VisitorDriver driver;

    @Inject
    public FoldedResultProvider(VisitorDriver driver, AbstractFoldingVisitor<K> visitor) {
        this.driver = driver;
        this.visitor = visitor;
    }

    public K get()
    {
        try {
             K result = driver.result(visitor);
             return result;
        } catch (IOException e) {
            
            throw new RuntimeException(e);
        }
    }

}
