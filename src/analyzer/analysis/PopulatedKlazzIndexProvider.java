package analyzer.analysis;

import com.google.inject.Inject;

public class PopulatedKlazzIndexProvider extends FoldedResultProvider<KlazzIndex> {

    @Inject
    public PopulatedKlazzIndexProvider(VisitorDriver driver,
            AbstractFoldingVisitor<KlazzIndex> visitor) {
        super(driver, visitor);
    }

}
