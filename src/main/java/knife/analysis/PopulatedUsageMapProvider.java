package knife.analysis;

import com.google.inject.Inject;

/**
 * Guice goo for the {@link UsageMap}, see {@link FoldedResultProvider}.
 */
public class PopulatedUsageMapProvider extends FoldedResultProvider<UsageMap> {

    @Inject
    public PopulatedUsageMapProvider(VisitorDriver driver,
            AbstractFoldingVisitor<UsageMap> visitor) {
        super(driver, visitor);
    }

}
