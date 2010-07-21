package knife.analysis;

import com.google.inject.Inject;

/**
 * Guice goo for the {@link ClassIndex}, see {@link FoldedResultProvider}.
 */
public class PopulatedClassIndexProvider extends FoldedResultProvider<ClassIndex> {

    @Inject
    public PopulatedClassIndexProvider(VisitorDriver driver,
            AbstractFoldingVisitor<ClassIndex> visitor) {
        super(driver, visitor);
    }

}
