package analyzer.analysis;

import com.google.inject.Inject;

public class PopulatedUsageMapProvider extends FoldedResultProvider<UsageMap> {

    @Inject
    public PopulatedUsageMapProvider(VisitorDriver driver,
            AbstractFoldingVisitor<UsageMap> visitor) {
        super(driver, visitor);
    }

}
