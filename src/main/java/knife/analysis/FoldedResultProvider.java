package knife.analysis;

import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.Provider;

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
            return driver.analysis(visitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
