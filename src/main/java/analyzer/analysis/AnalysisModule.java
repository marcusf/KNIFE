package analyzer.analysis;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

public class AnalysisModule extends AbstractModule {

    @Override
    protected void configure()
    {
            
        bind(UsageMapVisitor.class);
        bind(KlazzIndexVisitor.class);
        
        bind(UsageMap.class)
            .toProvider(PopulatedUsageMapProvider.class);
        
        bind(KlazzIndex.class)
            .toProvider(PopulatedKlazzIndexProvider.class);
        
        bind(new TypeLiteral<AbstractFoldingVisitor<UsageMap>>(){})
            .to(UsageMapVisitor.class);

        bind(new TypeLiteral<AbstractFoldingVisitor<KlazzIndex>>(){})
            .to(KlazzIndexVisitor.class);
        
        configureAnalyses();
    }

    @Provides @UsageMap.New
    UsageMap providesUsageMap() {
        return new UsageMap();
    }
    
    @Provides @KlazzIndex.New
    KlazzIndex providesKlazzIndex() {
        return new KlazzIndex();
    }
    
    private void configureAnalyses()
    {
        for (String analysis: AvailableAnalyses.getAnalyses().keySet()) {
            bind(Analysis.class)
                .annotatedWith(Names.named(analysis))
                .to(AvailableAnalyses.getAnalyses().get(analysis));
        }
    }
    
}
